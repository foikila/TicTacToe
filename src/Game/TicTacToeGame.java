package Game;
import java.util.Arrays;

public class TicTacToeGame {
	private final static int SIZE = 3;
	private final static char EMPTYBOX = ' ';
	public final static char PLAYER1 = 'X';
	public final static char PLAYER2 = 'O';
	private final static int NR_OF_PLAYERS = 2;
	
	private String winner;
	private int turns = 0;	
    // The full game board.
	private char[][] gameBoard;
	// Players
	private Player[] players;	
	/**
	 * Places the marker on right place
	 * @param row
	 * @param col
	 * @param mark
	 */
	private void setMarker(int row, int col, char mark) {
		this.gameBoard[row][col] = mark;
	}
	public TicTacToeGame() {
		this("Player1", "Player2");
	}	
	public TicTacToeGame(String player1, String player2) {
		initGame();
		this.players[0] = new Player(player1, 0);
		this.players[1] = new Player(player2, 0);
	}
	/**
	 *  Creates an 2d-array with stuff and shit
	 */
	public void initGame() {
		this.gameBoard = new char[SIZE][SIZE];
		this.players = new Player[NR_OF_PLAYERS];
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				gameBoard[i][j] = EMPTYBOX;
			}
		}
	}
	/**
	 * Returns the array as a string
	 * @return the gameboard as a string
	 */
	public String gameBoardToString() {
		String returnStr = "";
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++) {
				returnStr += "[" + gameBoard[i][j] + "]";
			}
			returnStr += "\n";
		}
		return returnStr;
	}
	/**
	 * Checks whether [row][col] is empty and places a new Mark there.
	 * @param row
	 * @param col
	 * @throws Exception
	 */
	public void placeMarker(int row, int col) throws Exception {
		if(this.gameBoard[row][col] != PLAYER1 && this.gameBoard[row][col] != PLAYER2) {
			if(turns%2 == 0) { 
				// player one
				setMarker(row, col, PLAYER1);				
			} else { 
				// player two
				setMarker(row, col, PLAYER2);
			}
			turns++;
		} else {
			throw new Exception("The [" + row + "][" + col + "] was already taken");
		}
	}
	/**
	 * Validates the gameboard and checks for three in row
	 */
	public boolean validate(){
		
		/*
		r
		o
		w
	col	   0  1  2
		0 [0][0][0]
		1 [0][0][0]
        2 [0][0][0]
      */
		char mark = ' ';
		boolean win = false;
		for(int player = 0; player < NR_OF_PLAYERS - 1 && !win; player++) {
			 mark = this.getWhoseMarker(player);
			// TODO validate that this is true! :@		
			for(int row = 0; row < SIZE; row++) {
				if(this.gameBoard[row][0] == mark && this.gameBoard[row][1] == mark && this.gameBoard[row][2] == mark) {
					// horizontal
					win = true;
				} else if(this.gameBoard[0][row] == mark && this.gameBoard[1][row] == mark && this.gameBoard[1][row] ==  mark && this.gameBoard[2][row] == mark) {
					// vertical
					win = true;
				} 
			}
			// diagonal 
			// TODO validate that this is true! :@
			if (this.gameBoard[0][0] == mark && 
				this.gameBoard[1][1] == mark &&
				this.gameBoard[2][2] ==  mark) {				
				win = true;
			} else if(
				this.gameBoard[2][2] == mark && 
				this.gameBoard[1][1] == mark &&
				this.gameBoard[2][2] ==  mark) {
				win = true;
			}
			if(win) {
				this.winner = this.players[player].getName() + " won the game!";
			}
		}
		return win;
	}
	/**
	 * resets the hole game
	 * @param player1
	 * @param player2
	 */
	public void resetGame(String player1, String player2) {
		initGame();
		players[0] = new Player(player1, 0);
		players[1] = new Player(player1, 0);
	}
	public boolean isFull() {
		return this.turns == SIZE * SIZE;
	}
	/**
	 * 
	 * @return name on the player whose turn it is
	 */
	public String WhoseTurn() {		
		return turns%2 == 0 ? players[0].getName() : players[1].getName();
	} 
	public char getWhoseMarker(int who) {
		return turns%2 == 0 ? PLAYER1 : PLAYER2;
	}
	public String getWhoWon() {
		return this.winner;		
	}
	@Override
	public String toString() {
		return "TicTackToeGame [gameBoard=" + Arrays.toString(gameBoard)
				+ ", players=" + Arrays.toString(players) + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(gameBoard);
		result = prime * result + Arrays.hashCode(players);
		result = prime * result + turns;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TicTacToeGame other = (TicTacToeGame) obj;
		if (!Arrays.deepEquals(gameBoard, other.gameBoard)) {
			return false;
		}
		if (!Arrays.equals(players, other.players)) {
			return false;
		}
		if (turns != other.turns) {
			return false;
		}
		return true;
	}
	/**
	 * @return the turns
	 */
	public int getTurns() {
		return turns;
	}
	
	
}
