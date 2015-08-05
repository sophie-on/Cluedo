package cluedo.model.commands;

import java.util.List;
import java.util.Scanner;
import cluedo.model.Game;
import cluedo.model.board.DoorSquare;

/**
 * A MoveCommand uses the console to interact with players to determine where
 * they would like to move on their turn. Players may either jump directly to a
 * room within their reach or manually enter a coordinate using the board
 * coordinate system
 * 
 * @author Cameron Bryers, Hannah Craighead
 *
 */

public class MoveCommand implements Command {	

	public enum MoveOption {
		JUMP(1), MANUAL(2);

		private final int value;

		MoveOption(int value) {
			this.value = value;
		}

		public String toString() {
			switch (value) {
			case 1:
				return "Jump";
			case 2:
				return "Manual";
			default:
				return "Error";
			}
		}
	}

	private int m_x, m_y;
	private MoveOption option;

	public MoveCommand(Scanner scan, Game game) {

		// Checks for a situation where a player is blocked inside a room and m ust forfeit their turn	
		if(game.getBoard().djikstra(game.getBoard().squareAt(game.getCurrent().getX(), game.getCurrent().getY()), game.getRoll()).isEmpty()){
			System.out.println("*** You are blocked, you must forfeit your turn ***");
			return;
		}		

		// Ask for which option they want to take
		System.out
				.println("*** Which move option do you want? ***\n*** Jump(1) * Manual(2) ***");
		
		boolean isValid = false;
		int command = 0;
		while (true) {
			// Wait for a proper response
			while (!scan.hasNextInt()) {
				System.out.println("*** Please enter an integer ***");
				scan.nextLine();
			}
			command = scan.nextInt();
			scan.nextLine();
			if (command != 1 && command != 2)
				System.out.println("*** That is not a valid input ***");
			else
				break;
		}

		switch (command) {
		case 2:
			option = MoveOption.MANUAL;
			break;
		default:
			option = MoveOption.JUMP;
			break;
		}

		calculateMove(scan, game);
	}
	
	/**
	 * calculateMove is used as a wrapper class for the two
	 * different types of player movement, jumping and manual entering
	 * 
	 * @param scan a Scanner for taking in player input
	 * @param game to applay the move to
	 */

	private void calculateMove(Scanner scan, Game game) {
		switch (option) {
		case JUMP:

			if (game.getJumpLocations().isEmpty()) {
				System.out
						.println("*** Sorry you cannot jump to any rooms ***");
				manualMove(scan, game);
			} else
				jumpMove(scan, game);

			break;
		case MANUAL:
			manualMove(scan, game);
			break;
		default:
			break;

		}
	}

	/**
	 * Allows a player to jump to any room in range
	 *
	 * @param scan a Scanner for player input
	 * @param game a Game to apply the move to
	 */
	private void jumpMove(Scanner scan, Game game) {

		// Get list of rooms available
		List<DoorSquare> rooms = game.getJumpLocations();

		System.out.println("*** Please select a room ***");
		for (int i = 0; i < rooms.size(); i++)
			System.out.println(rooms.get(i).getRoom().toString() + " (" + i + ") ");

		// Check for a valid input
		int command = 0;

		while (true) {
			while (!scan.hasNextInt()) {
				System.out.println("*** That is not a valid input");
				scan.nextLine();
			}
			command = scan.nextInt();
			scan.nextLine();

			if (command < 0 || command > (rooms.size() - 1))
				System.out.println("*** That is not a valid input ***");
			else
				break;
		}

		// Put in coordinates of room
		DoorSquare jumpTo = rooms.get(command);
		this.m_x = jumpTo.getX();
		this.m_y = jumpTo.getY();
	}

	/**
	 * Create a move given user input. Note x and y are swapped around give the
	 * design of the board
	 */
	private void manualMove(Scanner scan, Game game) {

		int x, y;

		while (true) {

			System.out
					.println("*** Please enter the x coordinate for your move ***");	
			
			// X coordinate may be in number form or letter form as on board 
			if (scan.hasNextInt())
				y = scan.nextInt();
			else {
				String ch = scan.next();
				char input = ch.charAt(0);
				
				if (Character.isUpperCase(input))
					y = (int) input - 65;
				else
					y = input - 97;
			}

			scan.nextLine();

			System.out
					.println("*** Please enter the y coordinate for your move ***");

			while (!scan.hasNextInt()) {
				System.out.println("*** Please enter an integer ***");
				scan.nextLine();
			}

			x = scan.nextInt();
			scan.nextLine();

			// Check for a valid input
			if (x <= 0 || x > 25 || y <= 0 || y > 25)
				System.out.println("*** That is not a valid input ***");
			else
				break;
		}

		this.m_x = x;
		this.m_y = y;
	}

	@Override
	public void execute(Game game) {
		game.move(m_x, m_y);
	}

	public final int getX() {
		return this.m_x;
	}

	public final int getY() {
		return this.m_y;
	}

}
