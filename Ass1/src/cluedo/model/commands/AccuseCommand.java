package cluedo.model.commands;

import java.util.Scanner;

import cluedo.model.Game;
import cluedo.model.gameObjects.Location.Room;

/**
 * Represents an accusation, same as the SuggestCommand class but it asks the
 * user for a room.
 *
 * @author Cameron Bryers, Hannah Craighead.
 *
 */
public class AccuseCommand extends SuggestCommand {

	public AccuseCommand(Game game, Scanner scan) {
		super(game, scan);

		// Ask for a room
		System.out.println("*** Please enter a room ***");

		for (int i = 0; i < 9; i++) {
			Room r = Room.values()[i];
			System.out.println(r.toString() + " (" + r.getValue() + ") ");
		}

		int command = 0;

		// Wait for a valid input
		while (true) {

			while (!scan.hasNextInt()) {
				System.out.println("*** Please enter an integer ***");
				scan.next();
			}

			command = scan.nextInt() - 1;

			if (command >= 0 && command <= 8)
				break;
			else
				System.out.println("*** That is not a valid room ***");
		}
		
		//Sets given room for accuseCommand
		this.setRoom(Room.values()[command]);
	}
}
