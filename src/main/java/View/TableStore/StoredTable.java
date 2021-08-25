package View.TableStore;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox; 

import Controller.SystemController;
import Model.Images;
import View.Tools.ConfirmationButton;
import View.Tools.PopUpWindow;

/**
 * View to represent a file that has been loaded into the filestore.
 */
public class StoredTable extends HBox{

    // member variables
    private TableStore filestore;
    private File file;
    private String name; 
    private Label nameLabel;
    private Button openInEditorButton;
    private ConfirmationButton removeButton; 

    //////////////////
    // INITIALIZING //
    //////////////////  

    /**
     * Class constructor.
     * 
     * @param file The File object associated with this loaded file.
     */
    public StoredTable(TableStore filestore, File file){
        // initializing
        this.filestore = filestore;
        this.file = file;
        this.name = file.getName();
        this.nameLabel = new Label(this.name);
        this.openInEditorButton = new Button("", new ImageView(Images.OPEN_IN_EDITOR)); // ImageView created here so each button has a seperate image
        this.removeButton = new ConfirmationButton("", new ImageView(Images.REMOVE),
                                                   "Remove Loaded File", 
                                                   "Are you sure you want to remove this table from the system?" + "\n" +
                                                   "Any unsaved progress in the Editor will be lost.");

        // Configuring Member Variables //

        // setting graphic of name label
        this.nameLabel.setGraphic(new ImageView(Images.TABLE));

        /////////////////
        // CONFIGURING //
        /////////////////

        // event handling
        this.configureEvents();

        // adding controls
        this.getChildren().addAll(this.nameLabel, this.openInEditorButton, this.removeButton);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(5);
    }

    /**
     * Defines the event handling for the events that can occur 
     * within the control.
     */
    private void configureEvents(){
        // Open in Editor
        this.openInEditorButton.setOnAction((e) -> {
            // placing the file within the editor
            try{
                SystemController.getInstance().openStoredTableIntoEditor(this);
            }
            // handling error
            catch (Exception ex) {
                PopUpWindow.showErrorWindow(this.getScene().getWindow(), ex);
            } 
        });

        // Remove
        this.removeButton.setOnAction((e) -> {
            // showing the confirmation button pop-up window
            boolean removeConfirmed = this.removeButton.showConfirmationWindow(this.getScene().getWindow());

            // clearing if the use confirmed the remove
            if(removeConfirmed){
                // removing the loaded file from the filestore
                this.filestore.removeStoredTable(this);
            }
        });
    }

    /**
     * Updates the content displayed in the stored table graphic.
     */
    private void updateContent(){
        // updating name label
        this.nameLabel.setText(this.name);
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

    public void setFile(File file){
        this.file = file;

        /**
         * Also need to update the name and displayed content.
         */
        this.name = file.getName();
        this.updateContent();
    }
}
