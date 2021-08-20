package View.App;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import Controller.SystemController;
import View.Tools.ErrorAlert;
import View.Tools.InputForm;

/**
 * View to represent the window displayed when creating a new file.
 */
public class NewProgramForm extends InputForm{

    // static variables
    private static final String title = "Create New Program";
    private static final int width = 350;
    private static final int height = 175;
    private static final String confirmText = "Create";
    private static final String cancelText = "Cancel";
    private static final String initialNote = "Note: Do not include the file extension in the name!";
    private static final String noNameNote = "Error: You must provide a filename!";
    private static final String fileExtNote = "Error: You must not use a file extension!";
    
    // member variables
    private TextField filename;
    private Label noteLabel;

    /**
     * Class constructor.
     */
    public NewProgramForm(){
        // initializing
        super(title, width, height, confirmText, cancelText, false);
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

        // selecting text field
        this.filename.requestFocus();

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
        // all good
        else{
            // creating a new program with the provided name
            try{
                SystemController.addProgram(this.filename.getText());

                // closing the form
                this.close();
            }
            catch(Exception ex){
                ErrorAlert.showErrorAlert(this.getScene().getWindow(), ex);
            }
        }
    }
}
