package View;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Represents the toolbar within the application.
 */
public class Toolbar extends MenuBar {

    // member variables
    // FILE
    private Menu file;
    private MenuItem newFile;
    private MenuItem open;
    private MenuItem save;
    // EDIT
    private Menu edit;
    private MenuItem undo;
    private MenuItem redo;


    /**
     * Class constructor.
     */
    public Toolbar(){
        // initialising member variables
        // FILE
        this.file = new Menu("File");
        this.newFile = new MenuItem("New File");
        this.open = new MenuItem("Open");
        this.save = new MenuItem("Save");
        this.file.getItems().addAll(this.newFile, this.open, this.save);
        // EDIT
        this.edit = new Menu("Edit");
        this.undo = new MenuItem("Undo");
        this.redo = new MenuItem("redo");
        this.edit.getItems().addAll(this.undo, this.redo);

        // adding items to the toolbar
        this.getMenus().addAll(this.file, this.edit);


        // CONFIGURING MENU ITEM ACTIONS //

        // FILE //

        this.newFile.setOnAction((e) -> {
            // TODO
        });

        this.open.setOnAction((e) -> {
            // TODO
        });

        this.save.setOnAction((e) -> {
            // TODO
        });

        // EDIT //

        this.undo.setOnAction((e) -> {
            // TODO
        });

        this.redo.setOnAction((e) -> {
            // TODO
        });
    }
}