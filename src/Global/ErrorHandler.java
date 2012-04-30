package Global;

import javax.swing.JOptionPane;

/**
 * Class with the capability of showing a pop-up
 * warning dialog
 */
public class ErrorHandler {

	/**
	 * Creates a warning pop-up with the given message
	 * @param messageHead	The title of the dialog
	 * @param message		The message of the dialog
	 */
	 public static void showWarning(String messageHead, String message) {
	 	JOptionPane.showMessageDialog(null,
	 								  message,
	 								  messageHead,
	 								  JOptionPane.WARNING_MESSAGE);
	 }
}