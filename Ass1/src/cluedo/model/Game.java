package cluedo.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import cluedo.model.board.Board;
import cluedo.model.board.DoorSquare;
import cluedo.model.board.InhabitableSquare;
import cluedo.model.board.RoomSquare;
import cluedo.model.board.Square;
import cluedo.model.cards.Card;
import cluedo.model.cards.CharacterCard;
import cluedo.model.cards.RoomCard;
import cluedo.model.cards.WeaponCard;
import cluedo.model.commands.AccuseCommand;
import cluedo.model.commands.MoveCommand;
import cluedo.model.commands.SuggestCommand;
import cluedo.model.gameObjects.CluedoCharacter;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;
import cluedo.model.gameObjects.Location;
import cluedo.model.gameObjects.Location.Room;
import cluedo.model.gameObjects.Weapon;
import cluedo.model.gameObjects.Weapon.WeaponType;

/**
 * Class that represents the game itself
 *
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class Game {

	// Starting positions for the characters
	public static final Point MRS_WHITE_START = new Point(0, 9);
	public static final Point MR_GREEN_START = new Point(0, 14);
	public static final Point MRS_PEACOCK_START = new Point(6, 24);
	public static final Point COLONEL_MUSTARD_START = new Point(17, 0);
	public static final Point MISS_SCARLET_START = new Point(24, 7);
	public static final Point PROFESSOR_PLUM_START = new Point(19, 24);

	public static final boolean DEBUG = false;
	public static int NUM_OF_DICE;

	// The game board
	private Board m_board;

	// Players in the game
	private Set<Player> players;

	private List<Player> playersList;

	// Cards to be distributed to players
	private final List<Card> deck;

	// Cards in the envelope
	private final List<Card> envelope;

	// Current player and next
	private Player current, next;

	// Characters that have been used
	private Set<Suspect> usedSuspects;

	// Current roll
	private int roll;

	private final Scanner READER;

	public Game() {

		READER = new Scanner(System.in);

		System.out.println("*** Welcome to Cluedo (Pre - Alpha Version) ***");
		System.out.println("*** By Cameron Bryers and Hannah Craighead ***");
		System.out.println(
				"\n*** Please note that since this version of the game is played a single screen, \n you should only look at the screen when it's your turn ***");

		// Initialize the deck and the envelope
		List<List<Card>> cards = createDeck();
		deck = cards.get(0);
		envelope = cards.get(1);

		// Create players
		setupPlayers();

		// Deal cards
		deal();

		// Load board
		m_board = new Board("cluedo.txt");
		m_board.addPlayers(players);

		// Set up dice
		setupDice();

		// NUM_OF_DICE = 1;

		// TODO start the game
		startGame();

		READER.close();
	}

	private void setupDice() {

		// Scanner reader = new Scanner(System.in);

		// Create dice
		System.out.println("*** How many dice are you playing with? (min = 1, max = 2) ***");

		// Wait for proper response
		while (!READER.hasNextInt()) {
			System.out.println("*** That is not a valid number of dice ***");
			READER.nextLine();
		}

		NUM_OF_DICE = READER.nextInt();

		// READER.close();
	}

	/**
	 * Start the actual game
	 */
	private void startGame() {

		/**
		 * loop until game is over: for each player: ask for a command validate
		 * command check if player has lost update board
		 *
		 */

		boolean gameOver = false;
		// Scanner reader = new Scanner(System.in);

		while (!gameOver) {

			// Wipe the console
			clearConsole();

			// Draw the board
			m_board.drawBoard();

			for (Player p : playersList) {

				// Check for game over
				if (players.size() == 1) {
					gameOver = true;
					System.out.println("*** Congragulations " + p.getName() + " You won! ***");
					break;
				}

				// Wait for player to be ready
				while (true) {

					// System.out.
					System.out.println("\n*** " + p.getName()
							+ " are you ready? (Press any key (character or int) then ENTER) ***");

					if (READER.hasNext()) {
						READER.next();
						break;
					}
				}

				READER.nextLine();

				// Display name
				System.out.println("\n*** " + p.getName() + " it's your turn to move ***");

				// Display the player's cards
				System.out.println("*** Your cards ***\n");
				for (Card c : p.getHand())
					System.out.println(c.getObject().getName());

				System.out.println("\n*** Your character is " + p.getCharacter().toMiniString() + " ***");

				// Roll the die/ dice
				roll = randomNumber(1 * NUM_OF_DICE, 6 * NUM_OF_DICE);

				System.out.println("*** You rolled a " + roll + " ***");

				current = p;
				next = getNextPlayer();

				/**
				 * Move if (inRoom) Suggestion else if (inSwimmingPool)
				 * Accusation
				 */

				// Move Command

				// Wait for a valid response
				while (true) {

					if (DEBUG)
						System.out.println("curX: " + p.getX() + " curY: " + p.getY());

					MoveCommand move = new MoveCommand(READER, this);

					if (getBoard().isValid(current, move.getX(), move.getY(), roll)) {

						move.execute(this);
						if (DEBUG)
							System.out.println("newX: " + p.getX() + "newY: " + p.getY());
						break;
					} else
						System.out.println("*** Sorry that is not a valid move, try again ***");
				}

				// Update board
				m_board.drawBoard();

				// Do suggestion or accusation
				if (m_board.squareAt(p.getX(), p.getY()) instanceof RoomSquare) {

					RoomSquare room = (RoomSquare) m_board.squareAt(p.getX(), p.getY());

					// Accusation
					if (room.getRoom().equals(Room.SWIMMING_POOL)) {

						System.out.println("\n*** Accusation ***");

						AccuseCommand accuse = new AccuseCommand(this, READER);

						// Wrong accusation
						if (!checkAccusation(accuse)) {
							System.out.println("*** Sorry that accusation was wrong ***");
							playersList.remove(p);
							Room.SWIMMING_POOL.removePlayer(p);

							// Update board
							m_board.drawBoard();
							break;
						}

						// Correct accusation
						else {
							System.out.println("*** That accusation was correct! You won ***");
							endGame();
						}

					}

					// Suggestion
					else {

						System.out.println("\n*** Suggestion ***");

						SuggestCommand suggest = new SuggestCommand(this, READER);

						// Go through the other players, if a player has at
						// least one of the cards the suggestion can not be
						// refuted. Otherwise refute it
						int refutes = 0;
						for (Player c : players) {
							if (!c.equals(current)) {
								if (checkSuggestion(suggest, c)) {
									System.out.println("*** " + c.getName() + " can refute this suggestion ***");
									refutes++;
								}
							}
						}
						if (refutes == 0) {
							System.out.println("*** No one could refute your suggestion ***");
						}
					}

					System.out.println();
					m_board.drawBoard();
				}
			}
		}
	}

	/**
	 * Creates the game deck and envelope
	 * 
	 * @return
	 */
	public static List<List<Card>> createDeck() {

		List<Card> suspects = new ArrayList<Card>();
		List<Card> rooms = new ArrayList<Card>();
		List<Card> weapons = new ArrayList<Card>();

		// Add suspects, rooms and weapons.
		for (Suspect s : Suspect.values())
			suspects.add(new CharacterCard(new CluedoCharacter(s)));

		for (int i = 0; i < Room.values().length - 1; i++)
			rooms.add(new RoomCard(new Location(Room.values()[i])));

		for (WeaponType w : WeaponType.values())
			weapons.add(new WeaponCard(new Weapon(w)));

		List<Card> envelope = new ArrayList<Card>();

		// Generate random criminals
		envelope.add(suspects.remove(randomNumber(0, 5)));
		envelope.add(rooms.remove(randomNumber(0, 8)));
		envelope.add(weapons.remove(randomNumber(0, 5)));

		// Add remaining cards to the deck
		List<Card> deck = new ArrayList<Card>();
		deck.addAll(suspects);
		deck.addAll(rooms);
		deck.addAll(weapons);

		Collections.shuffle(deck);

		List<List<Card>> cards = new ArrayList<List<Card>>();
		cards.add(deck);
		cards.add(envelope);

		return cards;
	}

	/**
	 * Initialize the game, players etc.
	 */
	private void setupPlayers() {

		players = new HashSet<Player>();
		playersList = new ArrayList<Player>();
		usedSuspects = new HashSet<Suspect>();

		// Create a scanner
		// Scanner READER = new Scanner(System.in);

		// Number of players (min = 3, max = 6)
		int numOfPlayers = 0;

		// Get the number of players first
		do {
			System.out.println("\n*** How many players? (min = 3, max = 6) ***");

			// Wait for a proper response
			while (!READER.hasNextInt()) {
				System.out.println("*** Please enter an integer you scrub ***");
				READER.nextLine();
			}

			numOfPlayers = READER.nextInt();
			READER.nextLine();

			// Error message
			if (numOfPlayers < 3 || numOfPlayers > 6)
				System.out.println("*** That is not a valid number of players ***");

		} while (numOfPlayers < 3 || numOfPlayers > 6);

		// Enter player details
		for (int i = 0; i < numOfPlayers; i++) {

			boolean isValidName;
			String name;

			do {

				isValidName = true;

				// Get name
				System.out.println("*** Player " + (i + 1) + " please enter a name ***");
				name = READER.nextLine();

				// Check if name is valid
				for (Player p : players)
					if (p.getName().equalsIgnoreCase(name)) {
						System.out.println("*** Name is already being used! ***");
						isValidName = false;
						break;
					}

			} while (!isValidName);

			boolean isValidCharacter;
			Suspect suspect;

			// Get character
			do {
				isValidCharacter = true;
				suspect = null;

				// Print out characters
				System.out.println("*** " + name + " please choose a character ***\n");
				for (int j = 0; j < Suspect.values().length; j++)
					if (!usedSuspects.contains(Suspect.values()[j]))
						System.out.println(Suspect.values()[j].toString() + ": " + (j + 1));

				// Wait for a proper response
				while (!READER.hasNextInt()) {
					System.out.println("*** Please enter integer you scrub ***");
					READER.nextLine();
				}

				int character = READER.nextInt();
				READER.nextLine();

				switch (character) {
				case 1:
					suspect = Suspect.MISS_SCARLET;
					break;
				case 2:
					suspect = Suspect.COLONEL_MUSTARD;
					break;
				case 3:
					suspect = Suspect.MRS_WHITE;
					break;
				case 4:
					suspect = Suspect.THE_REVEREND_GREEN;
					break;
				case 5:
					suspect = Suspect.MRS_PEACOCK;
					break;
				case 6:
					suspect = Suspect.PROFESSOR_PLUM;
					break;
				default:
					suspect = null;
					break;
				}

				// Check if character is valid
				if (suspect == null)
					isValidCharacter = false;

				for (Player p : players)
					if (p.getCharacter().equals(suspect)) {
						isValidCharacter = false;
						break;
					}

				if (!isValidCharacter)
					System.out.println("*** Character is not valid or is already taken ***");
				else
					break;

			} while (!isValidCharacter);

			// Add suspects to this set so no other player can choose it
			usedSuspects.add(suspect);

			Point p;

			// Find starting point
			switch (suspect) {
			case COLONEL_MUSTARD:
				p = Game.COLONEL_MUSTARD_START;
				break;
			case MISS_SCARLET:
				p = Game.MISS_SCARLET_START;
				break;
			case MRS_PEACOCK:
				p = Game.MRS_PEACOCK_START;
				break;
			case MRS_WHITE:
				p = Game.MRS_WHITE_START;
				break;
			case PROFESSOR_PLUM:
				p = Game.PROFESSOR_PLUM_START;
				break;
			case THE_REVEREND_GREEN:
				p = Game.MR_GREEN_START;
				break;
			default:
				p = null;
				break;
			}
			Player player = new Player(name, suspect, p);
			// players.add(player);
			// playersList.add(player);

			if (players.add(player))
				playersList.add(player);
		}
		// READER.close();
	}

	/**
	 * Deal cards to players
	 */
	private void deal() {

		// Number of cards in each player's hand
		int numOfCards = deck.size() / players.size();

		Iterator<Card> iter = deck.iterator();

		for (Player player : players) {

			// Create a hand
			Set<Card> hand = new HashSet<Card>();

			// Add cards to the hand
			for (int j = 0; j < numOfCards; j++)
				hand.add(iter.next());

			// Give player the cards
			player.setHand(hand);
		}

	}

	/**
	 * Move a player given a position and a roll. *NOT USED*
	 *
	 * @param player
	 * @param dx
	 * @param dy
	 * @param roll
	 * @return true if the move was valid
	 */
	public boolean move(Player player, int dx, int dy, int roll) {
		if (!m_board.isValid(player, dx, dy, roll))
			return false;

		player.move(dx, dy);
		return true;
	}

	/**
	 * Move a player given a new position, *USE THIS ONE*.
	 *
	 * @param newX
	 * @param newY
	 */
	public void move(int newX, int newY) {

		// if (!m_board.isValid(current, dx, dy, roll))
		// return false;

		// Find the original square the player was at
		Square s = m_board.squareAt(current.getX(), current.getY());

		// Set the player at that square to null
		((InhabitableSquare) s).addPlayer(null);

		// Move the player's position
		current.move(newX, newY);

		// Get new Square
		s = m_board.squareAt(current.getX(), current.getY());

		// Lets player enter a room if landed on a door square
		if (s instanceof DoorSquare) {
			((DoorSquare) s).getRoom().addPlayer(current);
		}

		// Find the square that the player lands on and add the player to it
		else {
			((InhabitableSquare) s).addPlayer(current);
		}
	}

	/**
	 * Generate a random number between a given min and max. Used to choose the
	 * murderer, murder weapon and crime scene. It is also used to get a
	 * player's roll
	 *
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomNumber(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}

	public final List<Card> getDeck() {
		return deck;
	}

	public final List<Card> getEnvelope() {
		return envelope;
	}

	public final Board getBoard() {
		return this.m_board;
	}

	public final int getRoll() {
		return this.roll;
	}

	/**
	 *
	 * @return the next player to play
	 */
	public final Player getNextPlayer() {
		int curIndex = playersList.indexOf(current);
		return playersList.get((curIndex + 1) % playersList.size());
	}

	public final Player getCurrent() {
		return current;
	}

	public final List<DoorSquare> getRoomsInReach() {
		return m_board.roomsInReach(current, roll);
	}

	public final List<DoorSquare> getJumpLocations() {
		return m_board.getJumpLocations(current, getRoll());
	}

	/**
	 * Check if the accusation was correct
	 *
	 * @param accuse
	 * @return
	 */
	private boolean checkAccusation(SuggestCommand accuse) {

		// Check if the accusation is correct
		for (Card c : envelope) {

			if (c instanceof CharacterCard) {
				if (!c.getObject().getName().equals(accuse.getSuspect().toString()))
					return false;
			} else if (c instanceof RoomCard) {
				if (!c.getObject().getName().equals(accuse.getRoom().toString()))
					return false;
			} else if (c instanceof WeaponCard) {
				if (!c.getObject().getName().equals(accuse.getWeapon().toString()))
					return false;
			}
		}
		return true;
	}

	/**
	 * Check if a player can refute a suggestion
	 *
	 * @param accuse
	 * @return
	 */
	private boolean checkSuggestion(SuggestCommand suggest, Player p) {

		// Check if the accusation is correct
		for (Card c : p.getHand()) {
			if (c instanceof CharacterCard) {
				if (c.getObject().getName().equals(suggest.getSuspect().toString()))
					return true;
			} else if (c instanceof RoomCard) {
				if (c.getObject().getName().equals(suggest.getRoom().toString()))
					return true;
			} else if (c instanceof WeaponCard) {
				if (c.getObject().getName().equals(suggest.getWeapon().toString()))
					return true;
			}
		}

		return false;
	}

	/**
	 * Clears the console, used to prevent players from cheating
	 */
	public final static void clearConsole() {

		// If you're running the game on the system console use this code
		// try {
		// final String os = System.getProperty("os.name");
		//
		// if (os.contains("Windows")) {
		// Runtime.getRuntime().exec("cls");
		// } else {
		// Runtime.getRuntime().exec("clear");
		// }
		// } catch (final Exception e) {
		// // Handle any exceptions.
		// }

		// If your running it in eclipse use this
		for (int i = 0; i < 100; i++)
			System.out.println();
	}

	/**
	 * Ends game
	 */
	public void endGame() {
		System.out.println("*** Game Over ***");
		System.exit(0);
	}

	public static void main(String args[]) {

		Game cluedo = new Game();

		if (DEBUG) {

			System.out.println("*CARDS IN DECK*\n");

			for (Card c : cluedo.getDeck())
				System.out.println(c.getObject().getName());

			System.out.println("\n*CARDS IN ENVELOPE*\n");

			for (Card c : cluedo.getEnvelope())
				System.out.println(c.getObject().getName());

			System.out.println("\n*CARDS IN PLAYER HANDS*\n");

			for (Player p : cluedo.playersList)
				for (Card c : p.getHand())
					System.out.println(c.getObject().getName());
		}
	}

}
