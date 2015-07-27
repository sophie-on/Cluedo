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

	// Player that has this card
	private Player m_player;

	public RoomCard(Location room) {
		this.m_room = room;
	}

	@Override
	public GameObject getObject() {
		return m_room;
	}

	@Override
	public void dealToPlayer(Player player) {
		m_player = player;
	}

	@Override
	public Player getPlayer() {
		return m_player;
	}

	@Override
	public boolean isInHand() {
		return m_player != null;
	}
}
