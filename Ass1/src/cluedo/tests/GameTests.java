package cluedo.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import cluedo.model.Game;
import cluedo.model.cards.Card;

public class GameTests {

	@Test
	public void testDeck() {
		List<Card> deck = Game.createDeck().get(0);
		List<Card> envelope = Game.createDeck().get(1);
		assertFalse(deck.containsAll(envelope));
	}
	
	@Test
	public void testDeck_2() {
		assertNotEquals(Game.createDeck().get(0), Game.createDeck().get(0));
	}
}
