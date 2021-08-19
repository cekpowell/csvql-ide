package View.FileStore;

import java.io.File;

import View.Tools.ConfirmationButton;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * View to represent a file that has been loaded into the filestore.
 */
public class LoadedFile extends HBox{

    // member variables
    private FileStore filestore;
    private File file;
    private String name; 
    private ConfirmationButton removeButton;

    /**
     * Class constructor.
     * 
     * @param file The File object associated with this loaded file.
     */
    public LoadedFile(FileStore filestore, File file){
        // initializing
        this.filestore = filestore;
        this.file = file;
        this.name = file.getName();
        Label nameLabel = new Label(this.name);
        this.removeButton = new ConfirmationButton("X", 
                                                   "Remove Loaded File", 
                                                   "Are you sure you want to remove this file from the file store?");

        /////////////////
        // CONFIGURING //
        /////////////////

        this.getChildren().addAll(nameLabel, this.removeButton);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(5);

        /////////////
        // ACTIONS //
        /////////////

        // remove
        this.removeButton.setOnAction((e) -> {
            // showing the confirmation button pop-up window
            boolean removeConfirmed = this.removeButton.showConfirmationWindow(this.getScene().getWindow());

            // clearing if the use confirmed the remove
            if(removeConfirmed){
                // removing the loaded file from the filestore
                this.filestore.removeLoadedFile(this);
            }
        });
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public File getFile(){
        return this.file;
    }

    public String getName(){
        return this.name;
    }
}
