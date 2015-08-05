package cluedo.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cluedo.model.Game;

public class GameTests {

	@Test
	public void testDeck() {
		Game game1 = new Game();
		Game game2 = new Game();
		
		assertFalse(game1.getDeck().equals(game2.getDeck()));
	}
}
