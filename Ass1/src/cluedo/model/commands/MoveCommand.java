package cluedo.model.commands;

import java.util.Scanner;

import cluedo.model.Game;

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
		System.out.println("*** Which option do you want? ***\n*** Jump(1) * Manual(2) ***");

		// if(scan.hasNext()) scan.nextLine();

		boolean isValid = false;
		int command = 0;
		do {
			// Wait for a proper response
			while (!scan.hasNextInt()) {
				System.out.println("*** Please enter an integer ***");
				scan.nextLine();
			}

			command = scan.nextInt();
			scan.nextLine();
			System.out.println("COMMAND: " + command);

		} while (!isValid);
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
			jumpMove(scan, game);
			break;
		case MANUAL:
			manualMove(scan, game);
			break;
		default:
			break;

		}
	}

	private void jumpMove(Scanner scan, Game game) {

		// Get list of rooms available
		game.getRoomsInReach();
		
		
	}

	private void manualMove(Scanner scan, Game game) {

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
