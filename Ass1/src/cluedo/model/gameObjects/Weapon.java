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
		CANDLESTICK(1), DAGGER(2), LEAD_PIPE(3), REVOLVER(4), ROPE(5), SPANNER(
				6);

		private final int value;

		WeaponType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public String toString() {
			switch (value) {
			case 1:
				return "Candlestick";
			case 2:
				return "Dagger";
			case 3:
				return "Lead Pipe";
			case 4:
				return "Revolver";
			case 5:
				return "Rope";
			case 6:
				return "Spanner";
			default:
				return "Error";
			}
		}
	}

	private WeaponType m_weapon;

	public Weapon(boolean isMurderWeapon, WeaponType weapon) {
		super(isMurderWeapon);
		this.m_weapon = weapon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((m_weapon == null) ? 0 : m_weapon.hashCode());
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
		Weapon other = (Weapon) obj;
		if (m_weapon != other.m_weapon)
			return false;
		return true;
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
