package View.App;

import java.io.File;

import Controller.SystemController;
import View.Tools.ErrorAlert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Represents the main toolbar for the application.
 */
public class Toolbar extends MenuBar {

    // member variables
    private Dashboard dashboard;
    // FILE
    private Menu file;
    private MenuItem newFile;
    private MenuItem open;

    /**
     * Class constructor.
     */
    public Toolbar(Dashboard dashboard){
        // initializing
        this.dashboard = dashboard;
        
        // FILE
        this.file = new Menu("File");
        this.newFile = new MenuItem("New Program");
        this.open = new MenuItem("Open");
        this.file.getItems().addAll(this.newFile, this.open);

        /////////////////
        // CONFIGURING //
        /////////////////

        this.getMenus().addAll(this.file);

        /////////////
        // ACTIONS //
        /////////////

        // File Menu //
        
        this.newFile.setOnAction((e) -> {
            // creating input form
            NewProgramForm newProgramForm = new NewProgramForm();
            newProgramForm.initOwner(this.getScene().getWindow());
            newProgramForm.show();
        });

        this.open.setOnAction((e) -> {
            // configuring the file chooser to load a new file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load CSV File");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSVQL Files", "*.cql"));

            // showing the open dialog
            File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());

            // adding the program to the system
            if (selectedFile != null) {
                try{
                    SystemController.addProgram(selectedFile);
                }
                catch(Exception ex){
                    ErrorAlert errorAlert = new ErrorAlert(ex);
                    errorAlert.showWindow(this.getScene().getWindow());
                }
            }
        });
    }
}