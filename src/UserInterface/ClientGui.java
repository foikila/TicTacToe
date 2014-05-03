package UserInterface;
import java.io.Serializable;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import Game.TicTacToeGame;


/**
 * 
 */

/**
 * @author Jonatan Karlsson
 * @year 2014
 * @version 0.1
 */
public class ClientGui extends JFrame implements Serializable{
	// TODO auto generate this
	private static final long serialVersionUID = 1L;
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			placeMarks(e.getActionCommand().toString());
			
			
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
	
	
	
	/**
	 *  Constant for easy configuration  
	 */
	private static final boolean RESIZEBLE = false;
	private static final int DEFAULT_CLOSE_OPERATION = WindowConstants.EXIT_ON_CLOSE;
	
	/**
	 * Javax.swings
	 */	
	private Container contentPane;

	private ImageIcon defaultIcon;
	private ImageIcon iconPlayer1;
	private ImageIcon iconPlayer2;
	
	private String frameTitle;
	private JButton[][]btnArray;
	
	private TicTacToeGame game = new TicTacToeGame();
	
	
	public ClientGui(String frameTitle) {
		this.frameTitle = frameTitle;
		
		initiateInstanceVaribales();
		configFrame();
		addComponentsToContentPane();
		buildMenuBar();
	}
	
	public void placeMarks(String e) {
		int col = 0; int row = 0;
		String values[] = e.split(",");
		col = Integer.parseInt(values[0]);
		row = Integer.parseInt(values[1]);
		
		//System.out.println("Col: "+ col);
		//System.out.println("Row: " + row);
		
		
		try {
			game.placeMarker(row, col);
		} catch (Exception e1) {
			// Gör någon felhantering.
			this.btnArray[row][col].setEnabled(false);
		}
		
		// Validera
		if(game.validate()) {
			System.out.println(game.getWhoWon());
			System.out.println("Nr of turns played: " + game.getTurns());
		}
		
	}

	private JPanel buildButtons() {
		JPanel buttonArea = new JPanel();
		buttonArea.setLayout(new GridLayout(3, 3));
		
		//JButton btn;
		ButtonListener btnListener = new ButtonListener();
		int colCount = 0;
		int rowCount = 0;
		for (int i = 0; i < this.btnArray.length; i++) {
			
			for (int j = 0; j < this.btnArray.length; j++) {
				this.btnArray[i][j] = new JButton();
				this.btnArray[i][j].setSize(100, 100);
				this.btnArray[i][j].addActionListener(btnListener);
				this.btnArray[i][j].setActionCommand(Integer.toString(rowCount) + "," + Integer.toString(colCount));
				if(i % 2 == 0) 
					this.btnArray[i][j].setIcon(this.iconPlayer1);
				 else 
					this.btnArray[i][j].setIcon(this.iconPlayer2);
								
				buttonArea.add(this.btnArray[i][j]);
				
				if(rowCount == 2) {
					rowCount = -1;
					colCount++;
				}
				rowCount++;
			}
			
		}
		
		return buttonArea;
	}
	
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
		// Reset Game
		item = new JMenuItem("Reset game");
		gameMenu.add(item);
		item.addActionListener(menuListener);
		
		
		this.setJMenuBar(bar);
	}
	

	private void initiateInstanceVaribales() {
		this.contentPane = this.getContentPane();
		//this.contentPane.setLayout(null);
		this.contentPane.setLayout(new FlowLayout()); // Fungerar
		
		// Icons
		this.iconPlayer1 = new ImageIcon(System.getProperty("user.dir") + "/Images/x.png");
		this.iconPlayer2 = new ImageIcon(System.getProperty("user.dir") + "/Images/o.png");
		
		this.btnArray = new JButton[3][3];
	}

	private void addComponentsToContentPane() {
		this.contentPane.add(buildButtons());
	}
	
	private void configFrame() {
		this.setTitle(this.frameTitle);
		//this.setBounds(100,100,600,600);
		this.setSize(450,480);
		this.setLocationRelativeTo(null);
		this.setResizable(RESIZEBLE);
		this.setDefaultCloseOperation(DEFAULT_CLOSE_OPERATION);
	}
	
	public static void main(String args[]) {
		ClientGui gui = new ClientGui("Tic-Tac-Toe");
		gui.setVisible(true);
	}
}
