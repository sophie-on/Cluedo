package cluedo.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import cluedo.model.Game;
import cluedo.model.cards.Card;

public class GameTests {

	@Test
	public void testDeck() {
		List<Card> deck = Game.createTestDeck().get(0);
		List<Card> envelope = Game.createTestDeck().get(1);
		assertFalse(deck.containsAll(envelope));
	}
	
	@Test
	public void testDeck_2() {
		assertNotEquals(Game.createTestDeck().get(0), Game.createTestDeck().get(0));
	}
}
