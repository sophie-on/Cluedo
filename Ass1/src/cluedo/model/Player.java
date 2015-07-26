package cluedo.model;

import java.util.Set;

import cluedo.model.cards.Card;
import cluedo.model.gameObjects.CluedoCharacter;

/**
 * Class that represents the player
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class Player {

	// Cards in the players hand
	private Set<Card> hand;
	
	// Player's checklist
	// private CheckList checkList;
	
	// Player's character token
	private CluedoCharacter m_character;
	
	// Player's position on the board
	int m_x, m_y;
	
	public Player(Set<Card> hand, int start_x, int start_y) {
		this.hand = hand;
		this.m_x = start_x;
		this.m_y = start_y;
	}
	
	public void move(int x, int y) {
		m_x += x;
		m_y += y;
	}
}
