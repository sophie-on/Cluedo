package cluedo.model.gameObjects;

/**
 * This item is used turn by turn by players and rolled
 * to determine how far they are allowed to move their token
 * @author Hannah
 *
 */

public class Die {
	
	public int roll(){
		return (int)(1 + Math.random()*5);
	}
	
}
