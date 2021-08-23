package View.Editor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import Controller.SystemController;
import View.Editor.EditorFile.EditorFileType;
import View.Tools.ErrorAlert;
import View.Tools.InputForm;

/**
 * View to represent the window displayed when renaming a file.
 */
public class RenameFileForm extends InputForm{

    // static variables
    private static final String title = "Change File Name";
    private static final int width = 350;
    private static final int height = 215;
    private static final String confirmText = "Create";
    private static final String cancelText = "Cancel";
    private static final String initialNote = "Note: Do not include the file extension in the name!";
    private static final String noNameNote = "Error: You must provide a filename!";
    private static final String fileExtNote = "Error: You must not use a file extension!";
    private static final String sameNameNote = "Error: The filename must not be the same as the original!";
    
    // member variables
    private EditorFileType type;
    private String initialName;
    private String fileExtension;
    private TextField filename;
    private Label noteLabel;

    /**
     * Class constructor.
     */
    public RenameFileForm(String fullInitialname){
        // initializing
        super(title, width, height, confirmText, cancelText, false);
        String[] nameAndExt = fullInitialname.split("\\.");
        this.initialName = nameAndExt[0]; // name is first index in filename
        this.fileExtension = nameAndExt[1]; // file extension is second index in filename
        this.filename = new TextField();
        this.noteLabel = new Label(initialNote);

        // Configuring Member Variables //

        // setting initial name into the filename textfield and selecting the text
        this.filename.setText(this.initialName);
        this.filename.selectAll();
        
        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // Filename label
        Label filenameLabel = new Label("Filename:");

        // contanier for all items
        VBox container = new VBox(filenameLabel, this.filename, this.noteLabel);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10));
        container.setSpacing(10);

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding content to form
        this.setContent(container);
    }


    /**
     * Verifies the user's inputs are valid. Creates a new object if they are, and displays an error message if they
     * are not.
     */
    public void submit(){
        // filename cannot be null
        if(this.filename.getText().equals("")){
            // creating error label
            this.noteLabel.setText(noNameNote);
        }
        // filename cannot have an extension
        else if (this.filename.getText().contains(".")){
            // creating error label
            this.noteLabel.setText(fileExtNote);
        }
        // filename cannot be the same as original
        else if (this.filename.getText().equals(this.initialName)){
            // creating error label
            this.noteLabel.setText(sameNameNote);
        }
        // checks passed
        else{
            // creating a new EditorFile with the provided name
            try{
                // attempting to rename the file
                SystemController.renameSelectedEditoFile(this.filename.getText() + "." + this.fileExtension);

                // closing the form
                this.close();
            }
            catch(Exception ex){
                ErrorAlert.showErrorAlert(this.getScene().getWindow(), ex);
            }
        }
    }
}
