package cluedo.model.commands;

import java.util.Scanner;

import cluedo.model.Game;
import cluedo.model.Player;
import cluedo.model.board.RoomSquare;
import cluedo.model.board.Square;
import cluedo.model.gameObjects.CluedoCharacter.Suspect;
import cluedo.model.gameObjects.Location.Room;
import cluedo.model.gameObjects.Weapon;
import cluedo.model.gameObjects.Weapon.WeaponType;

/**
 * Represents a suggestion in the game
 *
 * @author Cameron Bryers, Hannah Craighead.
 *
 */

public class SuggestCommand implements Command {

	// Things involved in the murder.
	private Suspect suspect;
	private Room room;
	private WeaponType weapon;

	public SuggestCommand(Game game, Scanner scan) {

		// Room must be that of which the character is in
		Square sq = game.getBoard().squareAt(game.getCurrent().getX(),
				game.getCurrent().getY());
		assert sq instanceof RoomSquare;

		// Set the room if it is not the swimming pool
		if (!((RoomSquare) sq).getRoom().equals(Room.SWIMMING_POOL))
			room = ((RoomSquare) sq).getRoom();

		// Ask for suspect
		System.out.println("*** Please enter a suspect ***");

		for (Suspect s : Suspect.values())
			System.out.println(s.toString() + " (" + s.getValue() + ")");

		boolean isValid = false;
		int command = 0;

		// Wait for a proper response
		while (!isValid) {

			while (!scan.hasNextInt()) {
				System.out.println("*** Please enter an integer ***");
				scan.next();
			}

			command = scan.nextInt() - 1;

			if (command >= 0 && command <= 5)
				isValid = true;
			else
				System.out.println("*** That is not a valid suspect ***");
		}

		suspect = Suspect.values()[command];

		// Ask for a weapon
		System.out.println("*** Please enter a weapon ***");

		for (WeaponType w : WeaponType.values())
			System.out.println(w.toString() + " (" + w.getValue() + ") ");

		isValid = false;
		command = 0;

		// Wait for a proper response
		while (!isValid) {

			while (!scan.hasNextInt()) {
				System.out.println("*** Please enter an integer ***");
				scan.next();
			}

			command = scan.nextInt() - 1;

			if (command >= 0 && command <= 5)
				isValid = true;
			else
				System.out.println("*** That is not a valid weapon ***");
		}

		weapon = WeaponType.values()[command];
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public void execute(Game game) {
		// Does nothing
		System.out
				.println("*** Execute method in suggest/ accuse not used ***");
	}

	public final Suspect getSuspect() {
		return suspect;
	}

	public final Room getRoom() {
		return room;
	}

	public final WeaponType getWeapon() {
		return weapon;
	}

}
