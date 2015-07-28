package cluedo.model.board;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import cluedo.model.Player;
import cluedo.model.gameObjects.Location;
import cluedo.model.gameObjects.Location.Room;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;

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
	private Map<String,Room> places;
	private int character;

	public Board(String fileName){
		try {
			Scanner s = new Scanner(new File(fileName));
			x_size = s.nextInt();
			y_size = s.nextInt();
			board = new Square[x_size][y_size];
			s.useDelimiter("");
			setUpMap();
			parseBoard(s);
		} catch (FileNotFoundException e) { e.printStackTrace();}
	}

	private void setUpMap() {
		places.put("k",Room.KITCHEN);
		places.put("b",Room.BALL_ROOM);
		places.put("B",Room.BILLIARD_ROOM);
		places.put("D",Room.DINING_ROOM);
		places.put("l",Room.LIBRARY);
		places.put("H",Room.HALL);
		places.put("L",Room.LOUNGE);
		places.put("S",Room.STUDY);
		places.put("C", Room.CONSERVATORY);
	}

	/**
	 * Takes input from the Scanner and generates the Squares for the board
	 * @param s
	 */
	private void parseBoard(Scanner s) {
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j< board[i].length; j++){
				String key = s.next();
				switch(key){
				case "!": // Does nothing, a non-used square for decoration
					break;
				case "s": // Generates a starting square
					board[i][j] = new StarterSquare(i,j,Suspect.values()[character++]);
					break;
				case "c":
					board[i][j] = new CorridorSquare(i,j);
					break;
				case "p":
					board[i][j] = new PassageWaySquare(i,j, findRoom(i,j), ?);
					break;
				case "d":
					board[i][j] = new DoorSquare(i,j,findRoom(i,j)); // Need to deal with how to find room it is related to
					break;
				case "k|b|B|D|l|L|H|S|C":
					board[i][j] = new RoomSquare(i,j,places.get(key));
				}
			}
		}

	}

	private Room findRoom(int i, int j) {
		if(i > 0){
			if(board[i-1][j] instanceof RoomSquare){
				return ((RoomSquare)(board[i-1][j])).getRoom();
			}
		}
		if(j > 0){
			if(board[i][j-1] instanceof RoomSquare){
				return ((RoomSquare)(board[i][j-1])).getRoom();
			}
		}
		return null; // NOTE: need to figure out case where there is no left or above room
	}

	/**
	 * Brute force method to find which rooms a player can move to according to
	 * the roll they made.
	 **/
	public Set<Location> roomsInReach(Player player, int roll) {

		Set<Location> rooms = new HashSet<Location>();

		for (int i = player.getX(); i < x_size; i++) {
			for (int j = player.getY(); j < y_size; j++) {

				// If the player is in reach of a room add it to the room set
				if ((i + j) <= roll) {
					Square square = squareAt(i, j);
					if (square instanceof DoorSquare) {
						DoorSquare door = (DoorSquare) square;
						// rooms.add(door.getLocation());
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

	public void drawBoard(){
		for(int i = 0; i < board.length + 2; i++){
			for(int j = 0; j < board[i].length + 2; j++){
				if(j == 0 || j == board[i].length){
					System.out.println("_");
				}
				else if(i == 0 || i == board.length){
					System.out.println("|");
				}
				else{
					// Need to do this
				}
			}
		}
	}
}
