package cluedo.model.cards;

import cluedo.model.Player;
import cluedo.model.gameObjects.GameObject;
import cluedo.model.gameObjects.Weapon;

/**
 * Class that represents a weapon card in the game.
 * 
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class WeaponCard implements Card {

	// Weapon that this card is associated with
	private Weapon m_weapon;

	public WeaponCard(Weapon weapon) {
		this.m_weapon = weapon;
	}

	@Override
	public GameObject getObject() {
		return m_weapon;
	}
}
