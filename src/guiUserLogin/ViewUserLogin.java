package guiUserLogin;

import javafx.geometry.Pos;
import javafx.geometry.Insets;    // ADDED BY KENNY NGUYEN: Needed for VBox padding
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;  // ADDED BY KENNY NGUYEN: VBox for vertical alignment
import javafx.scene.text.Font;
import javafx.stage.Stage;


/*******
 * <p> Title: GUIStartupPage Class. </p>
 * 
 * <p> Description: The Java/FX-based System Startup Page.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-04-20 Initial version
 *  
 */

public class ViewUserLogin {

	/*-********************************************************************************************

	Attributes

	 *********************************************************************************************/

	// These are the application values required by the user interface

	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

	private static Label label_ApplicationTitle = new Label("Foundation Application Startup Page");

	// This set is for all subsequent starts of the system
	private static Label label_OperationalStartTitle = new Label("Log In or Invited User Account Setup ");
	private static Label label_LogInInsrtuctions = new Label("Enter your user name and password and "+	
			"then click on the LogIn button");
	protected static Alert alertUsernamePasswordError = new Alert(AlertType.INFORMATION);


	//	private User user;
	protected static TextField text_Username = new TextField();
	protected static PasswordField text_Password = new PasswordField();
	private static Button button_Login = new Button("Log In");	

	private static Label label_AccountSetupInsrtuctions = new Label("No account? "+	
			"Enter your invitation code and click on the Account Setup button");
	private static TextField text_Invitation = new TextField();
	private static Button button_SetupAccount = new Button("Setup Account");

	private static Button button_Quit = new Button("Quit");

	private static Stage theStage;	
	private static Scene theUserLoginScene = null;	


	private static ViewUserLogin theView = null;	//	private static guiUserLogin.ControllerUserLogin theController;


	/*-********************************************************************************************

	Constructor

	 *********************************************************************************************/

	public static void displayUserLogin(Stage ps) {
		
		// Establish the references to the GUI. There is no current user yet.
		theStage = ps;
		
		// If not yet established, populate the static aspects of the GUI
		if (theView == null) theView = new ViewUserLogin();
		
		// Populate the dynamic aspects of the GUI with the data from the user and the current
		// state of the system.		
		text_Username.setText("");		// Reset the username and password from the last use
		text_Password.setText("");
		text_Invitation.setText("");	// Same for the invitation code

		// Set the title for the window, display the page, and wait for the Admin to do something
		theStage.setTitle("CSE 360 Foundation Code: User Login Page");		
		theStage.setScene(theUserLoginScene);
		theStage.show();
	}

	/**********
	 * <p> Method: ViewUserLoginPage() </p>
	 * 
	 * <p> Description: This method is called when the application first starts. It must handle
	 * two cases: 1) when no has been established and 2) when one or more users have been 
	 * established.
	 * 
	 * If there are no users in the database, this means that the person starting the system jmust
	 * be an administrator, so a special GUI is provided to allow this Admin to set a username and
	 * password.
	 * 
	 * If there is at least one user, then a different display is shown for existing users to login
	 * and for potential new users to provide an invitation code and if it is valid, they are taken
	 * to a page where they can specify a username and password.</p>
	 * 
	 * @param ps specifies the JavaFX Stage to be used for this GUI and it's methods
	 * 
	 * @param theRoot specifies the JavaFX Pane to be used for this GUI and it's methods
	 * 
	 * @param db specifies the Database to be used by this GUI and it's methods
	 * 
	 */
	private ViewUserLogin() {

		// ADDED BY KENNY NGUYEN: VBox for vertical alignment
		VBox root = new VBox(15);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(20));
		root.getStyleClass().add("page-root");
		
		// ADDED BY KENNY NGUYEN: Set limiters for width so they do not stretch across the page.
		text_Username.setMaxWidth(500);
		text_Password.setMaxWidth(500);
		text_Invitation.setMaxWidth(500);
		button_Login.setMaxWidth(150);
		button_SetupAccount.setMaxWidth(150);
		button_Quit.setMaxWidth(50);

		theUserLoginScene = new Scene(root, width, height);
		theUserLoginScene.getStylesheets().add(ViewUserLogin.class.getResource("/styles/app.css").toExternalForm());
		
		label_ApplicationTitle.getStyleClass().add("title");
		label_OperationalStartTitle.getStyleClass().add("subtitle");

		label_LogInInsrtuctions.getStyleClass().add("form-label");
		label_AccountSetupInsrtuctions.getStyleClass().add("form-label");

		text_Username.getStyleClass().add("text-field");
		text_Password.getStyleClass().add("password-field");
		text_Invitation.getStyleClass().add("text-field");

		button_Login.getStyleClass().add("primary-btn");
		button_Quit.setId("quitBtn");
		
		// Existing user log in portion of the page
		text_Username.setPromptText("Enter Username");
		text_Password.setPromptText("Enter Password");

		// Set up the Log In button
		button_Login.setOnAction((event) -> {ControllerUserLogin.doLogin(theStage); });
		alertUsernamePasswordError.setTitle("Invalid username/password!");
		alertUsernamePasswordError.setHeaderText(null);

		// Set up the Setup Account button
		text_Invitation.setPromptText("Enter Invitation Code");
		button_SetupAccount.setOnAction((event) -> {
			System.out.println("**** Calling doSetupAccount");
			ControllerUserLogin.doSetupAccount(theStage, text_Invitation.getText());
		});

		// Set up the Quit button  
		button_Quit.setOnAction((event) -> {ControllerUserLogin.performQuit(); });

		// ADDED BY KENNY NGUYEN: VBox children in vertical order
		root.getChildren().addAll(
			label_ApplicationTitle,
			label_OperationalStartTitle,
			label_LogInInsrtuctions,
			text_Username,
			text_Password,
			button_Login,
			label_AccountSetupInsrtuctions,
			text_Invitation,
			button_SetupAccount,
			button_Quit
		);
	}


	/*-********************************************************************************************

	Helper methods to reduce code length

	 *********************************************************************************************/
	/**********
	 * Private local method to initialize the standard fields for a label
	 */
	
	// NOTE: These are unused since we are not using pane and instead using VBox

	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
	}
	
	
	/**********
	 * Private local method to initialize the standard fields for a button
	 * 
	 * @param b		The Button object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}
	
	
	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
	}		
}


