package UserInterface;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Game.TicTacToeGame;

/**
 * @author Jonatan Karlsson & Henrik Ölund
 * @year 2014
 * @version 0.1
 */
public class ClientGui extends JFrame implements Serializable {
	private static final long serialVersionUID = 787111024491775161L;
	/**
	 * Constant for easy configuration
	 */
	private static final boolean RESIZEBLE = false;
	private static final int DEFAULT_CLOSE_OPERATION = WindowConstants.EXIT_ON_CLOSE;

	/**
	 * Javax.swings
	 */
	private Container contentPane;
	private ImageIcon iconPlayer1;
	private ImageIcon iconPlayer2;
	private ImageIcon iconDefault;
	private String frameTitle;
	private JButton[][] btnArray;

	/**
	 * Game stuff
	 */
	private static TicTacToeGame game;

	/**
	 * Server client stuff
	 */
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private boolean isClient;
	private int port;
	private String ip;
	private boolean amISending;

	/**
	 * Runnables
	 */
	
	/**
	 * Listen to the socket and places markes 
	 * @return void
	 */
	private Runnable waiting = new Runnable() {
		@Override
		public void run() {

			// Get object from enemy
			Network.Package p;
			int row = -1;
			int col = -1;
			boolean win = false;
			try {

				ObjectInputStream ois = new ObjectInputStream(
						clientSocket.getInputStream());
				// gets package
				do {
					p = (Network.Package) ois.readObject();
				} while (p == null);

				row = p.getRow();
				col = p.getCol();
				win = p.getWin();
				
				
				try {
					// Place markers from enemy
					game.placeMarker(row, col);
					// Update gui.
					updateGraphicalGameBoard();
					
					if (win) {
						JOptionPane.showMessageDialog(null, "The other player won.. the game is now ending");
						System.exit(0);
					}
					if (game.validate()) {
						JOptionPane.showMessageDialog(null, game.getWhoWon());
						System.exit(0);
					}
				} catch (Exception e) {
					System.out.println("Row: " + row + " Col: " + col
							+ " was already taken.");
				}
			} catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			System.out.println(game.gameBoardToString());
			disableButtons(false);
			amISending = true;
		}
	};
	/**
	 * Accepts a connection and starts waiting..
	 * @return void
	 */
	private Runnable accept = new Runnable() {
		@Override
		public void run() {
			try {
				System.out.print(clientSocket);
				clientSocket = serverSocket.accept();
				System.out.println(clientSocket.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			new Thread(waiting).start();
		}
	};

	/**
	 * Button Listeners
	 * 
	 */
	private class ButtonListener implements ActionListener {
		/**
		 * 
		 * @param event
		 */
		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				ObjectOutputStream oos;
				int col = 0;
				int row = 0;
				boolean win = false;
				updateGraphicalGameBoard();

				String values[] = e.getActionCommand().toString().split(",");
				col = Integer.parseInt(values[0]);
				row = Integer.parseInt(values[1]);

				game.placeMarker(row, col);
				
				if (game.validate()) {
					System.out.println("Dog här");
					win = true;
					JOptionPane.showMessageDialog(null, game.getWhoWon());
					//System.exit(0);
				}
				updateGraphicalGameBoard();
				try {
					oos = new ObjectOutputStream(clientSocket.getOutputStream());
					Network.Package p = new Network.Package(row, col, win);
					System.out.println(game.gameBoardToString());

					// sending an object to the server
					oos.writeObject(p);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				// changes the state
				amISending = false;
				disableButtons(true);
				
				if (win) {
					System.exit(0);
				}
				// Skapa ny tråd
				new Thread(waiting).start();

			} catch (Exception ex) {
				ex.printStackTrace();
				System.exit(0);
			}
		}
	}

	private class MenuListener implements ActionListener {
		/**
		 * [actionPerformed description]
		 * @param e [description]
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "Set name":
				System.out.println("Du tryckte på: Set name");
				break;

			case "Reset game":
				System.out.println("Du tryckte på: Reset game");
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Updates the gameboard layout
	 */
	public void updateGraphicalGameBoard() {
		for (int row = 0; row < btnArray.length; row++) {
			for (int col = 0; col < btnArray.length; col++) {
				char temp = game.getMarker(row, col);
				if (temp == TicTacToeGame.PLAYER1) {
					this.btnArray[row][col].setIcon(iconPlayer1);
				} else if (temp == TicTacToeGame.PLAYER2) {
					this.btnArray[row][col].setIcon(iconPlayer2);
				} else {
					this.btnArray[row][col].setIcon(iconDefault);
				}
			}
		}
	}

	/**
	 * Disables the buttons. this is called when it's listening 
	 * @param on Boolean
	 */
	private void disableButtons(Boolean on) {
		// why not :D:D:D:D
		on = on == false ? true : false;
		for (int i = 0; i < this.btnArray.length; i++) {
			for (int j = 0; j < this.btnArray.length; j++) {
				this.btnArray[i][j].setEnabled(on);
			}
		}
	}

	/**
	 * constructor all
	 * @param  frameTitle            
	 */
	public ClientGui(String frameTitle) {
		this.frameTitle = frameTitle;
		initiateInstanceVaribales();
		configFrame();
		addComponentsToContentPane();
		buildMenuBar();
		getIfServer();

		try {
			if (this.isClient) {
				System.out.println(this.ip + " " + this.port);
				clientSocket = new Socket(this.ip, this.port);
			} else {
				serverSocket = new ServerSocket(this.port);
				new Thread(accept).start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
					"ALERT", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	/**
	 * Gets if it is a server 
	 */
	private void getIfServer() {
		int an = JOptionPane.showConfirmDialog(null, "Connecting to someone?");
		switch (an) {
		case JOptionPane.OK_OPTION:
			this.isClient = true;
			this.amISending = true;
			// get ip and port and stuff
			String ans = JOptionPane.showInputDialog("Host ip:");
			this.ip = ans;
			ans = JOptionPane.showInputDialog("Host port:");
			this.port = Integer.parseInt(ans);
			break;
		default:
		case JOptionPane.NO_OPTION:
			this.amISending = false;
			this.port = 4444;
			try {
				JOptionPane.showMessageDialog(this, "Your ip is "
						+ Inet4Address.getLocalHost().getHostAddress()
						+ ":4444");
			} catch (HeadlessException | UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.isClient = false;
			this.disableButtons(true);
			break;
		}
	}

	/**
	 * Builds the buttons 
	 * @return JPanel
	 */
	private JPanel buildButtons() {
		JPanel buttonArea = new JPanel();
		buttonArea.setLayout(new GridLayout(3, 3));

		// JButton btn;
		ButtonListener btnListener = new ButtonListener();
		int colCount = 0;
		int rowCount = 0;
		for (int i = 0; i < this.btnArray.length; i++) {

			for (int j = 0; j < this.btnArray.length; j++) {
				this.btnArray[i][j] = new JButton();
				this.btnArray[i][j].setSize(100, 100);
				this.btnArray[i][j].addActionListener(btnListener);
				this.btnArray[i][j].setActionCommand(Integer.toString(rowCount)
						+ "," + Integer.toString(colCount));
				this.btnArray[i][j].setIcon(this.iconDefault);

				buttonArea.add(this.btnArray[i][j]);

				if (rowCount == 2) {
					rowCount = -1;
					colCount++;
				}
				rowCount++;
			}
		}

		return buttonArea;
	}

	/**
	 * Builds the menu bar
	 */
	private void buildMenuBar() {
		JMenuBar bar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		JMenu profileMenu = new JMenu("Profile");
		bar.add(gameMenu);
		bar.add(profileMenu);

		// Menu item.
		JMenuItem item;
		// MenuListener
		MenuListener menuListener = new MenuListener();

		// PROFILE SETTINGS:
		// Set Name
		item = new JMenuItem("Set name");
		item.addActionListener(menuListener);
		profileMenu.add(item);

		// GAME SETTINGS:
		// Set server
		item = new JMenuItem("Set server");
		gameMenu.add(item);
		item.addActionListener(menuListener);

		// Reset Game
		item = new JMenuItem("Reset game");
		gameMenu.add(item);
		item.addActionListener(menuListener);

		this.setJMenuBar(bar);
	}

	/**
	 * Initiate instance variables
	 */
	private void initiateInstanceVaribales() {
		this.contentPane = this.getContentPane();
		// this.contentPane.setLayout(null);
		this.contentPane.setLayout(new FlowLayout()); // Fungerar

		// Icons
		this.iconPlayer1 = new ImageIcon(System.getProperty("user.dir")
				+ "/Images/x.png");
		this.iconPlayer2 = new ImageIcon(System.getProperty("user.dir")
				+ "/Images/o.png");
		this.iconDefault = new ImageIcon(System.getProperty("user.dir")
				+ "/Images/default.jpg");

		this.btnArray = new JButton[3][3];
		this.game = new TicTacToeGame();
	}

	/**
	 * Add components to the pane.
	 */
	private void addComponentsToContentPane() {
		this.contentPane.add(buildButtons());
	}

	/**
	 * Configure the frame.
	 */
	private void configFrame() {
		this.setTitle(this.frameTitle);
		// this.setBounds(100,100,600,600);
		this.setSize(450, 480);
		this.setLocationRelativeTo(null);
		this.setResizable(RESIZEBLE);
		this.setDefaultCloseOperation(DEFAULT_CLOSE_OPERATION);
	}
	
	/**
	 * [main description]
	 * @param args[] [description]
	 */
	public static void main(String args[]) {
		ClientGui gui = new ClientGui("Tic-Tac-Toe");
		gui.setVisible(true);
	}
}
