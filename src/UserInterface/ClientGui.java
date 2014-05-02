package UserInterface;
import java.io.Serializable;
import java.awt.Container;
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
	
	
	public ClientGui(String frameTitle) {
		this.frameTitle = frameTitle;
		
		initiateInstanceVaribales();
		configFrame();
		addComponentsToContentPane();
	}

	private void initiateInstanceVaribales() {
		this.contentPane = this.getContentPane();
		this.contentPane.setLayout(null);
	}

	private void addComponentsToContentPane() {
				
	}
	
	private void configFrame() {
		this.setBounds(100,100,600,600);
		this.setTitle(this.frameTitle);
		this.setResizable(RESIZEBLE);
		this.setDefaultCloseOperation(DEFAULT_CLOSE_OPERATION);
	}
}
