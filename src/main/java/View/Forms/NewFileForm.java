package View.Forms;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import Controller.SystemController;
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
    private static final String noNameNote = "Error: You must provide a filename!";
    private static final String FILENAME_DELIM = " ";
    
    // member variables
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
    public static void showForm(Window owner){
        // creating input form
        NewFileForm newFileForm = new NewFileForm();  
        newFileForm.initOwner(owner);

        // displaying input form
        newFileForm.show();
    }

    //////////////////////////////
    // SUBMITTING FORM CONTENTS //
    //////////////////////////////

    /**
     * Verifies the user's inputs are valid. Creates a new object if they are, and displays an error message if they
     * are not.
     */
    public void submit(){
        // VALIDATING //

        // filename cannot be null
        if(this.filename.getText().equals("")){
            // creating error label
            this.noteLabel.setText(noNameNote);
        }
        
        // VALIDATED //

        else{
            // gathering list of file names
            String[] filenames = this.filename.getText().split(NewFileForm.FILENAME_DELIM);
            
            // recording if all files were created successfully
            boolean success = true;

            // iterating through list of filenames
            for(String filename : filenames){
                try{
                    // creating new program through system controller
                    SystemController.getInstance().createNewFile(filename);
                }
                catch(Exception ex){
                    success = false;
                    PopUpWindow.showErrorWindow(this.getScene().getWindow(), ex);
                }
            }
            
            // closing the form if all files created successfully
            if(success){
                this.close();
            }
        }
    }
}