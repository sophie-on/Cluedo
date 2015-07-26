package cluedo.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import cluedo.model.board.Board;
import cluedo.model.cards.Card;
import cluedo.model.cards.CharacterCard;
import cluedo.model.cards.RoomCard;
import cluedo.model.cards.WeaponCard;
import cluedo.model.gameObjects.*;
import cluedo.model.gameObjects.CluedoCharacter;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;
import cluedo.model.gameObjects.Location.Room;
import cluedo.model.gameObjects.Weapon.WeaponType;

/**
 * Class that represents the game itself
 * 
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class Game {

	private static final boolean DEBUG = true;

	// The game board
	private Board m_board;

	// Players in the game
	// private Set<Player> players;

	// Cards to be distributed to players
	private Set<Card> deck;

	// Cards in the envelope
	private Set<Card> envelope;

	// Dice in the game
	private Set<Die> dice;

	public Game() {

		// Initialize the deck and the envelope
		deck = new HashSet<Card>();
		envelope = new HashSet<Card>();
		createDeck();
		
		// Create a scanner
		Scanner reader = new Scanner(System.in);

		// TODO Create players

		// TODO Load board

		// Create dice
		dice = new HashSet<Die>();
		System.out.println("How many dice are you playing with?");
		// TODO read number of dice
	}

	/**
	 * Creates the game deck and fills the envelope
	 */
	public void createDeck() {

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

		// Create the rooms
		for (int i = 0; i < Room.values().length; i++) {

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

	public static void main(String args[]) {
		
		Game cluedo = new Game();

		if (DEBUG) {

			System.out.println("*CARDS IN DECK*\n");

			for (Card c : cluedo.getDeck())
				System.out.println(c.getObject().getName());

			System.out.println("\n*CARDS IN ENVELOPE*\n");

			for (Card c : cluedo.getEnvelope())
				System.out.println(c.getObject().getName());
		}
	}

}
