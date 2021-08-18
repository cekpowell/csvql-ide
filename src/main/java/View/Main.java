package View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Main class for the application.
 */
public class Main extends Application{

    // static variables
    private static final String titleName = "CSVQL IDE";
    private static final String authorName = "charles powell";

    /**
     * Main method - entry point for the program.
     * 
     * @param args System arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starting point for application. Creates the scene for the
     * application.
     * 
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        // creating application components
        Toolbar toolbar = new Toolbar();
        Dashboard dashboard = new Dashboard();

        VBox container = new VBox();
        container.getChildren().addAll(toolbar,dashboard);
        VBox.setVgrow(dashboard,Priority.ALWAYS);

        // configuring the stage
        Scene scene = new Scene(container,1200,750);
        stage.setScene(scene);
        stage.setTitle(titleName + " by " + authorName);
        stage.show();
    }
}
