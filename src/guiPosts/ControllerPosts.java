package guiPosts;


import discussionModels.PostManager;
import entityClasses.Post;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ControllerPosts {
    private static final PostManager manager =
            new PostManager(applicationMain.FoundationsMain.database);

    public static void loadPosts(TableView<Post> table) {
        try {
            List<Post> data = manager.listAll();
            table.getItems().setAll(data);
        } catch (SQLException ex) {
            showError("Load Posts Failed", ex.getMessage());
        }
    }

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

    private static void showError(String header, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Posts");
        a.setHeaderText(header);
        a.setContentText(msg);
        a.showAndWait();
    }
}
