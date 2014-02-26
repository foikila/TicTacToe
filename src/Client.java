
public class Client {
	
	public static void main(String[] args) {
		TicTackToeGame game = new TicTackToeGame("Jonatan", "Henrik");
		try {
			game.placeMarker(0, 0);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(game.gameBoardToString());
		try {
			game.placeMarker(0, 0);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(game.gameBoardToString());
		try {
			game.placeMarker(1, 0);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println(game.gameBoardToString());
		System.out.println(game.getTurns());
		System.out.println(game.WhoseTrun());
		
	}
}
