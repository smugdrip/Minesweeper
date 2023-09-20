package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import minesweeper.Minesweeper;

/**
 * GUI for mine sweeper game
 * 
 * @author John
 *
 */
public class MinesweeperGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	/** Instance of the minesweeper the GUI will be working with */
	private Minesweeper ms;

	/** Timer that will send a signal every second to update the game timer */
	private Timer timer;

	/** Representing if the game is over or not */
	private boolean gameOver;

	/** Representing if the next click will be the first click of the game or not */
	private boolean firstClick;

	/** 2D Array of jlabels, each holding a cell for the minesweeper board */
	private JLabel[][] jlabels;

	/** Number of flagged cells on the board */
	private JLabel flagLabel;

	/** Timer for the game */
	private JLabel timerLabel;

	/** The time in seconds since the first click was made */
	private int timerCount;
	
	/** Size of one axis of the board */
	private int size;
	
	/** Menu bar to hold some functionality */
	private JMenuBar menuBar;
	
	/** Menu */
	private JMenu menu;
	
	/** Easy difficulty menu option */
	private JMenuItem itemEasy;
	
	/** Intermediate difficulty menu option */
	private JMenuItem itemIntermediate;
	
	/** Expert difficulty menu option */
	private JMenuItem itemExpert;
	
	/** Menu option to show records */
	private JMenuItem itemRecords;
	
	/** Panel that holds all the cells */
	private JPanel boardPanel;
	
	/** Hold the number of flags, timerCount, and the new game button */
	private JPanel infoPanel;
	
	private long startTime;
	
	private long endTime;
	
	private long elapsedTime;

	/** The JFrame for the game */
	private JFrame frame = new JFrame();

	/** Image icons for corresponding states of cells */
