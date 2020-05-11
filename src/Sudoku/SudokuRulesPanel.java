package Sudoku;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.awt.FlowLayout;
import java.awt.Dimension;

public class SudokuRulesPanel extends JPanel {
	public SudokuRulesPanel() {
		this.setSize(new Dimension(458, 285));
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JTextPane sudokuRulesText = new JTextPane();
		sudokuRulesText.setEditable(false);
		sudokuRulesText.setText(
				"Sudoku is a logic based, number placement puzzle. The board is a 9 x 9 grid with 81.\r\n\r\n1.You must place the numbers 1 \u2013 9 in each row without repeating a number.\r\n2.You must place the numbers 1 \u2013 9 in each column without repeating a number.\r\n3.You must place the numbers 1 \u2013 9 in each of the marked 3 x 3 boxes without repeating a number.\r\n");
		add(sudokuRulesText);
	}
}
