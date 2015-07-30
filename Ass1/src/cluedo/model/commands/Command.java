package cluedo.model.commands;

import java.util.Scanner;

import cluedo.model.Game;

public interface Command {

	public enum CommandType {

		MOVE(1), SUGGESTION(2), ACCUSATION(3), SHOW_CARDS(4), HELP(5), QUIT(6);

		private final int value;

		CommandType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public String toString() {
			switch (value) {
			case 1:
				return "Move";
			case 2:
				return "Suggestion";
			case 3:
				return "Accusation";
			case 4:
				return "Show Cards";
			case 5:
				return "Help";
			case 6:
				return "Quit";
			default:
				return "Error";
			}
		}
	}

	// private Command m_command;

//	public static Command getCommand(Scanner scan, Game game) {
//
//		String type = scan.next();
//
//		switch (type) {
//		case "1":
//			// m_command = new MoveCommand(scan, game);
//			break;
//		case "2":
//			// m_command = new SuggestCommand(scan, game);
//			break;
//		case "3":
//			// m_command = new AccuseCommand(scan, game);
//			break;
//		case "4":
//			// m_command = new ShowCardsCommand(scan, game);
//			break;
//		case "5":
//			// m_command = new HelpCommand(scan, game);
//			break;
//		case "6":
//			// m_command = new QuitCommand(scan, game);
//			break;
//		default:
//			// m_command = new MoveCommand(scan, game);
//			break;
//		}
//		return null;
//	}

	public abstract void execute(Game game);
}
