package guiStaff;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import database.Database;
//import database.Database;
import entityClasses.User;
import guiStudent.ControllerStudentHome;
import guiStudent.ViewStudentHome;
import guiUserUpdate.ViewUserUpdate;


/*******
 * <p> Title: ViewRole2Home Class. </p>
 * 
 * <p> Description: The Java/FX-based Role2 Home Page.  The page is a stub for some role needed for
 * the application.  The widgets on this page are likely the minimum number and kind for other role
 * pages that may be needed.</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 * 
 * @author Lynn Robert Carter
 * 
 * @version 1.00		2025-04-20 Initial version
 *  
 */

public class ViewStaffHome {
	
	/*-*******************************************************************************************

	Attributes
	
	 */
	
	// These are the application values required by the user interface
	
	private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
	private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;


	// These are the widget attributes for the GUI. There are 3 areas for this GUI.
	
	// GUI Area 1: It informs the user about the purpose of this page, whose account is being used,
	// and a button to allow this user to update the account settings
	protected static Label label_PageTitle = new Label();
	protected static Label label_UserDetails = new Label();
	protected static Button button_UpdateThisUser = new Button("Account Update");
		
	// This is a separator and it is used to partition the GUI for various tasks
	protected static Line line_Separator1 = new Line(20, 95, width-20, 95);

	// GUI ARea 2: This is a stub, so there are no widgets here.  For an actual role page, this are
	// would contain the widgets needed for the user to play the assigned role.
	private static javafx.scene.control.Button btnMyPosts = new javafx.scene.control.Button("My Posts");
	
	
	// This is a separator and it is used to partition the GUI for various tasks
	protected static Line line_Separator4 = new Line(20, 525, width-20,525);
	
	// GUI Area 3: This is last of the GUI areas.  It is used for quitting the application and for
	// logging out.
	protected static Button button_Logout = new Button("Logout");
	protected static Button button_Quit = new Button("Quit");

	// This is the end of the GUI objects for the page.
	
	// These attributes are used to configure the page and populate it with this user's information
	private static ViewStaffHome theView;		// Used to determine if instantiation of the class
												// is needed

	// Reference for the in-memory database so this package has access
	private static Database theDatabase = applicationMain.FoundationsMain.database;

	protected static Stage theStage;			// The Stage that JavaFX has established for us	
	protected static Pane theRootPane;			// The Pane that holds all the GUI widgets
	protected static User theUser;				// The current logged in User
	
	private static Scene theStaffHomeScene;		// The shared Scene each invocation populates
	protected static final int theRole = 3;		// Admin: 1; Role1: 2; Role2: 3

	private static discussionModels.PostManager postManager;
	private static javafx.collections.ObservableList<entityClasses.Post> posts;
	protected static javafx.collections.ObservableList<entityClasses.feedback> feedbacks;
	private static javafx.scene.control.ListView<entityClasses.feedback> feedbackView;
	private static javafx.scene.control.ListView<entityClasses.Post> listView;
	private static javafx.scene.control.Button btnRefresh = new javafx.scene.control.Button("All Posts");
	private static javafx.scene.control.Button btnNew = new javafx.scene.control.Button("New");
	private static javafx.scene.control.Button btnEdit = new javafx.scene.control.Button("Edit");
	private static javafx.scene.control.Button btnDelete = new javafx.scene.control.Button("Delete");
	private static javafx.scene.control.Button btnFeedback = new javafx.scene.control.Button("See Feedback");
	private static javafx.scene.control.Button btnFeedbackTest = new javafx.scene.control.Button("Test Feedback");
	private static javafx.scene.control.TextArea postContent = new javafx.scene.control.TextArea();
	
	
	// variables for the replies
	
	private static discussionModels.ReplyManager replyManager;
	private static javafx.collections.ObservableList<entityClasses.Reply> replies;
	private static javafx.scene.control.ListView<entityClasses.Reply> repliesView;

	private static javafx.scene.control.Button btnReplyNew = new javafx.scene.control.Button("Reply");
	private static javafx.scene.control.Button btnReplyEdit = new javafx.scene.control.Button("Edit Reply");
	private static javafx.scene.control.Button btnReplyDelete = new javafx.scene.control.Button("Delete Reply");
	
	
	
	/*-*******************************************************************************************

	Constructors
	
	 */


