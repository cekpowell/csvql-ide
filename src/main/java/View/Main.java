package View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import Controller.SystemController;
import View.App.Dashboard;
import View.App.DashboardToolbar;
import View.Tools.PopUpWindow;

/**
 * Main class for the application.
 */
public class Main extends Application{

    // static variables
    private static final int width = 1300;
    private static final int height = 800;
    private static final String titleName = "CSVQL IDE";
    private static final String authorName = "charles powell";

    /**
     * Main method - entry point for the program.
     * 
     * @param args System arguments.
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
        DashboardToolbar toolbar = new DashboardToolbar(dashboard);

        // configuring system controller
        SystemController.init(toolbar, dashboard);

        // container for components
        VBox container = new VBox();
        container.getChildren().addAll(toolbar,dashboard);
        VBox.setVgrow(dashboard,Priority.ALWAYS);

        // configuring the scene
        Scene scene = new Scene(container,width,height);

        // configuring the stage
        stage.setScene(scene);

        // Closing Event
        stage.setOnCloseRequest((e) ->{
            // testing for unsaved editor tabs
            if(dashboard.getEditor().getEditorTabContainer().hasUnsavedFiles()){
                // confirming the close
                boolean confirmClosed = PopUpWindow.showConfirmationWindow(dashboard.getScene().getWindow(), 
                                                                                  "Close Application", 
                                                                                  "Are you sure you want to close without saving?");

                // closing system if exit confirmed
                if(confirmClosed){
                    System.exit(0);
                }
                // consuming close event if exit not confirmed
                else{
                    e.consume();
                }
            }
        });

        // loading the stage
        stage.setTitle(titleName + " by " + authorName);
        stage.show();
    }
}