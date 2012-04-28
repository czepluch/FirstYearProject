package View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Global.MinAndMaxValues.*;
import Model.Trip;
import Model.TripEdge;
import Model.Turn;
import java.util.List;

/*
 * Panel containing components for user interaction other than the map
 */
public class SearchPanel extends JPanel {
	private TernaryTrie trie;
	
	private SearchListener listener;
	private Trip trip;
	
	private JPanel inputPanel;
	private JLabel firstLabel;
	private JTextField firstTF;
	private JList firstList;
	private JLabel secondLabel;
	private JTextField secondTF;
	private JList secondList;
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
		
		trie = new TernaryTrie();
		
		this.setLayout(new BorderLayout(6, 6));
		
		createInputPanel();
		createTablePanel();
		setButtonListeners();
		setTableListeners();
		setTextFieldListeners();
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
	
	/**
	 * Updates the first list according to the given strings
	 * @param listItems the strings to be displayed
	 */
	public void updateFirstList(String[] listItems) {
		firstList.setListData(listItems);
	}
	
	/**
	 * Updates the second list according to the given strings
	 * @param listItems the strings to be displayed
	 */
	public void updateSecondList(String[] listItems) {
		secondList.setListData(listItems);
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
		firstList = new JList();
		firstList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		firstList.setVisibleRowCount(-1);
		
		secondLabel = new JLabel("To");
		secondTF = new JTextField(textFieldColums);
		secondList = new JList();
		secondList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		secondList.setVisibleRowCount(-1);
		
		swapButton = new JButton("Swap");
		findButton = new JButton("Find");
		
		inputPanel.add(firstLabel);
		inputPanel.add(firstTF);
		inputPanel.add(firstList);
		inputPanel.add(secondLabel);
		inputPanel.add(secondTF);
		inputPanel.add(secondList);
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
		// table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableSP = new JScrollPane(table);
		tableSP.setPreferredSize(new Dimension(250, 400));
		tablePanel.add(tableSP);
	}
	
	/**
	 * Sets the button listeners
	 */
	private void setButtonListeners() {
		// Add ActionListener to swap button
		swapButton.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											swapAction();
										}
									});
		
		// Add ActionListener to find button
		findButton.addActionListener(new ActionListener() {
										@Override
										public void actionPerformed(ActionEvent e) {
											// If only the second text field contains something, swap the fields
											if (firstTF.getText().length() == 0 &&
												secondTF.getText().length() > 0) swapAction();
											
											// If only one field contains something, find a location
											if (firstTF.getText().length() > 0 &&
												secondTF.getText().length() <= 0) {
												listener.findLocation(firstTF.getText());
											// Else find directions
											} else {
												listener.findDirections(firstTF.getText(), secondTF.getText());
											}
										}
									});
	}
	
	/**
	 * Sets the table listeners
	 */
	private void setTableListeners() {
		ListSelectionModel sm = table.getSelectionModel();
		sm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sm.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				int row = table.getSelectedRow();			// Get the selected row
				TripEdge e = trip.getEdges().get(row);		// Find the corresponding edge
				DragHandler.moveTo(e.getToX(), e.getToY());	// Move the view to the end point
			}
		});
	}
	
	/*
	 * Sets the text field listeners
	 */
	private void setTextFieldListeners() {
		// Add a document listener to the first text field
		firstTF.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) { firstTFAction(); }
			
			@Override
			public void insertUpdate(DocumentEvent e) { firstTFAction(); }
			
			@Override
			public void changedUpdate(DocumentEvent e) { firstTFAction(); }
		});
		
		// Add a document listener to the second text field
		secondTF.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) { secondTFAction(); }
			
			@Override
			public void insertUpdate(DocumentEvent e) { secondTFAction(); }
			
			@Override
			public void changedUpdate(DocumentEvent e) { secondTFAction(); }
		});
	}
	
	/**
	 * The action of the first text field
	 * Calls the listener for a change in the content of the first list
	 */
	private void firstTFAction() {
		if (firstTF.getText().length() == 0) {
			String[] clearContent = new String[0];
			updateFirstList(clearContent);
		} else {
			listener.findOptionsFirstList(firstTF.getText());
		}
	}
	
	/**
	 * The action of the second text field
	 * Calls the listener for a change in the content of the second list
	 */
	private void secondTFAction() {
		if (secondTF.getText().length() == 0) {
			String[] clearContent = new String[0];
			updateFirstList(clearContent);
		} else {
			listener.findOptionsSecondList(secondTF.getText());
		}
	}
	
	/**
	 * Swaps the content of the two input text fields
	 */
	private void swapAction() {
		String newFrom = secondTF.getText();
		String newTo = firstTF.getText();
		firstTF.setText(newFrom);
		secondTF.setText(newTo);
	}
}
