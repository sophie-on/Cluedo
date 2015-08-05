package cluedo.model.board;

import cluedo.model.Player;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;

/**
 * Represents a starting position for a player, these are predefined in
 * Game.java
 * 
 * @author Cameron Bryers and Hannah Craighead.
 *
 */
public class StarterSquare implements Square, InhabitableSquare {

	// Suspect that starts at this square
	private Suspect s;
	private int m_x;
	private int m_y;
	private Player p;
	private boolean visited;

	public StarterSquare(int x, int y, Suspect suspect) {
		m_x = x;
		m_y = y;
		s = suspect;
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

	public String toString() {
		if (p != null) {
			return s.toMiniString();
		}
		return "?";
	}

	@Override
	public Player getPlayer() {
		return p;
	}

	public Suspect getSuspect() {
		return s;
	}

	@Override
	public void addPlayer(Player p) {
		if (this.p != null) {
			throw new RuntimeException("Cannot add two players to the same square");
		}
		this.p = p;
	}

	@Override
	public void setVisited(boolean v) {
		visited = v;
	}

	@Override
	public boolean visited() {
		return visited;
	}

}
