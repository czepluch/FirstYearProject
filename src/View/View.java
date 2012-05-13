package View;

import java.util.ArrayList;

import Model.MapLocation;
import Model.Trip;
import Model.TripEdge;
import Model.Turn;
import Controller.ErrorHandler;

/**
 * The View/GUI of the application
 * Contains the controller as a listener, which is notified, when updates in the GUI in
 * need of accessing the stored data are needed
 */
public class View implements MapListener, SearchListener {
	private MainFrame mf;
	private ViewListener listener;
	private TernaryTrie trie;
	
	private String firstAddress;
	private String secondAddress;
	private boolean isSearching;
	
	private enum InputField { FIRST, SECOND }
	
	/**
	 * Constructor for the View class
	 * @param listener The ViewListener of the view
	 * @param lines The edges to be drawn
	 */
	public View(ViewListener listener, int[][][] lines) {
		trie = new TernaryTrie();
		mf = new MainFrame(lines, this, this);
		this.listener = listener;
		
		firstAddress = "";
		secondAddress = "";
		isSearching = false;
	}
	
	/**
	 * Is invoked by the MapPanel, when the viewbox is changed.
	 * Invokes the viewboxUpdated on the ViewListener (the Controller)
	 */
	public void viewboxUpdated() {
		listener.viewboxUpdated();
	}
	
	/**
	 * Updates the map in the view according to the given data
	 */
	public void updateView(int[][][] lines, Trip<Integer> trip, MapLocation<Integer> location) {
		mf.updateMap(lines, trip, location);
		// Check if the view must move to a newly found location or trip
		if (isSearching) {
			// Reset the isSearching variable
			isSearching = false;
			// Check for what is updated
			if (trip != null) {
				// Move the view to the found trip
				listener.moveToTrip();
			} else if (location != null) {
				// Move the view to the found location
				listener.moveToLocation();
			}
		}
	}
	
	public void moveTo(MapLocation<Double> location) {
		ZoomHandler.zoomTo(location, this);
	}
	
	public void moveTo(Trip<Double> trip) {
		ZoomHandler.zoomTo(trip, this);
	}

	@Override
	public void findLocation(String input) {
		// Trim the input
		input = input.trim();
		
		// Check if the string is the command for activating or
		// deactivating the Rainbow Road easter egg
		if (input.toLowerCase().equals("rainbow road")) {
			// Set the MapPanel class to draw found trips as rainbows
			mf.setRainbow(true);
			// Clear the text field
			mf.updateFirstTextField("");
		} else if (input.toLowerCase().equals("ordinary road")) {
			// Set the MapPanel class to draw found trips as rainbows
			mf.setRainbow(false);
			// Clear the text field
			mf.updateFirstTextField("");
		} else {
			// Check if the input is in binary
			// If it is, convert it
			if (AddressParser.isBinary(input)) {
				input = AddressParser.convertBinaryToString(input);
			}	
		
			int nodeId = getNodeId(input, InputField.FIRST);
			if (nodeId >= 0) {
				mf.updateFirstTextField(firstAddress);
				listener.findLocation(nodeId);
			} else {
				// Show a warning
				ErrorHandler.showWarning("Invalid address", "Could not find the given address");
			}
		}
	}

	@Override
	public void findDirections(String input1, String input2) {
		// Trim the input
		input1 = input1.trim();
		input2 = input2.trim();
		
		// Check if each input is in binary
		// If it is, convert it
		if (AddressParser.isBinary(input1)) {
			input1 = AddressParser.convertBinaryToString(input1);
		}
		if (AddressParser.isBinary(input2)) {
			input2 = AddressParser.convertBinaryToString(input2);
		}
		
		int fromId = getNodeId(input1, InputField.FIRST);
		int toId = getNodeId(input2, InputField.SECOND);
		if ((fromId >= 0) && (toId >= 0)) {
			// Update the valid fields and notify the ViewListener
			mf.updateFirstTextField(firstAddress);
			mf.updateSecondTextField(secondAddress);
			listener.findDirections(fromId, toId);
		} else {
			if ((fromId >= 0) && (toId < 0)) {
				// Show a warning but update the valid field
				mf.updateFirstTextField(firstAddress);
				ErrorHandler.showWarning("Invalid address", "Could not find the second of the given addresses");
			} else if ((fromId < 0) && (toId >= 0)) {
				// Show a warning but update the valid field
				mf.updateSecondTextField(secondAddress);
				ErrorHandler.showWarning("Invalid address", "Could not find the first of the given addresses");
			} else {
				// Show a warning
				ErrorHandler.showWarning("Invalid addresses", "Could not find any of the given addresses");
			}
		}
	}
	
