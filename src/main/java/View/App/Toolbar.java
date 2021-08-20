package View.App;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.util.List;

import Controller.SystemController;
import View.Tools.ErrorAlert;

/**
 * Represents the main toolbar for the application.
 */
public class Toolbar extends MenuBar {

    // member variables
    private Dashboard dashboard;
    // FILE
    private Menu file;
    private MenuItem newProgram;
    private MenuItem openProgram;

    /**
     * Class constructor.
     */
    public Toolbar(Dashboard dashboard){
        // initializing
        this.dashboard = dashboard;
        this.file = new Menu("File");
        this.newProgram = new MenuItem("New Program");
        this.openProgram = new MenuItem("Open Program");
        this.file.getItems().addAll(this.newProgram, this.openProgram);

        /////////////////
        // CONFIGURING //
        /////////////////

        this.getMenus().addAll(this.file);

        /////////////
        // ACTIONS //
        /////////////

        // New Program
        this.newProgram.setOnAction((e) -> {
            // creating input form
            NewProgramForm newProgramForm = new NewProgramForm();
            newProgramForm.initOwner(this.getScene().getWindow());
            newProgramForm.show();
        });

        // Open Program
        this.openProgram.setOnAction((e) -> {
            // configuring the file chooser to load a new file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load CSV File");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSVQL Files", "*.cql"));

            // showing the open dialog
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(this.getScene().getWindow());

            // adding the file to the filestore if a file was gathered
            if (selectedFiles != null) {
                for(File file : selectedFiles){
                    try{
                        SystemController.addProgram(file);
                    }
                    // handling error
                    catch(Exception ex){
                        ErrorAlert.showErrorAlert(this.getScene().getWindow(), ex);
                    }
                }
            }
        });
    }
}