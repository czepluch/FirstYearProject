package Controller;
import View.*;
import Model.*;
import Global.*;

/*
 * The main class
 * Contains both the View and the Model
 * Listens to the view for changes, then calls the model for response
 */
public class Controller implements ViewListener {
	private Model model;
	private View view;
	
	/*
	 * Constructor for the Controller class
	 */
	public Controller() {
		model = new Model();
		view = new View(this, model.getFilteredEdges());
	}
	
	/*
	 * Is called by the view when the viewbox on the map is updated
	 * Calls for the model to filter the data according to the given viewbox
	 * Then updates the view according to the new data.
	 */
	public void viewboxUpdated() {
		// Call for the model to filter the data according to the given viewbox
		// Update the view according the the new data
		view.updateView(model.getFilteredEdges());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Controller controller = new Controller();
	}

}
