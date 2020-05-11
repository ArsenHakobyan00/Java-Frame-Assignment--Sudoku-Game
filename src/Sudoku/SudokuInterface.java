package Sudoku;

import java.util.Scanner;

public class SudokuInterface {

	public static void main(String[] args) {

		boolean go = false;
		SudokuGame sudoku = new SudokuGame();
		Scanner input = new Scanner(System.in);
		String filename = "";

		System.out.println("Welcome to Heritage Sudoku");
		System.out.print("\nPlease enter the filename for your puzzle: ");
		filename = input.nextLine();

		if (filename.trim().isEmpty()) {
			System.err.println("No file name entered. Opening the default sudoku.txt file...");
			filename = "sudoku.txt";
		} else if (filename.indexOf(".txt") == -1 && !filename.isEmpty()) {
			filename += ".txt";
		}
		while (sudoku.loadGame(filename) != 1) {
			System.out.println("\"" + filename + "\" does not exist");
			System.out.print("Please enter the filename for your puzzle: ");
			filename = input.nextLine();
			if (filename.trim().isEmpty()) {
				System.err.println("No file name entered. Opening the default sudoku.txt file...");
				filename = "sudoku.txt";
			}
		}

		System.out.println("\nType Q at any time to exit, S to save the game or U to undo your last move.\n");

		String[][] myBoard = sudoku.getBoard();

		for (int x = 0; x < myBoard.length; x++) {
			for (int y = 0; y < myBoard[0].length; y++) {
				System.out.print(myBoard[x][y] + " ");
			}
		}

		System.out.println();
		do {
			System.out.print("\nEnter a square number (row, column) -> ");
			String square = input.next();

			if (square.equalsIgnoreCase("q")) {
				System.err.println("Exiting...");
				System.out.println("Have a nice day!");
				sudoku.quitGame();

			} else if (square.equalsIgnoreCase("s")) {
				System.out.print("Please enter the name of your new save file: ");
				filename = input.next();
				sudoku.saveGame(filename);
			} else if (square.equalsIgnoreCase("u")) {
				sudoku.undoMove();
			} else if (sudoku.validateMove(square) == 1) {
				System.err.print("\nThe square number you entered is invalid. Please try again...");
				System.err.println();
			} else {
				int row = Integer.parseInt(square.trim().substring(0, square.indexOf(",")));
				int col = Integer.parseInt(square.trim().substring(square.indexOf(",") + 1, square.length()));

				if (sudoku.validateSquareValue(myBoard[row - 1][col - 1]) == 2) {
					System.err.print("\nThe square already has a value...");
					System.err.print("\nPlease choose another square");
					System.err.println();
				} else {
					String val = "";
					int isValid = 0;
					System.out.print("\nEnter value (1-9): ");
					do {
						val = input.next();
						isValid = sudoku.validateValue(val);
						switch (isValid) {
						case 1:
							System.err.println(val + " is not a number...");
							System.err.println("Please enter a valid value");
							System.out.print("Enter value (1-9): ");
							break;
						case 2:
							System.err.println(val + " is out of range.");
							System.err.println("Your value needs to be between 1 and 9");
							System.out.print("Enter value (1-9): ");
							break;
						case 3:
							int resultRow = sudoku.validateRow(row - 1, val);
							int resultCol = sudoku.validateCol(col - 1, val);

							if (resultRow == 5 && resultCol == 6) {
								System.out.println("All good");
								if (col == 1) {
									myBoard[row - 1][col - 1] = "\n" + val;
								} else {
									myBoard[row - 1][col - 1] = val;
								}
								sudoku.updateBoard(myBoard);
								sudoku.setLastMove(row - 1, col - 1);
								int count = 0;
								for (int x = 0; x < myBoard.length; x++) {
									for (int y = 0; y < myBoard[x].length; y++) {
										if (!myBoard[x][y].equals("*")) {
											count++;
										} else {
											break;
										}
									}
								}
								if (count == 81) {
									int checkRows = sudoku.checkRows();
									int checkCols = sudoku.checkCols();

									if (checkRows == 1 && checkCols == 4) {
										System.out.println("You Won! Thank you for playing.");
									} else {
										System.err.println("You lost. Thank you for playing.");
									}
								}
							} else {
								if (resultRow == 3 && resultCol == 4) {
									System.err.println("Invalid Move. There is already a " + val
											+ " in that row/column/square. Please Try Again");
								} else {
									if (resultRow == 3) {
										System.err.println("Invalid Move. There is already a " + val
												+ " in that row. Please Try Again");
									}
									if (resultCol == 4) {
										System.err.println("Invalid Move. There is already a " + val
												+ " in that column. Please Try Again");
									}
								}
								System.err.println();
							}
							break;
						default:
							System.err.println("Something went wrong...");
							System.err.println("Please reenter your value");
						}// switch (isValid)
					} // do
					while (isValid != 3);

				} // else
			} // else

			System.out.println();
			for (int x = 0; x < myBoard.length; x++) {
				for (int y = 0; y < myBoard[0].length; y++) {
					System.out.print(myBoard[x][y] + " ");
				}
			}
			System.out.println();

		} // do
		while (go != true);
		input.close();
	}
}
