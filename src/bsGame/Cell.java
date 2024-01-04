package bsGame;

import java.io.Serializable;

public class Cell implements Serializable {
	private static final long serialVersionUID = 1L;
	private int number;
	private boolean firedAt;
	private Ship theShip;

	public Cell(int number) {
		setNumber(number);
	}

	// Is there a ship?
	public boolean isThereAShip() {
		if (theShip == null) {
			return false;
		}
		return true;
	}

	public Ship getTheShip() {
		return this.theShip;
	}

	public void setTheShip(Ship theShip) {
		this.theShip = theShip;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isFiredAt() {
		return this.firedAt;
	}

	public void setFiredAt(boolean firedAt) {
		this.firedAt = firedAt;
	}

}
