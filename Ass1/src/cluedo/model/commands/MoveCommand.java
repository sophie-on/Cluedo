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

		// Wait for a proper response
		while (!scan.hasNextInt()) {
			System.out.println("*** Please enter an integer ***");
			scan.next();
		}

		int command = scan.nextInt();

		switch (command) {
		case 2:
			option = MoveOption.MANUAL;
			break;
		default:
			option = MoveOption.JUMP;
			break;
		}

	}

	// Probably gonna get rid of this
	@Override
	public boolean check(Game game) {
		return true;
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
