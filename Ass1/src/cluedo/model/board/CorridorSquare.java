package cluedo.model.board;

/**
 * A CorridorSquare is a type of square on the board that is a tile
 * that players can move on. They exist as the main part of the board
 * as pathways between rooms
 * 
 * @author Cameron Bryers, Hannah Craighead
 *
 */

public class CorridorSquare implements Square{

	private int m_x;
	private int m_y;
	private boolean isOccupied;
	//private Player p;
	
	public CorridorSquare(int x, int y){
		m_x = x;
		m_y = y;
	}
	
	/**
	 * Returns a boolean to indicate whether this square is vacant or taken by another 
	 * character token
	 * 
	 * @return boolean indicating occupation
	 */
	
	@Override
	public boolean isOccupied(){
		return isOccupied;		
	}
	
	@Override
	public int getX() {
		return m_x;
	}
	
	@Override
	public int getY() {
		return m_y;
	}
	
	/**
	 * Returns the player that resides on this square.
	 * In the case it is vacant, null will be returned
	 * 
	 * @return Player p on this square
	 */
	
//	private Player p(){
//		return p;		
//	}
	
}
