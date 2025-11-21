package guiStaff;

import database.Database;
import entityClasses.feedback;

//import guiStaff.ViewStaffHome;


/*-********************************************************************************************

The is the controller for staff home 

**********************************************************************************************/


/**********
 * <p> Title: ControllerStaffHome Class</p>
 * 
 * <p> Description: This public class supports the actions of guiStaff including quitting, logging out, 
 * updating, and feedbackPupUp. In this case, there are 5 methods, 4 of which are called in ViewStaffHome.
 * There is one default constructor, and one attribute which is the database for this class. </p>
 *
 */

public class ControllerStaffHome {
	/**********
	 * <p>This is the database connection that ControllerStaffHome uses. It is the one that the whole 
	 * application uses.</p>
	 */

	private static Database theDatabase = applicationMain.FoundationsMain.database;
	/**********
	 * <p> Description: This method is a default constructor of ControllerStaffHome, but since it has no attributes,
	 * it does nothing.
	 * 	 
	 * 
	 */
	public ControllerStaffHome() {
	}
	
	/*-********************************************************************************************

	The methods for buttons
	
	**********************************************************************************************/
	
	/**********
	 * <p> Method: protected feedbackPopUp(entityClasses.Post p) </p>
	 * 
	 * <p> Description: This method is called when a staff member clicks the feedback 
	 * button in ViewStaffHome. It will create the popUp for the staff to write feedback
	 * and call a method to create and save the feedback.
	 * 	 
	 * @param p is the reply that will get feedback.
	 * @return the string verification that the feedback pop up was successful
	 * 
	 */
	protected static String feedbackPopUp(entityClasses.Post p) {
			var ta = new javafx.scene.control.TextArea();
			ta.setPromptText("Write your feedback...");
			var d = new javafx.scene.control.Dialog<String>();
			d.setTitle("New Feedback");
			d.getDialogPane().getButtonTypes().addAll(
			javafx.scene.control.ButtonType.OK, javafx.scene.control.ButtonType.CANCEL);
			d.getDialogPane().setContent(ta);
			d.setResultConverter(bt -> bt == javafx.scene.control.ButtonType.OK ? ta.getText().trim() : null);			d.showAndWait().ifPresent(text -> {
			if (text.isEmpty()) return;
			String staff = ViewStaffHome.theUser.getUserName();
			String student = p.getAuthor();
			createFeedback(text, staff, student);
		});
			return "feedback write shown";
		}
	
	
	/**********
	 * <p> Method: protected String createFeedback(String text, String staff, String student) </p>
	 * 
	 * <p> Description: This method is called when feedbackPopUp has text to create feedback to a 
	 * post. It creates a feedback object and returns a string when feedback is correctly sent.
	 * 	 
	 * @param text is the content of the feedback.
	 * 
	 * @param staff is the staff member that is sending the feedback.
	 * 
	 * @param student is the student that is receiving the feedback.
	 * 
	 * @return string determining if feedback is sent
	 * 
	 */
	protected static String createFeedback(String text, String staff, String student) {
		try {
			if (text.isEmpty()) {
				ViewStaffHome.alert("ERROR", "String empty not accepted");
				return "feedback not sent! empty";
			}
			if (text.length()>700) {
				ViewStaffHome.alert("ERROR", "String over 700 characters not accepted");
				return "feedback not sent! too long";
			}
			var feed = new feedback(0, text, student, staff);
			theDatabase.createFeedback(feed);
			return "feedback sent";
		} catch (java.sql.SQLException ex) {
			ex.printStackTrace();
			return "feedback not sent";
		}
	}
	
	
	/**********
	 * <p> Method: public String showFeedback() </p>
	 * 
	 * <p> Description: This method is called a user clicks the see feedback button. It collects all feedback 
	 * that this user can view and then shows it in the UI</p>
	 * 	 
	 * @return the string verification that the feedback is being shown
	 * 
	 */
	protected static String showFeedback() {
	    try {
	        ViewStaffHome.feedbacks.setAll(theDatabase.listFeedback(ViewStaffHome.theUser.getUserName()));
	        System.out.println("Staff Home loaded " + ViewStaffHome.feedbacks.size() + " feedback(s) [ALL]");
	        ViewStaffHome.selectFirstAndSyncUIFeed();
	        return "All feedback showing";
	    } catch (java.sql.SQLException ex) {
	        ex.printStackTrace();
	        ViewStaffHome.alert("Error", "Failed to load posts");
	        return "no feedback shown";
	    }
	}
	
	/**********
	 * <p> Method: public performUpdate() </p>
	 * 
	 * <p> Description: This method is called when a user clicks account update button. 
	 * This is called in ViewStaffHome.
	 * 	 
	 * 
	 */
	protected static void performUpdate () {
		guiUserUpdate.ViewUserUpdate.displayUserUpdate(ViewStaffHome.theStage, ViewStaffHome.theUser);
	}	

	/**********
	 * <p> Method: public performLogout() </p>
	 * 
	 * <p> Description: This method is called when a user clicks log out button. 
	 * This is called in ViewStaffHome.
	 * 	 
	 * 
	 */
	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewStaffHome.theStage);
	}
	
	/**********
	 * <p> Method: public performQuit() </p>
	 * 
	 * <p> Description: This method is called when a user clicks quit button. 
	 * This is called in ViewStaffHome.
	 * 	 
	 * 
	 */
	protected static void performQuit() {
		System.exit(0);
	}


}
