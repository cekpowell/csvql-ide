package View.Forms;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
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
    private static final String noNameNote = "Error: You must provide a filename!";
    
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
        this.noteLabel = new Label();
        
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

    /////////////////////
    // DISPLAYING FORM //
    /////////////////////

    /**
     * Creates a NewFileForm instance and displays it on the screen.
     * 
     * @param owner The Window the NewFileForm will be displayed into.
     */
    public static void showForm(Window owner, EditorTab editorTab){
        // configuring rename window
        RenameFileForm renameFileForm = new RenameFileForm(editorTab);
        renameFileForm.initOwner(owner);

        // displaying rename window
        renameFileForm.show();
    }

    //////////////////////////////
    // SUBMITTING FORM CONTENTS //
    //////////////////////////////

    /**
     * Verifies the user's inputs are valid. Creates a new object if they are, and displays an error message if they
     * are not.
     */
    public void submit(){
        // VALIDATING INPUT //

        // filename cannot be null
        if(this.filename.getText().equals("")){
            // creating error label
            this.noteLabel.setText(noNameNote);
        }

        // VALIDATED //
        
        else{
            try{
                // EditorTab is Program
                if(this.editorTab.getFileType() == FileType.PROGRAM_CSVQL){
                    // renaming the new EditorTab through system controller
                    SystemController.getInstance().renameEditorTab(editorTab, this.filename.getText());
                }
                // EditorTag is Table
                else{
                    // renaming the new EditorTab through system controller
                    SystemController.getInstance().renameEditorTab(editorTab, this.filename.getText());
                }

                // closing the form
                this.close();
            }
            catch(Exception ex){
                // displaying error alert
                PopUpWindow.showErrorWindow(this.getOwner(), ex);
            }
        }
    }
}