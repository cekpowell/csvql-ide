package View.Forms;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import Controller.SystemController;
import Model.FileType;
import View.Editor.EditorTab;
import View.Tools.InputForm;
import View.Tools.PopUpWindow;

/**
 * View to represent the window displayed when creating a new file.
 */
public class RenameFileForm extends InputForm{

    // constants
    private static final String title = "Rename File";
    private static final int width = 350;
    private static final int height = 215;
    private static final String confirmText = "Confirm";
    private static final String cancelText = "Cancel";
    private static final String initialNote = "Note: Do not include the file extension in the name!";
    private static final String noNameNote = "Error: You must provide a filename!";
    private static final String fileExtNote = "Error: You must not use a file extension!";
    
    // member variables
    private EditorTab editorTab;
    private TextField filename;
    private Label noteLabel;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     * 
     * @param editorTab The EditorTab being renamed.
     */
    public RenameFileForm(EditorTab editorTab){
        // initializing
        super(title, width, height, confirmText, cancelText, false);
        this.editorTab = editorTab;
        this.filename = new TextField();
        this.noteLabel = new Label(initialNote);
        
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

        // content
        this.setContent(container);
    }

    //////////////////////////////
    // SUBMITTING FORM CONTENTS //
    //////////////////////////////

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
        // checks passed
        else{
            try{
                // EditorTab is Program
                if(this.editorTab.getFileType() == FileType.PROGRAM){
                    // renaming the new EditorTab through system controller
                    SystemController.getInstance().renameEditorTab(editorTab, this.filename.getText() + FileType.PROGRAM.getDefaultExtension());
                }
                // EditorTag is Table
                else{
                    // renaming the new EditorTab through system controller
                    SystemController.getInstance().renameEditorTab(editorTab, this.filename.getText() + FileType.TABLE.getDefaultExtension());
                }

                // closing the form
                this.close();
            }
            catch(Exception ex){
                // displaying error alert
                PopUpWindow.showErrorWindow(this.getOwner(), "Unable to rename file.");
            }
        }
    }
}