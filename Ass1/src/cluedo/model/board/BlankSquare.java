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

	public String toString(){
		return "#";
	}

}
