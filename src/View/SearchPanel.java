package View;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import static Global.MinAndMaxValues.*;

/*
 * Panel containing components for user interaction other than the map
 */
public class SearchPanel extends JPanel {
	private SearchListener listener;
	
	private JPanel inputPanel;
	private JLabel firstLabel;
	private JComboBox firstCB;
	private JLabel secondLabel;
	private JComboBox secondCB;
	private JButton swapButton;
	private JButton findButton;
	
	private JPanel tablePanel;
	private JScrollPane tableSP;
	private JTable table;
	
	/*
	 * Constructor
	 */
	public SearchPanel(SearchListener listener) {
		super();
		
		this.listener = listener;
		
		this.setLayout(new BorderLayout(6, 6));
		this.setSize(new Dimension(SEARCH_PANEL_WIDTH, 10));
		
		createInputPanel();
		createTablePanel();
		setButtonListeners();
		setTableListeners();
	}
	
	/*
	 * Creates the panel containing the input fields
	 */
	private void createInputPanel() {
		inputPanel = new JPanel();
		inputPanel.setLayout(new MigLayout("wrap 1"));
		this.add(inputPanel);
		
		Dimension CBSize = new Dimension(180, 10);
		
		firstLabel = new JLabel("Point / from");
		firstCB = new JComboBox();
		firstCB.setPreferredSize(CBSize);
		secondLabel = new JLabel("To");
		secondCB = new JComboBox();
		secondCB.setPreferredSize(CBSize);
		swapButton = new JButton("Swap");
		findButton = new JButton("Find");
		
		inputPanel.add(firstLabel);
		inputPanel.add(firstCB);
		inputPanel.add(secondLabel);
		inputPanel.add(secondCB);
		inputPanel.add(swapButton, "split 2");
		inputPanel.add(findButton);
	}
	
	/*
	 * Creates the panel containing the table showing trip info
	 */
	private void createTablePanel() {
		
	}
	
	/*
	 * Sets the button listeners
	 */
	private void setButtonListeners() {
		
	}
	
	/*
	 * Sets the table listeners
	 */
	private void setTableListeners() {
		// Not yet implemented
	}
}
