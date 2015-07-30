package cluedo.model.board;

import cluedo.model.Player;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;

public class StarterSquare implements Square, InhabitableSquare{

	private Suspect s;
	private int m_x;
	private int m_y;
	private boolean isOccupied;
	private Player p;

	public StarterSquare(int x, int y, Suspect suspect) {
		m_x = x;
		m_y = y;
		isOccupied = true;
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
		return isOccupied;
	}

	public String toString(){
		if(isOccupied){
			return s.toMiniString();
		}
		return "?";
	}

	@Override
	public Player getPlayer() {
		return p;
	}

}
