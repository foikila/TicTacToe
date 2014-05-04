package Network;

import Game.Player;
import java.io.Serializable;

public class Package implements Serializable {

	private static final long serialVersionUID = -1494999107286302383L;

	private Player player;
	private int row;
	private int col;

	public Package(int row, int col) {
		this(row, col, new Player());
	}

	public Package(int row, int col, Player player) {
		this.row = row;
		this.col = col;
		this.player = player;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public String toString() {
		return "Package [player=" + player + ", row=" + row + ", col=" + col
				+ "]";
	}

}
