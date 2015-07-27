package cluedo.model.board;

import cluedo.model.gameObjects.Location;

/**
 * A DoorSquare is essentially a special type of RoomSquare.
 * It is an entry point to the room it is a part, a room may not be entered via 
 * a RoomSquare. Like any other tile, it takes one move to move to it. However,
 * once a piece does this, it is considered to be in the room and not occuping this tile.
 * 
 * A player must also exit using a DoorSquare. A special DoorSquare exists between corner
 * rooms that lead out to another DoorSquare in the opposite corner room as opposed to a
 * CorridorSquare.
 * 
 * @author Cameron Bryers, Hannah Craighead
 *
 */

public class DoorSquare implements Square{	

	private int m_x;
	private int m_y;
	private Location l;

	public DoorSquare(int x, int y, Location l){
		m_x = x;
		m_y = y;
		this.l = l;
	}

	@Override
	public int getX() {
		return m_x;
	}

	@Override
	public int getY() {
		return m_y;
	}

	public Location getRoom() {
		return l;
	}

	@Override
	public boolean isOccupied() {
		return false;
	}


}
