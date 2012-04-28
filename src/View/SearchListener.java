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
}
