package cluedo.model.board;
import cluedo.model.gameObjects.Location;
import cluedo.model.gameObjects.Location.Room;

/**
 * RoomSquares are used to represent tiles of the board that
 * belong to rooms. However, once inside a room, it is not possible
 * navigate the tiles within a room
 *
 * @author Cameron Bryers, Hannah Craighead
 *
 */

public class RoomSquare implements Square{

	private int m_x;
	private int m_y;
	private Room r;

	public RoomSquare(int x, int y, Room room){
		this.m_x = x;
		this.m_y = y;
		this.r = room;
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
		return false;
	}

	/**
	 * Returns the room that this square is a part of
	 *
	 * @return Room r
	 */


	public Room getRoom(){
		return r;
	}

	public String toString(){
		switch(r){
		case BALL_ROOM:
			return "b";
		case BILLIARD_ROOM:
			return "B";
		case HALL:
			return "h";
		case LOUNGE:
			return "L";
		case LIBRARY:
			return "l";
		case DINING_ROOM:
			return "D";
		case STUDY:
			return "S";
		case KITCHEN:
			return "K";
		case CONSERVATORY:
			return "C";
		}
		return "";
	}

}
