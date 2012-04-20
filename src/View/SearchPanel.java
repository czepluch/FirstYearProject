package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import net.miginfocom.swing.MigLayout;
import java.awt.*;
import static Global.MinAndMaxValues.*;
import Model.Trip;
import Model.TripEdge;
import Model.Turn;
import java.util.List;

/*
 * Panel containing components for user interaction other than the map
 */
public class SearchPanel extends JPanel {
	private SearchListener listener;
	private Trip trip;
	
	private JPanel inputPanel;
	private JLabel firstLabel;
	private JTextField firstTF;
	private JLabel secondLabel;
	private JTextField secondTF;
	private JButton swapButton;
	private JButton findButton;

	private JPanel tablePanel;
	private JScrollPane tableSP;
	private DefaultTableModel tm;
	private JTable table;
	
	/*
	 * Constructor
	 */
	public SearchPanel(SearchListener listener) {
		super();
		
		this.listener = listener;
		
		this.setLayout(new BorderLayout(6, 6));
		
		createInputPanel();
		createTablePanel();
		setButtonListeners();
		setTableListeners();
	}
	
	public void updateTrip(Trip trip) {
		this.trip = trip;
		
		tm.setRowCount(0); // Clear the table model
		
		if (trip != null) {
			List<TripEdge> edges = trip.getEdges();
			String[] tableRows = new String[edges.size()];
			
			for (int i = 0; i < edges.size(); i++) { // Add new data to the table model
				tm.addRow(new String[] { edges.get(i).toString() });
			}
		}
	}
	
	/*
	 * Creates the panel containing the input fields
	 */
	private void createInputPanel() {
		inputPanel = new JPanel();
		inputPanel.setLayout(new MigLayout("wrap 1"));
		this.add(inputPanel, BorderLayout.NORTH);
		
		int textFieldColums = 20;
		
		firstLabel = new JLabel("Point / from");
		firstTF = new JTextField(textFieldColums);
		secondLabel = new JLabel("To");
		secondTF = new JTextField(textFieldColums);
		swapButton = new JButton("Swap");
		findButton = new JButton("Find");
		
		inputPanel.add(firstLabel);
		inputPanel.add(firstTF);
		inputPanel.add(secondLabel);
		inputPanel.add(secondTF);
		inputPanel.add(swapButton, "split 2");
		inputPanel.add(findButton);
	}
	
	/*
	 * Creates the panel containing the table showing trip info
	 */
	private void createTablePanel() {
		tablePanel = new JPanel();
		this.add(tablePanel, BorderLayout.CENTER);
		
		tm = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		tm.addColumn("Directions");
		table = new JTable(tm);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableSP = new JScrollPane(table);
		tableSP.setPreferredSize(new Dimension(250, 400));
		tablePanel.add(tableSP);
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
	
	/*
	 * Sets the text field listeners
	 */
	private void setTextFieldListeners() {
		// Not yet implemented
	}
}
