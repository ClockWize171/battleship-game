package bsGame;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		displayMenu();
	}

	public static void displayMenu() {
		BattleShip bs = new BattleShip();
		String output, menuAsString;
		int menu;

		do {
			output = "Welcome to Battleship Game\n";
			output += "-----------------------------------------------\n";
			output += "1. Start a new game\n";
			output += "2. Load a saved game\n";
			output += "3. High Score Board\n";
			output += "4. Debug Mode\n";
			output += "5. Exit\n";

			menuAsString = JOptionPane.showInputDialog(null, output, "BattleShip Game", JOptionPane.DEFAULT_OPTION);

			try {
				menu = Integer.parseInt(menuAsString);

				switch (menu) {
				case 1:
					bs.play(false);
					break;

				case 2:
					BattleShip loadedGame = BattleShip.loadGame("saved_game.txt");
					if (loadedGame != null) { 
						bs = loadedGame;
						bs.play(false);
					}
					break;

				case 3:
					JOptionPane.showMessageDialog(null, "High Score Table Here");
					break;

				case 4:
					bs.play(true);
					break;

				case 5:
					JOptionPane.showMessageDialog(null, "Exiting Game...");
					break;

				default:
					JOptionPane.showMessageDialog(null, "Invalid option. Please choose a valid option.", "Error",
							JOptionPane.WARNING_MESSAGE);
					break;
				}
			} catch (Exception error) {
				JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.", "Error",
						JOptionPane.ERROR_MESSAGE);
				menu = 0; // Set to an invalid option to continue the loop
			}

		} while (menu != 5);
	}// end of displayMenu()

}
