package guiUserLogin;

import guiTools.BaseView;                      
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

public class ViewUserLogin extends BaseView {

  

    // Application variables required by interface
    private static final double WIN_W = applicationMain.FoundationsMain.WINDOW_WIDTH;
    private static final double WIN_H = applicationMain.FoundationsMain.WINDOW_HEIGHT;

    private static final Label label_ApplicationTitle      = new Label("Foundation Application Startup Page");
    private static final Label label_OperationalStartTitle = new Label("Log In or Invited User Account Setup ");
    private static final Label label_LogInInsrtuctions     = new Label("Enter your user name and password and then click on the LogIn button");

    // Keep static so it is accessible by controller
    public  static final TextField     text_Username  = new TextField();
    public  static final PasswordField text_Password  = new PasswordField();
    private static final Button        button_Login   = new Button("Log In");

    private static final Label label_AccountSetupInsrtuctions = new Label("No account? Enter your invitation code and click on the Account Setup button");
    public  static final TextField text_Invitation   = new TextField();
    private static final Button button_SetupAccount = new Button("Setup Account");

    public  static final Alert alertUsernamePasswordError = new Alert(Alert.AlertType.INFORMATION);

  

    // ADDED BY KENNY: use BaseView for easier implementation
    public ViewUserLogin(Stage stage) {
        super(stage, WIN_W, WIN_H);

        // ADDED BY KENNY: add CSS classes so the style is controlled globally
        label_ApplicationTitle.getStyleClass().add("title");
        label_OperationalStartTitle.getStyleClass().add("subtitle");
        label_LogInInsrtuctions.getStyleClass().add("form-label");
        label_AccountSetupInsrtuctions.getStyleClass().add("form-label");

        text_Username.getStyleClass().add("text-field");
        text_Password.getStyleClass().add("password-field");
        text_Invitation.getStyleClass().add("text-field");

        button_Login.getStyleClass().add("primary-btn");
        button_SetupAccount.getStyleClass().add("primary-btn");

        // ADDED BY KENNY: prompts and reasonable max widths so controls don’t stretch full screen
        text_Username.setPromptText("Enter Username");
        text_Password.setPromptText("Enter Password");
        text_Invitation.setPromptText("Enter Invitation Code");

        text_Username.setMaxWidth(360);
        text_Password.setMaxWidth(360);
        text_Invitation.setMaxWidth(360);
        button_Login.setMaxWidth(360);
        button_SetupAccount.setMaxWidth(360);

        alertUsernamePasswordError.setTitle("Invalid username/password!");
        alertUsernamePasswordError.setHeaderText(null);

        // ADDED BY KENNY: wire actions to the existing controller
        button_Login.setOnAction(e -> ControllerUserLogin.doLogin(stage));
        button_SetupAccount.setOnAction(e -> ControllerUserLogin.doSetupAccount(stage, text_Invitation.getText()));
    }

 
    // ADDED BY KENNY: modern stacked layout with compact rows
    @Override
    protected Node buildContent() {
        VBox header = new VBox(10, label_ApplicationTitle, label_OperationalStartTitle);
        header.setAlignment(Pos.CENTER);


        VBox loginBox  = new VBox(10, label_LogInInsrtuctions, text_Username, text_Password, button_Login );
        loginBox.setAlignment(Pos.CENTER);
        

       

        VBox inviteBox = new VBox(10, label_AccountSetupInsrtuctions, text_Invitation, button_SetupAccount);
        inviteBox.setAlignment(Pos.CENTER);

        VBox content = new VBox(16, header, loginBox, inviteBox);
        content.setAlignment(Pos.CENTER);
        return content;
    }

    // ADDED BY KENNY: delegate quit to the existing controller
    @Override
    protected void onQuit() {
        ControllerUserLogin.performQuit();
    }

    /*-********************************************************************************************
     * Compatibility entry point (keeps old call sites working)
     *********************************************************************************************/

    public static void displayUserLogin(Stage stage) {
        // Reset per previous behavior
        text_Username.setText("");
        text_Password.setText("");
        text_Invitation.setText("");

        new ViewUserLogin(stage).show();
    }
    
    // DO NOT SHOW LOGOUT BUTTON
    protected boolean showLogout() {
    	return false;
    }
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


	/*-********************************************************************************************

	Helper methods to reduce code length

	 *********************************************************************************************/
	/**********
	 * Private local method to initialize the standard fields for a label
	 */
	
	// NOTE: These are unused since we are not using pane and instead using VBox
	
	
	/**********	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
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
	 
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}
	
	
	/**********
	 * Private local method to initialize the standard fields for a text field
	 
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);		
		t.setEditable(e);
	}	*/




