package cluedo.model.commands;
import cluedo.model.Game;

/**
 * A class to represent the different types of commands a player can make
 * on their turn
 * 
 * @author Cameron Bryers, Hannah Craighead
 *
 */

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

	public abstract void execute(Game game);
}
