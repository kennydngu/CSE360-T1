package guiUserUpdate;

import database.Database;
import entityClasses.User;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

import emailRecognizer.EmailRecognizer;
import passwordPopUpWindow.Model;


public class ControllerUserUpdate {
	/*-********************************************************************************************

	The Controller for ViewUserUpdate 
	
	**********************************************************************************************/

	/**********
	 * <p> Title: ControllerUserUpdate Class</p>
	 * 
	 * <p> Description: This static class supports the actions initiated by the ViewUserUpdate
	 * class. In this case, there is just one method, no constructors, and no attributes.</p>
	 *
	 */

	/*-********************************************************************************************

	The User Interface Actions for this page
	
	**********************************************************************************************/

	
	/**********
	 * <p> Method: public goToUserHomePage(Stage theStage, User theUser) </p>
	 * 
	 * <p> Description: This method is called when the user has clicked on the button to
	 * proceed to the user's home page.
	 * 
	 * @param theStage specifies the JavaFX Stage for next next GUI page and it's methods
	 * 
	 * @param theUser specifies the user so we go to the right page and so the right information
	 */
	
	private static Database db = applicationMain.FoundationsMain.database;

	
	protected static void goToUserHomePage(Stage theStage, User theUser) {
		
		// Get the roles the user selected during login
		int theRole = applicationMain.FoundationsMain.activeHomePage;

		// Use that role to proceed to that role's home page
		switch (theRole) {
		case 1:
			guiAdminHome.ViewAdminHome.displayAdminHome(theStage, theUser);
			break;
		case 2:
			guiStudent.ViewStudentHome.displayStudentHome(theStage, theUser);
			break;
		case 3:
			guiStaff.ViewStaffHome.displayStaffHome(theStage, theUser);
			break;
		default: 
			System.out.println("*** ERROR *** UserUpdate goToUserHome has an invalid role: " + 
					theRole);
			System.exit(0);
		}
 	}
	
	private static String validateName(String label, String value) {
		if (value == null || value.trim().isEmpty()) {
			return label + " cannot be empty.";
		}
		if (value.length() > 50) {
			return label + " must be less than 50 characters.";
		}
		if (!value.matches("^[A-Za-z-]+$")) { // For names like Anne-marie
	        return label + " can only contain letters and hyphens.";
	    }
	    return ""; // valid name
	}
	

	private static void showError(Stage stage, String title, String header, String content) {
		javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
		alert.initOwner(stage);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public static void handleUpdateEmail(Stage stage, User user, TextInputDialog dialog, Label targetLabel) {
			
			dialog.setContentText("Enter your email:");
			String email = dialog.showAndWait().orElse("").trim();
			
			if (email.isEmpty()) {
			return; // user hit cancel or left it blank
			}
			
			String errMsg = EmailRecognizer.checkForValidEmail(email);
			if (!errMsg.isEmpty()) {
			showError(stage, "Invalid Email", "Email validation failed", errMsg);
			return;
			}
			
			db.updateEmailAddress(user.getUserName(), email);
			db.getUserAccountDetails(user.getUserName());
			String newEmail = db.getCurrentEmailAddress();
			
			user.setEmailAddress(newEmail);
			targetLabel.setText((newEmail == null || newEmail.isEmpty()) ? "<none>" : newEmail);
			}
	
	public static void handleUpdateName(Stage stage, User user, TextInputDialog dialog, Label targetLabel, String nameType) {
			dialog.setContentText("Enter your " + nameType + " name: ");
			String name = dialog.showAndWait().orElse("").trim();
			if (name.isEmpty()) return;
			
			String err = validateName(nameType + " Name", name);
			if (!err.isEmpty()) {
				showError(stage, "Invalid " + nameType + " Name", "Validation failed", err);
		        return;
			}
			
			switch(nameType.toLowerCase()) {
				case "first":
					db.updateFirstName(user.getUserName(), name);
		            db.getUserAccountDetails(user.getUserName());
		            user.setFirstName(db.getCurrentFirstName());
		            targetLabel.setText(emptyCheck(user.getFirstName()));
		            break;
				
				case "middle":
					db.updateMiddleName(user.getUserName(), name);
		            db.getUserAccountDetails(user.getUserName());
		            user.setMiddleName(db.getCurrentMiddleName());
		            targetLabel.setText(emptyCheck(user.getMiddleName()));
		            break;
					
				case "last":
					db.updateLastName(user.getUserName(), name);
		            db.getUserAccountDetails(user.getUserName());
		            user.setLastName(db.getCurrentLastName());
		            targetLabel.setText(emptyCheck(user.getLastName()));
		            break;
					
				case "preferred first":
					db.updatePreferredFirstName(user.getUserName(), name);
		            db.getUserAccountDetails(user.getUserName());
		            user.setPreferredFirstName(db.getCurrentPreferredFirstName());
		            targetLabel.setText(emptyCheck(user.getFirstName()));
		            break;
		            
		         default:
		        	 showError(stage, "Error", "Unknown field", "Unsupported Name Type " + nameType);
		        	 return;
			}
	
	}
	
	private static String emptyCheck(String value) {
	    return (value == null || value.isEmpty()) ? "<none>" : value;
	}
	
	public static void handleUpdatePassword(Stage stage, User user, Label targetLabel) {

        TextInputDialog d1 = new TextInputDialog("");
        d1.setTitle("Update Password");
        d1.setHeaderText("Update your Password");
        d1.setContentText("Enter your new password:");
        String pw = d1.showAndWait().orElse("").trim();
        if (pw.isEmpty()) return;
        	
        String err = Model.evaluatePassword(pw);
        if (!err.isEmpty()) { 
        	showError(stage, "Invalid Password", "Password Validation Failed", err);
        	return;
        }
        
	}
	
	
	
	
	
	
	
		}








