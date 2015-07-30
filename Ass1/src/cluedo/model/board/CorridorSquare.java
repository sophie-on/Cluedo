package cluedo.model.board;

import cluedo.model.Player;

/**
 * A CorridorSquare is a type of square on the board that is a tile
 * that players can move on. They exist as the main part of the board
 * as pathways between rooms
 *
 * @author Cameron Bryers, Hannah Craighead
 *
 */

public class CorridorSquare implements Square, InhabitableSquare{

	private int m_x;
	private int m_y;
	private boolean isOccupied;
	private Player p;

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

	public String toString(){
		return "_";
	}




	@Override
	public Player getPlayer() {
		return p;
	}

}
