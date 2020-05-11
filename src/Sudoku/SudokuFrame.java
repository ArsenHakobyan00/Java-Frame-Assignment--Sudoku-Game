package Sudoku;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class SudokuFrame extends JFrame implements ActionListener {
	private SudokuGame sudoku = new SudokuGame();
	private boolean isFileSelected = false;

	private String sudokuFilename;
	private String filename;

	// NumPad
	private String[] numLabel = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	private JButton[] num = new JButton[numLabel.length];
	private JButton numVal = new JButton();

	// Board
	private String[][] boardArr = sudoku.getBoard();
	private JButton[][] boardBtn = new JButton[9][9];
	private JButton currentSquare = new JButton();
	private int[] lastMove = new int[2];

	// Board and numPad panels
	private JPanel numPadPanel = new JPanel();
	private JPanel sudokuBoardPanel = new JPanel();

	// Buttons
	private JButton btnSaveGame = new JButton("Save Game");
	private JButton btnUndoMove = new JButton("Undo Move");
	private JButton btnQuitGame = new JButton("Quit Game");

	// Menu
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenu helpMenu = new JMenu("Help");
	private JMenuItem saveMenuItem = new JMenuItem("Save");
	private JMenuItem exitMenuItem = new JMenuItem("Exit");
	private JMenuItem aboutMenuItem = new JMenuItem("About");
	private JMenu editMenu = new JMenu("Edit");
	private JMenuItem undoMenuItem = new JMenuItem("Undo");
	private JMenu openMenu = new JMenu("Open");
	private JMenuItem sudokuFileMenuItem = new JMenuItem("Open a Sudoku file");
	private JMenuItem defaultFileMenuItem = new JMenuItem("Open the default file");
	private JMenuItem gameRulesMenuItem = new JMenuItem("Game Rules");

	public SudokuFrame() {
		setJMenuBar(menuBar);
		getContentPane().setLayout(null);

		numPadPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		sudokuBoardPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);

		fileMenu.add(saveMenuItem);
		fileMenu.add(openMenu);
		fileMenu.add(exitMenuItem);

		openMenu.add(sudokuFileMenuItem);
		openMenu.add(defaultFileMenuItem);

		editMenu.add(undoMenuItem);

		helpMenu.add(gameRulesMenuItem);
		helpMenu.add(aboutMenuItem);

		numPadPanel.setLayout(new GridLayout(3, 3));
		numPadPanel.setBounds(515, 24, 220, 220);
		getContentPane().add(numPadPanel);

		sudokuBoardPanel.setLayout(new GridLayout(9, 9));
		sudokuBoardPanel.setBounds(28, 24, 429, 429);
		getContentPane().add(sudokuBoardPanel);

		btnSaveGame.setBounds(515, 363, 220, 34);
		btnSaveGame.setBackground(Color.WHITE);
		btnSaveGame.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		btnSaveGame.setFont(new Font("label", Font.PLAIN, 20));
		btnSaveGame.setFocusPainted(false);
		getContentPane().add(btnSaveGame);

		btnUndoMove.setBounds(515, 306, 220, 34);
		btnUndoMove.setBackground(Color.WHITE);
		btnUndoMove.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		btnUndoMove.setFont(new Font("label", Font.PLAIN, 20));
		btnUndoMove.setFocusPainted(false);
		getContentPane().add(btnUndoMove);

		btnQuitGame.setBounds(515, 419, 220, 34);
		btnQuitGame.setBackground(Color.WHITE);
		btnQuitGame.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		btnQuitGame.setFont(new Font("label", Font.PLAIN, 20));
		btnQuitGame.setFocusPainted(false);
		getContentPane().add(btnQuitGame);

		btnQuitGame.addActionListener(this);
		btnUndoMove.addActionListener(this);
		btnSaveGame.addActionListener(this);
		saveMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);
		sudokuFileMenuItem.addActionListener(this);
		defaultFileMenuItem.addActionListener(this);
		undoMenuItem.addActionListener(this);
		aboutMenuItem.addActionListener(this);
		gameRulesMenuItem.addActionListener(this);
	}

	public void generateBoard() {
		for (int i = 0; i < boardArr.length; i++) {
			for (int j = 0; j < boardArr[i].length; j++) {
				boardBtn[i][j] = new JButton(boardArr[i][j]);
				boardBtn[i][j].setFont(new Font("label", Font.PLAIN, 20));
				boardBtn[i][j].setBackground(Color.WHITE);
				boardBtn[i][j].setFocusPainted(false);

				if (sudokuBoardPanel.getComponentCount() >= 81) {
					sudokuBoardPanel.removeAll();
				}

				sudokuBoardPanel.add(boardBtn[i][j]);

				if (!boardBtn[i][j].getText().trim().equals("*")) {
					boardBtn[i][j].setEnabled(false);
				}

				boardBtn[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						for (int i = 0; i < boardBtn.length; i++)
							for (int j = 0; j < boardBtn[i].length; j++) {
								if (e.getSource() == boardBtn[i][j]) {

									if (currentSquare.getForeground() == Color.RED)
										currentSquare.setForeground(Color.BLACK);

									currentSquare = boardBtn[i][j];
									currentSquare.setForeground(Color.RED);
									System.out.println("\n" + currentSquare.getText());
									generateNumPad();

								}

							}
					}
				});
				this.setVisible(true);
			}
		}
	}

	public void generateNumPad() {
		for (int i = 0; i < numLabel.length; i++) {
			num[i] = new JButton(numLabel[i]);
			num[i].setFont(new Font("label", Font.PLAIN, 25));
			num[i].setBackground(Color.WHITE);
			num[i].setFocusPainted(false);

			if (numPadPanel.getComponentCount() >= 9)
				numPadPanel.removeAll();

			numPadPanel.add(num[i]);

			num[i].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < num.length; i++) {
						if (e.getSource() == num[i]) {
							numVal = num[i];
							currentSquare.setForeground(Color.BLACK);
							makeMove();
							for (int j = 0; j < num.length; j++) {
								num[j].setEnabled(false);
							}
						}
					}
					int count = 0;
					for (int x = 0; x < boardBtn.length; x++) {
						for (int y = 0; y < boardBtn[0].length; y++) {
							if (!boardBtn[x][y].getText().equals("*")) {
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
							JOptionPane.showMessageDialog(getContentPane(), "You Won! Thank you for playing.",
									"You won", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(getContentPane(), "You lost. Thank you for playing.",
									"You lost", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			});

			this.setVisible(true);
		}
	}

	public void makeMove() {

		for (int i = 0; i < boardBtn.length; i++) {
			for (int j = 0; j < boardBtn[i].length; j++) {
				if (boardBtn[i][j].equals(currentSquare)) {
					int isValidRow = sudoku.validateRow(i, numVal.getText());
					int isValidCol = sudoku.validateCol(j, numVal.getText());

					if (isValidRow == 3 && isValidCol == 4) {
						JOptionPane.showMessageDialog(this,
								"There is already a " + numVal.getText()
										+ " in that row/column/square. Please Try Again",
								"Invalid Move", JOptionPane.ERROR_MESSAGE);
					} else {
						if (isValidRow == 3) {
							JOptionPane.showMessageDialog(this,
									"There is already a " + numVal.getText() + " in that row. Please Try Again",
									"Invalid Move", JOptionPane.ERROR_MESSAGE);
						} else if (isValidCol == 4) {
							JOptionPane.showMessageDialog(this,
									"There is already a " + numVal.getText() + " in that column. Please Try Again",
									"Invalid Move", JOptionPane.ERROR_MESSAGE);
						}

						else {

							currentSquare.setText(numVal.getText());

							if (currentSquare.equals(boardBtn[i][0])) {
								boardArr[i][j] = "\n" + numVal.getText();
							} else {
								boardArr[i][j] = numVal.getText();
							}
							boardBtn[i][j].setText(boardArr[i][j]);
							lastMove = sudoku.getLastMove();
							if (!boardBtn[lastMove[0]][lastMove[1]].getText().equals("*")) {
								boardBtn[lastMove[0]][lastMove[1]].setEnabled(false);
							}
							sudoku.updateBoard(boardArr);
							sudoku.setLastMove(i, j);
						}
					}
				}
			}

		}

		// Test Array After value change
		System.out.println();
		System.out.println("The board should be: ");
		for (int i = 0; i < boardBtn.length; i++) {
			for (int j = 0; j < boardBtn[i].length; j++) {
				System.out.print(boardBtn[i][j].getText() + " ");
			}
		}

	}

	public static void main(String[] args) {

		SudokuFrame frame = new SudokuFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Sudoku");
		frame.setBounds(100, 100, 782, 536);
		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sudokuFileMenuItem) {
			String directoryName, infilename;
			FileDialog fileDialog = new FileDialog(this, "Select Your Sudoku Filename", FileDialog.LOAD);
			fileDialog.setVisible(true);
			directoryName = fileDialog.getDirectory();
			infilename = fileDialog.getFile();
			if (infilename != null) {
				isFileSelected = true;
				sudokuFilename = directoryName + infilename;
				int loadGameResult = sudoku.loadGame(sudokuFilename);
				if (loadGameResult == 1) {
					setTitle("Sudoku - " + infilename);
					generateBoard();

					if (e.getSource() == btnUndoMove || e.getSource() == undoMenuItem) {
						sudoku.undoMove();
					}

					else if (e.getSource() == btnSaveGame || e.getSource() == saveMenuItem) {
						do {
							filename = JOptionPane.showInputDialog(this, "Please enter a filename for your save file",
									"Filename input", JOptionPane.OK_CANCEL_OPTION);
						} while (filename.trim().isEmpty());
						sudoku.saveGame(filename);
					}

				}

				else if (loadGameResult == 3) {
					JOptionPane.showMessageDialog(this, "Your file is not a Sudoku file", "File invalid",
							JOptionPane.ERROR_MESSAGE);
					isFileSelected = false;
				}

			}

			else {
				int next = JOptionPane.showConfirmDialog(this, "Would you like to open the default file?",
						"Default File", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (next == JOptionPane.YES_OPTION) {
					isFileSelected = true;
					int loadGameResult = sudoku.loadGame("sudoku.txt");
					if (loadGameResult == 1) {
						setTitle("Sudoku - sudoku.txt");
						generateBoard();
						if (e.getSource() == btnUndoMove || e.getSource() == undoMenuItem) {
							sudoku.undoMove();
						} else if (e.getSource() == btnSaveGame || e.getSource() == saveMenuItem) {
							do {
								filename = JOptionPane.showInputDialog(this,
										"Please enter a filename for your save file", "Filename input",
										JOptionPane.OK_CANCEL_OPTION);
							} while (filename.trim().isEmpty());
							sudoku.saveGame(filename);
						}

					} else if (loadGameResult == 3) {
						JOptionPane.showMessageDialog(this,
								"Your file is not a Sudoku file\nPlease choose another file", "File invalid",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}

		else if (e.getSource() == defaultFileMenuItem) {
			isFileSelected = true;
			int loadGameResult = sudoku.loadGame("sudoku.txt");
			if (loadGameResult == 1) {
				setTitle("Sudoku - sudoku.txt");
				generateBoard();
				if (e.getSource() == btnUndoMove || e.getSource() == undoMenuItem) {
					sudoku.undoMove();
					for (int i = 0; i < boardBtn.length; i++) {
						for (int j = 0; j < boardBtn[i].length; j++) {
							System.out.print(boardBtn[i][j].getText() + " ");
						}
					}
				}

				else if (e.getSource() == btnSaveGame || e.getSource() == saveMenuItem) {
					do {
						filename = JOptionPane.showInputDialog(this, "Please enter a filename for your save file",
								"Filename input", JOptionPane.OK_CANCEL_OPTION);
					} while (filename.trim().isEmpty());
					int saveGameResults = sudoku.saveGame(filename);
					if (saveGameResults == 5) {
						JOptionPane.showMessageDialog(this, "Your game file has been saved successfully", "Saved",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this, "Something went wrong while trying to save your file",
								"Error", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}

			else if (loadGameResult == 3) {
				JOptionPane.showMessageDialog(this, "Your file is not a Sudoku file\nPlease choose another file",
						"File invalid", JOptionPane.ERROR_MESSAGE);
				isFileSelected = false;
			}
		}

		else if (e.getSource() == btnQuitGame || e.getSource() == exitMenuItem) {
			if (isFileSelected == true) {
				int next = JOptionPane.showConfirmDialog(btnQuitGame, "Are you sure you want to quit the game?",
						"Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (next == JOptionPane.YES_OPTION)
					sudoku.quitGame();
			} else {
				sudoku.quitGame();
			}
		}

		else if (e.getSource() == gameRulesMenuItem) {
			JOptionPane.showMessageDialog(this, new SudokuRulesPanel(), "Game rules", JOptionPane.PLAIN_MESSAGE);
		}

		else if (e.getSource() == aboutMenuItem) {
			JOptionPane.showMessageDialog(this, new SudokuAboutPanel(), "About", JOptionPane.PLAIN_MESSAGE);
		}

		else if (e.getSource() != sudokuFileMenuItem && isFileSelected == true) {
			if (e.getSource() == btnUndoMove || e.getSource() == undoMenuItem) {
				sudoku.undoMove();
				boardArr = sudoku.getBoard();
				generateBoard();
			} else if (e.getSource() == btnSaveGame || e.getSource() == saveMenuItem) {
				do {
					filename = JOptionPane.showInputDialog(this, "Please enter a filename for your save file",
							"Filename input", JOptionPane.OK_CANCEL_OPTION);
				} while (filename.trim().isEmpty());
				int saveGameResults = sudoku.saveGame(filename);
				if (saveGameResults == 5) {
					JOptionPane.showMessageDialog(this, "Your game file has been saved successfully", "Saved",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Something went wrong while trying to save your file", "Error",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}		

		}

		else {
			JOptionPane.showMessageDialog(this, "The game has not started yet\nPlease select a file", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}
}
