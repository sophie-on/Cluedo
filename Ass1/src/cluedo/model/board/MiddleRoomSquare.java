package cluedo.model.board;

import cluedo.model.Player;

/**
 * This type of square is used to represent the middle room of the cluedo board.
 * This is to differentiate it from a regular corridor square, but it cannot
 * be entered like ordinary rooms
 *
 * @author Cameron Bryers, Hannah Craighead
 *
 */


public class MiddleRoomSquare implements Square, InhabitableSquare{

	private int m_x;
	private int m_y;
	private boolean isOccupied;
	private Player p;

	public MiddleRoomSquare(int x, int y){
		m_x = x;
		m_y = y;
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
		return "X";
	}

	@Override
	public boolean isOccupied() {
		return isOccupied;
	}

	@Override
	public Player getPlayer() {
		return p;
	}

}
