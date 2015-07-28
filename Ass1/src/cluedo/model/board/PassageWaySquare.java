package cluedo.model.board;

import cluedo.model.gameObjects.Location.Room;

/**
 * A PassageWaySqaure is a special type of door square that exists only in the corner rooms of the board.
 * They take one move to explore, taking the player to the room in the opposite corner.
 *
 * @author Cameron Bryers, Hannah Craighead
 *
 */

public class PassageWaySquare implements Square{

	private int m_x;
	private int m_y;
	private Room from;
	private Room to;

	public PassageWaySquare(int x, int y, Room from, Room to){
		m_x = x;
		m_y = y;
		this.from = from;
		this.to = to;
	}

	public Room getFrom() {
		return from;
	}

	public Room getTo() {
		return to;
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
	public boolean isOccupied() { // Should never be used, can never be occupied
		return false;
	}

	public String toString(){
		return "p";
	}

}
