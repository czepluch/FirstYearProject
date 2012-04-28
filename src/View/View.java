package View;

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
		mf = new MainFrame(lines, this, this);
		this.listener = listener;
		trie = new TernaryTrie();
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
		System.out.println("Address parsed:\t" + address);
		String value = trie.get(address);
		System.out.println(value);
		if (value != null) {
			int nodeId = Integer.parseInt(value);
			listener.findLocation(nodeId);
		}
	}

	@Override
	public void findDirections(String input1, String input2) {
		String address1 = NewAddressParser.parseAddress(input1);
		String address2 = NewAddressParser.parseAddress(input2);
		String value1 = trie.get(address1);
		String value2 = trie.get(address2);
		if ((value1 != null) && (value2 != null)) {
			int fromId = Integer.parseInt(value1);
			int toId = Integer.parseInt(value2);
			listener.findDirections(fromId, toId);
		}
	}
}
