package cluedo.model.commands;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import cluedo.model.Game;
import cluedo.model.gameObjects.Location;
import cluedo.model.gameObjects.Location.Room;

public class MoveCommand implements Command {

	// Coordinates of rooms TODO

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

		// Scanner reader = new Scanner(System.in);

		// Ask for which option they want to take
		System.out
				.println("*** Which option do you want? ***\n*** Jump(1) * Manual(2) ***");

		// if(scan.hasNext()) scan.nextLine();

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
			System.out.println("COMMAND: " + command);

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

	private void calculateMove(Scanner scan, Game game) {

		switch (option) {
		case JUMP:

			if (game.getRoomsInReach().isEmpty()) {
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
	 * @param scan
	 * @param game
	 */
	private void jumpMove(Scanner scan, Game game) {

		// Get list of rooms available
		List<Room> rooms = game.getRoomsInReach();

		System.out.println("*** Please select a room ***");
		for (int i = 0; i < rooms.size(); i++)
			System.out.println(rooms.get(i).toString() + " (" + i + ") ");

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

	}

	private void manualMove(Scanner scan, Game game) {

		System.out
				.println("*** Please enter the coordinates for your move ***");
	}

	@Override
	public void execute(Game game) {
		game.move(m_x, m_y);
	}

	public final int getX() {
		return this.m_x;
	}

	public final int getY() {
		return this.getY();
	}

}
