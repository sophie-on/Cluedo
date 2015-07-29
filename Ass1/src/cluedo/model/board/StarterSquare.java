package cluedo.model.board;

import cluedo.model.gameObjects.CluedoCharacter.Suspect;

public class StarterSquare implements Square{

	private Suspect c;
	private int m_x;
	private int m_y;
	private boolean isOcuppied;

	public StarterSquare(int x, int y, Suspect suspect) {
		m_x = x;
		m_y = y;
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
		return isOcuppied;
	}

	public String toString(){
		if(isOcuppied){
			return c.toString().substring(0, 1);
		}
		return "?";
	}

}
