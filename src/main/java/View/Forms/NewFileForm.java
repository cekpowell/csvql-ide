package View.Forms;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import Controller.SystemController;
import Model.FileType;
import View.Tools.InputForm;
import View.Tools.PopUpWindow;

/**
 * View to represent the window displayed when creating a new file.
 */
public class NewFileForm extends InputForm{

    // constants
    private static final String title = "Create New File";
    private static final int width = 350;
    private static final int height = 215;
    private static final String confirmText = "Create";
    private static final String cancelText = "Cancel";
    private static final String initialNote = "Note: Do not include the file extension in the name!";
    private static final String noNameNote = "Error: You must provide a filename!";
    private static final String fileExtNote = "Error: You must not include a file extension!";
    
    // member variables
    private ToggleGroup fileType;
    private RadioButton program;
    private RadioButton table;
    private TextField filename;
    private Label noteLabel;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     */
    public NewFileForm(){
        // initializing
        super(title, width, height, confirmText, cancelText, false);
        this.fileType = new ToggleGroup();
        this.program = new RadioButton("Program");
        this.table = new RadioButton("Table");
        this.filename = new TextField();
        this.noteLabel = new Label(initialNote);

        // Configuring Member Variables //

        // connfiguring file type radio buttons
        this.program.setToggleGroup(this.fileType);
        this.program.setUserData(FileType.PROGRAM);
        this.table.setToggleGroup(this.fileType);
        this.table.setUserData(FileType.TABLE);
        this.fileType.selectToggle(this.program);
        
        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // container for radio buttons
        HBox fileTypeContainer = new HBox(this.program, this.table);
        fileTypeContainer.setAlignment(Pos.CENTER);
        fileTypeContainer.setSpacing(10);

        // Filename label
        Label filenameLabel = new Label("Filename:");

        // contanier for all items
        VBox container = new VBox(fileTypeContainer, filenameLabel, this.filename, this.noteLabel);
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
            // creating a new EditorTab through system controller
            try{
                // creating new program through system controller
                if(this.fileType.getSelectedToggle() == this.program){
                    // creating new program through system controller
                    SystemController.getInstance().createNewEditorTab(this.filename.getText() + FileType.PROGRAM.getDefaultExtension(), // NAME
                                                                       FileType.PROGRAM);                                                  // FILE TYPE
                }
                else{
                    // creating new table through system controller
                    SystemController.getInstance().createNewEditorTab(this.filename.getText() + FileType.TABLE.getDefaultExtension(), // NAME
                                                                       FileType.TABLE);                                                // FILE TYPE
                }

                // closing the form
                this.close();
            }
            catch(Exception ex){
                PopUpWindow.showErrorWindow(this.getScene().getWindow(), ex);
            }
        }
    }
}