package bsGame;

import java.io.Serializable;
import java.util.ArrayList;

public class Grid implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Row> theRows = new ArrayList<Row>();
	private final int NUMBER_OF_ROWS = 10;

	// constructor
	public Grid() {
		Row tempRow;
		for (int loop = 1; loop <= this.NUMBER_OF_ROWS; loop++) {
			tempRow = new Row(loop);
			this.theRows.add(tempRow);
		}
	}// end of Grid()

	public boolean isThereAShipOn(int row, int square) {
		for (Row tempRow : this.theRows) {
			// found the correct row
			if (tempRow.getRowNumber() == row) {
				for (Cell tempCell : tempRow.getTheCells()) {
					// found the correct square
					if (tempCell.getNumber() == square) {
						return tempCell.isThereAShip();
					}
				}
			}
		}
		return false;
	}// end of isThereAShipOn()

	public Ship getShip(int row, int square) {
		for (Row tempRow : this.theRows) {
			// find the row
			if (tempRow.getRowNumber() == row) {
				for (Cell tempCell : tempRow.getTheCells()) {
					if (tempCell.getNumber() == square) {
						if (tempCell.isThereAShip()) {
							// found ship
							return tempCell.getTheShip();
						}
					}
				}
			}
		}
		// No sunk ship found in location
		return null; 
	}// end of getShip()

	// new class
	public Row getRow(int rowNumber) {
		for (Row tempRow : this.theRows) {
			if (tempRow.getRowNumber() == rowNumber) {
				return tempRow;
			}
		}
		// Return null if the row with the specified number is not found
		return null; 
	}//end of getRow

	public void addShip(Ship ship, int row, int square) {
		for (Row tempRow : this.theRows) {
			if (tempRow.getRowNumber() == row) {
				// found correct row
				for (Cell tempCell : tempRow.getTheCells()) {
					if (tempCell.getNumber() == square) {
						// found correct square
						tempCell.setTheShip(ship);
					}
				}
			}
		}
	}// end of addShip()

	public boolean areAllShipsSunk() {
		// loop each row
		for (Row tempRow : this.theRows) {
			// loop each cells
			for (Cell tempCell : tempRow.getTheCells()) {
				// Check if there is a ship on the current cell and the ship is not sunk
				if (tempCell.isThereAShip() == true && tempCell.getTheShip().isSunk() == false) {
					return false;
				}
			}
		}
		return true;
	}// end of areAllShipsSunk()

}
