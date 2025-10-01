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

    // keep stage here so show() can set scene
    protected final Stage stage;
    protected final double width;
    protected final double height;

    protected final BorderPane root;
    protected final Scene scene;
    protected final Button quitButton;
	protected final Button logoutButton;
	

    // constructor
    public BaseView(Stage stage, double width, double height) {
        this.stage = stage;
        this.width = width;
        this.height = height;

        this.root = new BorderPane();
        this.scene = new Scene(root, width, height);

        // global stylesheet + dark background k
        scene.getStylesheets().add(BaseView.class.getResource("/styles/app.css").toExternalForm());
        root.getStyleClass().add("page-root");

        // Quit button
        this.quitButton = new Button("Quit");
        this.logoutButton = new Button("Logout");
        quitButton.setId("quitBtn");
        quitButton.setOnAction(e -> onQuit());
        logoutButton.setOnAction(e -> onLogout());
        

        HBox footer = new HBox(10);
        footer.setAlignment(Pos.BOTTOM_LEFT);
        footer.setPadding(new Insets(10));
        
        if (showLogout()) {
            footer.getChildren().addAll(logoutButton, quitButton);
        } else {
            footer.getChildren().add(quitButton);
        }
        root.setBottom(footer);

        // Ask subclass for center content
        root.setCenter(buildContent());
    }
      

    // child builds the content 
    protected abstract Node buildContent();
    
    // Choose whether or not logout button will be shown by default yes if no the view will update this to false
    protected boolean showLogout() { return true; }

    // child decides what happens on quit
    protected void onQuit() { }
    protected void onLogout() { }
    


    // method to expose scene
    public Scene getScene() { return scene; }
    public Stage getStage() { return stage; }

    // show view
    public void show() {
        stage.setScene(scene);
        stage.show();
    }

   // helpers for the layout
   // create a centered VBox
    protected VBox vbox(double spacing, Insets padding, Node... children) {
        VBox v = new VBox(spacing, children);
        v.setAlignment(Pos.CENTER);
        if (padding != null) v.setPadding(padding);
        return v;
    }

    // create a HBOX that is centered
    protected HBox hbox(double spacing, Insets padding, Node... children) {
        HBox h = new HBox(spacing, children);
        h.setAlignment(Pos.CENTER);
        if (padding != null) h.setPadding(padding);
        return h;
    }
}
