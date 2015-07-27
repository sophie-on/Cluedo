package cluedo.model.board;

public class StarterSquare implements Square{

	private Character c;
	private int m_x;
	private int m_y;
	private boolean isOcuppied;

	public StarterSquare(int x, int y, Character c) {
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
	public boolean isOccupied() {
		return isOcuppied;
	}

}
