package UserInterface;
import Game.TicTackToeGame;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		boolean playAgain = false;		
		String player1 = "", player2 = "";
		Scanner s = new Scanner(System.in);
		System.out.println("player1 name: ");
		player1 =  s.next();
		System.out.println("player2 name: ");
		player2 =  s.next();
		do {
			TicTackToeGame game = new TicTackToeGame(player1, player2);
			int row = 0, col = 0;
			boolean gotWinner = false;
			while(gotWinner != true || !game.isFull()) 
			{
				System.out.println(game.gameBoardToString());
				String who = game.WhoseTurn();
				System.out.println(who + "\nRow: ");
				row = s.nextInt();
				System.out.print("Col: ");
				
				col = s.nextInt();
				try {
					game.placeMarker(row, col);
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}
				// TODO fix so the validate function validates at the right round
				// TODO check vertical validation & diagonal validation
				if(game.validate()) {
					System.out.println(game.getWhoWon());
					System.out.println("nr of turns played: " + game.getTurns());
					gotWinner = true;
				}
			}	
			
			System.out.println("Play again= (y/n)");
			String ans = s.next();
			if(ans == "n" || ans == "N") {
				playAgain = true;
			}
			
		} while(playAgain);
	}
}
