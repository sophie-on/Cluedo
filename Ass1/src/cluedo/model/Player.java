package cluedo.model;

import java.awt.Point;
import java.util.Set;

import cluedo.model.cards.Card;
import cluedo.model.gameObjects.CluedoCharacter;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;
import cluedo.model.gameObjects.Location.Room;
import cluedo.model.gameObjects.Weapon;

/**
 * Class that represents the player
 *
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class Player {

	// Cards in the players hand
	private Set<Card> hand;

	// Player's checklist
	// private CheckList checkList;

	// Player's character token
	private final Suspect m_character;

	// Player's gamertag
	private final String m_name;

	// Player's position on the board
	private int m_x, m_y;

	public Player(Set<Card> hand, String name, Suspect character, int start_x, int start_y) {
		this.hand = hand;
		this.m_name = name;
		this.m_character = character;
		this.m_x = start_x;
		this.m_y = start_y;
	}

	public Player(String name, Suspect character, Point start) {
		this.m_name = name;
		this.m_character = character;
		this.m_x = start.x;
		this.m_y = start.y;
	}

	public void move(int x, int y) {
		m_x = x;
		m_y = y;
	}

	public void setHand(Set<Card> hand) {
		this.hand = hand;
	}

	public int getX() {
		return m_x;
	}

	public int getY() {
		return m_y;
	}

	public String getName() {
		return m_name;
	}

	public final Suspect getCharacter() {
		return m_character;
	}

	public final Set<Card> getHand() {
		return hand;
	}

}
