package guiPosts;
import entityClasses.Post;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * <p> Class: ViewPostEditor </p>
 * 
 * <p> Description: Provides a JavaFX dialog window for creating or editing discussion posts. The editor allows users to input a post title
 * and content/body. Upon completion creates a new post object inside the TableView and database or edits an existing post. </p>
 * 
 * @author Team 8
 * 
 */
public class ViewPostEditor {
	
	/**
	 * <p> Interface: SaveHandler </p>
	 * 
	 * <p> Description: Functional interface used to define a callback for handling the save operation when the user confirms post changes </p>
	 */
    public interface SaveHandler { 
    	/** <p> Method: onSave </p>
    	 * 
    	 * <p> Description: Is called when the user hits the save button in the editor dialog. Receives the entered title and content as parameters. </p>
    	 * 
    	 * @param title
    	 * @param content
    	 */
    	void onSave(String title, String content); }
    /**
     * <p> Method: show </p>
     * 
     * <p> Description: Displays a modal dialog window for editing or creating a discussion post. The dialog includes fields for title and content along with a Save and cancel button.
     * </p>
     * 
     * <p> If an existing post object is provided then instead the data of that post is loaded and the user will be able to edit the contents of that post. </p>
     * @param owner
     * @param titleText
     * @param existing
     * @param handler
     */
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
