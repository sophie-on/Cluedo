package cluedo.model.gameObjects;

/**
 * Class that represents the characters in the game.
 * 
 * @author Cameron Bryers, Hannah Craighead.
 * 
 */
public class CluedoCharacter extends GameObject {

	/**
	 * List of possible suspects.
	 * 
	 * @author Cameron Bryers, Hannah Craighead.
	 *
	 */
	public enum Suspect {
		MISS_SCARLET, COLONEL_MUSTARD, MRS_WHITE, THE_REVEREND_GREEN, MRS_PEACOCK, PROFESSOR_PLUM;
	}

	private Suspect m_suspect;

	public CluedoCharacter(boolean isMurderer, Suspect suspect) {
		super(isMurderer);
		this.m_suspect = suspect;
	}

	@Override
	public String getName() {
		switch (m_suspect) {
		case COLONEL_MUSTARD:
			return "Colonel Mustard";
		case MISS_SCARLET:
			return "Miss Scarlet";
		case MRS_PEACOCK:
			return "Mrs. Peacock";
		case MRS_WHITE:
			return "Mrs. White";
		case PROFESSOR_PLUM:
			return "Professor Plum";
		case THE_REVEREND_GREEN:
			return "The Reverend Green";
		default:
			return null;
		}
	}
}
