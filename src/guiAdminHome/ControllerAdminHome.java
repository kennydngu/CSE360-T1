package guiAdminHome;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import database.Database;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/*******
 * <p> Title: GUIAdminHomePage Class. </p>
 * 
 * <p> Description: The Java/FX-based Admin Home Page.  This class provides the controller actions
 * basic on the user's use of the JavaFX GUI widgets defined by the View class.
 * 
 * This page contains a number of buttons that have not yet been implemented.  WHen those buttons
 * are pressed, an alert pops up to tell the user that the function associated with the button has
 * not been implemented. Also, be aware that What has been implemented may not work the way the
 * final product requires and there maybe defects in this code.
 * 
 * The class has been written assuming that the View or the Model are the only class methods that
 * can invoke these methods.  This is why each has been declared at "protected".  Do not change any
 * of these methods to public.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-08-17 Initial version
 *  
 */

public class ControllerAdminHome {
	
	/*-*******************************************************************************************

	User Interface Actions for this page
	
	This controller is not a class that gets instantiated.  Rather, it is a collection of protected
	static methods that can be called by the View (which is a singleton instantiated object) and 
	the Model is often just a stub, or will be a singleton instantiated object.
	
	*/

	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	/**********
	 * <p> 
	 * 
	 * Title: performInvitation () Method. </p>
	 * 
	 * <p> Description: Protected method to send an email inviting a potential user to establish
	 * an account and a specific role. </p>
	 */
	protected static void performInvitation () {
		// Verify that the email address is valid - If not alert the user and return
		String emailAddress = ViewAdminHome.text_InvitationEmailAddress.getText();
		if (invalidEmailAddress(emailAddress)) {
			return;
		}
		
		// Check to ensure that we are not sending a second message with a new invitation code to
		// the same email address.  
		if (theDatabase.emailaddressHasBeenUsed(emailAddress)) {
			ViewAdminHome.alertEmailError.setContentText(
					"An invitation has already been sent to this email address.");
			ViewAdminHome.alertEmailError.showAndWait();
			return;
		}
		
		// Inform the user that the invitation has been sent and display the invitation code
		String theSelectedRole = (String) ViewAdminHome.combobox_SelectRole.getValue();
		String invitationCode = theDatabase.generateInvitationCode(emailAddress,
				theSelectedRole);
		String msg = "Code: " + invitationCode + " for role " + theSelectedRole + 
				" was sent to: " + emailAddress;
		System.out.println(msg);
		ViewAdminHome.alertEmailSent.setContentText(msg);
		ViewAdminHome.alertEmailSent.showAndWait();
		
		// Update the Admin Home pages status
		ViewAdminHome.text_InvitationEmailAddress.setText("");
		ViewAdminHome.label_NumberOfInvitations.setText("Number of outstanding invitations: " + 
				theDatabase.getNumberOfInvitations());
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: manageInvitations () Method. </p>
	 * 
	 * <p> Description: Protected method that is currently a stub informing the user that
	 * this function has not yet been implemented. </p>
	 */
	protected static void manageInvitations () {
		StringBuilder invitationlist = new StringBuilder();
		int invitationCount = theDatabase.getNumberOfInvitations();
		if (invitationCount == 0)
		{
			invitationlist.append("No outstanding invitation found!");
		}
		else {
			invitationlist.append("Total invitaion: ").append(invitationCount).append("\n");
			
			invitationlist.append("Use the database admin to view invitation records");
		}
		ViewAdminHome.alertNotImplemented.setTitle("Manage Invitaions");
		ViewAdminHome.alertNotImplemented.setHeaderText("Invitations Managament");
		ViewAdminHome.alertNotImplemented.setContentText(invitationlist.toString());
		ViewAdminHome.alertNotImplemented.showAndWait();
		
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: setOnetimePassword () Method. </p>
	 * 
	 * <p> Description: Protected method that is currently a stub informing the user that
	 * this function has not yet been implemented. </p>
	 */
	protected static void set_one_time_password() {
	    String selected_user=(String) ViewAdminHome.combobox_SelectUser.getValue();
	    
	    if (selected_user==null||selected_user.equals("<Select a User>")) 
	    {
	        ViewAdminHome.alertEmailError.setTitle("Selection Error");
	        ViewAdminHome.alertEmailError.setHeaderText("No User Selected");
	        ViewAdminHome.alertEmailError.setContentText("Please select a user before generating a one-time password.");
	        ViewAdminHome.alertEmailError.showAndWait();
	        return;
	    }
	    
	    String one_time_password="Cse360"+System.currentTimeMillis() % 100000;
	    
	    if (theDatabase.update_user_password(selected_user, one_time_password)) 
	    {
	        String message = "One-time password has been set for user: " + selected_user + 
	                        "\nNew Password: " + one_time_password;
	        ViewAdminHome.alertEmailSent.setTitle("One-Time Password Set");
	        ViewAdminHome.alertEmailSent.setHeaderText("Password Updated");
	        ViewAdminHome.alertEmailSent.setContentText(message);
	        ViewAdminHome.alertEmailSent.showAndWait();
	    } else 
	    {
	        ViewAdminHome.alertEmailError.setTitle("Not able to update the password ");
	        ViewAdminHome.alertEmailError.setHeaderText("There was a database error. ");
	        ViewAdminHome.alertEmailError.setContentText("Failed to update password for user: "+selected_user);
	        ViewAdminHome.alertEmailError.showAndWait();
	    }
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: deleteUser () Method. </p>
	 * 
	 * <p> Description: Protected method that is currently a stub informing the user that
	 * this function has not yet been implemented. </p>
	 */
	protected static void deleteUser() {

	    String selectedUser = (String) ViewAdminHome.combobox_SelectUser.getValue();

	    

	    if (selectedUser ==null||selectedUser.equals("<Select a User>")) {

	        ViewAdminHome.alertEmailError.setTitle("Selection Error");
	        ViewAdminHome.alertEmailError.setHeaderText("No User Selected");
	        ViewAdminHome.alertEmailError.setContentText("Please select a user to delete.");
	        ViewAdminHome.alertEmailError.showAndWait();
	        return;
	    }

	    if (selectedUser.equals(ViewAdminHome.theUser.getUserName())) 
	    {
	    	
	        ViewAdminHome.alertEmailError.setTitle("Cannot Delete Self");
	        ViewAdminHome.alertEmailError.setHeaderText("Delete User Error");
	        ViewAdminHome.alertEmailError.setContentText("You cannot delete your own account while logged in.");
	        ViewAdminHome.alertEmailError.showAndWait();
	        return;
	        }
	    Alert confirmdelete = new Alert(Alert.AlertType.CONFIRMATION);
	    confirmdelete.setTitle("Delete user");
	    confirmdelete.setHeaderText("Are you sure?");
	    ButtonType yes= new ButtonType("Yes");
	    ButtonType no = new ButtonType("No");
	    confirmdelete.getButtonTypes().setAll(yes,no);
	    Optional<ButtonType> result = confirmdelete.showAndWait();
	    if (result.isPresent() && result.get() == yes) 
	    {
	    	if (theDatabase.delete_user(selectedUser)) {
	    		ViewAdminHome.alertEmailSent.setContentText("User " + selectedUser + " has been deleted!");
	    		ViewAdminHome.alertEmailSent.showAndWait();
	    		List<String> updatedUserList = theDatabase.getUserList();
	    		ViewAdminHome.combobox_SelectUser.setItems(FXCollections.observableArrayList(updatedUserList));
	    		ViewAdminHome.combobox_SelectUser.getSelectionModel().select(0);
	    	}
	    }
	    //ViewAdminHome.alertNotImplemented.setTitle("Deletion completed");
	    //ViewAdminHome.alertNotImplemented.setHeaderText("Delete User Confirmation");
	    //ViewAdminHome.alertNotImplemented.setContentText("Deleted user " + selectedUser);
	    //ViewAdminHome.alertNotImplemented.showAndWait();
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: listUsers () Method. </p>
	 * 
	 * <p> Description: Protected method that is currently a stub informing the user that
	 * this function has not yet been implemented. </p>
	 */
	protected static void listUsers() {
	    StringBuilder usersList = new StringBuilder();

	    List<String> users = theDatabase.getUserList();
	    
	    if (users.size() <= 1) {
	        usersList.append("No users found in the system.");
	    } else {
	        usersList.append("Total users: ").append(users.size() - 1).append("\n\n");
	        
	        for (String username : users) {
	            if (!username.equals("<Select a User>")) {
	                if (theDatabase.getUserAccountDetails(username)) {
	                    usersList.append("Username: ").append(username).append("\n");
	                    usersList.append("Name: ").append(theDatabase.getCurrentFirstName());
	                    String middleName = theDatabase.getCurrentMiddleName();
	                    if (middleName != null && !middleName.trim().isEmpty()) {
	                        usersList.append(" ").append(middleName);
	                    }
	                    usersList.append(" ").append(theDatabase.getCurrentLastName()).append("\n");
	                    usersList.append("Email: ").append(theDatabase.getCurrentEmailAddress()).append("\n");
	                    usersList.append("Roles: ");
	                    List<String> rolesList = new ArrayList<>();
	                    if (theDatabase.getCurrentAdminRole()) rolesList.add("Admin");
	                    if (theDatabase.getCurrentStudentRole()) rolesList.add("Student");
	                    if (theDatabase.getCurrentStaffRole()) rolesList.add("Staff");
	                    
	                    if (rolesList.isEmpty()) {
	                        usersList.append("None");
	                    } else {
	                        usersList.append(String.join(", ", rolesList));
	                    }
	                    
	                    usersList.append("\n\n");
	                }
	            }
	        }
	    }
	    
	    ViewAdminHome.alertNotImplemented.setTitle("User List");
	    ViewAdminHome.alertNotImplemented.setHeaderText("All System Users");
	    ViewAdminHome.alertNotImplemented.setContentText(usersList.toString());
	    ViewAdminHome.alertNotImplemented.showAndWait();
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: addRemoveRoles () Method. </p>
	 * 
	 * <p> Description: Protected method that allows an admin to add and remove roles for any of
	 * the users currently in the system.  This is done by invoking the AddRemoveRoles Page. There
	 * is no need to specify the home page for the return as this can only be initiated by and
	 * Admin.</p>
	 */
	protected static void addRemoveRoles() {
		 guiAddRemoveRoles.ViewAddRemoveRoles.displayAddRemoveRoles(ViewAdminHome.theView.getStage(), ViewAdminHome.theUser);
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: invalidEmailAddress () Method. </p>
	 * 
	 * <p> Description: Protected method that is intended to check an email address before it is
	 * used to reduce errors.  The code currently only checks to see that the email address is not
	 * empty.  In the future, a syntactic check must be performed and maybe there is a way to check
	 * if a properly email address is active.</p>
	 * 
	 * @param emailAddress	This String holds what is expected to be an email address
	 */
	protected static boolean invalidEmailAddress(String emailAddress) {
		if (emailAddress == null || emailAddress.trim().isEmpty()) {
	        ViewAdminHome.alertEmailError.setContentText("Email address cannot be empty.");
	        ViewAdminHome.alertEmailError.showAndWait();
	        return true;
	    }
		
		String errMsg = emailRecognizer.EmailRecognizer.checkForValidEmail(emailAddress.trim());
		if (!errMsg.isEmpty()) {
			ViewAdminHome.alertEmailError.setTitle("Invalid Email");
	        ViewAdminHome.alertEmailError.setHeaderText("Validation Failed");
	        ViewAdminHome.alertEmailError.setContentText(errMsg);
	        ViewAdminHome.alertEmailError.showAndWait();
	        return true;
		}
		
		return false;
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: performLogout () Method. </p>
	 * 
	 * <p> Description: Protected method that logs this user out of the system and returns to the
	 * login page for future use.</p>
	 */
	protected static void performLogout() {
		guiUserLogin.ViewUserLogin.displayUserLogin(ViewAdminHome.theView.getStage());
	}
	
	/**********
	 * <p> 
	 * 
	 * Title: performQuit () Method. </p>
	 * 
	 * <p> Description: Protected method that gracefully terminates the execution of the program.
	 * </p>
	 */
	protected static void performQuit() {
		System.exit(0);
	}
}
