/**
 * 
 */
package minesweeper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cell.Cell;

/**
 * @author John
 *
 */
class MinesweeperTest {
	
	private Cell[][] testBoard1 = {
			{new Cell(1), new Cell(1), new Cell(1), new Cell(1), new Cell(2), new Cell(1), new Cell(1), new Cell(0), new Cell(0)},
			{new Cell(-1), new Cell(2), new Cell(2), new Cell(-1), new Cell(2), new Cell(-1), new Cell(1), new Cell(0), new Cell(0)},
			{new Cell(1), new Cell(2), new Cell(-1), new Cell(2), new Cell(2), new Cell(1), new Cell(1), new Cell(1), new Cell(1)},
			{new Cell(1), new Cell(2), new Cell(1), new Cell(1), new Cell(0), new Cell(0), new Cell(0), new Cell(1), new Cell(-1)},
			{new Cell(-1), new Cell(1), new Cell(0), new Cell(0), new Cell(1), new Cell(1), new Cell(1), new Cell(2), new Cell(2)},
			{new Cell(1), new Cell(1), new Cell(0), new Cell(0), new Cell(1), new Cell(-1), new Cell(1), new Cell(1), new Cell(-1)},
			{new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(1), new Cell(1), new Cell(2), new Cell(2), new Cell(2)},
			{new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(2), new Cell(-1), new Cell(2)},
			{new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(2), new Cell(-1), new Cell(2)}
	};
	
	private Cell[][] testBoard1Bombs = {
			{new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
			{new Cell(-1), new Cell(0), new Cell(0), new Cell(-1), new Cell(0), new Cell(-1), new Cell(0), new Cell(0), new Cell(0)},
			{new Cell(0), new Cell(0), new Cell(-1), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
			{new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(-1)},
			{new Cell(-1), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0)},
			{new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(-1), new Cell(0), new Cell(0), new Cell(-1)},
			{new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(1), new Cell(0), new Cell(0), new Cell(0)},
			{new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(-1), new Cell(0)},
			{new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(0), new Cell(-1), new Cell(0)}
	};
	

	
	
	
	/**
	 * Test generate board
	 */
	@Test
	void testGenerateBombs() {
		
		Minesweeper ms = new Minesweeper(9, 10);
		
		Cell[][] board = ms.getBoard();
		
		int bombCount = 0;
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j].getState() == -1) {
					bombCount++;
				}
			}
		}
		
		assertEquals(10, bombCount);
		
	}
	
	/**
	 * Test generate board
	 */
	@Test
	void testGenerateNumbers() {
		
		Minesweeper ms = new Minesweeper(9, 10);
		
		ms.setBoard(testBoard1Bombs);
		ms.generateBoard();
		
		Cell[][] realBoard = ms.getBoard();
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				
				assertEquals(realBoard[i][j].getState(), testBoard1[i][j].getState());
			}
		}
		
	}
	
//	/**
//	 * Test generate board
//	 */
//	@Test
//	void testGenerateNumbers2() {
//		
//		Minesweeper ms = new Minesweeper();
//		
//		ms.setBoardArray(testBoardBombs_2);
//		ms.generateNumbers();
//		
//		int[][] realBoard = ms.getBoardArray();
//		
//		for (int i = 0; i < 9; i++) {
//			for (int j = 0; j < 9; j++) {
//				
//				assertEquals(realBoard[i][j], testBoardFull_2[i][j]);
//			}
//		}
//		
//	}
//	
//	/**
//	 * Test getCell
//	 */
//	@Test
//	void testGetCell() {
//		
//		Minesweeper ms = new Minesweeper();
//		
//		ms.setBoardArray(testBoardFull_2);
//		assertEquals(-1, ms.getCell(0, 0).getCellId());
//		
//	}
	
}
