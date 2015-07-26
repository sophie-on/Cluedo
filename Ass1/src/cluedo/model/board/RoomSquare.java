package cluedo.model.board;
import cluedo.model.gameObjects.Location;

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
	private Location l;
	
	public RoomSquare(int x, int y, Location l){
		this.m_x = x;
		this.m_y = y;
		this.l = l;
	}
	
	/**
	 * Returns the room that this square is a part of
	 * 
	 * @return Location
	 */
	public Location getRoom(){
		return l;		
	}

	@Override
	public int getX() {
		return m_x;
	}

	@Override
	public int getY() {
		return m_y;
	}
	
}
