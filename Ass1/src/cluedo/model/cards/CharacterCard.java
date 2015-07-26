package cluedo.model.cards;

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

	public CharacterCard(CluedoCharacter character) {
		this.m_character = character;
	}

	@Override
	public GameObject getObject() {
		return m_character;
	}
}
