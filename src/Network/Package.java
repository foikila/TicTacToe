package Network;

import java.io.Serializable;

public class Package implements Serializable {

	private static final long serialVersionUID = -1494999107286302383L;

	private String name;
	private int row;
	private int col;

	Package(int row, int col) {
		this(row, col, "Unknown");
	}

	Package(int row, int col, String name) {
		this.row = row;
		this.col = col;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
