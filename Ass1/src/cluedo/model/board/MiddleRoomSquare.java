package cluedo.model.board;
/**
 * This type of square is used to represent the middle room of the cluedo board.
 * This is to differentiate it from a regular corridor square, but it cannot
 * be entered like ordinary rooms
 *
 * @author Cameron Bryers, Hannah Craighead
 *
 */


public class MiddleRoomSquare implements Square{

	private int m_x;
	private int m_y;

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

	@Override
	public boolean isOccupied() { // should never be occupied
		return false;
	}

	public String toString(){
		return "X";
	}

}
