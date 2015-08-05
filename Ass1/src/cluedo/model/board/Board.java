package cluedo.model.board;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import cluedo.model.Game;
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

	/**
	 * Used to print out grid coordinates.
	 *
	 * @author Cameron Bryers, Hannah Craighead.
	 */
	public enum Alphabet {
		A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y;
	}

	private final static boolean DEBUG = Game.DEBUG;

	private Square[][] board;

	private int x_size;
	private int y_size;
	private Map<Character, Room> places;
	private int character;
	private Map<Room, Room> passages;

	public Board(String fileName) {
		try {
			Scanner s = new Scanner(new File(fileName));
			x_size = s.nextInt();
			y_size = s.nextInt();
			board = new Square[x_size][y_size];
			s.useDelimiter("");
			setUpMap();
			setUpPassage();
			parseBoard(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void setUpMap() {
		places = new HashMap<Character, Room>();
		places.put('k', Room.KITCHEN);
		places.put('b', Room.BALL_ROOM);
		places.put('B', Room.BILLIARD_ROOM);
		places.put('D', Room.DINING_ROOM);
		places.put('l', Room.LIBRARY);
		places.put('H', Room.HALL);
		places.put('L', Room.LOUNGE);
		places.put('S', Room.STUDY);
		places.put('C', Room.CONSERVATORY);
		places.put('x', Room.SWIMMING_POOL);
	}

	private void setUpPassage() {
		passages = new HashMap<Room, Room>();
		passages.put(Room.STUDY, Room.KITCHEN);
		passages.put(Room.KITCHEN, Room.STUDY);
		passages.put(Room.LOUNGE, Room.CONSERVATORY);
		passages.put(Room.CONSERVATORY, Room.LOUNGE);
	}

	/**
	 * Takes input from the Scanner and generates the Squares for the board
	 *
	 * @param s
	 */
	private void parseBoard(Scanner s) {
		s.next();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				char key = s.next().charAt(0);
				if (DEBUG)
					System.out.println("Key " + key + "i " + i + " j " + j);
				switch (key) {
				case '\r':
				case '\n': // Does nothing, end of line character to indicate
					// new row
					j--;
					if (DEBUG)
						System.out.println("Newline found");
					break;
				case ' ': // Does nothing, end of line character to indicate new
					// row
					j--;
					if (DEBUG)
						System.out.println("Space found");
					break;
				case '!': // Does nothing, a non-used square for decoration
					board[i][j] = new BlankSquare(i, j);
					break;
				case 's': // Generates a starting square
					board[i][j] = new StarterSquare(i, j,
							Suspect.values()[character++]);
					if (DEBUG) {
						System.out.println("Character is " + character);
						System.out.println("Coordinates are " + i + " " + j);
					}
					break;
				case 'c':
					board[i][j] = new CorridorSquare(i, j);
					break;
				case 'p':
					Room r = findRoom(i, j, s);
					PassageWaySquare pws = new PassageWaySquare(i, j, r,
							passages.get(r));
					r.setPassage(pws);
					if (DEBUG)
						System.out.println("R is " + r);// +
					board[i][j] = pws;
					break;
				case 'd':
					Room dr = findRoom(i, j, s);
					DoorSquare d = new DoorSquare(i, j, dr);
					dr.addDoor(d);
					board[i][j] = d;
					break;

				default:
					if (DEBUG) {
						System.out.println("Making room square " + key);
					}
					Room room = places.get(key);
					board[i][j] = new RoomSquare(i, j, room);
					room.addSquare((RoomSquare) board[i][j]);
					break;

				}
			}
		}
	}

	/**
	 * Adds players to the board at the start of a game
	 *
	 * @param players
	 */

	public void addPlayers(Set<Player> players) {
		if (players == null) {
			throw new RuntimeException("Cannot add null players to the board");
		}
		for (Player p : players) {
			int i = p.getX();
			int j = p.getY();
			Square s = board[i][j];
			if (s instanceof InhabitableSquare) {
				((InhabitableSquare) s).addPlayer(p);
			} else {
				throw new RuntimeException("Player cannot move here " + i + " "
						+ j + " " + s.getClass());
			}
		}
	}

	private Room findRoom(int i, int j, Scanner s) {
		if (DEBUG)
			System.out.println("FR");
		if (i > 0) {
			if (DEBUG)
				System.out.println("i > 0 ");
			if (board[i - 1][j] instanceof RoomSquare) {
				if (DEBUG)
					System.out.println("Returning room square 2");
				return ((RoomSquare) (board[i - 1][j])).getRoom();
			}
		}
		if (j > 0) {
			if (DEBUG)
				System.out.println("j > 0 ");
			if (board[i][j - 1] instanceof RoomSquare) {
				if (DEBUG)
					System.out.println("Returning room square 2");
				return ((RoomSquare) (board[i][j - 1])).getRoom();
			}
		}

		if (s.hasNext("k")) {
			return Room.KITCHEN;
		}
		if (s.hasNext("b")) {
			return Room.BALL_ROOM;
		}
		if (s.hasNext("B")) {
			return Room.BILLIARD_ROOM;
		}
		if (s.hasNext("D")) {
			return Room.DINING_ROOM;
		}
		if (s.hasNext("l")) {
			return Room.LIBRARY;
		}
		if (s.hasNext("L")) {
			return Room.LOUNGE;
		}
		if (s.hasNext("H")) {
			return Room.HALL;
		}
		if (s.hasNext("S")) {
			return Room.STUDY;
		}
		if (s.hasNext("C")) {
			return Room.CONSERVATORY;
		}
		if (s.hasNext("x")) {
			return Room.SWIMMING_POOL;
		}
		throw new RuntimeException("Not a valid room");
	}

	/**
	 * Brute force method to find which rooms a player can move to according to
	 * the roll they made.
	 **/
	public final List<DoorSquare> roomsInReach(Player player, int roll) {

		List<DoorSquare> rooms = new ArrayList<DoorSquare>();

		// Calculate search limits
		int a = player.getX() - roll;
		int b = player.getY() - roll;

		// If a is less than 0 set minX to zero etc. etc.
		int minX = (a > 0) ? a : 0;
		int minY = (b > 0) ? b : 0;

		for (int i = minX; i < x_size; i++) {
			for (int j = minY; j < y_size; j++) {

				if (DEBUG)
					System.out.println("RIR x: " + i + " y: " + j);

				// If the player is in reach of a room add it to the room set
				if ((Math.abs(player.getX() - i) + Math.abs(player.getY() - j)) <= roll) {
					Square square = squareAt(i, j);
					if (square instanceof DoorSquare) {
						if (!(square instanceof PassageWaySquare)) {
							DoorSquare door = (DoorSquare) square;
							rooms.add(door);
						}
					}
				}
			}
		}

		// Tunnel or secret passageways to rooms
		Square s = board[player.getX()][player.getY()];
		if (s instanceof RoomSquare) {
			if (((RoomSquare) s).getRoom().hasPassage()) {
				rooms.add(passages.get(((RoomSquare) s).getRoom()).getPassage());
			}
		}

		return rooms;
	}

	/**
	 * Better method for finding locations to jump to, uses a version of
	 * djikstra's algorithm instead of a brute force algorithm.
	 *
	 * @param player
	 * @param roll
	 * @return List of doors that a player can jump to
	 */
	public final List<DoorSquare> getJumpLocations(Player player, int roll) {

		List<DoorSquare> jumps = new ArrayList<DoorSquare>();

		// Get player's current location
		Square playerAt = squareAt(player.getX(), player.getY());

		// Get list of all possible locations
		Set<Square> possible = new HashSet<Square>();

		if(playerAt instanceof RoomSquare){	
			Set<DoorSquare> doors = ((RoomSquare)playerAt).getRoom().getDoors();
			for(DoorSquare door: doors){
				Set<Square> tilesFromDoor = new HashSet<Square>();
				tilesFromDoor = djikstra(door,roll);
				possible.addAll(tilesFromDoor);
			}
		} else{

			possible = djikstra(playerAt, roll);
		}


		// Go through each square, if it is a door square add it to the list
		for (Square sq : possible) {

			if(sq instanceof DoorSquare || sq instanceof PassageWaySquare){
				jumps.add((DoorSquare)sq);
			}

			//			// If the door is a passage way add it to the list iff the passage
			//			// way is in the room the player is currently in
			//			if (sq instanceof PassageWaySquare) {
			//				if (playerAt instanceof RoomSquare) {
			//					RoomSquare room = (RoomSquare) playerAt;
			//					PassageWaySquare passage = (PassageWaySquare) sq;
			//					if (room.getRoom().equals(passage.getRoom()))
			//						jumps.add(passage);
			//				}
			//			}
			//
			//			// If the door is contained in a room that the player is currently
			//			// in do not add it to the list
			//			else if (sq instanceof DoorSquare
			//					&& !(sq instanceof PassageWaySquare)) {
			//				if (playerAt instanceof RoomSquare) {
			//					RoomSquare room = (RoomSquare) playerAt;
			//					DoorSquare door = (DoorSquare) sq;
			//					if (!room.getRoom().equals(door.getRoom()))
			//						jumps.add((DoorSquare) sq);
			//				} else
			//					jumps.add((DoorSquare) sq);
			//			}
		}
		return jumps;
	}

	private class dStore {
		private int moves;
		private Square s;

		public dStore(int m, Square s) {
			moves = m;
			this.s = s;
		}

		public int getMoves() {
			return moves;
		}

		public Square getSquare() {
			return s;
		}
	}

	/**
	 * Djikstra's algorithm for path finding
	 */

	public Set<Square> djikstra(Square start, int roll) {
		System.out.println("Djikstra");
		Set<Square> squares = new HashSet<Square>();
		// Sets all squares to unvisited
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				Square s = board[i][j];
				if (s instanceof InhabitableSquare) {
					((InhabitableSquare) s).setVisited(false);
				}
			}
		}

		// create comparator for priority queue

		Comparator<dStore> comparator = new Comparator<dStore>() {
			public int compare(dStore o1, dStore o2) {
				return o1.getMoves() - o2.getMoves();
			}
		};

		// set up fringe

		Queue<dStore> fringe = new PriorityQueue<dStore>(comparator);
		if(start instanceof InhabitableSquare){
			dStore first = new dStore(0, start);
			fringe.offer(first);
		}
		else{ // starts from a door square
			DoorSquare door = ((DoorSquare)start);
			System.out.println("In a room");

			// Gets passageway square if a secret tunnel exists
			Room room = door.getRoom();
			if(passages.containsKey(room)){
				PassageWaySquare tunnel = passages.get(room).getPassage();
				squares.add(tunnel);
				System.out.println("PassageWay found");
			}

			if(door.getY() != 0){
				Square below = squareAt(door.getX(),
						door.getY() - 1);
				if (below instanceof InhabitableSquare) {
					InhabitableSquare b = (InhabitableSquare) below;
					fringe.offer(new dStore(1,below));
				}

			}
			if(door.getY() < board.length -1){
				Square above = squareAt(door.getX(),
						door.getY() + 1);
				if (above instanceof InhabitableSquare) {
					InhabitableSquare a = (InhabitableSquare) above;
					fringe.offer(new dStore(1,above));
				}

			}
			if(door.getX() < board[0].length -1){
				Square right = squareAt(door.getX()+1,
						door.getY());
				if (right instanceof InhabitableSquare) {
					InhabitableSquare r = (InhabitableSquare) right;
					fringe.offer(new dStore(1,right));
				}
			}
			if(door.getX() != 0){
				Square right = squareAt(door.getX()-1,
						door.getY());
				if (right instanceof InhabitableSquare) {
					InhabitableSquare r = (InhabitableSquare) right;
					fringe.offer(new dStore(1,right));
				}
			}







		}

		// continue until all possible landing squares found

		while (!fringe.isEmpty()) {
			dStore current = fringe.poll(); // removes shortest path so far
			InhabitableSquare currentSquare = (InhabitableSquare) current
					.getSquare();

			if (!currentSquare.visited()) {
				currentSquare.setVisited(true);

				if (current.getMoves() == roll) { // add as final destination
					// square
					squares.add((Square) currentSquare);
				}

				else { // add all neighbours onto the fringe, if they are a door
					// square add to squares (ends turn early)

					// add square above
					if (currentSquare.getY() != 0) {
						Square above = squareAt(currentSquare.getX(),
								currentSquare.getY() - 1);
						if (above instanceof InhabitableSquare) {
							InhabitableSquare a = (InhabitableSquare) above;

							// Only visit a square if it hasn't already has been
							// visited occupied
							if (!a.visited() && !a.isOccupied()) {
								fringe.offer(new dStore(current.getMoves() + 1,
										above));
							}
						} else if (above instanceof DoorSquare && !(above instanceof PassageWaySquare) ){
							squares.add(above);
						}
					}

					// add square below
					if (currentSquare.getY() < board[0].length - 1) {
						Square below = squareAt(currentSquare.getX(),
								currentSquare.getY() + 1);
						if (below instanceof InhabitableSquare) {
							InhabitableSquare b = (InhabitableSquare) below;

							// Only visit a square if it hasn't already has been
							// visited occupied
							if (!b.visited() && !b.isOccupied()) {
								fringe.offer(new dStore(current.getMoves() + 1,
										below));
							}
						} else if (below instanceof DoorSquare && !(below instanceof PassageWaySquare)) {
							squares.add(below);
						}
					}

					// add square left
					if (currentSquare.getX() != 0) {
						Square left = squareAt(currentSquare.getX() - 1,
								currentSquare.getY());
						if (left instanceof InhabitableSquare) {
							InhabitableSquare l = (InhabitableSquare) left;

							// Only visit a square if it hasn't already has been
							// visited occupied
							if (!l.visited() && !l.isOccupied()) {
								fringe.offer(new dStore(current.getMoves() + 1,
										left));
							}
						} else if (left instanceof DoorSquare && !(left instanceof PassageWaySquare)) {
							squares.add(left);
						}
					}

					// add square right
					if (currentSquare.getX() < board[0].length - 1) {
						Square right = squareAt(currentSquare.getX() + 1,
								currentSquare.getY());
						if (right instanceof InhabitableSquare) {
							InhabitableSquare r = (InhabitableSquare) right;

							// Only visit a square if it hasn't already has been
							// visited occupied
							if (!r.visited() && !r.isOccupied()) {
								fringe.offer(new dStore(current.getMoves() + 1,
										right));
							}
						} else if (right instanceof DoorSquare && !(right instanceof PassageWaySquare)) {
							squares.add(right);
						}
					}
				}

			}
		}
		return squares;
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
	public boolean isValid(Player player, int newX, int newY, int roll) {

		Set<Square> tiles = new HashSet<Square>();
		Square current = squareAt(player.getX(), player.getY());
		if(! (current instanceof RoomSquare)){
			System.out.println("Not in a room square " + player.getX() + " " + player.getY());
			tiles = djikstra(current, roll);
		}
		else{ // include possible destination tiles from all doors
			System.out.println("In a room");
			Room currentRoom = ((RoomSquare)current).getRoom();
			Set<DoorSquare> doors = currentRoom.getDoors();
			for(DoorSquare door: doors){
				Set<Square> tilesFromDoor = new HashSet<Square>();
				tilesFromDoor = djikstra(door,roll);
				tiles.addAll(tilesFromDoor);
			}
			if(currentRoom.hasPassage()){				
				PassageWaySquare pws = (passages.get(currentRoom).getPassage());
				tiles.add(pws);
				System.out.println("Added passage  " + pws.getX() + " " + pws.getY() + " " + pws.getRoom());
			}
		}

		Square dest = squareAt(newX, newY);
		return tiles.contains(dest);

		/*
		 * // If the move is not on the board if (newX < 0 || newX > 24 || newY
		 * < 0 || newY > 24) { return false; }
		 *
		 * Square current = squareAt(player.getX(),player.getY()); Square future
		 * = squareAt(newX, newY); // if the player is in a room
		 *
		 * if(current instanceof RoomSquare){ Room currentRoom =
		 * ((RoomSquare)current).getRoom(); PassageWaySquare p =
		 * (currentRoom.getPassage()); if(p != null && future instanceof
		 * PassageWaySquare){ // if the current room has a tunnel and the future
		 * square is a tunnel
		 * if(passages.get(currentRoom).equals(((PassageWaySquare
		 * )future).getRoom())){ return true; } else{ return false; } } else{ //
		 * Room does not contain a secret tunnel for(DoorSquare d:
		 * currentRoom.getDoors()){ if (Math.abs((newX - d.getX()) + (newY -
		 * d.getY())) <= roll) { return true; } }
		 * System.out.println("*** ROLL TUMEKE ***"); return false; //
		 * destination could not be reached by leaving any door
		 *
		 * }
		 *
		 * }
		 *
		 *
		 *
		 * // If the move is too far if (Math.abs((newX - player.getX()) + (newY
		 * - player.getY())) > roll) { if (DEBUG)
		 * System.out.println("*** ROLL TUMEKE ***"); return false; }
		 *
		 *
		 *
		 * // TODO fix this if (future instanceof RoomSquare) { return false; }
		 *
		 * // If the square is occupi/am/phoenix/home1/craighhann/workspaceed if (future instanceof InhabitableSquare)
		 * { if (((InhabitableSquare) future).isOccupied()) { if (DEBUG)
		 * System.out.println("*** SQUARE IS OCCUPIED ***"); return false; } }
		 *
		 * if (future instanceof DoorSquare) { ((DoorSquare)
		 * future).getRoom().addPlayer(player); return true; }
		 *
		 * return true;
		 */
	}

	public final Square squareAt(int x, int y) {
		if (DEBUG)
			System.out.println("*** SQUARE AT [" + x + "][" + y + "] ***");
		return board[x][y];
	}

	/**
	 * Draw the board and display it on the console
	 */
	public void drawBoard() {
		System.out.printf("   ");

		// Print out y coordinates
		for (Alphabet a : Alphabet.values())
			System.out.printf(a.name() + " ");

		System.out.println();

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {

				// Print out the x coordinates
				if (j == 0 && i < 10) {
					if (i == 0)
						System.out.print(" " + Alphabet.values()[i].ordinal());
					else
						System.out.print(" " + Alphabet.values()[i].ordinal());
				} else if (j == 0 && i < 25)
					System.out.print(Alphabet.values()[i].ordinal());

				if (j == 0) {
					System.out.printf("|");
				}

				if (board[i][j] instanceof InhabitableSquare) {
					InhabitableSquare sq = (InhabitableSquare) board[i][j];
					if (sq.isOccupied()) {
						System.out.print(sq.getPlayer().getCharacter()
								.toMiniString()
								+ "|");
					} else {
						System.out.print(board[i][j].toString() + "|");
					}
				} else {
					System.out.print(board[i][j].toString() + "|");
				}
			}
			System.out.println("");
		}

	}

	public static void main(String args[]) {
		new Board("cluedo.txt").drawBoard();
	}
}
