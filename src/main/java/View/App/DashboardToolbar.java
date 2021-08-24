package View.App;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

import Controller.SystemController;
import Model.Images;

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
            SystemController.getInstance().createNewFile();
        });

        // Open Program
        this.openFile.setOnAction((e) -> {
            // opening files through the system controller
            SystemController.getInstance().openFile();
        });
    }
}