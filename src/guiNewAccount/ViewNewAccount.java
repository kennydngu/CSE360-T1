package guiNewAccount;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import database.Database;
import entityClasses.User;

/*******
 * <p> Title: ViewNewAccount Class. </p>
 * 
 * <p> Description: The ViewNewAccount Page is used to enable a potential user with
 * an invitation code to establish an account after they have specified an
 * invitation code on the standard login page. </p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00 2025-08-19 Initial version
 * 
 */

public class ViewNewAccount {
	
	/*-********************************************************************************************
	
	Attributes
	
	*/

	// These are the application values required by the user interface
	
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;
	
	private static Label label_ApplicationTitle = 
			new Label("Foundation Application Account Setup Page");
    protected static Label label_NewUserCreation = new Label(" User Account Creation.");
    protected static Label label_NewUserLine = new Label("Please enter a username and a password.");
    protected static TextField text_Username = new TextField();
    protected static PasswordField text_Password1 = new PasswordField();
    protected static PasswordField text_Password2 = new PasswordField();
    protected static Button button_UserSetup = new Button("User Setup");
    protected static TextField text_Invitation = new TextField();

    protected static Alert alertInvitationCodeIsInvalid = new Alert(AlertType.INFORMATION);
	protected static Alert alertUsernamePasswordError = new Alert(AlertType.INFORMATION);

    protected static Button button_Quit = new Button("Quit");

	private static ViewNewAccount theView;

	private static Database theDatabase = applicationMain.FoundationsMain.database;		

	protected static Stage theStage;
	private static Pane theRootPane;
	protected static User theUser;
   
    protected static String theInvitationCode;
    protected static String emailAddress;
    protected static String theRole;
	public static Scene theNewAccountScene = null;

	// ADDED BY KENNY NGUYEN: validation feedback labels
	protected static Label errUserPart1 = new Label();
	protected static Label errUserPart2 = new Label();
	protected static Label errUserPart3 = new Label();
	protected static Label label_UserValid = new Label();

	protected static Label errPassPart1 = new Label();
	protected static Label errPassPart2 = new Label();
	protected static Label errPassPart3 = new Label();
	protected static Label label_PassValid = new Label();
	protected static Label label_PassMatch = new Label();
	

	/*-********************************************************************************************
	
	Constructors
	
	*/

	/**********
	 * <p> Method: displayNewAccount(Stage ps, String ic) </p>
	 */
	public static void displayNewAccount(Stage ps, String ic) {
		// This is the only way some component of the system can cause a New User Account page to
		// appear.  The first time, the class is created and initialized.  Every subsequent call it
		// is reused with only the elements that differ being initialized.
		
		theStage = ps;				
		theInvitationCode = ic;		
		
		if (theView == null) theView = new ViewNewAccount();
		
		text_Username.setText("");
		text_Password1.setText("");
		text_Password2.setText("");
		
		theRole = theDatabase.getRoleGivenAnInvitationCode(theInvitationCode);
		
		if (theRole.length() == 0) {
			alertInvitationCodeIsInvalid.showAndWait();
			return;
		}
		
		emailAddress = theDatabase.getEmailAddressUsingCode(theInvitationCode);
		
    	theRootPane.getChildren().clear();
    	theRootPane.getChildren().addAll(
			label_NewUserCreation, label_NewUserLine,
			text_Username, text_Password1, text_Password2,
			button_UserSetup, button_Quit,
			// ADDED BY KENNY NGUYEN: validation labels
			errUserPart1, errUserPart2, errUserPart3, label_UserValid,
			errPassPart1, errPassPart2, errPassPart3, label_PassValid, label_PassMatch
		);

    	// ADDED BY KENNY NGUYEN: keep disabled until inputs are valid and wire live validation
    	button_UserSetup.setDisable(true);
    	ControllerNewAccount.attachLiveValidation();

		theStage.setTitle("CSE 360 Foundation Code: New User Account Setup");	
        theStage.setScene(theNewAccountScene);
		theStage.show();
	}
	
	/**********
	 * <p> Constructor: ViewNewAccount() </p>
	 */
	private ViewNewAccount() {
		
		theRootPane = new Pane();
		theNewAccountScene = new Scene(theRootPane, width, height);

		setupLabelUI(label_ApplicationTitle, "Arial", 28, width, Pos.CENTER, 0, 5);
    	setupLabelUI(label_NewUserCreation, "Arial", 32, width, Pos.CENTER, 0, 10);
    	setupLabelUI(label_NewUserLine, "Arial", 24, width, Pos.CENTER, 0, 70);
		
		setupTextUI(text_Username, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 160, true);
		text_Username.setPromptText("Enter the Username");
		
		setupTextUI(text_Password1, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 210, true);
		text_Password1.setPromptText("Enter the Password");
		
		setupTextUI(text_Password2, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 260, true);
		text_Password2.setPromptText("Enter the Password Again");
		
		alertInvitationCodeIsInvalid.setTitle("Invalid Invitation Code");
		alertInvitationCodeIsInvalid.setHeaderText("The invitation code is not valid.");
		alertInvitationCodeIsInvalid.setContentText("Correct the code and try again.");

		alertUsernamePasswordError.setTitle("Passwords Do Not Match");
		alertUsernamePasswordError.setHeaderText("The two passwords must be identical.");
		alertUsernamePasswordError.setContentText("Correct the passwords and try again.");

        setupButtonUI(button_UserSetup, "Dialog", 18, 200, Pos.CENTER, 475, 210);
        button_UserSetup.setOnAction((event) -> { ControllerNewAccount.doCreateUser(); });
		
        setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 540);
        button_Quit.setOnAction((event) -> { ControllerNewAccount.performQuit(); });
        
       
        // ADDED BY KENNY NGUYEN: tidy, aligned layout for error helpers

        // Username row
        setupLabelUI(errUserPart1, "Consolas", 18, 300, Pos.BASELINE_LEFT,  50, 185); // underline line
        setupLabelUI(errUserPart2, "Consolas", 18,  20, Pos.BASELINE_LEFT,  50, 185); // ↑ at error
        setupLabelUI(errUserPart3, "Arial",    14, 500, Pos.BASELINE_LEFT,  50, 205); // message
        setupLabelUI(label_UserValid,"Arial",  14, 300, Pos.BASELINE_LEFT, 360, 160); // summary at right

        // Password row (1st)
        setupLabelUI(errPassPart1, "Consolas", 18, 300, Pos.BASELINE_LEFT,  50, 235);
        setupLabelUI(errPassPart2, "Consolas", 18,  20, Pos.BASELINE_LEFT,  50, 235);
        setupLabelUI(errPassPart3, "Arial",    14, 500, Pos.BASELINE_LEFT,  50, 255);
        setupLabelUI(label_PassValid,"Arial",  14, 300, Pos.BASELINE_LEFT, 360, 210);

        // Password row (confirm / match)
        setupLabelUI(label_PassMatch,"Arial",  14, 300, Pos.BASELINE_LEFT, 360, 260);
        
        // ADDED BY KENNY NGUYEN: let clicks go through helper labels
        errUserPart1.setMouseTransparent(true);
        errUserPart2.setMouseTransparent(true);
        errPassPart1.setMouseTransparent(true);
        errPassPart2.setMouseTransparent(true);

        errUserPart1.setPickOnBounds(false);
        errUserPart2.setPickOnBounds(false);
        errPassPart1.setPickOnBounds(false);
        errPassPart2.setPickOnBounds(false);

	}
	
	/*-********************************************************************************************
	
	Helper methods to reduce code length

	 */
	
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);		
	}
	
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}

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

