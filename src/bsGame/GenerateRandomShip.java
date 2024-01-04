package bsGame;

import java.io.Serializable;
import java.util.Random;

public class GenerateRandomShip implements Serializable {
	private static final long serialVersionUID = 1L;

	// Generate Ship Random
	public static void placeShipRandom(Grid grid, Ship ship) {
		final int MAX_ROW_COLUMN = 10;
		boolean orientation;
		int row, column;
		Random random = new Random();

		row = random.nextInt(MAX_ROW_COLUMN - ship.getLength() + 1) + 1;
		column = random.nextInt(MAX_ROW_COLUMN - ship.getLength() + 1) + 1;
		orientation = random.nextBoolean();

		// prevent overlapping
		while (shipOverlap(grid, ship, row, column, orientation) == true) {
			row = random.nextInt(MAX_ROW_COLUMN - ship.getLength() + 1) + 1;
			column = random.nextInt(MAX_ROW_COLUMN - ship.getLength() + 1) + 1;
		}
		// For Debug
		// System.out.println("Placing " + ship.getType() + " at:");

		for (int loop = 0; loop < ship.getLength(); loop++) {
			if (orientation == false) {
				grid.addShip(ship, row, column + loop);
				// For Debug
				// System.out.println("Row: " + row + " | Column: " + (column +
				// loop));
			} else {
				grid.addShip(ship, row + loop, column);
				// For Debug
				// System.out.println("Row: " + (row + loop) + " | Column: " +
				// column);
			}
		}
	}// end of placeShipRandom()

	private static boolean shipOverlap(Grid grid, Ship ship, int startRow, int startColumn, boolean orientation) {
		for (int loop = 0; loop < ship.getLength(); loop++) {
			if (orientation == false) {
				if (grid.isThereAShipOn(startRow, startColumn + loop)) {
					return true;
				}
			} else {
				if (grid.isThereAShipOn(startRow + loop, startColumn)) {
					return true;
				}
			}
		}
		return false;
	}// end of shipOverlap()
}
