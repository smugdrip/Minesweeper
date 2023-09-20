/**
 * 
 */
package cell;

/**
 * A Cell of the Minesweeper game.
 * 
 * @author John Butterfield
 *
 */
public class Cell {

	/** State of the cell. -1 for bomb, 0-9 representing # of near bombs. */
	private int state;

	/** Boolean representing if a cell has been flagged by the player. */
	private boolean flagged;

	/** Boolean representing if a cell has been clicked by the player. */
	private boolean clicked;

	/**
	 * Construct the Cell. By default: flagged = false, clicked = false.
	 * 
	 * @param nearBomb : number of bombs directly next to this cell, including
	 *                 diagonals.
	 */
	public Cell(int nearBomb) {
		setState(nearBomb);
		flagged = false;
		clicked = false;
	}

	/**
	 * Default constructor for a Cell. Near bombs will be set to zero.
	 */
	public Cell() {
		this(0);
	}

	/**
	 * Set the state of the cell based on the number of nearby bombs.
	 * 
	 * @param nearBomb : number of near bombs.
	 */
	public void setState(int nearBomb) {
		if (nearBomb < -1 || nearBomb > 8) {
			throw new IllegalArgumentException("Invalid number of near bombs");
		} else {
			this.state = nearBomb;
		}
	}

	/**
	 * Set clicked to some value.
	 * 
	 * @param clicked
	 */
	public void setClicked(boolean clicked) {

		this.clicked = clicked;

	}

	/**
	 * Get the state of this cell. -1 = bomb, 0-8 represent the number of near
	 * bombs.
	 * 
	 * @return An integer representing the state of this cell.
	 */
	public int getState() {
		return this.state;
	}

	/**
	 * Get the flagged boolean value.
	 * 
	 * @return Whether or not this cell is flagged.
	 */
	public boolean isFlagged() {
		return this.flagged;
	}

	/**
	 * Get the clicked boolean value.
	 * 
	 * @return Whether or not this cell has been clicked.
	 */
	public boolean isClicked() {
		return this.clicked;
	}

	/**
	 * Set the flagged boolean to some state.
	 * 
	 * @param b
	 */
	public void setFlagged(boolean b) {
		this.flagged = b;

	}

	/**
	 * Set flagged to the opposite boolean value.
	 */
	public void flag() {
		if (flagged) {
			this.flagged = false;
		} else {
			this.flagged = true;
		}

	}

}
