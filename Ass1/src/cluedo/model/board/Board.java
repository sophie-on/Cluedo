package cluedo.model.board;

/**
 * This class represents the board (or tiles) of the game Cluedo.
 * The game board will be a set size of 25x25 tiles.
 * 
 * @author Cameron Bryers, Hannah Craighead
 *
 */

public class Board {
	
	private Square[][] board;
	
	private int size;
	
	
	
	/**
	 * Brute force method to find which rooms a player can move to according
	 * to the roll they made.
	 **/
	public Set<Location> roomsInReach(Player player, int roll) {
	  
	  Set<Location> rooms = new HashSet<Location>();
	  
	  for (int i = player.x; i < size; i++) {
	    for (int j = player.y; j < size; j++) {
	     if ((i + j) <= roll) {
	      Square square = board.squareAt(i, j);
	      if (square instanceof DoorSquare) {
		DoorSquare door = (DoorSquare) square;
		rooms.add(door.getLocation());
	     }
	    }
	  }
	  return rooms;
	}
	
	/**
	 * Checks if the player's move is valid.
	 * */
	public boolean isValid(Player player, int x, int y, int roll) {
	 
	  // If the move is too far
	  if ((x + y) != roll)
	    return false;
	  
	  Square square = squareAt(player.getX() + x, player.getY() + y);
	  
	  // If the square is occupied
	  if (!(square instanceof DoorSquare) && !(square instanceof RoomSquare)
	  && square.isOccuppied())
	    return false;
	    
	  return true;
	}
	
	public final Square squareAt(int x, int y) {
		return board[x][y];
	}
}
