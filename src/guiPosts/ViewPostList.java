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

public class ViewPostList {
    private static ObservableList<Post> posts = FXCollections.observableArrayList();
    private static ListView<Post> listView = new ListView<>(posts);
    private static PostManager pm = new PostManager(applicationMain.FoundationsMain.database);

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
