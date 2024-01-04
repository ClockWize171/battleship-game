package bsGame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class BattleShip implements Serializable {
	private static final long serialVersionUID = 1L;
	private Grid theGrid = new Grid();
	final int MAX_GUESSES = 10;
	// store row and columns
	ArrayList<int[]> guessedLocations = new ArrayList<int[]>();
	int points = 0;
	int guess = 0;

	public void play(boolean debugMode) {
		if (guessedLocations.isEmpty()) {
			// Generate ships only if it's a new game
			generateRandomShips(); 
		}
		while (theGrid.areAllShipsSunk() == false && guess < MAX_GUESSES && guessedLocations.size() < MAX_GUESSES) {
			acceptInput(debugMode);
		}

		displayGameSummary();

		if (theGrid.areAllShipsSunk()) {
			JOptionPane.showMessageDialog(null, "Congratulations! You sunk all the ships!");
		} else {
			JOptionPane.showMessageDialog(null, "Game over! You reached the maximum number of guesses.");
		}

		// Delete the saved game after displaying the summary
		deleteSavedGame();
		resetGame();

	}// end of play()

	private void generateRandomShips() {

		// submarine data
		final int SUBMARINE_LENGTH = 3;
		final int SUBMARINE_POINTS = 6;
		// aircraft data
		final int AIRCRAFT_LENGTH = 5;
		final int AIRCRAFT_POINTS = 2;
		// battleship data
		final int BATTLESHIP_LENGTH = 4;
		final int BATTLESHIP_POINTS = 4;
		// destroyer data
		final int DESTROYER_LENGTH = 2;
		final int DESTROYER_POINTS = 8;
		// partrolBoat data
		final int PATROLBOAT_LENGTH = 1;
		final int PATROLBOAT_POINTS = 10;

		Ship[] ships = { new Ship(SUBMARINE_LENGTH, "Submarine", SUBMARINE_POINTS),
				new Ship(AIRCRAFT_LENGTH, "Aircraft Carrier", AIRCRAFT_POINTS),
				new Ship(BATTLESHIP_LENGTH, "Battleship", BATTLESHIP_POINTS),
				new Ship(DESTROYER_LENGTH, "Destroyer", DESTROYER_POINTS),
				new Ship(PATROLBOAT_LENGTH, "Patrol Boat", PATROLBOAT_POINTS) };

		for (Ship ship : ships) {
			GenerateRandomShip.placeShipRandom(theGrid, ship);
		}
	}// end of generateRandomShip

	public void acceptInput(boolean debugMode) {
		int row, column;
		String rowAsString, columnAsString, output;
		int[] guessedPosition;
		String shipPositions = getShipPositions();

		if (debugMode) {
			JOptionPane.showMessageDialog(null, "-Welcome from DEBUG MODE-");
		}

		while (theGrid.areAllShipsSunk() == false && guessedLocations.size() < MAX_GUESSES) {
			do {
				output = "Guess: " + guess + "\n";
				output = output + "Points: " + points + "\n";
				if (debugMode) {
					output = output + shipPositions + "\n";
				}

				// Accept Row
				rowAsString = JOptionPane.showInputDialog(null, output + "Enter row [1-10]: ", "Guess a row!",
						JOptionPane.QUESTION_MESSAGE);
				if (rowAsString == null) {
					// User pressed Cancel
					handleCancel();
					// Exit the method
					return;
				}
				row = Integer.parseInt(rowAsString);
				// Validate maximum and minimum value for row and column
				validateMaxMinInput(row, rowAsString, output, "row");

				// Accept Column
				columnAsString = JOptionPane.showInputDialog(null, output + "Enter column [1-10]: ", "Guess a column!",
						JOptionPane.QUESTION_MESSAGE);
				if (columnAsString == null) {
					// User pressed Cancel
					handleCancel();
					// Exit the method
					return;
				}
				column = Integer.parseInt(columnAsString);

				// Validate maximum and minimum value for row and column
				validateMaxMinInput(column, columnAsString, output, "column");

				// Store guessed position
				guessedPosition = new int[] { row, column };

				// validate same square
				validateSameSquare(guessedPosition);

				// Check if the user has already guessed this square
			} while (hasGuessedCoordinate(guessedPosition));

			guessedLocations.add(new int[] { row, column });
			checkHitOrMiss(row, column, hasGuessedCoordinate(guessedPosition));
		}
		guessedLocations.clear();
	}// end of acceptInput()

	private void displayGameSummary() {
		String output = "You hit these ship:\n\n";
		final int MAX_ROW_COLUMN = 10;
		ArrayList<String> sunkShips = new ArrayList<>();

		for (int loop = 1; loop <= MAX_ROW_COLUMN; loop++) {
			Row tempRow = theGrid.getRow(loop);
			for (Cell tempCell : tempRow.getTheCells()) {
				if (tempCell.isThereAShip()) {
					Ship ship = tempCell.getTheShip();
					if (ship.isSunk() && sunkShips.contains(ship.getType()) == false) {
						output = output + ship.getType() + "\n";
						sunkShips.add(ship.getType());
					}
				}
			}
		}

		output = output + "\nTotal Points: " + points;
		JOptionPane.showMessageDialog(null, output, "Game Summary", JOptionPane.INFORMATION_MESSAGE);
	}// end of displayGameSummary()

	private void resetGame() {
		// reset points
		points = 0;
		// reset guesses
		guess = 0;
		// set new Grid and clear array
		theGrid = new Grid();
		guessedLocations.clear();
	}// end of resetGame()

	private String getShipPositions() {
		final int MAX_ROW_COLUMN = 10;
		String output = "\n";

		// Keep track of displayed ship types
		List<String> displayedShipTypes = new ArrayList<>();

		// loop 10 for rows and column
		for (int loop1 = 1; loop1 <= MAX_ROW_COLUMN; loop1++) {
			Row tempRow = theGrid.getRow(loop1);
			// loop rows
			for (Cell tempCell : tempRow.getTheCells()) {
				// check the ship is sunk
				if (tempCell.isThereAShip() && tempCell.getTheShip().isSunk() == false) {
					Ship ship = tempCell.getTheShip();
					String shipType = ship.getType();

					// Check if the ship type is contain?
					if (displayedShipTypes.contains(shipType) == false) {
						output = output + shipType + "    ";

						// add the positions for the ship to output
						for (int loop2 = 1; loop2 <= MAX_ROW_COLUMN; loop2++) {
							Row shipRow = theGrid.getRow(loop2);
							// loop rows again to get cells
							for (Cell shipCell : shipRow.getTheCells()) {
								// check ships types are equal
								if (shipCell.isThereAShip() && shipCell.getTheShip().getType().equals(shipType)) {
									output = output + "(" + loop2 + "," + shipCell.getNumber() + ") ";
								}
							}
						}
						output = output + "\n";
						// Mark the ship type as displayed
						displayedShipTypes.add(shipType);
					}
				}
			}
		}
		return output;
	}// end of getShipPositions()

	private void validateMaxMinInput(int rowOrColumn, String rowOrColumnAsString, String output,
			String outputRowOrColumn) {
		// did not separate because row & column are same
		final int MAX_ROW_COLUMN = 10;
		final int MIN_ROW_COLUMN = 1;
		while (rowOrColumn > MAX_ROW_COLUMN || rowOrColumn < MIN_ROW_COLUMN) {
			String invalid_input = "Invalid Input! \n";
			rowOrColumnAsString = JOptionPane.showInputDialog(null,
					output + invalid_input + "Please enter " + outputRowOrColumn + " between [1-10] again: ",
					"Invalid Input", JOptionPane.ERROR_MESSAGE);
			rowOrColumn = Integer.parseInt(rowOrColumnAsString);
		}
	}// end of validateMaxMinInput()

	private void validateSameSquare(int[] guessedPosition) {
		if (hasGuessedCoordinate(guessedPosition) == true) {
			JOptionPane.showMessageDialog(null, "Oops! You already fired this square. \n");
		}
	}// end of validateSameSquare

	private boolean hasGuessedCoordinate(int[] guessedPosition) {
		for (int[] coordinate : guessedLocations) {
			if (coordinate[0] == guessedPosition[0] && coordinate[1] == guessedPosition[1]) {
				return true;
			}
		}
		return false;
	}// end of hasGuessedCoordinate()

	private void checkHitOrMiss(int row, int column, boolean sameSqure) {
		boolean hitOrMiss;
		hitOrMiss = this.theGrid.isThereAShipOn(row, column);
		if (hitOrMiss) {
			Ship hitShip = this.theGrid.getShip(row, column);

			if (hitShip.isSunk() == false) {
				JOptionPane.showMessageDialog(null,
						"You hit a (" + hitShip.getType() + ") and gained " + hitShip.getPoints() + " points!");
				// Mark the ship as hit
				hitShip.takeHit();
				// increase points
				points += hitShip.getPoints();
				guess++;
			} else {
				JOptionPane.showMessageDialog(null, "You already sunk that ship!");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Miss!");
			guess++;
		}
	}// end of checkHitOrMiss

	// SAVE AND LOADING RELATED METHODS
	public void saveGame(String fileName) {
		try (ObjectOutputStream saveData = new ObjectOutputStream(new FileOutputStream(fileName))) {
			// save and serialize every BattleShip object (points, guess, ship
			// position and etc.)
			saveData.writeObject(this);
			JOptionPane.showMessageDialog(null, "Game saved successfully.");
		} catch (IOException error) {
			error.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to save the game.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}// end of saveGame()

	public static BattleShip loadGame(String fileName) {
		try (ObjectInputStream loadData = new ObjectInputStream(new FileInputStream(fileName))) {
			return (BattleShip) loadData.readObject();
		} catch (IOException | ClassNotFoundException error) {
			error.printStackTrace();
			JOptionPane.showMessageDialog(null, "There is no previous saved game!", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}// end of loadGame()

	public void deleteSavedGame() {
		File file = new File("saved_game.txt");
		if (file.exists()) {
			file.delete();
		}
	}// end of deleteSavedGame()

	private void handleCancel() {
		int option = JOptionPane.showConfirmDialog(null, "Do you want to save the game?", "Save Game",
				JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			saveGame("saved_game.txt");
		}
		JOptionPane.showMessageDialog(null, "Exiting Game...");
		// Terminate the program
		System.exit(0);
	}// end of handleCancel
	
}
