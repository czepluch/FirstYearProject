package Controller;

import View.*;
import Model.*;

/**
 * The main class
 * Contains both the View and the Model
 * Listens to the view for changes, then calls the model for response
 */
public class Controller implements ViewListener {
	private Model model;
	private View view;
	
	/**
	 * Constructor for the Controller class
	 */
	public Controller() {
		try {
			model = new Model();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view = new View(this, model.getFilteredEdges());
	}
	
	/**
	 * Is called by the view when the viewbox on the map is updated
	 * Calls for the model to filter the data according to the given viewbox
	 * Then updates the view according to the new data.
	 */
	public void viewboxUpdated() {
		// Call for the model to filter the data according to the given viewbox
		// Update the view according the the new data
		view.updateView(model.getFilteredEdges(), model.getTrip(), model.getLocation());
	}
	
	@Override
	public void findLocation(int nodeId) {
		model.updateLocation(nodeId);
		view.updateView(model.getFilteredEdges(), null, model.getLocation());
	}

	@Override
	public void findDirections(int fromId, int toId) {
		model.updateTrip(fromId, toId);
		view.updateView(model.getFilteredEdges(), model.getTrip(), null);
	}
	
	@Override
	public void moveToTrip() {
		view.moveTo(model.getUTMTrip());
	}

	@Override
	public void moveToLocation() {
		view.moveTo(model.getUTMLocation());
	}
	
	@Override
	public void setShortSearch() {
		model.setShortSearch();
	}

	@Override
	public void setFastSearch() {
		model.setFastSearch();
	}

	/**
	 * @param args -
	 */
	public static void main(String[] args) {
		new Controller();
	}
}
