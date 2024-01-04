package bsGame;

import java.io.Serializable;
import java.util.ArrayList;

public class Row implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Cell> theCells = new ArrayList<Cell>();
	private int rowNumber;
	private final int NUMBER_OF_CELLS = 10;

	// constructor
	public Row(int rowNumber) {
		Cell tempCell;
		for (int loop = 1; loop <= this.NUMBER_OF_CELLS; loop++) {
			tempCell = new Cell(loop);
			this.theCells.add(tempCell);
		}

		setRowNumber(rowNumber);
	}

	public ArrayList<Cell> getTheCells() {
		return this.theCells;
	}

	public void setTheCells(ArrayList<Cell> theCells) {
		this.theCells = theCells;
	}

	public int getRowNumber() {
		return this.rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getNUMBER_OF_CELLS() {
		return this.NUMBER_OF_CELLS;
	}

}
