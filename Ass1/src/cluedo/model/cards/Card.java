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
	
	/**
	 * Give the card to a player
	 * @param player
	 */
	public abstract void dealToPlayer(Player player);
	
	/**
	 * 
	 * @return the player that has this card
	 */
	public abstract Player getPlayer();
	
	/**
	 * 
	 * @return true if the card is in someone's hand
	 */
	public abstract boolean isInHand();
}
