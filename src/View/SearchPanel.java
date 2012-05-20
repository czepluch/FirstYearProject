package View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Model.Trip;

/*
 * Panel containing components for user interaction other than the map
 */
public class SearchPanel extends JPanel {
	private SearchListener listener;
	private Trip<Integer> trip;
	
	private JPanel inputPanel;
	private JLabel firstLabel;
	private JTextField firstTF;
	private JList firstList;
	
	private JLabel secondLabel;
	private JTextField secondTF;
	private JList secondList;
	
	private JButton swapButton;
	private JButton findButton;
	
	private ButtonGroup buttonGroup;
	private JRadioButton fastRB;
	private JRadioButton shortRB;
	
	private JLabel tripLengthLab;
	private JLabel tripLengthLabVar;
	private JLabel tripTimeLab;
	private JLabel tripTimeLabVar;
	
	/**
	 * Constructor of the SearchPanel class
	 * @param listener the SearchListener to be stored
	 */
	public SearchPanel(SearchListener listener) {
		super();
		
		this.listener = listener;
		
		this.setLayout(new BorderLayout(6, 6));
		
		createInputPanel();
		setButtonListeners();
		setTextFieldListeners();
		setListListeners();
		setRBListeners();
	}
	
	/**
	 * Updates the stored trip according to the given Trip object
	 * @param trip	the Trip object to be stored
	 */
	public void updateTrip(Trip<Integer> trip) {
		this.trip = trip;
		
		if (trip == null) {
			tripLengthLabVar.setText("");
			tripTimeLabVar.setText("");
		} else {
			String distance = String.format("%.2f", trip.getDistance() / 1000);
			String time = String.format("%.2f", trip.getTime());
			tripLengthLabVar.setText(distance + "  km");
			tripTimeLabVar.setText(time + " min");
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
	
	/**
	 * Updates the first text field according to the given string
	 * @param s the string to be inserted
	 */
	public void updateFirstTextField(String s) {
		firstTF.setText(s);
		clearFirstList();
	}
	
	/**
	 * Updates the second text field according to the given string
	 * @param s the string to be inserted
	 */
	public void updateSecondTextField(String s) {
		secondTF.setText(s);
		clearSecondList();
	}
	
	/**
	 * Clears the content of the first list
	 */
	private void clearFirstList() {
		String[] c = new String[0];
		updateFirstList(c);
	}
	
	/**
	 * Clears the content of the second list
	 */
	private void clearSecondList() {
		String[] c = new String[0];
		updateSecondList(c);
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
		
		buttonGroup = new ButtonGroup();
		fastRB = new JRadioButton("Fast");
		fastRB.setSelected(true);
		shortRB = new JRadioButton("Short");
		buttonGroup.add(fastRB);
		buttonGroup.add(shortRB);
		
		tripLengthLab = new JLabel("Distance:");
		tripLengthLabVar = new JLabel("", SwingConstants.RIGHT);
		tripTimeLab = new JLabel("Time:");
		tripTimeLabVar = new JLabel("", SwingConstants.RIGHT);
		
		inputPanel.add(firstLabel);
		inputPanel.add(firstTF);
		inputPanel.add(firstList);
		
		inputPanel.add(secondLabel);
		inputPanel.add(secondTF);
		inputPanel.add(secondList);
		
		inputPanel.add(swapButton, "split 2");
		inputPanel.add(findButton);
		
		inputPanel.add(fastRB, "split 2");
		inputPanel.add(shortRB, "wrap 40");
		
		inputPanel.add(tripLengthLab, "split 2");
		inputPanel.add(tripLengthLabVar, "gapleft push");
		inputPanel.add(tripTimeLab, "split 2");
		inputPanel.add(tripTimeLabVar, "gapleft push");
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
												// Signal to the listener that a search is starting
												listener.startSearching();
												// Start the search
												listener.findLocation(firstTF.getText());
											// Else find directions
											} else {
												// Signal to the listener that a search is starting
												listener.startSearching();
												// Start the search
												listener.findDirections(firstTF.getText(), secondTF.getText());
											}
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
	 * Sets the list listeners
	 */
	private void setListListeners() {
		firstList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!(firstList.getSelectedIndex() == -1)) {
					// Set the text field to contain the corresponding list item
					String s = (String) firstList.getSelectedValue();
					firstTF.setText(s);
					// Clear the list content
					clearFirstList();
				}
			}
		});
		
		secondList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!(secondList.getSelectedIndex() == -1)) {
					// Set the text field to contain the corresponding list item
					String s = (String) secondList.getSelectedValue();
					secondTF.setText(s);
					// Clear the list content
					clearSecondList();
				}
			}
		});
	}
	
	/**
	 * Sets the RadioButton listeners
	 */
	private void setRBListeners() {
		fastRB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.setFastSearch();
			}
		});
		
		shortRB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listener.setShortSearch();
			}
		});
	}
	
	/**
	 * The action of the first text field
	 * Calls the listener for a change in the content of the first list
	 */
	private void firstTFAction() {
		if (firstTF.getText().length() == 0) {
			clearFirstList();
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
			clearSecondList();
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
		clearFirstList();
		clearSecondList();
	}
	
	/**
	 * Main method for testing the SearchPanel
	 * This is only for displaying the layout
	 * No actions will work
	 * @param args - Unused
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new SearchPanel(null));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
