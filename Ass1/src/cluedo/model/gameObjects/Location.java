/**
 * 
 */
package cluedo.model.gameObjects;

import java.util.HashSet;
import java.util.Set;

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
		KITCHEN, BALL_ROOM, CONSERVATORY, DINING_ROOM, BILLIARD_ROOM, LIBRARY, LOUNGE, HALL, STUDY;
	}

	private Room m_room;

	// Weapons that are in this room.
	private Set<Weapon> weapons;

	// Characters that are in this room.
	private Set<CluedoCharacter> characters;

	public Location(boolean isCrimeScene, Room room) {
		super(isCrimeScene);
		this.m_room = room;
		characters = new HashSet<CluedoCharacter>();
		weapons = new HashSet<Weapon>();
	}

	/**
	 * Adds a character to that room.
	 * 
	 * @param character
	 * @return
	 */
	public boolean addCharacter(CluedoCharacter character) {
		return characters.add(character);
	}

	/**
	 * Remove a character from the room.
	 * 
	 * @param character
	 * @return
	 */
	public boolean removeCharacter(CluedoCharacter character) {
		return characters.remove(character);
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
