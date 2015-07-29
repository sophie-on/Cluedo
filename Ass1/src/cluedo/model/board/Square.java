package cluedo.model.board;

/**
 * This interface is used to encapsulate all the different kinds of Squares
 * that a Cluedo board may have
 *
 * @author Cameron Bryers, Hannah Craighead
 *
 */

public interface Square {


	/**
	 * Gets x coordinate of this square
	 * @return x coordinate
	 */

	public int getX();

	/**
	 * Gets y coordinate of this square
	 * @return y coordinate
	 */

	public int getY() ;

	/**
	 * Indicates whether a player is occuping a square
	 * @return boolean indicating if occupied
	 */

	public boolean isOccupied();

	public String toString();
}
