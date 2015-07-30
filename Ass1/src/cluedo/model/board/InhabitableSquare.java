package cluedo.model.board;

import cluedo.model.Player;

/**
 *
 * This represents a type of square that is able to contain a character
 *
 * @author Cameron Bryers, Hannah Craighead
 *
 */

public interface InhabitableSquare {

	/**
	 * Indicates whether a player is occuping a square
	 * @return boolean indicating if occupied
	 */

	public boolean isOccupied();

	/**
	 * Gets the player occupying this square
	 * Returns null if no occupant
	 * @return Player at this square
	 */

	public Player getPlayer();

	public void addPlayer(Player p);

}
