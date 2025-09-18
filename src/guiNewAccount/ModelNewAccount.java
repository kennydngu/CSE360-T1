package guiNewAccount;

// ADDED BY KENNY NGUYEN: Import FSM validators
import userNameRecognizerTestbed.UserNameRecognizer;
import passwordPopUpWindow.Model;

/*******
 * <p> Title: ModelNewAccount Class. </p>
 * 
 * <p> Description: The NewAccount Page Model. This class is not used as there is no
 * data manipulated by this MVC beyond accepting role information and saving it
 * in the database.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00 2025-08-15 Initial version
 * 
 */
public class ModelNewAccount {

	// ADDED BY KENNY NGUYEN: cached error states (handy for testing)
	public static String usernameError = "";
	public static int usernameErrIndex = -1;
	public static String passwordError = "";
	public static int passwordErrIndex = -1;

	// ADDED BY KENNY NGUYEN: Update username & password validations (optional helper)
	protected static void updateAll() {
		usernameError = UserNameRecognizer.checkForValidUserName(ViewNewAccount.text_Username.getText());
		usernameErrIndex = UserNameRecognizer.userNameRecognizerIndexofError;

		passwordError = Model.evaluatePassword(ViewNewAccount.text_Password1.getText());
		passwordErrIndex = Model.passwordIndexofError;

		boolean ok = usernameError.isEmpty() && passwordError.isEmpty();
		ViewNewAccount.button_UserSetup.setDisable(!ok);
	}
}
