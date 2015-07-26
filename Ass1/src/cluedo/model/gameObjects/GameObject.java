package cluedo.model.gameObjects;

/**
 * Class that encapsulates the objects of the game.
 * 
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public abstract class GameObject {

	// Is the object and the murder connected?
	private boolean m_isConnected;

	public GameObject(boolean isConnected) {
		this.m_isConnected = isConnected;
	}

	public boolean isConnected() {
		return m_isConnected;
	}

	public abstract String getName();
}
