package cluedo.tests;

import static org.junit.Assert.assertFalse;
import org.junit.Test;

import cluedo.model.Game;

public class CluedoTests {

	/**
	 * Test that the deck and the envelope do not contain the same cards
	 */
	@Test
	public void testEnvelope() {
		Game game = new Game();
		assertFalse(game.getDeck().containsAll(game.getEnvelope()));
	}

	/**
	 * Check that decks from different games are not the same
	 */
	@Test
	public void randomDeck() {
		Game game1 = new Game();
		Game game2 = new Game();

		assertFalse(game1.getDeck().containsAll(game2.getDeck()));
	}
	
	
}
