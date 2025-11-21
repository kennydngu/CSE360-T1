package guiPosts;


import discussionModels.PostManager;
import entityClasses.Post;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
/**
 * <p> Class: ConntrollerPosts </p>
 * 
 * <p> Description: Handles all CRUD operations for student discussion posts including loading, creating, reading, editing, and deleting. </p>
 * 
 * 
 * <p> This class acts as a controller that bridges the GUI and the post management in the data base ensuring proper loading and saving between the database,
 * and UI. </p>
 * 
 * @author Team 8
 */
public class ControllerPosts {
    private static final PostManager manager =
            new PostManager(applicationMain.FoundationsMain.database);
    /**
     * <p> Method: LoadPosts </p>
     * 
     * <p> Description: Loads all discussion posts currently in database and populates them into TableView.
     * @param table 	the TableView object to display posts.
     */
    public static void loadPosts(TableView<Post> table) {
        try {
            List<Post> data = manager.listAll();
            table.getItems().setAll(data);
        } catch (SQLException ex) {
            showError("Load Posts Failed", ex.getMessage());
        }
    }
    /**
     * <p> Method: createNewPost </p>
     * 
     * <p> Description: creates a new post object with the given details given through the arguments and inserts it into database. 
     * 
     * 
     * @param author 	the post's author
     * @param title 	the title of the post
     * @param content 	the body of the post
     * @param thread 	the selected thread for the post
     * @param table 	table the TableView object to display post
     */
    public static void createNewPost(String author, String title, String content, String thread,
                                     TableView<Post> table) {
        try {
            Post p = new Post(0, author, title, content, thread,
                    new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()));
            int newId = manager.create(p);
            p.setId(newId);
            table.getItems().add(p);
        } catch (SQLException ex) {
            showError("Create Post Failed", ex.getMessage());
        }
    }
    /**
     * <p> Method: updatePost </p>
     * 
     * <p> Description: Updates an existing post's title and content then saves the changes to database. Refreshes in the TableView ensures that the updated edits are reflected.
     * 
     * @param p				the Post object to be updated
     * @param newTitle		the new title of the post
     * @param newContent	the new content/body of the post
     * @param table			the TableView to refresh after an update
     */
    public static void updatePost(Post p, String newTitle, String newContent,
                                  TableView<Post> table) {
        try {
            p.setTitle(newTitle);
            p.setContent(newContent);
            p.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            if (manager.update(p)) {
                table.refresh();
            }
        } catch (SQLException ex) {
            showError("Update Post Failed", ex.getMessage());
        }
    }
    /**
     * <p> Method: deleteSelected </p>
     * 
     * <p> Description: deletes the currently selected post from the TableView and removes it from the database </p>
     * 
     * @param table	the TableView that the selected post is deleted from
     */
    public static void deleteSelected(TableView<Post> table) {
        Post sel = table.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        try {
            if (manager.delete(sel.getId())) {
                table.getItems().remove(sel);
            }
        } catch (SQLException ex) {
            showError("Delete Post Failed", ex.getMessage());
        }
    }
    /**
     * <p> Method: showError </p>
     * 
     * <p> Description: Displays an error message using a JavaFX alert dialog to show exceptions or failed operations to the user </p>
     * 
     * @param header	the title of the error
     * @param msg		the error message that is displayed
     */
    private static void showError(String header, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Posts");
        a.setHeaderText(header);
        a.setContentText(msg);
        a.showAndWait();
    }
}
