/**
 *
 */
package cluedo.model.gameObjects;

import java.util.HashSet;
import java.util.Set;

import cluedo.model.Player;
import cluedo.model.board.RoomSquare;

/**
 * Class that represents a room on the board.
 *
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class Location extends GameObject {

	/**
	 * List of possible crime scenes.
	 *
	 * @author Cameron Bryers, Hannah Craighead.
	 *
	 */
	public enum Room {
		KITCHEN, BALL_ROOM, CONSERVATORY, DINING_ROOM, BILLIARD_ROOM, LIBRARY, LOUNGE, HALL, STUDY, SWIMMING_POOL;

		//RoomSquares that belong to this room
		private Set<RoomSquare> squares;

		// Characters that are in this room.
		private Set<Player> characters;

		private Room(){
			squares = new HashSet<RoomSquare>();
			characters = new HashSet<Player>();
		}

		public void addSquare(RoomSquare r){
			squares.add(r);
		}

		public void addPlayerToRoom(Player p){
			for(RoomSquare r: squares){
				if(!r.isOccupied()){
					r.addPlayer(p);
					p.move(r.getX(), r.getY());
					return;
				}
			}
		}

		public void addPlayer(Player p){
			characters.add(p);
			addPlayerToRoom(p);
		}
	}

	private Room m_room;

	// Weapons that are in this room.
	private Set<Weapon> weapons;





	public Location(boolean isCrimeScene, Room room) {
		super(isCrimeScene);
		this.m_room = room;

		weapons = new HashSet<Weapon>();
	}

	/**
	 * Add a weapon to the room.
	 *
	 * @param weapon
	 * @return
	 */
	public boolean addWeapon(Weapon weapon) {
		return weapons.add(weapon);
	}

	/**
	 * Remove a weapon from the room.
	 *
	 * @param weapon
	 * @return
	 */
	public boolean removeWeapon(Weapon weapon) {
		return weapons.remove(weapon);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((m_room == null) ? 0 : m_room.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (m_room != other.m_room)
			return false;
		return true;
	}



	@Override
	public String getName() {
		switch (m_room) {
		case BALL_ROOM:
			return "Ball Room";
		case BILLIARD_ROOM:
			return "Billiard Room";
		case CONSERVATORY:
			return "Conservatory";
		case DINING_ROOM:
			return "Dining Room";
		case HALL:
			return "Hall";
		case KITCHEN:
			return "Kitchen";
		case LIBRARY:
			return "Library";
		case LOUNGE:
			return "Lounge";
		case STUDY:
			return "Study";
		default:
			return null;
		}
	}

}
