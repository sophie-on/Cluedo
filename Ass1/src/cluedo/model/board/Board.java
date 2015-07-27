package cluedo.model.board;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
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

	private int x_size;
	private int y_size;

	public Board(String fileName){
		try {
			Scanner s = new Scanner(new File(fileName));
			x_size = s.nextInt();
			y_size = s.nextInt();
			board = new Square[x_size][y_size];
			s.useDelimiter("");
			parseBoard(s);
		} catch (FileNotFoundException e) { e.printStackTrace();}
	}

	/**
	 * Takes input from the Scanner and generates the Squares for the board
	 * @param s
	 */
	private void parseBoard(Scanner s) {
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j< board[i].length; j++){
				switch(s.next()){
				case "!": // Does nothing, a non-used square for decoration
					break;
				case "s": // Generates a starting square
				//	board[i][j] = new StarterSquare(i,j,);
					break;
				case "k":
					board[i][j] = new RoomSquare(i,j, new Location(KITCHEN));
					break;
				case "c":
					break;
				case "b":
					break;
				case "C":
					break;
				case "!":
					break;
				case "!":
					break;
				case "!":
					break;
				case "!":
					break;
				}
			}
		}

	}

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
