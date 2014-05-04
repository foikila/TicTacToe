package UserInterface;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Game.TicTacToeGame;

/**
 * 
 */

/**
 * @author Jonatan Karlsson & Henrik Ölund
 * @year 2014
 * @version 0.1
 */
public class ClientGui extends JFrame implements Serializable {
	private static final long serialVersionUID = 1L;
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
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private boolean isClient;
	private int port;
	private String ip;
	/**
	 * Runnables
	 */
	private Runnable waiting = new Runnable() {
		@Override
		public void run() {
			// disable buttons
			changeButtons(false);

			// Get object from enemy
			ObjectInputStream ois;
			int row = -1;
			int col = -1;
			try {
				ois = new ObjectInputStream(clientSocket.getInputStream());
				Network.Package p = (Network.Package) ois.readObject();
				row = p.getRow();
				col = p.getCol();
				System.out.println("Läste paketet");
				System.out.println(p.toString());
			} catch (IOException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}

			try {
				// Place markers from enemy
				game.placeMarker(row, col);

				// Update gui.
				updateGraphicalGameBoard(row, col);

			} catch (Exception e) {
				e.printStackTrace();
			}

			// Visa att det är användarens tur och enablea knappar
			changeButtons(true);
			System.out.println("Det är din tur...");
		}
	};

	private Runnable accept = new Runnable() {
		@Override
		public void run() {
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.out.println("Clientsocket acceptar serversocket");
				e.printStackTrace();
			}
		}
	};

	/**
	 * Button Listeners
	 * 
	 */
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			// placeMarks(e.getActionCommand().toString());
			play(e.getActionCommand().toString());
		}

	}

	private class MenuListener implements ActionListener {
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

	public void play(String e) {
		if (this.isClient) {
			try {
				// Connectar till servern
				clientSocket = new Socket("10.0.0.44", 4444);
				// clientSocket = new Socket(this.ip, this.port);
				ObjectOutputStream oos;
				int col = 0;
				int row = 0;

				String values[] = e.split(",");
				col = Integer.parseInt(values[0]);
				row = Integer.parseInt(values[1]);

				try {
					oos = new ObjectOutputStream(clientSocket.getOutputStream());
					Network.Package p = new Network.Package(row, col);
					System.out.println("Sending: " + p);
					// sending an object to the server
					oos.writeObject(p);
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				// Skapa ny tråd
				new Thread(waiting).start();

			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this,
						"Error: " + ex.getMessage(), "ALERT",
						JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} else {
			// Im host
			try {
				serverSocket = new ServerSocket(this.port);
				new Thread(accept).start();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.exit(0);
			}
		}
	}

	/**
	 * Sets the icon of the buttons.
	 * 
	 * @param row
	 * @param col
	 */
	public void updateGraphicalGameBoard(int row, int col) {
		if (game.getTurns() % 2 == 0)
			this.btnArray[row][col].setIcon(iconPlayer1);
		else
			this.btnArray[row][col].setIcon(iconPlayer2);
	}

	/**
	 * Sets the buttons to enable/disable
	 * 
	 * @param on
	 */
	private void changeButtons(Boolean on) {
		for (int i = 0; i < this.btnArray.length; i++) {
			for (int j = 0; j < this.btnArray.length; j++) {
				this.btnArray[i][j].setEnabled(on);
			}
		}
	}

	public ClientGui(String frameTitle) {
		this.frameTitle = frameTitle;
		getIfServer();
		initiateInstanceVaribales();
		configFrame();
		addComponentsToContentPane();
		buildMenuBar();
	}

	private void getIfServer() {
		int an = JOptionPane.showConfirmDialog(null, "Connecting to someone?");
		switch (an) {
		case JOptionPane.NO_OPTION:
			try {
				JOptionPane.showMessageDialog(this, "Your ip is "
						+ Inet4Address.getLocalHost().getHostAddress());
			} catch (HeadlessException | UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.isClient = true;
			break;
		case JOptionPane.OK_OPTION:
		default:
			this.isClient = false;
			// get ip and port and stuff
			String ans = JOptionPane.showInputDialog("Host ip:");
			this.ip = ans;
			ans = JOptionPane.showInputDialog("Host port:");
			this.port = Integer.parseInt(ans);
			break;
		}
	}

	public void placeMarks(String e) {
		int col = 0;
		int row = 0;
		String values[] = e.split(",");
		col = Integer.parseInt(values[0]);
		row = Integer.parseInt(values[1]);

		try {
			game.placeMarker(row, col);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(this, e1.getMessage());
			// this.btnArray[row][col].setEnabled(false);
		}

		// Validera
		if (game.validate()) {
			System.out.println(game.getWhoWon());
			System.out.println("Nr of turns played: " + game.getTurns());
		}

	}

	/**
	 * Builds the buttons for the game
	 * 
	 * @return
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

	public static void main(String args[]) {
		ClientGui gui = new ClientGui("Tic-Tac-Toe");
		gui.setVisible(true);
	}
}
