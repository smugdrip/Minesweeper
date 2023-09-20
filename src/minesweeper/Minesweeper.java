/**
 * 
 */
package minesweeper;

import java.util.Random;
import java.util.Scanner;

import cell.Cell;
import io.RecordsIO;

/**
 * @author John
 *
 */
public class Minesweeper {

	/** 2D Array of Cell objects, representing a minesweeper board */
	private Cell[][] board;
	
	/** Size of the board */
	private int size;
	
	/** number of bombs, corresponding to different sizes. */
	public int numBombs;
	
	/** Time in seconds of the easy record in the record file */
	private long easyRecordTime;
	
	/** Name corresponding to the easy record */
	private String easyRecordName;
	
	/** Time in seconds of the medium record in the record file */
	private long medRecordTime;
	
	/** Name corresponding to the medium record */
	private String medRecordName;
	
	/** Time in seconds of the expert record in the record file */
	private long expRecordTime;
	
	/** /** Name corresponding to the expert record */
	private String expRecordName;
	

	/**
	 * Construct a minesweeper
	 */
	public Minesweeper(int size, int numBombs) {
		this.size = size;
		this.numBombs = numBombs;
		this.board = new Cell[size][size];
		newBoard();
		readRecords();

	}
	
	
	/**
	 * Write new records if the player beats a time.
	 */
	public void writeNewRecords() {
		
		String s = "Easy," + easyRecordName + "," + easyRecordTime + ",Intermediate," + medRecordName + "," + medRecordTime + ",Expert," + expRecordName + "," + expRecordTime;
		RecordsIO.writeRecords(s);
	}



	/**
	 * @return the easyRecordTime
	 */
	public long getEasyRecordTime() {
		return easyRecordTime;
	}





	/**
	 * @param easyRecordTime the easyRecordTime to set
	 */
	public void setEasyRecordTime(long easyRecordTime) {
		this.easyRecordTime = easyRecordTime;
	}





	/**
	 * @return the easyRecordName
	 */
	public String getEasyRecordName() {
		return easyRecordName;
	}





	/**
	 * @param easyRecordName the easyRecordName to set
	 */
	public void setEasyRecordName(String easyRecordName) {
		this.easyRecordName = easyRecordName;
	}





	/**
	 * @return the medRecordTime
	 */
	public long getMedRecordTime() {
		return medRecordTime;
	}





	/**
	 * @param medRecordTime the medRecordTime to set
	 */
	public void setMedRecordTime(long medRecordTime) {
		this.medRecordTime = medRecordTime;
	}





	/**
	 * @return the medRecordName
	 */
	public String getMedRecordName() {
		return medRecordName;
	}





	/**
	 * @param medRecordName the medRecordName to set
	 */
	public void setMedRecordName(String medRecordName) {
		this.medRecordName = medRecordName;
	}





	/**
	 * @return the expRecordTime
	 */
	public long getExpRecordTime() {
		return expRecordTime;
	}





	/**
	 * @param expRecordTime the expRecordTime to set
	 */
	public void setExpRecordTime(long expRecordTime) {
		this.expRecordTime = expRecordTime;
	}





	/**
	 * @return the expRecordName
	 */
	public String getExpRecordName() {
		return expRecordName;
	}





	/**
	 * @param expRecordName the expRecordName to set
	 */
	public void setExpRecordName(String expRecordName) {
		this.expRecordName = expRecordName;
	}





