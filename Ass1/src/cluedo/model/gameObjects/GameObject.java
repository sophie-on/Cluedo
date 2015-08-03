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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (m_isConnected ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameObject other = (GameObject) obj;
		if (m_isConnected != other.m_isConnected)
			return false;
		return true;
	}

	public abstract String getName();
}