	/**********
	 * <p> Method: displayRole1Home(Stage ps, User user) </p>
	 * 
	 * <p> Description: This method is the single entry point from outside this package to cause
	 * the Role1 Home page to be displayed.
	 * 
	 * It first sets up every shared attributes so we don't have to pass parameters.
	 * 
	 * It then checks to see if the page has been setup.  If not, it instantiates the class, 
	 * initializes all the static aspects of the GIUI widgets (e.g., location on the page, font,
	 * size, and any methods to be performed).
	 * 
	 * After the instantiation, the code then populates the elements that change based on the user
	 * and the system's current state.  It then sets the Scene onto the stage, and makes it visible
	 * to the user.
	 * 
	 * @param ps specifies the JavaFX Stage to be used for this GUI and it's methods
	 * 
	 * @param user specifies the User for this GUI and it's methods
	 * 
	 */
	public static void displayStaffHome(Stage ps, User user) {
		
		// Establish the references to the GUI and the current user
		theStage = ps;
		theUser = user;
		
		postManager = new discussionModels.PostManager(applicationMain.FoundationsMain.database);
		replyManager = new discussionModels.ReplyManager(applicationMain.FoundationsMain.database);
		
		// If not yet established, populate the static aspects of the GUI
		if (theView == null) theView = new ViewStaffHome();		// Instantiate singleton if needed
		
		
		// Populate the dynamic aspects of the GUI with the data from the user and the current
		// state of the system.
		theDatabase.getUserAccountDetails(user.getUserName());
		applicationMain.FoundationsMain.activeHomePage = theRole;
		
		label_UserDetails.setText("User: " + theUser.getUserName());
				
		// Set the title for the window, display the page, and wait for the Admin to do something
		theStage.setTitle("CSE 360 Foundations: Student Home Page");
		theStage.setScene(theStaffHomeScene);
		theStage.show();
	}
	
