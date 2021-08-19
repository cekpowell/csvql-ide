package View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Controller.SystemController;
import View.App.Dashboard;
import View.App.Toolbar;

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
        Dashboard dashboard = new Dashboard();
        Toolbar toolbar = new Toolbar(dashboard);

        // configuring system controller
        SystemController.setToolbar(toolbar);
        SystemController.setDashboard(dashboard);

        // container for components
        VBox container = new VBox();
        container.getChildren().addAll(toolbar,dashboard);
        VBox.setVgrow(dashboard,Priority.ALWAYS);

        // configuring the scene
        Scene scene = new Scene(container,1200,750);


        // configuring the stage
        stage.setScene(scene);

        // loading the stage
        stage.setTitle(titleName + " by " + authorName);
        stage.show();
    }
}
