package Controller;
import View.*;
import Model.*;

/*
 * The main class
 * Contains both the View and the Model
 * Listens to the view for changes, then calls the model for response
 */
public class Controller implements ViewListener {
	private Model model;
	private View view;
	
	public Controller() {
		model = new Model();
		view = new View();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Controller controller = new Controller();
	}

}
