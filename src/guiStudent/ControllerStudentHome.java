package guiStudent;

import database.Database;
import entityClasses.feedback;
import guiStudent.ViewStudentHome;

public class ControllerStudentHome {

	/*-*******************************************************************************************

	User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	 */

	private static Database theDatabase = applicationMain.FoundationsMain.database;
	/**********
	 * <p> Method: public ControllerStaffHome() </p>
	 * 
	 * <p> Description: This method is a default constructor of ControllerStaffHome, but since it has no attributes,
	 * it does nothing.
	 * 	 
	 * 
	 */
	public ControllerStudentHome() {
	}
	
	/*-********************************************************************************************

	The methods for buttons
	
	**********************************************************************************************/
		
	
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
	 * <p> Description: This method is called when feedbackPopUp has text to create feedback to a 
	 * post. It creates a feedback object and returns a string when feedback is correctly sent.
	 * 	 
	 * 
	 * 
	 */
	protected static String showFeedback() {
	    try {
	        ViewStudentHome.feedbacks.setAll(theDatabase.listFeedbackOnly(ViewStudentHome.theUser.getUserName()));
	        System.out.println("Student Home loaded " + ViewStudentHome.feedbacks.size() + " feedback(s) [ALL]");
	        ViewStudentHome.selectFirstAndSyncUIFeed();
	        return "All feedback showing";
	    } catch (java.sql.SQLException ex) {
	        ex.printStackTrace();
	        ViewStudentHome.alert("Error", "Failed to load posts");
	        return "no feedback shown";
	    }
	}
	
	
 	/**********
	 * <p> Method: performLogout() </p>
	 * 
	 * <p> Description: This method logs out the current user and proceeds to the normal login
	 * page where existing users can log in or potential new users with a invitation code can
	 * start the process of setting up an account. </p>
	 * 
	 */
	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewStudentHome.theStage);
	}
	
	
	/**********
	 * <p> Method: performQuit() </p>
	 * 
	 * <p> Description: This method terminates the execution of the program.  It leaves the
	 * database in a state where the normal login page will be displayed when the application is
	 * restarted.</p>
	 * 
	 */	
	protected static void performQuit() {
		System.exit(0);
	}
}
