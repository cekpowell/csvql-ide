package View.TableStore;

import java.io.File;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import Controller.SystemController;
import View.Tools.ConfirmationButton;
import View.Tools.ErrorAlert;

/**
 * View to represent a file that has been loaded into the filestore.
 */
public class Table extends HBox{

    // constants
    private static final Image tableImage = new Image("table.png");
    private static final Image openInEditorImage = new Image("openInEditor.png");
    private static final Image removeImage = new Image("remove.png");

    // member variables
    private TableStore filestore;
    private File file;
    private String name; 
    private Button openInEditorButton;
    private ConfirmationButton removeButton;

    /**
     * Class constructor.
     * 
     * @param file The File object associated with this loaded file.
     */
    public Table(TableStore filestore, File file){
        // initializing
        this.filestore = filestore;
        this.file = file;
        this.name = file.getName();
        this.openInEditorButton = new Button("", new ImageView(openInEditorImage)); // ImageView created here so each button has a seperate image
        this.removeButton = new ConfirmationButton("", new ImageView(removeImage),
                                                   "Remove Loaded File", 
                                                   "Are you sure you want to remove this table from the system?" + "\n" +
                                                   "Any unsaved progress in the Editor will be lost.");


        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // label for table
        Label nameLabel = new Label(this.name);
        nameLabel.setGraphic(new ImageView(tableImage));

        /////////////////
        // CONFIGURING //
        /////////////////

        this.getChildren().addAll(nameLabel, this.openInEditorButton, this.removeButton);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(5);

        /////////////
        // ACTIONS //
        /////////////

        // open in editor
        this.openInEditorButton.setOnAction((e) -> {
            // placing the file within the editor
            try{
                SystemController.addTableToEditor(this.file);
            }
            // handling error
            catch (Exception ex) {
                ErrorAlert.showErrorAlert(this.getScene().getWindow(), ex);
            }
        });

        // remove
        this.removeButton.setOnAction((e) -> {
            // showing the confirmation button pop-up window
            boolean removeConfirmed = this.removeButton.showConfirmationWindow(this.getScene().getWindow());

            // clearing if the use confirmed the remove
            if(removeConfirmed){
                // removing the loaded file from the filestore
                this.filestore.removeTable(this);

                // removing the file from the editor
                SystemController.removeTableFromEditor(this.file);
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
