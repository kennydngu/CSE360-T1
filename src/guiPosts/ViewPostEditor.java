package guiPosts;
import entityClasses.Post;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewPostEditor {

    public interface SaveHandler { void onSave(String title, String content); }

    public static void show(Stage owner, String titleText, Post existing, SaveHandler handler) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(owner);
        dialog.setTitle(titleText);

        TextField title = new TextField(existing != null ? existing.getTitle() : "");
        title.setPromptText("Title");

        TextArea body = new TextArea(existing != null ? existing.getContent() : "");
        body.setPromptText("Content");
        body.setPrefRowCount(12);

        Button save = new Button("Save");
        Button cancel = new Button("Cancel");

        save.setOnAction(e -> {
            String t = title.getText().trim();
            String c = body.getText().trim();
            if (t.isEmpty() || c.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Title and Content are required.").showAndWait();
                return;
            }
            handler.onSave(t, c);
            dialog.close();
        });
        cancel.setOnAction(e -> dialog.close());

        VBox box = new VBox(10, new Label("Title"), title, new Label("Content"), body, new HBox(10, save, cancel));
        box.setPadding(new Insets(12));

        dialog.setScene(new Scene(box, 520, 420));
        dialog.showAndWait();
    }
}
