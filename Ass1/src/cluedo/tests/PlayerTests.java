package cluedo.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import cluedo.model.Game;
import cluedo.model.Player;
import cluedo.model.board.Board;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;

public class PlayerTests {

	@Test 
	public void testPlayerName() {
		Player player = new Player("Jim", Suspect.COLONEL_MUSTARD, Game.COLONEL_MUSTARD_START);
		assertEquals(player.getName(), "Jim");
	}
	
	@Test
	public void testValidMove() {
		Board board = new Board("cluedo.txt");
		Player player = new Player("Jim", Suspect.COLONEL_MUSTARD, Game.COLONEL_MUSTARD_START);
		assertTrue(board.isValid(player, 17, 1, 1));
	}
	
	@Test
	public void testNewPosition() {
		Player player = new Player("Jim", Suspect.COLONEL_MUSTARD, Game.COLONEL_MUSTARD_START);
		
		int x = 13, y = 10;
		
		player.move(x, y);
		assertTrue(player.getX() == x && player.getY() == y);
	}
	
	@Test
	public void testInvalidMove() {
		Board board = new Board("cluedo.txt");
		Player player = new Player("Jim", Suspect.COLONEL_MUSTARD, Game.COLONEL_MUSTARD_START);
		assertFalse(board.isValid(player, 17, 2, 1));
	}
}
