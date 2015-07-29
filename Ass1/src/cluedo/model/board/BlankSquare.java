package cluedo.model.board;

/**
 * Blank Square used for decoration of the board
 * @author craighhann
 *
 */

public class BlankSquare implements Square{

	private int m_x;
	private int m_y;

	public BlankSquare(int x, int y){
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

	@Override
	public boolean isOccupied() { // should never be occupied
		return false;
	}

	public String toString(){
		return " ";
	}

}
