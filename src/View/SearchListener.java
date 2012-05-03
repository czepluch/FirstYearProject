package View;

public interface SearchListener {

	/**
	 * Looks up the node id corresponding to the given address and passes on the method call
	 * @param input the address to be looked up
	 */
	public void findLocation(String input);
	
	/**
	 * Looks up the node ids corresponding to the two given addresses and passes on the method call
	 * @param input1 the first address to be looked up
	 * @param input2 the second address to be looked up
	 */
	public void findDirections(String input1, String input2);
	
	/**
	 * Looks up the five addresses which it is most likely the user is looking for
	 * and updates the list accordingly
	 * @param input the input to be interpreted as an address
	 */
	public void findOptionsFirstList(String input);
	
	/**
	 * Looks up the five addresses which it is most likely the user is looking for
	 * and updates the list accordingly
	 * @param input the input to be interpreted as an address
	 */
	public void findOptionsSecondList(String input);
	
	/**
	 * Signals to the SearchListener that a search has started
	 */
	public void startSearching();
}