//	private ImageIcon unclicked = new ImageIcon(getClass().getClassLoader().getResource("unclicked.png"));
//	private ImageIcon empty = new ImageIcon(getClass().getClassLoader().getResource("empty.png"));
//	private ImageIcon bomb = new ImageIcon(getClass().getClassLoader().getResource("bomb.png"));
//	private ImageIcon flagged = new ImageIcon(getClass().getClassLoader().getResource("flagged.png"));
//	private ImageIcon one = new ImageIcon(getClass().getClassLoader().getResource("one.png"));
//	private ImageIcon two = new ImageIcon(getClass().getClassLoader().getResource("two.png"));
//	private ImageIcon three = new ImageIcon(getClass().getClassLoader().getResource("three.png"));
//	private ImageIcon four = new ImageIcon(getClass().getClassLoader().getResource("four.png"));
//	private ImageIcon five = new ImageIcon(getClass().getClassLoader().getResource("five.png"));
//	private ImageIcon six = new ImageIcon(getClass().getClassLoader().getResource("six.png"));
//	private ImageIcon seven = new ImageIcon(getClass().getClassLoader().getResource("seven.png"));
//	private ImageIcon eight = new ImageIcon(getClass().getClassLoader().getResource("eight.png"));
	
	private ImageIcon unclicked = new ImageIcon(".//res//unclicked.png");
	private ImageIcon empty = new ImageIcon(".//res//empty.png");
	private ImageIcon bomb = new ImageIcon(".//res//bomb.png");
	private ImageIcon flagged = new ImageIcon(".//res//flagged.png");
	private ImageIcon one = new ImageIcon(".//res//one.png");
	private ImageIcon two = new ImageIcon(".//res//two.png");
	private ImageIcon three = new ImageIcon(".//res//three.png");
	private ImageIcon four = new ImageIcon(".//res//four.png");
	private ImageIcon five = new ImageIcon(".//res//five.png");
	private ImageIcon six = new ImageIcon(".//res//six.png");
	private ImageIcon seven = new ImageIcon(".//res//seven.png");
	private ImageIcon eight = new ImageIcon(".//res//eight.png");

	/**
	 * Main method for running the Minesweeper Game
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		new MinesweeperGUI();
	}

	/**
	 * Construct the Minesweeper GUI.
	 */
	public MinesweeperGUI() {

		this.size = 8;
		this.ms = new Minesweeper(size, 10);
		gameOver = false;
		firstClick = true;

		
		

		// Set up the frame
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setTitle("Minesweeper");
		frame.setLocation(100, 100);
		
		createTimer();
		createMenuBar();
		createInfoPanel();
		createBoard();
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);

	}
	
	private void resetFlags() {
		flagLabel.setText("      0      ");
	}
	
	/**
	 * Create the timer to time the player
	 */
	private void createTimer() {
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (timerCount < 10) {
					timerLabel.setText("     00" + timerCount + "     ");
				} else if (timerCount < 100) {
					timerLabel.setText("     0" + timerCount + "     ");
				} else if (timerCount < 999) {
					timerLabel.setText("     " + timerCount + "     ");
				} else {
					timer.stop();
				}
				timerCount++;
			}
		});
		timer.setInitialDelay(0);
		
	}

	/**
	 * Create the info panel at the top of the game interface
	 */
	private void createInfoPanel() {
		
		infoPanel = new JPanel();
		
		JButton newGameButton = new JButton("New game");
		newGameButton.addMouseListener(new NewGameListener());
		newGameButton.setFocusPainted(false);
		
		flagLabel = new JLabel();
		flagLabel.setBorder(BorderFactory.createTitledBorder("Flags"));
		flagLabel.setText("      0      ");
		
		timerLabel = new JLabel();
		timerLabel.setBorder(BorderFactory.createTitledBorder("Time"));
		timerLabel.setText("     000     ");

		infoPanel.add(newGameButton);
		infoPanel.add(flagLabel);
		infoPanel.add(timerLabel);
		
		frame.add(infoPanel);
	}

	/**
	 * Create grid of JLabels and add it to the frame.
	 * Each JLabel will hold a cell.
	 */
	private void createBoard() {
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(size, size));
		jlabels = new JLabel[size][size];
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {

				JLabel cell = new JLabel(unclicked);
				boardPanel.add(cell);

				// Each JLabel in the grid has its own CellListener:
				cell.addMouseListener(new CellListener(cell, row, col));

				jlabels[row][col] = cell;
			}
		}
		frame.add(boardPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Set up the menu bar.
	 */
	private void createMenuBar() {
		menuBar = new JMenuBar();
		menu = new JMenu("Game");
		MenuListener ml = new MenuListener();
		
		itemEasy = new JMenuItem("Beginner");
		itemIntermediate = new JMenuItem("Intermediate");
		itemExpert = new JMenuItem("Expert");
		itemRecords = new JMenuItem("Show records");
		
		
		
		itemEasy.addMouseListener(ml);
		itemIntermediate.addMouseListener(ml);
		itemExpert.addMouseListener(ml);
		itemRecords.addMouseListener(ml);
		
		menu.add(itemEasy);
		menu.add(itemIntermediate);
		menu.add(itemExpert);
		
		menu.addSeparator();
		
		menu.add(itemRecords);
		
		menuBar.add(menu);
		frame.add(menuBar, BorderLayout.NORTH);
			
	}

	/**
	 * Listener for each cell.
	 * 
	 * @author John
	 *
	 */
	class CellListener extends MouseAdapter {

		JLabel label;
		int row;
		int col;

		CellListener(JLabel label, int row, int col) {
			this.label = label;
			this.row = row;
			this.col = col;
		}

		public void mousePressed(MouseEvent e) {

			if (gameOver) {
				return;
			}
			// left click
			if (e.getButton() == MouseEvent.BUTTON1) {
				
				if (firstClick) {
					timer.start();
					startTime = System.currentTimeMillis();
					ms.updateBoard(row, col, 1, firstClick);
					firstClick = false;
				} else {
					ms.updateBoard(row, col, 1);
				}

				// right click
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				ms.updateBoard(row, col, 0);
				flagLabel.setText("      " + ms.getFlagged() + "      " );

			}

			updateImageIcons();

		}

	}

	/**
	 * Listener for the new game button.
	 * 
	 * @author John
	 *
	 */
	class NewGameListener extends MouseAdapter {

		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				newGame();
			}
		}

	}
	
	/**
	 * Listener for the menu bar.
	 * @author John
	 *
	 */
	class MenuListener extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			
			if (e.getButton() == MouseEvent.BUTTON1) {
				
				if (e.getSource() == itemEasy) {
					size = 8;
					ms = new Minesweeper(8, 10);
					frame.remove(boardPanel);
					createBoard();
					frame.pack();
					newGame();
					
				} else if (e.getSource() == itemIntermediate) {
					size = 16;
					ms = new Minesweeper(16, 40);
					frame.remove(boardPanel);
					createBoard();
					frame.pack();
					newGame();
					
				} else if (e.getSource() == itemExpert) {
					size = 22;
					ms = new Minesweeper(22, 80);
					frame.remove(boardPanel);
					createBoard();
					frame.pack();
					newGame();
					
				} else if (e.getSource() == itemRecords) {
					
					JOptionPane.showMessageDialog(frame, createRecordsString(), "Best Times", JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}
	
	/**
	 * Create the string that will display the best times for this minesweeper.
	 * @return
	 */
	private String createRecordsString() {
		String s;
		
	    s =     "Beginner:            " + ms.getEasyRecordTime() + "  seconds" + "       " + ms.getEasyRecordName() + "\n \n";
		s = s + "Intermediate:        " + ms.getMedRecordTime() + "  seconds" + "       " + ms.getMedRecordName() + "\n \n";
		s = s + "Expert:              " + ms.getExpRecordTime() + "  seconds" + "       " + ms.getExpRecordName() + "\n \n";
		return s;
		
		
	}

	/**
	 * Functionality when starting a new game.
	 */
	private void newGame() {
		
		ms.newBoard();
		resetIcons();
		resetTimer();
		resetFlags();
		gameOver = false;
		firstClick = true;
		

	}
	
	/**
	 * Method to reset the timer
	 */
	private void resetTimer() {
		timer.stop();
		timerLabel.setText("     000     ");
		timerCount = 0;
	}

	/**
	 * Update each cell in the GUI with new values from Minesweeper
	 */
	public void updateImageIcons() {
		
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {

				if (ms.getBoard()[x][y].isClicked()) {
					if (ms.getBoard()[x][y].getState() == 0) {
						jlabels[x][y].setIcon(empty);
					} else if (ms.getBoard()[x][y].getState() == 1) {
						jlabels[x][y].setIcon(one);
					} else if (ms.getBoard()[x][y].getState() == 2) {
						jlabels[x][y].setIcon(two);
					} else if (ms.getBoard()[x][y].getState() == 3) {
						jlabels[x][y].setIcon(three);
					} else if (ms.getBoard()[x][y].getState() == 4) {
						jlabels[x][y].setIcon(four);
					} else if (ms.getBoard()[x][y].getState() == 5) {
						jlabels[x][y].setIcon(five);
					} else if (ms.getBoard()[x][y].getState() == 6) {
						jlabels[x][y].setIcon(six);
					} else if (ms.getBoard()[x][y].getState() == 7) {
						jlabels[x][y].setIcon(seven);
					} else if (ms.getBoard()[x][y].getState() == 8) {
						jlabels[x][y].setIcon(eight);
					} else if (ms.getBoard()[x][y].getState() == -1) {
						jlabels[x][y].setIcon(bomb);
						timer.stop();
						bombClicked();

					}
				}

				if (ms.getBoard()[x][y].isFlagged()) {
					jlabels[x][y].setIcon(flagged);
				}

				if (!ms.getBoard()[x][y].isClicked() && !ms.getBoard()[x][y].isFlagged()) {
					jlabels[x][y].setIcon(unclicked);
				}

			}
		}

		if (ms.getClicked() == size * size - ms.numBombs) {
			timer.stop();
			endTime = System.currentTimeMillis();
			winGame();
		}

	}

	/**
	 * Functionality when the player clicks all safe cells. (wins)
	 */
	private void winGame() {

		elapsedTime = endTime - startTime;
		
		String str = "" + elapsedTime;
		str = new StringBuilder(str).insert(str.length()-3, ".").toString();
		
		if (size == 8) {

			if (Long.compare(elapsedTime, ms.getEasyRecordTime() * 1000) < 0) {
				
				String s = JOptionPane.showInputDialog(frame, " New best time for Beginner!  \n \n You won with a time of " + str + " seconds. \n \n Enter your name to save the record, or press cancel:  ");
	
				if (s != null && s.length() != 0) {
					ms.setEasyRecordTime(elapsedTime / 1000);
					ms.setEasyRecordName(s);
				}
				ms.writeNewRecords();
				return;
			}
			
		} else if (size == 16) {
			if (Long.compare(elapsedTime, ms.getMedRecordTime() * 1000) < 0) {
				
				String s = JOptionPane.showInputDialog(frame, " New best time for Intermediate!  \n \n You won with a time of " + str + " seconds. \n \n Enter your name to save the record, or press cancel:  ");
	
				if (s != null && s.length() != 0) {
					ms.setMedRecordTime(elapsedTime / 1000);
					ms.setMedRecordName(s);
				}
				ms.writeNewRecords();
				return;
			}
			
		} else if (size == 22) {
			
			if (Long.compare(elapsedTime, ms.getExpRecordTime() * 1000) < 0) {
				
				String s = JOptionPane.showInputDialog(frame, " New best time for Expert!  \n \n You won with a time of " + str + " seconds. \n \n Enter your name to save the record, or press cancel:  ");
	
				if (s != null && s.length() != 0) {
					ms.setExpRecordTime(elapsedTime / 1000);
					ms.setExpRecordName(s);
				}
				ms.writeNewRecords();
				return;
			}
		}
		
		JOptionPane.showMessageDialog(frame, "You won with a time of " + str + " seconds.");

	}

	/**
	 * Functionality when user clicks a bomb.
	 */
	private void bombClicked() {
		if (!gameOver) {
			gameOver = true;
			JOptionPane.showMessageDialog(frame, "Game over!");
			showBombs();
		}

	}

	/**
	 * Reveal all bombs for when the player loses the game.
	 */
	public void showBombs() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (ms.getBoard()[x][y].getState() == -1) {
					ms.getBoard()[x][y].setClicked(true);
				}
				
			}
		}
		updateImageIcons();
	}

	/**
	 * Set every JLabel to the unclicked state.
	 */
	public void resetIcons() {

		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				if (jlabels[x][y].getIcon() != unclicked) {
					jlabels[x][y].setIcon(unclicked);
				}
				
			}
		}
		

	}
	
}


