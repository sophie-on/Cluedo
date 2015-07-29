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

		// Ask for which option they want to take
		System.out
				.println("*** Which option do you want? ***\n*** Jump(1) * Manual(2) ***");

		int command;

		do {
			command = scan.nextInt();
		}while(!command);

	}

	public int readInt(Scanner scan) {
		boolean isValid;
		int value = 0;

		try {
			value = scan.nextInt();
		} catch (Exception e) {
			System.out.println("*** Invalid entry, please try again ***");
		}

		return value;
	}

	@Override
	public boolean check(Game game) {
		return true;
	}

	@Override
	public void execute(Game game) {
		game.move(m_x, m_y);
	}

}
