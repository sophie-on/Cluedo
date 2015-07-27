package cluedo.model.board;

import java.util.HashSet;
import java.util.Set;

import cluedo.model.Player;
import cluedo.model.gameObjects.Location;

/**
 * This class represents the board (or tiles) of the game Cluedo. The game board
 * will be a set size of 25x25 tiles.
 * 
 * @author Cameron Bryers, Hannah Craighead
 *
 */

public class Board {

	private Square[][] board;

	private int size;

	/**
	 * Brute force method to find which rooms a player can move to according to
	 * the roll they made.
	 **/
	public Set<Location> roomsInReach(Player player, int roll) {

		Set<Location> rooms = new HashSet<Location>();

		for (int i = player.getX(); i < size; i++) {
			for (int j = player.getY(); j < size; j++) {
				
				// If the player is in reach of a room add it to the room set
				if ((i + j) <= roll) {
					Square square = squareAt(i, j);
					if (square instanceof DoorSquare) {
						DoorSquare door = (DoorSquare) square;
						rooms.add(door.getRoom());
					}
				}
			}
		}
		return rooms;
	}

	/**
	 * Check if the player's move is valid
	 * 
	 * @param player
	 * @param dx
	 *            change in x
	 * @param dy
	 *            change in y
	 * @param roll
	 *            the roll that the player got
	 * @return true if move was valid
	 */
	public boolean isValid(Player player, int dx, int dy, int roll) {

		// If the move is too far
		if ((dx + dy) != roll)
			return false;

		Square square = squareAt(player.getX() + dx, player.getY() + dy);

		// If the square is occupied
		if (square.isOccupied())
			return false;

		return true;
	}

	public final Square squareAt(int x, int y) {
		return board[x][y];
	}
}
