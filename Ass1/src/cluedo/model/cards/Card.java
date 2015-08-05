package cluedo.model.cards;

import cluedo.model.Player;
import cluedo.model.gameObjects.GameObject;

/**
 * Interface that represents the cards in the game, every game object is
 * represented by a single card.
 * 
 * 
 * @author Cameron Bryers, Hannah Craighead
 *
 */
public interface Card {

	public abstract GameObject getObject();
}