	/**********
	 * <p> Method: ViewRole1Home() </p>
	 * 
	 * <p> Description: This method initializes all the elements of the graphical user interface.
	 * This method determines the location, size, font, color, and change and event handlers for
	 * each GUI object.</p>
	 * 
	 * This is a singleton and is only performed once.  Subsequent uses fill in the changeable
	 * fields using the displayRole2Home method.</p>
	 * 
	 */
	private ViewStaffHome() {

		
		// Create the Pane for the list of widgets and the Scene for the window
		theRootPane = new Pane();
		theStaffHomeScene = new Scene(theRootPane, width, height);	// Create the scene
		
		// Set the title for the window
		
		// Populate the window with the title and other common widgets and set their static state
		
		// GUI Area 1
		label_PageTitle.setText("Staff Home Page");
		setupLabelUI(label_PageTitle, "Arial", 28, width, Pos.CENTER, 0, 5);

		label_UserDetails.setText("User: " + theUser.getUserName());
		setupLabelUI(label_UserDetails, "Arial", 20, width, Pos.BASELINE_LEFT, 20, 55);
		
		setupButtonUI(button_UpdateThisUser, "Dialog", 18, 170, Pos.CENTER, 610, 45);
		button_UpdateThisUser.setOnAction((event) ->
			{ViewUserUpdate.displayUserUpdate(theStage, theUser); });
		TextField tfSearch = new TextField();
		tfSearch.setPromptText("Search posts...");
		ComboBox<String> cbThread = new ComboBox<>();
		cbThread.getItems().addAll("General", "Other", "All");
		cbThread.setValue("All");

		Button btnSearch = new Button("Search");
		btnSearch.setOnAction(e -> {
		    String keyword = tfSearch.getText().trim();
		    String thread = cbThread.getValue();
		    
		    refreshSearchResults(keyword, thread);
		});
		cbThread.setOnAction(e ->
	    refreshSearchResults(tfSearch.getText().trim(), cbThread.getValue())
		);

		btnRefresh.setOnAction(e -> {
		    tfSearch.clear();
		    cbThread.setValue("All");
		    refreshPosts();                 
		});
		
		// GUI Area 2
		
		
		
		posts = javafx.collections.FXCollections.observableArrayList();
		listView = new javafx.scene.control.ListView<>(posts);
		
		feedbacks = javafx.collections.FXCollections.observableArrayList();
		feedbackView = new javafx.scene.control.ListView<>(feedbacks);
		
		
		//FIX TEXT
		feedbackView.setCellFactory(i -> new javafx.scene.control.ListCell<entityClasses.feedback>() {
		    @Override protected void updateItem(entityClasses.feedback p, boolean empty) {
		        super.updateItem(p, empty);
		        if (empty || p == null) { setText(null); setStyle(""); return; }
		       
		           
		            setText("#" + p.getId() + "  " + p.getStaff() + " to " + p.getStudent()
		                    + " - " + p.getContent());

		       
		    }
		});
		
		feedbackView.setOnMouseClicked(ev -> {
		    if (ev.getClickCount() == 2) {
		        var sel = feedbackView.getSelectionModel().getSelectedItem();
		        if (sel != null) openFeedbackViewer(sel);
		    }
		});
		
		// feedback view
		feedbackView.setLayoutX(20);
		feedbackView.setLayoutY(160);
		feedbackView.setPrefSize(width - 40, 340);
		
		listView.setCellFactory(i -> new javafx.scene.control.ListCell<entityClasses.Post>() {
		    @Override protected void updateItem(entityClasses.Post p, boolean empty) {
		        super.updateItem(p, empty);
		        if (empty || p == null) { setText(null); setStyle(""); return; }
		        try {
		            int total  = replyManager.countForPost(p.getId());
		            int unread = replyManager.countUnreadForUser(p.getId(), theUser.getUserName());
		            boolean read = postManager.isRead(p.getId(), theUser.getUserName());

		            String prefix = read ? "  " : "● ";
		            setText(prefix + "#" + p.getId() + "  " + p.getTitle() + " — " + p.getAuthor()
		                    + "  [" + p.getThread() + "]"
		                    + "  (" + total + " replies, " + unread + " unread)");

		            setStyle(read ? "" : "-fx-font-weight: bold;");
		        } catch (java.sql.SQLException e) {
		            e.printStackTrace();
		            setText("#" + p.getId() + "  " + p.getTitle() + " — " + p.getAuthor()
		                    + "  [" + p.getThread() + "]");
		            setStyle("");
		        }
		    }
		});
		
		listView.setCellFactory(i -> new javafx.scene.control.ListCell<entityClasses.Post>() {
		    @Override protected void updateItem(entityClasses.Post p, boolean empty) {
		        super.updateItem(p, empty);
		        if (empty || p == null) { setText(null); return; }
		        boolean deleted = "[POST DELETED]".equals(p.getTitle());
		        setText("#" + p.getId() + "  " + p.getTitle() +
		                (deleted ? "  [deleted]" : "") +
		                " — " + p.getAuthor());
		    }
		});


		
		listView.setOnMouseClicked(ev -> {
		    if (ev.getClickCount() == 2) {
		        var sel = listView.getSelectionModel().getSelectedItem();
		        if (sel != null) openPostViewer(sel);
		    }
		});
		
		listView.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
		    boolean hasSel = sel != null;
		    btnEdit.setDisable(!hasSel || !sel.getAuthor().equals(theUser.getUserName()));
		    btnDelete.setDisable(!hasSel || !sel.getAuthor().equals(theUser.getUserName()));
		});
		
		btnEdit.setDisable(true);
		btnDelete.setDisable(true);
		
		
		// replies list
		replies = javafx.collections.FXCollections.observableArrayList();
		repliesView = new javafx.scene.control.ListView<>(replies);
		repliesView.setCellFactory(i -> new javafx.scene.control.ListCell<entityClasses.Reply>(){
		    @Override protected void updateItem(entityClasses.Reply r, boolean empty){
		        super.updateItem(r, empty);
		        setText(empty || r == null ? null :
		                r.getAuthor() + ": " + r.getContent());
		    }
		});
		repliesView.setLayoutX(20);
		repliesView.setLayoutY( 50 ); 
		repliesView.setPrefSize(width - 40, 180);
			
		
		// toolbar
		javafx.scene.layout.HBox bar = new javafx.scene.layout.HBox(
			    8,
			    btnRefresh,                     // "All Posts"
			    btnMyPosts,                     // "My Posts"
			    new javafx.scene.control.Label("Thread:"),
			    cbThread,
			    tfSearch,
			    btnSearch,
			    new javafx.scene.control.Button("Clear") {{
			        setOnAction(e -> {
			            tfSearch.clear();
			            cbThread.setValue("All");
			            refreshPosts();         // back to full list
			        });
			    }},
			    btnNew, btnEdit, btnDelete, btnFeedback, btnFeedbackTest
			);
			bar.setLayoutX(20);
			bar.setLayoutY(120);


		// list view
		listView.setLayoutX(20);
		listView.setLayoutY(160);
		listView.setPrefSize(width - 40, 340);

		// wire actions
		btnRefresh.setOnAction(e -> refreshPosts());
		btnMyPosts.setOnAction(e -> refreshPostsMine());
		btnNew.setOnAction(e -> openEditor(null));
		btnEdit.setOnAction(e -> {
		    var sel = listView.getSelectionModel().getSelectedItem();
		    if (sel == null) return;
		    if (!sel.getAuthor().equals(theUser.getUserName())) {
		    	alert("Not allowed", "You can only edit your own posts.");
		    	return;
		    }
		    openEditor(sel);
		});
		btnDelete.setOnAction(e -> {
		    var sel = listView.getSelectionModel().getSelectedItem();
		    if (sel == null) return;
		    if (confirm("Delete Post", "Delete post #" + sel.getId() + "?")) {
		        try {
		            postManager.delete(sel.getId());
		            refreshPosts();
		            postContent.setText("");
		            updateButtonStates(null);
		        } catch (java.sql.SQLException ex) {
		            ex.printStackTrace();
		            alert("Error", "Failed to delete post.");
		        }
		    }
		});
		btnFeedback.setOnAction(e->ControllerStaffHome.showFeedback());
		btnFeedbackTest.setOnAction(e->TestFeedbackk.start());

		postContent.setEditable(false);
		postContent.setWrapText(true);
		postContent.setPrefRowCount(10);
		postContent.setLayoutX(20);
		postContent.setLayoutY(160 + 360 + 12);
		postContent.setPrefWidth(width - 40);
		
		
		listView.getSelectionModel().selectedItemProperty().addListener((obs,oldV, sel) -> {
			updateButtonStates(sel);
			postContent.setText(sel == null ? "" : sel.getContent());
		});
		feedbackView.getSelectionModel().selectedItemProperty().addListener((obs,oldV, sel) -> {
			postContent.setText(sel == null ? "" : sel.getContent());
		});
		
		// add Area 2 nodes to root
		theRootPane.getChildren().addAll(bar, listView, feedbackView, postContent);

		
		
		// initial load
		refreshPosts();
		
		
		
		if (!posts.isEmpty()) {
			listView.getSelectionModel().select(0);
			postContent.setText(posts.get(0).getContent());
			updateButtonStates(posts.get(0));
		}
		listView.getSelectionModel().selectedItemProperty().addListener((obs, oldP, newP) -> {
		    if (newP != null) {
		            refreshRepliesFor(newP.getId());
		    }
		});
		
		
		// GUI Area 3
		setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 20, 0);
		button_Logout.setOnAction((event) -> {ControllerStaffHome.performLogout(); });

		setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 0);
		button_Quit.setOnAction((event) -> {ControllerStaffHome.performQuit(); });

		// these keep them pinned to bottom:
		//button_Logout.layoutYProperty().bind(theViewStudentHomeScene.heightProperty().subtract(50));
		//button_Quit.layoutYProperty().bind(theViewStudentHomeScene.heightProperty().subtract(50));
		
		
		button_Logout.layoutYProperty().unbind();
		button_Quit.layoutYProperty().unbind();

		button_Logout.layoutYProperty().bind(
		    theRootPane.heightProperty()
		        .subtract(button_Logout.heightProperty())
		        .subtract(250)   // bottom padding
		);

		button_Quit.layoutYProperty().bind(
		    theRootPane.heightProperty()
		        .subtract(button_Quit.heightProperty())
		        .subtract(250)
		);
        
		// This is the end of the GUI initialization code
		
		// Place all of the widget items into the Root Pane's list of children
         theRootPane.getChildren().addAll(
			label_PageTitle, label_UserDetails, button_UpdateThisUser, line_Separator1,
	        line_Separator4, button_Logout, button_Quit);
	        
         
         
         btnReplyNew.setOnAction(e -> openReplyEditor(null));
         btnReplyEdit.setOnAction(e -> {
             var sel = repliesView.getSelectionModel().getSelectedItem();
             if (sel != null) openReplyEditor(sel);
         });
         btnReplyDelete.setOnAction(e -> {
             var sel = repliesView.getSelectionModel().getSelectedItem();
             if (sel == null) return;

             if (!theUser.getUserName().equals(sel.getAuthor())) {
                 alert("Not allowed", "You can only delete your own replies.");
                 return;
             }
             if (confirm("Delete Reply", "Delete this reply?")) {
                 try {
                     replyManager.delete(sel.getId());
                     var p = listView.getSelectionModel().getSelectedItem();
                     if (p != null) refreshRepliesFor(p.getId());
                 } catch (java.sql.SQLException ex) {
                     ex.printStackTrace();
                     alert("Error", "Failed to delete reply.");
                 }
             }
         });
         
         
         
         
}
	
	
	
	
	
	/*-********************************************************************************************

	Helper methods to reduce code length

	 */
	
	/**********
	 * Private local method to initialize the standard fields for a label
	 * 
	 * @param l		The Label object to be initialized
	 * @param ff	The font to be used
	 * @param f		The size of the font to be used
	 * @param w		The width of the Button
	 * @param p		The alignment (e.g. left, centered, or right)
	 * @param x		The location from the left edge (x axis)
	 * @param y		The location from the top (y axis)
	 */
	private static void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, 
			double y){
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
	private static void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, 
			double y){
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);		
	}
	
	private static void updateButtonStates(entityClasses.Post sel) {
		boolean owner = sel != null && theUser != null
			&& sel.getAuthor().equals(theUser.getUserName());
		btnEdit.setDisable(!owner);
		btnDelete.setDisable(false);
	}
	
	protected static void selectFirstAndSyncUIFeed() {
	    if (!feedbacks.isEmpty()) {
	        feedbackView.getSelectionModel().select(0);
	        listView.setVisible(false);
	        feedbackView.setVisible(true);
	        postContent.setText(feedbacks.get(0).getContent());
	    } else {
	        postContent.setText("");
	        updateButtonStates(null);
	    }
	}
	
	protected static void selectFirstAndSyncUI() {
	    if (!posts.isEmpty()) {
	        listView.getSelectionModel().select(0);
	        listView.setVisible(true);
	        feedbackView.setVisible(false);
	        postContent.setText(posts.get(0).getContent());
	        updateButtonStates(posts.get(0));
	    } else {
	        postContent.setText("");
	        updateButtonStates(null);
	    }
	}
	
	private static void refreshPostsMine() {
	    try {
	        posts.setAll(postManager.listMyPosts(theUser.getUserName())); // Calls list my post
	        System.out.println("Student Home loaded " + posts.size() + " of my posts");
	        selectFirstAndSyncUI();
	    } catch (java.sql.SQLException ex) {
	        ex.printStackTrace();
	        alert("Error", "Failed to load your posts");
	    }
	}
	
	private static void refreshPosts() {
	    try {
	        posts.setAll(postManager.listAll());
	        System.out.println("Student Home loaded " + posts.size() + " post(s) [ALL]");
	        selectFirstAndSyncUI();
	    } catch (java.sql.SQLException ex) {
	        ex.printStackTrace();
	        alert("Error", "Failed to load posts");
	    }
	}
	
	private static void refreshSearchResults(String keyword, String thread) {
	    try {
	        if (keyword == null) keyword = "";
	        if (thread == null || thread.isBlank()) thread = "All";
	        posts.setAll(postManager.searchPosts(keyword, thread));

	        if (!posts.isEmpty()) {
	            listView.getSelectionModel().select(0);
	            postContent.setText(posts.get(0).getContent());
	            updateButtonStates(posts.get(0));
	        } else {
	            postContent.setText("");
	            updateButtonStates(null);
	        }
	    } catch (java.sql.SQLException e) {
	        e.printStackTrace();
	        alert("Error", "Search failed.");
	    }
	}

	
	private static void openEditor(entityClasses.Post existing) {
	    var dlg = new javafx.scene.control.Dialog<entityClasses.Post>();
	    dlg.setTitle(existing == null ? "New post" : "Edit post");
	    var save = new javafx.scene.control.ButtonType("Save", javafx.scene.control.ButtonBar.ButtonData.OK_DONE);
	    dlg.getDialogPane().getButtonTypes().addAll(save, javafx.scene.control.ButtonType.CANCEL);

	    var tfTitle = new javafx.scene.control.TextField();
	    tfTitle.setPromptText("Title");

	    var cbThread = new javafx.scene.control.ComboBox<String>();
	    cbThread.getItems().setAll("General", "Other");          // existing threads only
	    cbThread.setEditable(false);                             // students cant make threads
	    cbThread.setValue("General");                            // default thread

	    var taContent = new javafx.scene.control.TextArea();
	    taContent.setPromptText("Content");
	    taContent.setPrefRowCount(10);

	    if (existing != null) {
	        tfTitle.setText(existing.getTitle());
	        taContent.setText(existing.getContent());
	        if (existing.getThread() != null && !existing.getThread().isEmpty())
	            cbThread.setValue(existing.getThread());
	        cbThread.setDisable(true); 
	    }

	    var box = new javafx.scene.layout.VBox(8,
	            new javafx.scene.control.Label("Title"), tfTitle,
	            new javafx.scene.control.Label("Thread"), cbThread,
	            new javafx.scene.control.Label("Content"), taContent);
	    box.setPadding(new javafx.geometry.Insets(10));
	    dlg.getDialogPane().setContent(box);

	    dlg.setResultConverter(btn -> {
	        if (btn == save) {
	            var t = tfTitle.getText().trim();
	            var c = taContent.getText().trim();
	            var th = cbThread.getValue() == null || cbThread.getValue().isBlank()
	                    ? "General" : cbThread.getValue();

	            if (t.isEmpty() || c.isEmpty()) {
	                alert("Validation", "Title and content are required.");
	                return null;
	            }

	            if (existing == null) {
	                var now = new java.sql.Timestamp(System.currentTimeMillis());
	                return new entityClasses.Post(0, theUser.getUserName(), t, c, th, now, now);
	            } else {
	                return new entityClasses.Post(existing.getId(), existing.getAuthor(), t, c,
	                        th, existing.getCreatedAt(), new java.sql.Timestamp(System.currentTimeMillis()));
	            }
	        }
	        return null;
	    });

	    var result = dlg.showAndWait();
	    if (result.isEmpty()) return;

	    var out = result.get();
	    try {
	        if (existing == null) postManager.create(out);
	        else postManager.update(out);
	        refreshPosts(); 
	    } catch (java.sql.SQLException ex) {
	        ex.printStackTrace();
	        alert("Error", "Failed to save post.");
	    }
	}
	
	private static boolean confirm(String title, String msg) {
	    var a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION, msg,
	            javafx.scene.control.ButtonType.OK, javafx.scene.control.ButtonType.CANCEL);
	    a.setTitle(title);
	    a.setHeaderText(null);
	    var r = a.showAndWait();
	    return r.isPresent() && r.get() == javafx.scene.control.ButtonType.OK;
	}
	
	private static void openPostViewer(entityClasses.Post p) {
	    try { postManager.markRead(p.getId(), theUser.getUserName()); } catch (java.sql.SQLException ex) { ex.printStackTrace(); }

	    var dlg   = new javafx.scene.control.Dialog<Void>();
	    dlg.setTitle("Post");

	    var title = new javafx.scene.control.Label(p.getTitle());
	    title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	    var meta  = new javafx.scene.control.Label("by " + p.getAuthor() + " \u2022 " + p.getCreatedAt());

	    var body = new javafx.scene.control.TextArea(p.getContent());
	    body.setEditable(false); body.setWrapText(true); body.setPrefRowCount(10);

	    var localReplies = javafx.collections.FXCollections.<entityClasses.Reply>observableArrayList();
	    var replyList    = new javafx.scene.control.ListView<>(localReplies);
	    replyList.setPrefHeight(180);
	    replyList.setCellFactory(g -> new javafx.scene.control.ListCell<>() {
	        @Override protected void updateItem(entityClasses.Reply r, boolean empty) {
	            super.updateItem(r, empty);
	            setText(empty || r == null ? null : "#" + r.getId() + " " + r.getAuthor() + " — " + r.getContent());
	        }
	    });

	    var cbUnreadOnly = new javafx.scene.control.CheckBox("Unread only");
	    Runnable loadReplies = () -> {
	        try {
	            if (cbUnreadOnly.isSelected()) {
	                localReplies.setAll(replyManager.listUnreadForPost(p.getId(), theUser.getUserName()));
	            } else {
	                localReplies.setAll(replyManager.listForPost(p.getId()));
	            }
	        } catch (java.sql.SQLException ex) {
	            ex.printStackTrace();
	            alert("Error", "Failed to load replies.");
	        }
	    };
	    cbUnreadOnly.setOnAction(e -> loadReplies.run());
	    loadReplies.run();

	    replyList.setOnMouseClicked(ev -> {
	        if (ev.getClickCount() == 2) {
	            var sel = replyList.getSelectionModel().getSelectedItem();
	            if (sel != null) openSingleReplyWindow(p, sel); 
	        }
	    });

	    var root = new javafx.scene.layout.VBox(8, title, meta, body,
	        cbUnreadOnly,                           
	        new javafx.scene.control.Label("Replies"),
	        replyList
	    );
	    root.setPadding(new javafx.geometry.Insets(10));
	    dlg.getDialogPane().setContent(root);

	    var replyBtn  = new javafx.scene.control.ButtonType("Reply",       javafx.scene.control.ButtonBar.ButtonData.LEFT);
	    var editBtn   = new javafx.scene.control.ButtonType("Edit Reply",  javafx.scene.control.ButtonBar.ButtonData.LEFT);
	    var deleteBtn = new javafx.scene.control.ButtonType("Delete Reply",javafx.scene.control.ButtonBar.ButtonData.LEFT);
	    var feedbackBtn = new javafx.scene.control.ButtonType("Feedback", javafx.scene.control.ButtonBar.ButtonData.LEFT);
	    dlg.getDialogPane().getButtonTypes().addAll(replyBtn, editBtn, deleteBtn, feedbackBtn, javafx.scene.control.ButtonType.CLOSE);

	    ((javafx.scene.control.Button) dlg.getDialogPane().lookupButton(replyBtn))
	        .addEventFilter(javafx.event.ActionEvent.ACTION, e -> { e.consume(); createReply(p, localReplies); loadReplies.run(); });
	    ((javafx.scene.control.Button) dlg.getDialogPane().lookupButton(editBtn))
	        .addEventFilter(javafx.event.ActionEvent.ACTION, e -> { e.consume(); var sel = replyList.getSelectionModel().getSelectedItem(); if (sel != null) { editReply(sel, localReplies); loadReplies.run(); }});
	    ((javafx.scene.control.Button) dlg.getDialogPane().lookupButton(deleteBtn))
	        .addEventFilter(javafx.event.ActionEvent.ACTION, e -> { e.consume(); var sel = replyList.getSelectionModel().getSelectedItem(); if (sel != null) { deleteReply(sel, localReplies); loadReplies.run(); }});
	    ((javafx.scene.control.Button) dlg.getDialogPane().lookupButton(feedbackBtn))
    	.addEventFilter(javafx.event.ActionEvent.ACTION, e -> { e.consume(); ControllerStaffHome.feedbackPopUp(p);});

	    dlg.setOnHidden(ev -> { try { refreshPosts(); } catch (Exception ignore) {} });
	    dlg.showAndWait();
	}
	
	protected static String openFeedbackViewer(entityClasses.feedback p) {

	    var dlg   = new javafx.scene.control.Dialog<Void>();
	    dlg.setTitle("Feedback");

	    var content = new javafx.scene.control.Label(p.getContent());
	    content.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
	    var meta  = new javafx.scene.control.Label("to you");

	    var root = new javafx.scene.layout.VBox(8, content, meta);
		root.setPadding(new javafx.geometry.Insets(10));
		dlg.getDialogPane().setContent(root);
		dlg.getDialogPane().getButtonTypes().addAll(javafx.scene.control.ButtonType.CLOSE);
	    dlg.setOnHidden(ev -> { try { ControllerStaffHome.showFeedback(); } catch (Exception ignore) {} });
	    dlg.showAndWait();
	    return "feedback viewing";
	}


	
	protected static void alert(String title, String msg) {
	    var a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
	    a.setTitle(title);
	    a.setHeaderText(null);
	    a.setContentText(msg);
	    a.showAndWait();
	}
	
	
	private static void refreshRepliesFor(int postId) {
	    // default to show all replies
	    refreshRepliesFor(postId, false);
	}

	private static void refreshRepliesFor(int postId, boolean unreadOnly) {
	    try {
	        if (unreadOnly) {
	            replies.setAll(replyManager.listUnreadForPost(postId, theUser.getUserName()));
	        } else {
	            replies.setAll(replyManager.listForPost(postId));
	        }
	    } catch (java.sql.SQLException e) {
	        e.printStackTrace();
	        alert("Error", "Failed to load replies.");
	    }
	}
	
	

	

	
	private static void openReplyEditor(entityClasses.Reply existing) {
	    var p = listView.getSelectionModel().getSelectedItem();
	    if (p == null) { alert("Pick a post", "Select a post first."); return; }

	    if (existing != null && !theUser.getUserName().equals(existing.getAuthor())) {
	        alert("Not allowed", "You can only edit your own replies.");
	        return;
	    }

	    var dlg = new javafx.scene.control.Dialog<entityClasses.Reply>();
	    dlg.setTitle(existing == null ? "New Reply" : "Edit Reply");
	    var save = new javafx.scene.control.ButtonType("Save", javafx.scene.control.ButtonBar.ButtonData.OK_DONE);
	    dlg.getDialogPane().getButtonTypes().addAll(save, javafx.scene.control.ButtonType.CANCEL);

	    var ta = new javafx.scene.control.TextArea();
	    ta.setPromptText("Reply…");
	    ta.setPrefRowCount(6);
	    if (existing != null) ta.setText(existing.getContent());

	    var box = new javafx.scene.layout.VBox(8, ta);
	    box.setPadding(new javafx.geometry.Insets(10));
	    dlg.getDialogPane().setContent(box);

	    dlg.setResultConverter(btn -> {
	        if (btn == save) {
	            var text = ta.getText().trim();
	            if (text.isEmpty()) { alert("Validation", "Reply cannot be empty."); return null; }
	            var now = new java.sql.Timestamp(System.currentTimeMillis());
	            if (existing == null) {
	                return new entityClasses.Reply(0, theUser.getUserName(), text, now, now, p.getId());
	            } else {
	                return new entityClasses.Reply(existing.getId(),
	                        existing.getAuthor(), text, existing.getCreatedAt(), now, existing.getPostId());
	            }
	        }
	        return null;
	    });

	    var r = dlg.showAndWait();
	    if (r.isEmpty()) return;

	    var out = r.get();
	    try {
	        if (existing == null) replyManager.create(out);
	        else replyManager.update(out);
	        refreshRepliesFor(p.getId());
	    } catch (java.sql.SQLException ex) {
	        ex.printStackTrace();
	        alert("Error", "Failed to save reply.");
	    }
	}
	
	
	private static void createReply(entityClasses.Post p,
        javafx.collections.ObservableList<entityClasses.Reply> replies) {
		var ta = new javafx.scene.control.TextArea();
		ta.setPromptText("Write your reply…");
		var d = new javafx.scene.control.Dialog<String>();
		d.setTitle("New Reply");
		d.getDialogPane().getButtonTypes().addAll(
		javafx.scene.control.ButtonType.OK, javafx.scene.control.ButtonType.CANCEL);
		d.getDialogPane().setContent(ta);
		d.setResultConverter(bt -> bt == javafx.scene.control.ButtonType.OK ? ta.getText().trim() : null);			d.showAndWait().ifPresent(text -> {
		if (text.isEmpty()) return;
		try {
			var now = new java.sql.Timestamp(System.currentTimeMillis());
			var r = new entityClasses.Reply(0, theUser.getUserName(),
				                        text, now, now, p.getId());
				applicationMain.FoundationsMain.database.createReply(r);
				replies.setAll(applicationMain.FoundationsMain.database.listRepliesForPost(p.getId()));
		} catch (java.sql.SQLException ex) {
			ex.printStackTrace();
		}
	});
	}
	
	private static void openSingleReplyWindow(entityClasses.Post parentPost, entityClasses.Reply r) {
	    if (theUser.getUserName().equals(parentPost.getAuthor())) {
	        try {
	            if (!replyManager.isRead(r.getId(), theUser.getUserName())) {
	                replyManager.markOneRead(r.getId(), theUser.getUserName());
	                refreshRepliesFor(parentPost.getId());
	                refreshPostsMine();
	            }
	        } catch (java.sql.SQLException ex) { ex.printStackTrace(); }
	    }

	    // Simple read-only dialog for the reply
	    var dlg = new javafx.scene.control.Dialog<Void>();
	    dlg.setTitle("Reply #" + r.getId());

	    var meta = new javafx.scene.control.Label(
	        "by " + r.getAuthor() + "  •  created: " + r.getCreatedAt()
	        + (r.getUpdatedAt() != null ? "  •  updated: " + r.getUpdatedAt() : "")
	    );

	    var body = new javafx.scene.control.TextArea(r.getContent());
	    body.setEditable(false);
	    body.setWrapText(true);
	    body.setPrefRowCount(10);

	    var root = new javafx.scene.layout.VBox(8,
	        new javafx.scene.control.Label("Reply"),
	        meta,
	        body
	    );
	    root.setPadding(new javafx.geometry.Insets(12));
	    dlg.getDialogPane().setContent(root);
	    dlg.getDialogPane().getButtonTypes().add(javafx.scene.control.ButtonType.CLOSE);
	    dlg.showAndWait();
	}

	private static void editReply(entityClasses.Reply r,
            javafx.collections.ObservableList<entityClasses.Reply> replies) {
			if (!theUser.getUserName().equals(r.getAuthor())) {
				alert("Not allowed", "You can only edit your own replies.");
				return;
			}
			var ta = new javafx.scene.control.TextArea(r.getContent());
			var d = new javafx.scene.control.Dialog<String>();
			d.setTitle("Edit Reply");
			d.getDialogPane().getButtonTypes().addAll(
			javafx.scene.control.ButtonType.OK, javafx.scene.control.ButtonType.CANCEL);
			d.getDialogPane().setContent(ta);
			d.setResultConverter(bt -> bt == javafx.scene.control.ButtonType.OK ? ta.getText().trim() : null);
			
			d.showAndWait().ifPresent(text -> {
			if (text.isEmpty()) return;
			try {
			r.setContent(text);
			r.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
			applicationMain.FoundationsMain.database.updateReply(r);
			replies.setAll(applicationMain.FoundationsMain.database.listRepliesForPost(r.getPostId()));
			} catch (java.sql.SQLException ex) {
			ex.printStackTrace();
			}
			});
			}

	private static void deleteReply(entityClasses.Reply r,
	    javafx.collections.ObservableList<entityClasses.Reply> replies) {
		if (!theUser.getUserName().equals(r.getAuthor())) {
		alert("Not allowed", "You can only delete your own replies.");
		return;
		}
		if (!confirm("Delete Reply", "Delete this reply?")) return;
		try {
		applicationMain.FoundationsMain.database.deleteReply(r.getId());
		replies.setAll(applicationMain.FoundationsMain.database.listRepliesForPost(r.getPostId()));
		} catch (java.sql.SQLException ex) {
		ex.printStackTrace();
		}
		}

	
	
	
	
	
}
