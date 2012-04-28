package View;

import java.util.ArrayList;

import Model.MapLocation;
import Model.Trip;
import Model.TripEdge;
import Model.Turn;

/**
 * The View/GUI of the application
 * Contains the controller as a listener, which is notified, when updates in the GUI in
 * need of accessing the stored data are needed
 */
public class View implements MapListener, SearchListener {
	private MainFrame mf;
	private ViewListener listener;
	private TernaryTrie trie;
	
	/**
	 * Constructor for the View class
	 * @param listener The ViewListener of the view
	 * @param lines The edges to be drawn
	 */
	public View(ViewListener listener, int[][][] lines) {
		trie = new TernaryTrie();
		mf = new MainFrame(lines, this, this);
		this.listener = listener;
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
	public void updateView(int[][][] lines, Trip trip, MapLocation location) {
		mf.updateMap(lines, trip, location);
	}

	@Override
	public void findLocation(String input) {
		String address = NewAddressParser.parseAddress(input);
		
		System.out.println("Address parsed:\t" + address); // For testing
		
		if (address != null) {
			String value = trie.get(address);
			
			System.out.println(value); // For testing
			
			if (value != null) {
				int nodeId = Integer.parseInt(value);
				listener.findLocation(nodeId);
			}
		}
	}

	@Override
	public void findDirections(String input1, String input2) {
		String address1 = NewAddressParser.parseAddress(input1);
		String address2 = NewAddressParser.parseAddress(input2);
		if ((address1 != null) & address2 != null) {
			String value1 = trie.get(address1);
			String value2 = trie.get(address2);
			if ((value1 != null) && (value2 != null)) {
				int fromId = Integer.parseInt(value1);
				int toId = Integer.parseInt(value2);
				listener.findDirections(fromId, toId);
			}
		}
	}

	@Override
	public void findOptionsFirstList(String input) {
		mf.updateFirstList(findListOptions(input));
	}

	@Override
	public void findOptionsSecondList(String input) {
		mf.updateSecondList(findListOptions(input));
	}
	
	/**
	 * Helper method for finding the content to be shown in the search lists
	 * @param input the input to be interpreted as an address
	 * @return String[] the list of addresses to be displayed
	 */
	private String[] findListOptions(String input) {
		String address = NewAddressParser.parseAddress(input);
		if (address != null) {
			Iterable<String> c = trie.startsWith(input); // Using input instead of address for testing
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
