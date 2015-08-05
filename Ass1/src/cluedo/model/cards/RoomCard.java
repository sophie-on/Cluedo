package cluedo.model.cards;

import cluedo.model.Player;
import cluedo.model.gameObjects.GameObject;
import cluedo.model.gameObjects.Location;

/**
 * Class that represents a room card in the game.
 * 
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class RoomCard implements Card {

	// Room that the card is associated with
	private Location m_room;

	public RoomCard(Location room) {
		this.m_room = room;
	}

	@Override
	public GameObject getObject() {
		return m_room;
	}
}
