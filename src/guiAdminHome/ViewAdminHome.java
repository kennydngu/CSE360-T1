package guiAdminHome;

import guiTools.BaseView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import database.Database;
import entityClasses.User;
import guiUserUpdate.ViewUserUpdate;

/*******
 * <p> Title: GUIAdminHomePage Class. </p>
 * 
 * <p> Description: The Java/FX-based Admin Home Page. This class provides the JavaFX GUI widgets
 * that enable an admin to perform admin functions. This page contains a number of buttons that
 * have not yet been implemented. What has been implemented may not work the way the final product
 * requires and there maybe defects in this code.
 * 
 * The class has been written using a singleton design pattern and is the View portion of the 
 * Model, View, Controller pattern. The pattern is designed that the all accesses to this page and
 * its functions starts by invoking the static method displayAdminHome. No other method should 
 * attempt to instantiate this class as that is controlled by displayAdminHome. It ensures that
 * only one instance of class is instantiated and that one is properly configured for each use.  
 * 
 * Please note that this implementation is not appropriate for concurrent systems with multiple
 * users. This Baeldung article provides insight into the issues: 
 *           https://www.baeldung.com/java-singleton</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00 2025-08-17 Initial version
 */

public class ViewAdminHome extends BaseView {
	
	/*-*******************************************************************************************
	 * Attributes
	 */

	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

	// GUI Area 1
	protected static Label label_PageTitle = new Label("Admin Home Page");
	protected static Label label_UserDetails = new Label();
	protected static Button button_UpdateThisUser = new Button("Account Update");
	private static Line line_Separator1 = new Line(20, 95, width - 20, 95);

	// GUI Area 2
	protected static Label label_NumberOfInvitations =
			new Label("Number of Oustanding Invitations: x");
	protected static Label label_NumberOfUsers = new Label("Number of Users: x");
	private static Line line_Separator2 = new Line(20, 165, width - 20, 165);

	// GUI Area 3
	protected static Label label_Invitations = new Label("Send An Invitation");
	protected static Label label_InvitationEmailAddress = new Label("Email Address");
	protected static TextField text_InvitationEmailAddress = new TextField();
	protected static ComboBox<String> combobox_SelectRole = new ComboBox<>();
	protected static Label label_SelectUser = new Label("Select User:");
	protected static ComboBox<String> combobox_SelectUser = new ComboBox<>();
	protected static String[] roles = {"Admin", "Student", "Staff"};
	protected static Button button_SendInvitation = new Button("Send Invitation");
	protected static Alert alertEmailError = new Alert(AlertType.INFORMATION);
	protected static Alert alertEmailSent = new Alert(AlertType.INFORMATION);
	private static Line line_Separator3 = new Line(20, 255, width - 20, 255);

	// GUI Area 4
	protected static Button button_ManageInvitations = new Button("Manage Invitations");
	protected static Button button_SetOnetimePassword = new Button("Set a One-Time Password");
	protected static Button button_DeleteUser = new Button("Delete a User");
	protected static Button button_ListUsers = new Button("List All Users");
	protected static Button button_AddRemoveRoles = new Button("Add/Remove Roles");
	protected static Alert alertNotImplemented = new Alert(AlertType.INFORMATION);
	private static Line line_Separator4 = new Line(20, 525, width - 20, 525);

	// GUI Area 5
	protected static Button button_Logout = new Button("Logout");
	protected static Button button_Quit = new Button("Quit");

	// Config
	static ViewAdminHome theView;
	private static Database theDatabase = applicationMain.FoundationsMain.database;
	protected static User theUser;
	private static final int theRole = 1;

	/*-*******************************************************************************************
	 * Constructors
	 */

	public static void displayAdminHome(Stage theStage, User user) {
		theUser = user;
		applicationMain.FoundationsMain.activeHomePage = theRole;

		theDatabase.getUserAccountDetails(user.getUserName());

		theStage.setTitle("CSE 360 Foundation Code: Admin Home Page");
		if (theView == null) theView = new ViewAdminHome(theStage);
		theView.show();
	}

