package cluedo.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import cluedo.model.board.Board;
import cluedo.model.cards.Card;
import cluedo.model.cards.CharacterCard;
import cluedo.model.cards.RoomCard;
import cluedo.model.cards.WeaponCard;
import cluedo.model.commands.MoveCommand;
import cluedo.model.gameObjects.CluedoCharacter;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;
import cluedo.model.gameObjects.Die;
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
	public static final Point MRS_WHITE_START = new Point(9, 0);
	public static final Point MR_GREEN_START = new Point(14, 0);
	public static final Point MRS_PEACOCK_START = new Point(24, 6);
	public static final Point COLONEL_MUSTARD_START = new Point(0, 17);
	public static final Point MISS_SCARLET_START = new Point(7, 24);
	public static final Point PROFESSOR_PLUM_START = new Point(24, 19);

	public static final boolean DEBUG = false;
	public final int NUM_OF_DICE;

	// The game board
	private Board m_board;

	// Players in the game
	private Set<Player> players;

	private List<Player> playersList;

	// Cards to be distributed to players
	private Set<Card> deck;

	// Cards in the envelope
	private Set<Card> envelope;

	// Dice in the game
	private Set<Die> dice;

	// Current player and next
	private Player current, next;

	// Characters that have been used
	private Set<Suspect> usedSuspects;

	// Current roll
	private int roll;

	public Game() {

		System.out.println("*** Welcome to Cluedo (Pre - Alpha Version) ***");
		System.out.println("*** By Cameron Bryers and Hannah Craighead ***");

		// Initialize the deck and the envelope
		deck = new HashSet<Card>();
		envelope = new HashSet<Card>();
		createDeck();

		// Create players
		setupPlayers();

		// Deal cards
		deal();

		// Load board
		m_board = new Board("cluedo.txt");

		// Create dice
		System.out
				.println("*** How many dice are you playing with? (min = 1, max = 2) ***");

		Scanner reader = new Scanner(System.in);

		// Wait for proper response
		while (!reader.hasNextInt()) {
			System.out.println("*** That is not a valid number of dice ***");
			reader.next();
		}

		NUM_OF_DICE = reader.nextInt();

		// TODO start the game
		startGame();
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

		// Draw the board
		m_board.drawBoard();

		boolean gameOver = false;
		Scanner reader = new Scanner(System.in);

		while (!gameOver) {
			for (Player p : players) {

				System.out.println("*** " + p.getName() + "it's your turn ***");

				// Roll the die/ dice
				roll = randomNumber(1 * NUM_OF_DICE, 6 * NUM_OF_DICE);

				current = p;
				next = getNextPlayer();

				// Check for game over
				if (players.size() == 1) {
					gameOver = true;
					System.out.println("*** Congragulations " + p.getName()
							+ " You won! ***");
					break;
				}

				// TODO parse commands

				/**
				 * Move
				 * if (inRoom)
				 *  Suggestion
				 * else if (inSwimmingPool)
				 *  Accusation
				 */

				// Move Command
				boolean isValid = false;

				// Wait for a valid response
				while (!isValid) {

					MoveCommand move = new MoveCommand(reader, this);

					if (getBoard().isValid(current, move.getX(), move.getY(),
							roll)) {
						move.execute(this);
						isValid = true;
					}
					else
						System.out.println("*** Sorry that is not a valid move, try again ***");
				}

				// Suggestion Command

				// Accusation Command
				// Update board
				m_board.drawBoard();
			}
		}
	}

	/**
	 * Creates the game deck and fills the envelope
	 */
	private void createDeck() {

		// Used to select the murderer, murder weapon and crime scene.
		int criminal = randomNumber(0, 5);

		// Create the characters
		for (int i = 0; i < Suspect.values().length; i++) {

			CluedoCharacter character;

			// If the character is the murderer
			if (criminal == i)
				character = new CluedoCharacter(true, Suspect.values()[i]);
			else
				character = new CluedoCharacter(false, Suspect.values()[i]);

			// Create the card
			CharacterCard card = new CharacterCard(character);

			// Add card to the deck
			if (character.isConnected())
				envelope.add(card);
			else
				deck.add(card);
		}

		criminal = randomNumber(0, 8);

		// Create the rooms (minus the swimming pool)
		for (int i = 0; i < Room.values().length - 1; i++) {

			Location room;

			// If the room is the crime scene
			if (criminal == i)
				room = new Location(true, Room.values()[i]);
			else
				room = new Location(false, Room.values()[i]);

			// Create the card
			RoomCard card = new RoomCard(room);

			// Add the card to the deck
			if (room.isConnected())
				envelope.add(card);
			else
				deck.add(card);
		}

		criminal = randomNumber(0, 5);

		// Create the weapons
		for (int i = 0; i < WeaponType.values().length; i++) {

			Weapon weapon;

			// If the weapon is the murder weapon
			if (criminal == i)
				weapon = new Weapon(true, WeaponType.values()[i]);
			else
				weapon = new Weapon(false, WeaponType.values()[i]);

			// Create the card
			WeaponCard card = new WeaponCard(weapon);

			if (weapon.isConnected())
				envelope.add(card);
			else
				deck.add(card);
		}
	}

	/**
	 * Initialize the game, players etc.
	 */
	private void setupPlayers() {

		players = new HashSet<Player>();
		playersList = new ArrayList<Player>();
		usedSuspects = new HashSet<Suspect>();

		// Create a scanner
		Scanner reader = new Scanner(System.in);

		// Number of players (min = 3, max = 6)
		int numOfPlayers = 0;

		// Get the number of players first
		do {
			System.out.println("*** How many players? (min = 3, max = 6) ***");

			// Wait for a proper response
			while (!reader.hasNextInt()) {
				System.out.println("*** Please enter an integer you scrub ***");
				reader.next();
			}

			numOfPlayers = reader.nextInt();

			// Error message
			if (numOfPlayers < 3 || numOfPlayers > 6)
				System.out
						.println("*** That is not a valid number of players ***");

		} while (numOfPlayers < 3 || numOfPlayers > 6);

		// Enter player details
		for (int i = 0; i < numOfPlayers; i++) {

			boolean isValidName;
			String name;

			do {

				isValidName = true;

				// Get name
				System.out.println("*** Player " + (i + 1)
						+ " please enter a name ***");
				name = reader.next();

				// Check if name is valid
				for (Player p : players)
					if (p.getName().equalsIgnoreCase(name)) {
						System.out
								.println("*** Name is already being used! ***");
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
				System.out.println("*** " + name
						+ " please choose a character ***\n");
				for (int j = 0; j < Suspect.values().length; j++)
					if (!usedSuspects.contains(Suspect.values()[j]))
						System.out.println(Suspect.values()[j].toString()
								+ ": " + (j + 1));

				// Wait for a proper response
				while (!reader.hasNextInt()) {
					System.out
							.println("*** Please enter integer you scrub ***");
					reader.next();
				}

				int character = reader.nextInt();

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
					System.out
							.println("*** Character is not valid or is already taken ***");
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
		reader.close();
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
	 * Move a player given a position and a roll.
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

	public boolean move(int dx, int dy) {
		if (!m_board.isValid(current, dx, dy, roll))
			return false;

		current.move(dx, dy);
		return true;
	}

	/**
	 * Generate a random number between a given min and max. Used to choose the
	 * murderer, murder weapon and crime scene.
	 *
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomNumber(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}

	public final Set<Card> getDeck() {
		return deck;
	}

	public final Set<Card> getEnvelope() {
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

			for (Player p : cluedo.players)
				for (Card c : p.getHand())
					System.out.println(c.getObject().getName());
		}
	}

}
