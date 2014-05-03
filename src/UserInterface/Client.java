package UserInterface;
//import Game.TicTacToeGame;

//import java.util.Scanner;
import java.net.*;
import java.io.*;

public class Client 
{
	final static int PORT = 444;
	final static String HOST = "10.0.0.166";
	
	public static void main(String[] args) 
	{
		try {
			Client c = new Client();
			c.run();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	public void run() throws Exception 
	{
		Socket sock = new Socket(HOST, PORT);
		PrintStream ps = new PrintStream(sock.getOutputStream());
		ps.println("Hello to server from client");
		
		InputStreamReader ir = new InputStreamReader(sock.getInputStream());
		BufferedReader br = new BufferedReader(ir);
		String msg = br.readLine();
		System.out.println(msg);
		
		sock.close();
		
	}
	
	
	/*public static void main(String[] args) {
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
	}*/
}

