package cluedo.tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import cluedo.model.Game;
import cluedo.model.Player;
import cluedo.model.board.Board;
import cluedo.model.board.DoorSquare;
import cluedo.model.board.InhabitableSquare;
import cluedo.model.board.Square;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;
import cluedo.model.gameObjects.Location.Room;

public class BoardTests {

	@Test
	public void testValidAddPlayer() {
		Board board = new Board("cluedo.txt");
		Player player = new Player("Jim", Suspect.COLONEL_MUSTARD, Game.COLONEL_MUSTARD_START);

		Set<Player> players = new HashSet<Player>();
		players.add(player);

		try {
			board.addPlayers(players);
		} catch (Exception e) {
			fail("Should be able to do this");
		}
	}

	@Test
	public void testInvalidAddPlayer() {
		Board board = new Board("cluedo.txt");

		try {
			board.addPlayers(null);
			fail("Can't add null to the board");
		} catch (Exception e) {

		}
	}

	@Test
	public void testInvalidAddPlayer_2() {
		Board board = new Board("cluedo.txt");
		Player player = new Player("Jim", Suspect.COLONEL_MUSTARD, new Point(0, 0));

		Set<Player> players = new HashSet<Player>();
		players.add(player);

		try {
			board.addPlayers(players);
			fail("Can't add a player in an invalid position");
		} catch (Exception e) {

		}
	}

	@Test
	public void testInvalidAddPlayer_3() {
		Board board = new Board("cluedo.txt");
		Player player1 = new Player("Jim", Suspect.COLONEL_MUSTARD, Game.COLONEL_MUSTARD_START);
		Player player2 = new Player("Dave", Suspect.MISS_SCARLET, Game.COLONEL_MUSTARD_START);

		Set<Player> players = new HashSet<Player>();
		players.add(player1);
		players.add(player2);

		try {
			board.addPlayers(players);
			fail("Shouldn't be able to two players on the same starting position");
		} catch (Exception e) {

		}
	}

	@Test
	public void testDoorsAndRoom() {
		Board board = new Board("cluedo.txt");
		Square sq = board.squareAt(19, 6);
		DoorSquare door = (DoorSquare) sq;
		assertTrue(door.getRoom().equals(Room.LOUNGE));
	}
	
	@Test
	public void testJumpLocations_1() {
		Board board = new Board("cluedo.txt");
		Player player = new Player("Jim", Suspect.MISS_SCARLET, Game.MISS_SCARLET_START);
		assertTrue(board.getJumpLocations(player, 1).isEmpty());
	}

	@Test
	public void testJumpLocations_2() {
		Board board = new Board("cluedo.txt");
		Player player = new Player("Jim", Suspect.MISS_SCARLET, Game.MISS_SCARLET_START);
		assertTrue(board.getJumpLocations(player, 6).size() == 1);
	}	
	
}
