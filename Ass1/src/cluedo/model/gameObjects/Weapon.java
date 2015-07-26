package cluedo.model.gameObjects;

/**
 * Class that represents a murder weapon.
 * 
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class Weapon extends GameObject {

	/**
	 * List of possible murder weapons.
	 * 
	 * @author Cameron Bryers, Hannah Craighead.
	 *
	 */
	public enum WeaponType {
		CANDLESTICK, DAGGER, LEAD_PIPE, REVOLVER, ROPE, SPANNER;
	}

	private WeaponType m_weapon;

	public Weapon(boolean isMurderWeapon, WeaponType weapon) {
		super(isMurderWeapon);
		this.m_weapon = weapon;
	}

	@Override
	public String getName() {
		switch (m_weapon) {
		case CANDLESTICK:
			return "Candlestick";
		case DAGGER:
			return "Dagger";
		case LEAD_PIPE:
			return "Lead Pipe";
		case REVOLVER:
			return "Revolver";
		case ROPE:
			return "Rope";
		case SPANNER:
			return "Spanner";
		default:
			return null;
		}
	}

}
