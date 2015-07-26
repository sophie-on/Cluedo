package cluedo.model.cards;

/**
 * Class that represents the character card in the game.
 * 
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class CharacterCard implements Card {

	// Character that the card is associated with.
	private Character m_character;

	public CharacterCard(Character character) {
		this.m_character = character;
	}

	public Character getCharacter() {
		return m_character;
	}
}
