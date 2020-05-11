package Sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SudokuGame {

//	FILE READING
	private static final int FILE_FOUND = 1;
	private static final int FILE_NOT_FOUND = 2;
	private static final int FILE_INVALID = 3;

// FILE WRITING
	private static final int COULDNT_CREATE = 1;
	private static final int COULDNT_WRITE = 2;
	private static final int COULDNT_CLOSE = 3;
	private static final int CLOSED = 4;
	private static final int SUCCESS = 5;

// 	VALIDATIONS		
	// Value Validation
	private static final int INVALID_VALUE = 1;
	private static final int VALUE_OUT_OF_RANGE = 2;
	private static final int VALID_VALUE = 3;

	// Square Value Validation
	private static final int SQUARE_EMPTY = 1;
	private static final int SQUARE_HAS_VALUE = 2;

	// Move Validation
	private static final int INVALID_MOVE = 1;
	private static final int VALID_MOVE = 2;
	private static final int INVALID_ROW = 3;
	private static final int INVALID_COL = 4;
	private static final int VALID_ROW = 5;
	private static final int VALID_COL = 6;

	// check win
	private static final int VALID_ROWS = 1;
	private static final int INVALID_ROWS = 2;
	private static final int INVALID_COLS = 3;
	private static final int VALID_COLS = 4;

	private FileWriter writer;
	private File file;

	private String[][] board = new String[9][9];
	private int[] lastMove = new int[2];

	public String[][] getBoard() {
		return board;
	}

	public void updateBoard(String[][] sBoard) {
		board = sBoard;
	}

	public int[] getLastMove() {
		return lastMove;
	}

	public void setLastMove(int r, int c) {
		lastMove[0] = r;
		lastMove[1] = c;
	}

	public void undoMove() {
		if (lastMove[1] == 0) {
			board[lastMove[0]][lastMove[1]] = "\n*";
		} else {
			board[lastMove[0]][lastMove[1]] = "*";
		}
	}

	public int validateRow(int row, String v) {
		int j = 0;
		while (j < board[row].length) {
			if (v.equals(board[row][j]))
				return INVALID_ROW;
			j++;
		}
		return VALID_ROW;
	}

	public int validateCol(int col, String v) {
		int j = 0;
		while (j < board.length) {
			if (v.equals(board[j][col]))
				return INVALID_COL;
			j++;
		}
		return VALID_COL;
	}

	public int validateValue(String val) {
		try {
			int v = Integer.parseInt(val);
			if (v > 9 || v < 1)
				return VALUE_OUT_OF_RANGE;
			else {
				return VALID_VALUE;
			}
		} catch (NumberFormatException e) {
			return INVALID_VALUE;
		}
	}

	public int validateSquareValue(String square) {
		if (square.trim().equals("*")) {
			return SQUARE_EMPTY;
		}
		return SQUARE_HAS_VALUE;
	}

	public int validateMove(String move) {
		if ((move.trim().length() > 3 || move.trim().length() < 3) || move.indexOf(",") == -1
				|| move.indexOf(",") == 0) {
			return INVALID_MOVE;
		}
		return VALID_MOVE;
	}

	public int checkRows() {
		int total = 0;
		int verifiedRows = 0;
		for (int row = 0; row < board[0].length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				total += Integer.parseInt(board[row][col].trim());
			}
			if (total == 45) {
				verifiedRows += 1;
			}
			total = 0;
		}

		if (verifiedRows == 9) {
			return VALID_ROWS;
		} else
			return INVALID_ROWS;
	}

	public int checkCols() {
		int total = 0;
		int verifiedCols = 0;
		for (int col = 0; col < board[0].length; col++) {
			for (int row = 0; row < board[0].length; row++) {
				total += Integer.parseInt(board[row][col].trim());
			}
			if (total == 45) {
				verifiedCols += 1;
			}
			total = 0;
		}

		if (verifiedCols == 9) {
			return VALID_COLS;
		} else
			return INVALID_COLS;
	}

	public int loadGame(String fileN) {
		try {
			String temp = "";
			File file = new File(fileN);
			Scanner sc = new Scanner(file);
			sc.useDelimiter("~");
			for (int row = 0; row < board.length; row++) {
				for (int col = 0; col < board[0].length; col++) {
					temp = sc.next();
					board[row][col] = temp;
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			return FILE_NOT_FOUND;
		} catch (NoSuchElementException e) {
			return FILE_INVALID;
		}
		return FILE_FOUND;
	}

	public int saveGame(String filename) {
		if (filename.indexOf(".txt") == -1 && !filename.isEmpty()) {
			filename += ".txt";
		}
		try {
			file = new File(filename);
			writer = new FileWriter(file);
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					try {
						writer.write((board[i][j] + "~"));
					} catch (IOException e) {
						e.printStackTrace();
						return COULDNT_WRITE;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return COULDNT_CREATE;
		}
		close();
		return SUCCESS;

	}

	public int close() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return COULDNT_CLOSE;
		}
		return CLOSED;
	}

	public void quitGame() {
		System.exit(1);
	}
}
