package cluedo.model.cards;

import cluedo.model.Player;
import cluedo.model.gameObjects.CluedoCharacter;
import cluedo.model.gameObjects.GameObject;

/**
 * Class that represents the character card in the game.
 * 
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class CharacterCard implements Card {

	// Character that the card is associated with.
	private CluedoCharacter m_character;
	
	// Player that has this card
	private Player m_player;

	public CharacterCard(CluedoCharacter character) {
		this.m_character = character;
	}

	@Override
	public GameObject getObject() {
		return m_character;
	}

	@Override
	public void dealToPlayer(Player player) {
		m_player = player;
	}

	@Override
	public Player getPlayer() {
		return m_player;
	}

	@Override
	public boolean isInHand() {
		return m_player != null;
	}
	
	
}
