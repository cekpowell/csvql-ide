package View.App;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Controller.SystemController;
import Model.FileType;
import Model.Images;
import View.Tools.PopUpWindow;
import View.Forms.*;

/**
 * The main toolbar for the application.
 */
public class DashboardToolbar extends MenuBar {

    // member variables
    private Dashboard dashboard;
    private Menu file;
    private MenuItem newFile;
    private MenuItem openFile;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     * 
     * @param dashboard The Dashboard associated with this toolbar.
     */
    public DashboardToolbar(Dashboard dashboard){
        // initializing
        this.dashboard = dashboard;
        this.file = new Menu("File", new ImageView(Images.FILE));
        this.newFile = new MenuItem("New", new ImageView(Images.NEW_FILE));
        this.openFile = new MenuItem("Open", new ImageView(Images.OPEN));

        // Configuring Member Variables //

        // File Menu Item
        this.file.getItems().addAll(this.newFile, this.openFile);

        /////////////////
        // CONFIGURING //
        /////////////////

        // event handling
        this.configureEvents();

        // content
        this.getMenus().addAll(this.file);
    }

    /**
     * Defines the event handling for the events that can occur 
     * within the control.
     */
    private void configureEvents(){
        // New File
        this.newFile.setOnAction((e) -> {
            // displaying new file form
            NewFileForm.showForm(this.getScene().getWindow());
        });

        // Open File
        this.openFile.setOnAction((e) -> {
            // configuring the file chooser to load a new file into the system
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");
            fileChooser.getExtensionFilters().addAll(FileType.PROGRAM.getExtensionFilters());
            fileChooser.getExtensionFilters().addAll(FileType.TABLE.getExtensionFilters());

            // showing the open dialog
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(this.getScene().getWindow());

            // checking if files were opened
            if (selectedFiles != null) {
                // iterating through all selected files
                for(File selectedFile : selectedFiles){
                    try{
                        // loading file through system controller
                        SystemController.getInstance().loadFile(selectedFile, FileType.PROGRAM, FileType.TABLE);
                    }
                    catch(Exception ex){
                        // handling errors
                        PopUpWindow.showErrorWindow(this.getScene().getWindow(), ex);
                    }
                }
            }
        });
    }
}