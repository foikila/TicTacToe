import java.util.Arrays;

public class TicTackToeGame {
	private final static int SIZE = 3;
	private final static char EMPTYBOX = ' ';
	private final static char PLAYER1 = 'X';
	private final static char PLAYER2 = 'O';
	private final static int NROFPLAYERS = 2;
	
	private int turns = 0;	
	/*
	 *  The full game board.
	 */
	private char[][] gameBoard = new char[SIZE][SIZE];
	/*
	 *  Players
	 */
	private Player[] players = new Player[NROFPLAYERS];	
	/*
	 *  Places the marker on right place
	 */
	private void setMarker(int row, int col, char mark) {
		this.gameBoard[row][col] = mark;
	}
	
	TicTackToeGame() {
		this("Player1", "Player2");
	}	
	TicTackToeGame(String player1, String player2) {
		initGame();
		this.players[0] = new Player(player1, 0);
		this.players[1] = new Player(player1, 0);
	}
	/*
	 *  Creates an 2d-array with stuff and shit
	 */
	public void initGame() {
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++) {
				gameBoard[i][j] = EMPTYBOX;
			}
		}
	}
	/*
	 *  Returns the array as a string 
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
	/*
	 *  Checks whether [row][col] is empty and places a new Mark there.
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
	/*
	 *  Check whether some player has tree in row or not
	 */
	public void validate(){
		for(int row = 0; row < SIZE; row++) {
			for(int col = 0; col < SIZE; col++){
				
			}				
		}
	}
	/*
	 *  resets the hole game
	 */
	public void resetGame(String player1, String player2) {
		this.initGame();
		this.players[0] = new Player(player1, 0);
		this.players[1] = new Player(player1, 0);
	}
	public String WhoseTrun() {		
		return turns%2 == 0 ? players[0].getName() : players[1].getName();
	} 
	@Override
	public String toString() {
		return "TicTackToeGame [gameBoard=" + Arrays.toString(gameBoard)
				+ ", players=" + Arrays.toString(players) + "]";
	}

	/**
	 * @return the turns
	 */
	public int getTurns() {
		return turns;
	}
	
	
}
