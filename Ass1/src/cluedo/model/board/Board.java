package cluedo.model.board;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

	private final static boolean DEBUG = false;

	private Square[][] board;

	private int x_size;
	private int y_size;
	private Map<Character,Room> places;
	private int character;
	private Map<Room, Room> passages;

	public Board(String fileName){
		try {
			Scanner s = new Scanner(new File(fileName));
			x_size = s.nextInt();
			y_size = s.nextInt();
			board = new Square[x_size][y_size];
			s.useDelimiter("");
			setUpMap();
			setUpPassage();
			parseBoard(s);
		} catch (FileNotFoundException e) { e.printStackTrace();}
	}

	private void setUpMap() {
		places = new HashMap<Character, Room>();
		places.put('k',Room.KITCHEN);
		places.put('b',Room.BALL_ROOM);
		places.put('B',Room.BILLIARD_ROOM);
		places.put('D',Room.DINING_ROOM);
		places.put('l',Room.LIBRARY);
		places.put('H',Room.HALL);
		places.put('L',Room.LOUNGE);
		places.put('S',Room.STUDY);
		places.put('C', Room.CONSERVATORY);
		places.put('x', Room.SWIMMING_POOL);
	}

	private void setUpPassage(){
		passages = new HashMap<Room,Room>();
		passages.put(Room.STUDY,Room.KITCHEN);
		passages.put(Room.KITCHEN,Room.STUDY);
		passages.put(Room.LOUNGE,Room.CONSERVATORY);
		passages.put(Room.CONSERVATORY,Room.LOUNGE);
	}

	/**
	 * Takes input from the Scanner and generates the Squares for the board
	 * @param s
	 */
	private void parseBoard(Scanner s) {
		s.next();
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j< board[i].length; j++){
				char key = s.next().charAt(0);
				if (DEBUG) System.out.println("Key "+key + "i " + i + " j " + j);
				switch(key){
				case '\r':
				case '\n': // Does nothing, end of line character to indicate new row
					j--;
					if (DEBUG) System.out.println("Newline found");
					break;
				case ' ': // Does nothing, end of line character to indicate new row
					j--;
					if (DEBUG) System.out.println("Space found");
					break;
				case '!': // Does nothing, a non-used square for decoration
					board[i][j] = new BlankSquare(i,j);
					break;
				case 's': // Generates a starting square
					board[i][j] = new StarterSquare(i,j,Suspect.values()[character++]);
					if (DEBUG) {
						System.out.println("Character is " + character);
						System.out.println("Coordinates are " + i + " " + j);
					}
					break;
				case 'c':
					board[i][j] = new CorridorSquare(i,j);
					break;
				case 'p':
					Room r = findRoom(i,j,s);
					if (DEBUG) System.out.println("R is " + r );//+ "passages.get(r) is " + passages.get(r));
					board[i][j] = new PassageWaySquare(i,j, r , passages.get(r));
					break;
				case 'd':
					board[i][j] = new DoorSquare(i,j,findRoom(i,j,s)); // Need to deal with how to find room it is related to
					break;

				default:
					if (DEBUG) System.out.println("Making room square " + key);
					assert places.get(key) != null;
					board[i][j] = new RoomSquare(i,j,places.get(key));
					break;

				}
			}
			//s.next(); // to skip newline character
		}

	}

	/**
	 * Adds players to the board at the start of a game
	 * @param players
	 */

	public void addPlayers(Set<Player> players){
		if(players == null){
			throw new RuntimeException("Cannot add null players to the board");
		}
		for(Player p : players){
			int i = p.getX();
			int j = p.getY();
			Square s = board[i][j];
			if(s instanceof InhabitableSquare){
				((InhabitableSquare)s).addPlayer(p);
			}
			else{
				throw new RuntimeException("Player cannot move here " + i + " " + j + " " + s.getClass());
			}
		}
	}

	private Room findRoom(int i, int j, Scanner s) {
		if (DEBUG) System.out.println("FR");
		if(i > 0){
			if (DEBUG) System.out.println("i > 0 ");
			if(board[i-1][j] instanceof RoomSquare){
				if (DEBUG) System.out.println("Returning room square 2");
				return ((RoomSquare)(board[i-1][j])).getRoom();
			}
		}
		if(j > 0){
			if (DEBUG) System.out.println("j > 0 ");
			if(board[i][j-1] instanceof RoomSquare){
				if (DEBUG) System.out.println("Returning room square 2");
				return ((RoomSquare)(board[i][j-1])).getRoom();
			}
		}

		if(s.hasNext("k")){return Room.KITCHEN;	}
		if(s.hasNext("b")){return Room.BALL_ROOM;}
		if(s.hasNext("B")){return Room.BILLIARD_ROOM;}
		if(s.hasNext("D")){return Room.DINING_ROOM;	}
		if(s.hasNext("l")){return Room.LIBRARY;	}
		if(s.hasNext("L")){return Room.LOUNGE;	}
		if(s.hasNext("H")){return Room.HALL;	}
		if(s.hasNext("S")){return Room.STUDY;	}
		if(s.hasNext("C")){return Room.CONSERVATORY;}
		if(s.hasNext("x")){return Room.SWIMMING_POOL;}
		throw new RuntimeException("Not a valid room");
	}

	/**
	 * Brute force method to find which rooms a player can move to according to
	 * the roll they made.
	 **/
	public final List<Room> roomsInReach(Player player, int roll) {

		List<Room> rooms = new ArrayList<Room>();

		for (int i = player.getX(); i < x_size; i++) {
			for (int j = player.getY(); j < y_size; j++) {

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
		if(square instanceof InhabitableSquare){
			if (!((InhabitableSquare)square).isOccupied())
				return true;
		}

		return false;
	}

	public final Square squareAt(int x, int y) {
		return board[x][y];
	}

	public void drawBoard(){
		for(int i = 0; i < board.length + 1; i++){
			for(int j = 0; j < board[0].length + 1; j++){
				if(i == 0  ){
					System.out.printf("__");
				}
				else if(i == board.length+1){

				}
				else if(j == 0 ){
					System.out.printf("|");
				}
				else{
					if(board[i-1][j-1] instanceof InhabitableSquare){
						InhabitableSquare sq = (InhabitableSquare)board[i-1][j-1];
						if(sq.isOccupied()){
							System.out.print(sq.getPlayer().getCharacter().toMiniString()+ "|");
							//System.out.println("Mini statement is " + sq.getPlayer().getCharacter().toMiniString() + "***");
						}
						else{
							System.out.print(board[i-1][j-1].toString() + "|");
						}
					}
					else{
						System.out.print(board[i-1][j-1].toString() + "|");
					}
				}
			}
			System.out.println("");
		}
	}

	public static void main(String args[]){
		new Board("cluedo.txt").drawBoard();
	}
}
