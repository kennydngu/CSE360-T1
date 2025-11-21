package guiStudent;

import java.sql.SQLException;

import database.Database;
import entityClasses.feedback;

/*-********************************************************************************************

The is the test for staff giving feedback 

**********************************************************************************************/

/**********
 * <p> Title: TestFeedback Class</p>
 * 
 * <p> Description: This  class supports the actions of testing receiving feedback from 
 * staff. In this case, there are 3 methods, 2 of which are simply called by start(). There is one 
 * default constructor and one attribute which is the database to connect to for this class. </p>
 *
 */
class TestFeedback {
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
	public TestFeedback() {
		
	}
	/**********
	 * <p> Method: public start() </p>
	 * 
	 * <p> Description: This method is called when a developer wants to test posts, so it's usually called
	 * within guiStudent.
	 * 	 
	 *
	 */
	public static void start() {
		feedbackShowTest();
		feedbackShowViewTest();
	}
	
	/**********
	 * <p> Method: private feedbackShowTest() </p>
	 * 
	 * <p> Description: This method is a test for if the feedback show method works which shows all
	 * the feedback sent. If the feedback show method works properly, a string explaining that 
	 * feedback showing would return. It is called by start() which is within this class.</p>
	 * 
	 */

	private static void feedbackShowTest() {
		String expected  = "All feedback showing";
		if (expected.equals(ControllerStudentHome.showFeedback())){
			System.out.println("Test show feedback passed!");
		} else {
			System.out.println("Test show feedback failed!");
		}
	}
	/**********
	 * <p> Method: private feedbackShowViewTest() </p>
	 * 
	 * <p> Description: This method is a test for if the feedback show method works which shows all
	 * the feedback sent. If the feedback show method works properly, a string explaining that 
	 * feedback showing would return. It is called by start() which is within this class.</p>
	 * 
	 */

	private static void feedbackShowViewTest() {
		String expected  = "feedback viewing";
		feedback f = new feedback();
		try {
			f = theDatabase.listFeedbackOnly(ViewStudentHome.theUser.getUserName()).get(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (expected.equals(ViewStudentHome.openFeedbackViewer(f))){
			System.out.println("Test show feedback passed!");
		} else {
			System.out.println("Test show feedback failed!");
		}
	}
	

}
