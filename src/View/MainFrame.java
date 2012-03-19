package View;

import javax.swing.*;
import java.awt.*;

/*
 * The main GUI containing the MapPanel and the rest of the GUI
 */
public class MainFrame {
	private JFrame frame;
	private JPanel cp;
	
	public MainFrame() {
		frame = new JFrame("Krax");
		frame.setSize(new Dimension(600, 600));
		frame.setResizable(true);
		
		cp = (JPanel) frame.getContentPane();
		
		frame.setVisible(true);
	}
}
