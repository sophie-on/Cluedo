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
	
	public boolean isOccupied();
}
