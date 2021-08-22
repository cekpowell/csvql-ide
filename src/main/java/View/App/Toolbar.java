package View.App;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import Controller.SystemController;

/**
 * Represents the main toolbar for the application.
 */
public class Toolbar extends MenuBar {

    // constants
    private static final Image fileImage = new Image("file.png");
    private static final Image openImage = new Image("open.png");
    private static final Image newImage = new Image("new.png");

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
        this.file = new Menu("File", new ImageView(fileImage));
        this.openProgram = new MenuItem("Open", new ImageView(openImage));
        this.newProgram = new MenuItem("New", new ImageView(newImage));
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
            SystemController.createNewFile();
        });

        // Open Program
        this.openProgram.setOnAction((e) -> {
            // opening files through the system controller
            SystemController.openFile();
        });
    }
}