	@Override
	public void startSearching() {
		isSearching = true;
	}
	
	/**
	 * Gets the node id corresponding to the input
	 * @param input the input to be interpreted
	 * @return	the found nodeId
	 * 			-1 if no such is found
	 */
	private int getNodeId(String input, InputField IF) {
		// First check if the given address is stored
		// Parse the address
		String address = AddressParser.parseAddress(input);
		// Get the id
		String id = null;
		if (address != null) id = trie.get(address);
		// If the id is not null, return it
		if (id != null) {
			// First store the address in the correct field
			String displayedAddress = cleanString(address);
			switch (IF) {
				case FIRST:	firstAddress = displayedAddress; break;
				default:	secondAddress = displayedAddress; break;
			}
			return Integer.parseInt(trie.get(address));
		}
		// Else the address is not stored. Instead, do a prefix
		// search on the given string
		// Find the list of "recognized" address
		String[] s = findListOptions(input);
		// If no address is recognized, return -1
		if (s.length == 0) return -1;
		// Else store the address in the correct field
		switch (IF) {
			case FIRST:	firstAddress = s[0]; break;
			default:	secondAddress = s[0]; break;
		}
		// Parse the address which is most likely to be
		// correct (the one at index 0) and get it
		address = AddressParser.parseAddress(s[0]);
		// Make sure the address is valid
		if (address == null) return -1;
		return Integer.parseInt(trie.get(address));
	}

	@Override
	public void findOptionsFirstList(String input) {
		mf.updateFirstList(findListOptions(input));
	}

	@Override
	public void findOptionsSecondList(String input) {
		mf.updateSecondList(findListOptions(input));
	}
	
	@Override
	public void setShortSearch() {
		listener.setShortSearch();
	}

	@Override
	public void setFastSearch() {
		listener.setFastSearch();
	}
	
	/**
	 * Helper method for finding the content to be shown in the search lists
	 * @param input the input to be interpreted as an address
	 * @return String[] the list of addresses to be displayed
	 */
	private String[] findListOptions(String input) {
		String address = AddressParser.parseAddressLive(input);
		if (address != null) {
			Iterable<String> c = trie.startsWith(address);
			if (c != null) {
				int maxItems = 5;
				ArrayList<String> listItems = new ArrayList<String>();
				int counter = 0;
				for (String s : c) {
					listItems.add(s);
					counter++;
					if (!(counter < maxItems)) break;
				}
				String[] liStrings = new String[listItems.size()];
				for (int i = 0; i < listItems.size(); i++) {
					// Clean up each string
					liStrings[i] = cleanString(listItems.get(i));
				}
				return liStrings;
			}
		}
		return new String[0];
	}
	
	/**
	 * First removes empty parts of the address.
	 * Then makes each word in the string start with a capitalized letter
	 * and replace "#" with ", "
	 * @param s the String to be cleaned
	 * @return the cleaned up String
	 */
	private String cleanString(String s) {
		// First find the first non-empty part
		String[] parts = s.split("#");
		
		int h = 0;
		while (h < parts.length) {
			if (parts[h].length() > 0) break;
			h++;
		}
		
		// Put the string back together, replacing the "#" with ", "
		s = parts[h];
		for (int k = h + 1; k < parts.length; k++) {
			if (parts[k].length() > 0) s+= ", " + parts[k];
		}
		
		// Make each word in the string start with a capitalised letter
		parts = s.split(" ");
		if (parts.length > 0) {
			for (int i = 0; i < parts.length; i++) {
				String part = parts[i];
				if (part.length() > 0) {
					parts[i] = part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase();
				}
			}
			
			// Put the string back together
			s = parts[0];
			for (int j = 1; j < parts.length; j++) {
				s += " " + parts[j];
			}
		}
		return s;
	}
}
