package bsGame;

import java.io.Serializable;

public class Ship implements Serializable {
	private static final long serialVersionUID = 1L;
	private int length;
	private String type;
	private int points;
	private boolean isSunk;

	public Ship(int length, String type, int points) {
		setLength(length);
		setType(type);
		setPoints(points);
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPoints() {
		return this.points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public boolean isSunk() {
		return this.isSunk;
	}

	public void setSunk(boolean isSunk) {
		this.isSunk = isSunk;
	}

	public void takeHit() {
		if (this.isSunk == false) {
			 // Mark the ship as sunk 
			this.isSunk = true; 
		}
	}
}