	/**
	 * Reset the board array to a fresh lay out of bombs and numbers.
	 */
	public void newBoard() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y] = new Cell();
			}
		}
		generateBombs();
		generateBoard();
	}

	/**
	 * Update the board based on user input. The update originated from the clicked
	 * cell.
	 * 
	 * @param row    : row of clicked cell
	 * @param col    : column of clicked cell
	 * @param action : 1 for click, 0 for flag.
	 */
	public void updateBoard(int x, int y, int action) {
		if (x < 0 || x > size - 1 || y < 0 || y > size - 1) {
			return;
		}

		if (action == 1) {
			if (board[x][y].isFlagged()) {
				return;
			}
			if (board[x][y].getState() == 0) {
				floodFill(x, y);
			} else {
				board[x][y].setClicked(true);
			}

		}
		if (action == 0) {
			if (board[x][y].isClicked()) {
				return;
			}
			board[x][y].flag();
		}

	}
	
	/**
	 * 
	 * @param row
	 * @param col
	 * @param i
	 * @param firstClick
	 */
	public void updateBoard(int row, int col, int i, boolean firstClick) {

		while (board[row][col].getState() == -1) {
			newBoard();
		}
		updateBoard(row, col, i);

	}

	/**
	 * Get the number of clicked cells on the board.
	 * @return the number of clicked cells on the board.
	 */
	public int getClicked() {
		int c = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j].isClicked()) {
					c++;
				}

			}
		}
		return c;
	}

	/**
	 * Get the number of flagged cells on the board.
	 * 
	 * @return the number of flagged cells on the board.
	 */
	public int getFlagged() {
		int f = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j].isFlagged()) {
					f++;
				}

			}
		}
		return f;
	}

	/**
	 * Recursive board clearing algorithm that clears open cells connected to the source cell.
	 * 
	 * @param x
	 * @param y
	 */
	private void floodFill(int x, int y) {
		if (x < 0 || x > size - 1 || y < 0 || y > size - 1) {
			return;
		}
		
		board[x][y].setFlagged(false);

		if (board[x][y].getState() != 0) {
			board[x][y].setClicked(true);
			return;
		}

		if (board[x][y].isClicked()) {
			return;
		}

		board[x][y].setClicked(true);

		floodFill(x + 1, y);
		floodFill(x + 1, y - 1);
		floodFill(x + 1, y + 1);

		floodFill(x - 1, y);
		floodFill(x - 1, y - 1);
		floodFill(x - 1, y + 1);

		floodFill(x, y + 1);
		floodFill(x, y - 1);

	}

	/**
	 * Get the board
	 * 
	 * @return the 2D array of Cells.
	 */
	public Cell[][] getBoard() {
		return this.board;
	}

	/**
	 * Set the game board to an existing 2D array of Cells
	 * 
	 * @param board : A 9x9 2D array of Cells
	 * @throws IllegalArgumentException if the board size is invalid.
	 */
	public void setBoard(Cell[][] board) {
		if (board == null || board.length != size) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < board.length; i++) {
			if (board[i].length != size) {
				throw new IllegalArgumentException();
			}
		}
		this.board = board;
	}

	/**
	 * Generate 10 bombs in random positions on the board.
	 */
	public void generateBombs() {
		Random r = new Random();
		int count = 0;
		while (count < numBombs) {
			int x = r.nextInt(size);
			int y = r.nextInt(size);
			if (board[x][y].getState() == 0) {
				board[x][y].setState(-1);
				count++;
			}
		}
	}
	
	/**
	 * Read the records from a file.
	 */
	public void readRecords() {
		
		String r = RecordsIO.readRecords();
		Scanner s = new Scanner(r);
		
		s.useDelimiter(",");
		if (s.next().equals("Easy")) {
			easyRecordName = s.next();
			easyRecordTime = Long.parseLong(s.next().trim());
		}
		if (s.next().equals("Intermediate")) {
			medRecordName = s.next();
			medRecordTime = Long.parseLong(s.next().trim());
		}
		if (s.next().equals("Expert")) {
			expRecordName = s.next();
			expRecordTime = Long.parseLong(s.next().trim());
		}
		
		s.close();
		
	}

	/**
	 * Count the number of bombs in adjacent cells for each cell that is not a bomb itself.
	 * This method should be used after the bombs have been generated.
	 */
	public void generateBoard() {

		int closeBombs = 0;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				if (board[i][j].getState() == 0) {

					if (i == 0 && j == 0) {
						for (int a = 0; a < 2; a++) {
							for (int b = 0; b < 2; b++) {
								if (board[i + a][j + b].getState() == -1) {
									closeBombs++;
								}
							}
						}

					} else if (i == 0 && j == size - 1) {
						for (int a = 0; a < 2; a++) {
							for (int b = -1; b < 1; b++) {
								if (board[i + a][j + b].getState() == -1) {
									closeBombs++;
								}
							}
						}

					} else if (i == size - 1 && j == 0) {
						for (int a = -1; a < 1; a++) {
							for (int b = 0; b < 2; b++) {
								if (board[i + a][j + b].getState() == -1) {
									closeBombs++;
								}
							}
						}

					} else if (i == size - 1 && j == size - 1) {
						for (int a = -1; a < 1; a++) {
							for (int b = -1; b < 1; b++) {
								if (board[i + a][j + b].getState() == -1) {
									closeBombs++;
								}
							}
						}

					} else if (i == 0) {
						for (int a = 0; a < 2; a++) {
							for (int b = -1; b < 2; b++) {
								if (board[i + a][j + b].getState() == -1) {
									closeBombs++;
								}
							}
						}

					} else if (j == 0) {
						for (int a = -1; a < 2; a++) {
							for (int b = 0; b < 2; b++) {
								if (board[i + a][j + b].getState() == -1) {
									closeBombs++;
								}
							}
						}

					} else if (i == size - 1) {
						for (int a = -1; a < 1; a++) {
							for (int b = -1; b < 2; b++) {
								if (board[i + a][j + b].getState() == -1) {
									closeBombs++;
								}
							}
						}

					} else if (j == size - 1) {
						for (int a = -1; a < 2; a++) {
							for (int b = -1; b < 1; b++) {
								if (board[i + a][j + b].getState() == -1) {
									closeBombs++;
								}
							}
						}

					} else {
						for (int a = -1; a < 2; a++) {
							for (int b = -1; b < 2; b++) {
								if (board[i + a][j + b].getState() == -1) {
									closeBombs++;
								}
							}
						}
					}
					board[i][j].setState(closeBombs);
					closeBombs = 0;
				}
			}
		}
	}
}
