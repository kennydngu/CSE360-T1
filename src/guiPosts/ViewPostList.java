package guiPosts;

import discussionModels.PostManager;
import entityClasses.Post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

/**
 * <p> Class: ViewPostList </p>
 * 
 * <p> Description: Displays a list of discussion posts within a JavaFX user interface. Provides functionality for displaying and refreshing posts from the database. </p>
 * 
 * <p> The class uses a ListView to load post summaries and relies on PostManager for database access.
 * The GUI allows users to view post titles, authors, post IDs, and a refresh button that updates the list </p>
 * 
 * @author Team 8
 */
public class ViewPostList {
	/**
	 * <p> Fields: posts, ListView, and pm. </p>
	 * 
	 * <p> Description: posts | Observable list that holds all post objects currently displayed in the ListView. Updates the UI when modified </p>
	 * <p> Description: ListView | ListView component that visually displays the list of posts for the user. Each list cell shows post ID, title, and author. </p>
	 * <p> Description pm | Instance of PostManager that retrieves post data from database. Used to populate and refresh posts shown in the GUI </p>
	 */
    private static ObservableList<Post> posts = FXCollections.observableArrayList();
    private static ListView<Post> listView = new ListView<>(posts);
    private static PostManager pm = new PostManager(applicationMain.FoundationsMain.database);
    /**
     * <p> Method: show </p>
     * 
     * <p> Description: Displays the main post list window, The method initializes the JavaFX layout and sets up ListView cell formatting and loads post from the database. </p>
     * 
     * <p> A refresh button is included that manually reloads post data from database. The layout includes a header, list section, and padding for proper spacing.
     * 
     * @param stage				the JavaFX Stage that the post list is displayed on
     * @param currentUserName	the username of the currently logged in user.
     */
    public static void show(Stage stage, String currentUserName) {
        BorderPane root = new BorderPane();

        Label title = new Label("Posts");
        Button refreshBtn = new Button("Refresh");
        HBox header = new HBox(10, title, refreshBtn);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(10));

        listView.setCellFactory(j -> new ListCell<>() {
            @Override protected void updateItem(Post p, boolean empty) {
                super.updateItem(p, empty);
                if (empty || p == null) {
                    setText(null);
                } else {
                    setText("#" + p.getId() + "  " + p.getTitle() + "  —  " + p.getAuthor());
                }
            }
        });

        VBox center = new VBox(listView);
        center.setPadding(new Insets(10));

        root.setTop(header);
        root.setCenter(center);

        refreshBtn.setOnAction(e -> refresh());

        // load once
        refresh();

        stage.setScene(new Scene(root, 900, 600));
        stage.setTitle("Posts");
        stage.show();
    }
    
    /**
     * <p> Method: refresh </p>
     * 
     * <p> Description: Reloads the list of posts from the database and updates the ListView. The method is invoked when a post is edited, created, deleted, or updated,
     * , when the refresh button is invoked, or when the GUI is first launched. </p>
     * 
     * <p> If an SQL exception occurs during loaded the error is printed to the console </p>
     * 
     */

    public static void refresh() {
        try {
            List<Post> data = pm.listAll();
            posts.setAll(data);
            System.out.println("[DEBUG] GUI loaded " + data.size() + " post(s)");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
