package guiStaff;

import java.sql.SQLException;

import database.Database;
import entityClasses.Post;
import entityClasses.feedback;

/*-********************************************************************************************

The is the test for staff giving feedback 

**********************************************************************************************/

/**********
 * <p> Title: TestFeedbackk Class</p>
 * 
 * <p> Description: This  class supports the actions of testing giving feedback to a student from a 
 * staff. In this case, there are 7 methods, 6 of which are simply called by start(). There is one 
 * default constructor and one attribute which is the database to connect to for this class. </p>
 *
 */
class TestFeedbackk {
	/**********
	 * <p>This is the database connection that TestFeedbackk uses. It is the one that the whole 
	 * application uses.</p>
	 */
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	/**********
	 * <p> Description: This method is a default constructor; however, since it is never necessary, it 
	 * does nothing</p>
	 * 
	 * 	 
	 */
	public TestFeedbackk() {
		
	}
	
	/**********
	 * <p> Method: public start() </p>
	 * 
	 * <p> Description: This method is called when a developer wants to test feedback, so it's usually called
	 * within guiStaff.
	 * 	 
	 * 
	 */
	
	public static void start() {
		createFeedback();
		createFeedbackEmpty();
		createFeedbackTooLong();
		feedbackPopupTest();
		feedbackShowTest();
		feedbackShowViewTest();
		
	}
	/**********
	 * <p> Method: private createFeedback() </p>
	 * 
	 * <p> Description: This method is a test for if createFeedback returns the string explaining that 
	 * feedback was sent. It is called by start within this class. 
	 * 
	 */

	private static void createFeedback() {
		String expected  = "feedback sent";
		if (expected.equals(ControllerStaffHome.createFeedback("feedback", "staff", "student"))){
			System.out.println("Test feedback sent passed!");
		} else {
			System.out.println("Test feedback sent failed!");
		}
	}
	/**********
	 * <p> Method: private createFeedbackEmpty() </p>
	 * 
	 * <p> Description: This method is a test for if createFeedback returns the string explaining that 
	 * feedback was not sent due to empty string. It is called by start within this class. 
	 * 
	 */

	private static void createFeedbackEmpty() {
		String expected  = "feedback not sent! empty";
		if (expected.equals(ControllerStaffHome.createFeedback("", "staff", "student"))){
			System.out.println("Test empty feedback not sent passed!");
		} else {
			System.out.println("Test empty feedback not sent failed!");
		}
	}
	/**********
	 * <p> Method: private createFeedbackTooLong() </p>
	 * 
	 * <p> Description: This method is a test for if createFeedback returns the string explaining that 
	 * feedback was not sent due to too long string. It is called by start within this class. 
	 * 
	 */

	private static void createFeedbackTooLong() {
		String expected  = "feedback not sent! too long";
		if (expected.equals(ControllerStaffHome.createFeedback("afgsoufgoafubaosugifgspfygaspfuapsfygapsufvapusfyapsfyapsfhcvfausfpasyfvpasvpasyfapusfvausfasdygvapsufhvpasuyfgapsyfgpasiyfcbapsuyfpasuyfgapusfhcvausyfvpuasyfgasuhfvausfpas8yufgpausfyvaousfvosafyvapusfvpauysvpasygdpaysdausyfasufvasufvca"
				+ "afgsoufgoafubaosugifgspfygaspfuapsfygapsufvapusfyapsfyapsfhcvfausfpasyfvpasvpasyfapusfvausfasdygvapsufhvpasuyfgapsyfgpasiyfcbapsuyfpasuyfgapusfhcvausyfvpuasyfgasuhfvausfpas8yufgpausfyvaousfvosafyvapusfvpauysvpasygdpaysdausyfasufvasufvca"
				+ "afgsoufgoafubaosugifgspfygaspfuapsfygapsufvapusfyapsfyapsfhcvfausfpasyfvpasvpasyfapusfvausfasdygvapsufhvpasuyfgapsyfgpasiyfcbapsuyfpasuyfgapusfhcvausyfvpuasyfgasuhfvausfpas8yufgpausfyvaousfvosafyvapusfvpauysvpasygdpaysdausyfasufvasufvca", "staff", "student"))){
			System.out.println("Test too long feedback not sent passed!");
		} else {
			System.out.println("Test too long feedback not sent failed!");
		}
	}
	
	/**********
	 * <p> Method: private feedbackPopupTest() </p>
	 * 
	 * <p> Description: This method is a test for if the feedback pop up works which is the method where
	 * a staff would click feedback to a post. If the feedback pop up works, a string explaining it would
	 * be returned. It is called by start within this class. </p>
	 * 
	 */

	private static void feedbackPopupTest() {
		String expected  = "feedback write shown";
		Post p = new Post();
		try {
			p = theDatabase.getPostById(0);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		if (expected.equals(ControllerStaffHome.feedbackPopUp(p))){
			System.out.println("Test feedback pop up works!");
		} else {
			System.out.println("Test feedback pop up failed!");
		}
	}
	
	/**********
	 * <p> Method: private feedbackShowTest() </p>
	 * 
	 * <p> Description: This method is a test for if the feedback show method works which shows all
	 * the feedback sent. If the feedback show method works properly, a string explaining that 
	 * feedback showing works would return.</p>
	 * 
	 */

	private static void feedbackShowTest() {
		String expected  = "All feedback showing";
		if (expected.equals(ControllerStaffHome.showFeedback())){
			System.out.println("Test show feedback passed!");
		} else {
			System.out.println("Test show feedback failed!");
		}
	}
	/**********
	 * <p> Method: private feedbackShowViewTest() </p>
	 * 
	 * <p> Description: This method is a test for if the feedback viewer method works which information
	 * on the feedback selected. If the feedback view method works properly, a string explaining that 
	 * feedback view works would return.</p>
	 * 
	 */

	private static void feedbackShowViewTest() {
		String expected  = "feedback viewing";
		feedback f = new feedback();
		try {
			f = theDatabase.listFeedback(ViewStaffHome.theUser.getUserName()).get(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (expected.equals(ViewStaffHome.openFeedbackViewer(f))){
			System.out.println("Test view feedback passed!");
		} else {
			System.out.println("Test view feedback failed!");
		}
	}
	

}