	// EDITED BY KENNY NGUYEN: Changed to match my GUI implementation for an updated and modern look
	private ViewAdminHome(Stage stage) {
		super(stage, width, height);

		label_PageTitle.getStyleClass().add("title");
		label_UserDetails.getStyleClass().add("subtitle");

		label_NumberOfInvitations.getStyleClass().add("form-label");
		label_NumberOfUsers.getStyleClass().add("form-label");
		label_Invitations.getStyleClass().add("form-label");
		label_InvitationEmailAddress.getStyleClass().add("form-label");
		label_SelectUser.getStyleClass().add("form-label");

		text_InvitationEmailAddress.getStyleClass().add("text-field");
		combobox_SelectRole.getStyleClass().add("combo");
		combobox_SelectUser.getStyleClass().add("combo");

		button_UpdateThisUser.getStyleClass().add("secondary-btn");
		button_SendInvitation.getStyleClass().add("primary-btn");
		button_ManageInvitations.getStyleClass().add("secondary-btn");
		button_SetOnetimePassword.getStyleClass().add("secondary-btn");
		button_DeleteUser.getStyleClass().add("danger-btn");
		button_ListUsers.getStyleClass().add("secondary-btn");
		button_AddRemoveRoles.getStyleClass().add("secondary-btn");

		// control widths
		text_InvitationEmailAddress.setMaxWidth(360);
		combobox_SelectRole.setMaxWidth(160);
		combobox_SelectUser.setMaxWidth(240);
		button_UpdateThisUser.setMaxWidth(500);
		button_SendInvitation.setMaxWidth(500);
		button_ManageInvitations.setMaxWidth(500);
		button_SetOnetimePassword.setMaxWidth(500);
		button_DeleteUser.setMaxWidth(500);
		button_ListUsers.setMaxWidth(500);
		button_AddRemoveRoles.setMaxWidth(500);

		// wire actions
		button_UpdateThisUser.setOnAction(e -> ViewUserUpdate.displayUserUpdate(stage, theUser));
		button_SendInvitation.setOnAction(e -> ControllerAdminHome.performInvitation());
		button_ManageInvitations.setOnAction(e -> ControllerAdminHome.manageInvitations());
		button_SetOnetimePassword.setOnAction(e -> ControllerAdminHome.set_one_time_password());
		button_DeleteUser.setOnAction(e -> ControllerAdminHome.deleteUser());
		button_ListUsers.setOnAction(e -> ControllerAdminHome.listUsers());
		button_AddRemoveRoles.setOnAction(e -> ControllerAdminHome.addRemoveRoles());

		// alerts
		alertEmailSent.setTitle("Invitation");
		alertEmailSent.setHeaderText("Invitation was sent");
	}

	// ADDED BY KENNY: build the stacked center content
	@Override
	protected Node buildContent() {
		label_UserDetails.setText("User: " + (theUser != null ? theUser.getUserName() : ""));
		label_NumberOfInvitations.setText("Number of outstanding invitations: " +
				theDatabase.getNumberOfInvitations());
		label_NumberOfUsers.setText("Number of users: " + theDatabase.getNumberOfUsers());

		if (combobox_SelectRole.getItems().isEmpty()) {
			combobox_SelectRole.setItems(FXCollections.observableArrayList(roles));
			combobox_SelectRole.getSelectionModel().select(0);
		}

		combobox_SelectUser.setItems(FXCollections.observableArrayList(theDatabase.getUserList()));
		if (!combobox_SelectUser.getItems().isEmpty()) {
			combobox_SelectUser.getSelectionModel().select(0);
		}

		// Header
		VBox header = new VBox(6, label_PageTitle, label_UserDetails);
		header.setAlignment(Pos.CENTER);

		// Status
		VBox statusBox = new VBox(6, label_NumberOfInvitations, label_NumberOfUsers);
		statusBox.setAlignment(Pos.CENTER);

		// Invitation entry row
		HBox inviteRow1 = new HBox(10,
				label_InvitationEmailAddress,
				text_InvitationEmailAddress,
				combobox_SelectRole,
				button_SendInvitation
		);
		inviteRow1.setAlignment(Pos.CENTER);

		VBox inviteBox = new VBox(10, label_Invitations, inviteRow1);
		inviteBox.setAlignment(Pos.CENTER);

		// Admin action buttons
		VBox actionsBox = new VBox(10,
				button_ManageInvitations,
				button_SetOnetimePassword,
				button_DeleteUser,
				button_ListUsers,
				button_AddRemoveRoles
		);
		actionsBox.setAlignment(Pos.CENTER);

		// Select User block
		VBox selectUserBox = new VBox(6, label_SelectUser, combobox_SelectUser);
		selectUserBox.setAlignment(Pos.CENTER);

		// Footer
		HBox footer = new HBox(20, button_Logout, button_Quit);
		footer.setAlignment(Pos.CENTER_LEFT);

		// Main stacked content
		VBox content = new VBox(18,
				header,
				line_Separator1,
				statusBox,
				inviteBox,
				line_Separator2,
				selectUserBox,
				line_Separator3,
				actionsBox
		);
		content.setAlignment(Pos.CENTER);

		return content;
	}

	@Override
	protected void onQuit() {
		ControllerAdminHome.performQuit();
	}

	protected void onLogout() {
		ControllerAdminHome.performLogout();
	}
}
