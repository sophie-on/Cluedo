package cluedo.model.board;
import cluedo.model.Player;
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

public class RoomSquare implements Square, InhabitableSquare{

	private int m_x;
	private int m_y;
	private Room r;
	private Player p;
	private boolean isOccupied;


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
		return p != null;
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
			return "s";
		case KITCHEN:
			return "K";
		case CONSERVATORY:
			return "C";
		case SWIMMING_POOL:
			return "x";
		}
		return "";
	}

	@Override
	public Player getPlayer() {
		return p;
	}

	@Override
	public void addPlayer(Player p) {
		this.p = p;
	}

}
