// ADDED BY KENNY NGUYEN: final unified BaseView used by all screens
package guiTools;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class BaseView {

    // ADDED BY KENNY NGUYEN: keep stage here so show() can set scene
    protected final Stage stage;
    protected final double width;
    protected final double height;

    protected final BorderPane root;
    protected final Scene scene;
    protected final Button quitButton;

    // ADDED BY KENNY NGUYEN: constructor
    public BaseView(Stage stage, double width, double height) {
        this.stage = stage;
        this.width = width;
        this.height = height;

        this.root = new BorderPane();
        this.scene = new Scene(root, width, height);

        // ADDED BY KENNY NGUYEN: global stylesheet + dark background k
        scene.getStylesheets().add(BaseView.class.getResource("/styles/app.css").toExternalForm());
        root.getStyleClass().add("page-root");

        // ADDED BY KENNY NGUYEN: Quit button
        this.quitButton = new Button("Quit");
        quitButton.setId("quitBtn");
        HBox quitBox = new HBox(quitButton);
        quitBox.setAlignment(Pos.BOTTOM_LEFT);
        quitBox.setPadding(new Insets(10));
        root.setBottom(quitBox);
        quitButton.setOnAction(e -> onQuit());

        // ADDED BY KENNY NGUYEN: ask child for center content
        root.setCenter(buildContent());
    }

    /** Child returns the main content that goes in the center */
    protected abstract Node buildContent();

    /** ADDED BY KENNY NGUYEN: overridden quit behavior*/
    protected void onQuit() { /* no-op by default */ }

    /** ADDED BY KENNY NGUYEN: expose scene if needed. */
    public Scene getScene() { return scene; }

    /** ADDED BY KENNY NGUYEN: show this view on its stage */
    public void show() {
        stage.setScene(scene);
        stage.show();
    }

   // Helpers for the layout
   // Create a centered VBox
    protected VBox vbox(double spacing, Insets padding, Node... children) {
        VBox v = new VBox(spacing, children);
        v.setAlignment(Pos.CENTER);
        if (padding != null) v.setPadding(padding);
        return v;
    }

    // Create a HBOX that is centered
    protected HBox hbox(double spacing, Insets padding, Node... children) {
        HBox h = new HBox(spacing, children);
        h.setAlignment(Pos.CENTER);
        if (padding != null) h.setPadding(padding);
        return h;
    }
}
