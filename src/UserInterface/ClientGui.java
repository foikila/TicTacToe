package UserInterface;
import java.io.Serializable;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


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
			switch (e.getActionCommand()) {
			case "x":
				System.out.println("Du tryckte på: X");
				break;
			case "o":
				System.out.println("Du tryckte på: O");
			default:
				break;
			}
			
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
	// TODO FIXA EGNA KNAPPAR? 
	private JButton buttons[];
	
	private ImageIcon defaultIcon;
	private ImageIcon iconPlayer1;
	private ImageIcon iconPlayer2;
	
	private String frameTitle;
	private JButton btn;
	
	public ClientGui(String frameTitle) {
		this.frameTitle = frameTitle;
		
		initiateInstanceVaribales();
		configFrame();
		addComponentsToContentPane();
		buildMenuBar();
	}
	
	private JPanel buildButtons() {
		JPanel buttonArea = new JPanel();
		buttonArea.setLayout(new GridLayout(3, 3));
		
		//JButton btn;
		ButtonListener btnListener = new ButtonListener();
		
		for (int i = 0; i < 9; i++) {
			
			this.btn = new JButton();
			this.btn.setSize(100, 100);
			this.btn.addActionListener(btnListener);
			//this.btn.setText(Integer.toString(i));
			if(i % 2 == 0) {
				this.btn.setIcon(this.iconPlayer1);
				this.btn.setActionCommand("x");
				//btn.setText("X");
			} else {
				this.btn.setIcon(this.iconPlayer2);
				this.btn.setActionCommand("o");
			}
			buttonArea.add(this.btn);	
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
		ClientGui gui = new ClientGui("Gui");
		gui.setVisible(true);
	}
}
