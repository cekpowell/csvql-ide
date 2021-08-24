package View.Terminal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import Controller.FileManager;
import Model.FileType;
import Model.Images;
import Model.KeyCodes;
import View.App.Dashboard;
import View.Tools.CodeArea;
import View.Tools.PopUpWindow;
import View.Tools.SectionTitle;

/**
 * View for displaying the result of running programs.
 */
public class Terminal extends BorderPane{

    // constants
    private static final String initialFileName = "Output.csv";

    // member variables
    private Dashboard dashboard;
    private TerminalToolbar terminalToolbar;
    private CodeArea codeArea;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     * 
     * @param dashboard The dashboard associated with this terminal.
     */
    public Terminal(Dashboard dashboard){
        // initializing
        this.dashboard = dashboard;
        this.terminalToolbar = new TerminalToolbar(this);
        this.codeArea = new CodeArea(FileType.TERMINAL.getCodeMirrorTemplate(), "");

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // configuring title label
        SectionTitle titleLabel = new SectionTitle("Terminal", new ImageView(Images.TERMINAL));

        // container for title 
        VBox container = new VBox(titleLabel);
        container.setPadding(new Insets(10));

        /////////////////
        // CONFIGURING //
        /////////////////

        // event handling
        this.configureEvents();

        // adding controls to the editor
        this.setTop(container);
        this.displayNoOutputScreen();        
    }

    /**
     * Defines the event handling for the events that can occur 
     * within the control.
     */
    private void configureEvents(){
        // Shortcuts
        this.codeArea.setOnKeyPressed((e) -> {
            // CTRL + S 
            if(KeyCodes.CTRL_S.match(e)){
                // saving file
                this.save();
            }
            // CTRL + PLUS
            else if(KeyCodes.CTRL_PLUS.match(e)){
                // performing zoom-in
                this.codeArea.zoomIn();;
            }
            // CTRL + MINUS
            else if(KeyCodes.CTRL_MINUS.match(e)){
                // performing zoom-out
                this.codeArea.zoomOut();;
            }
        });
    }

    //////////////////////
    // NO OUTPUT SCREEN //
    //////////////////////

    /**
     * Configures the panel to display a special screen for when there is no program
     * output to display (i.e., at program initialisation).
     */
    private void displayNoOutputScreen(){
        // creating error label
        Label noOutputLabel = new Label("No Output", new ImageView(Images.MESSAGE));

        // creating error message label
        Label messageLabel = new Label("Use the editor to run a program!");

        // container for labels
        VBox container = new VBox(noOutputLabel, messageLabel);
        container.setSpacing(10);
        container.setAlignment(Pos.CENTER);

        // setting container into panel
        this.setCenter(container);
    }

    ///////////////////////
    // DISPLAYING OUTPUT //
    ///////////////////////

    /**
     * Displays the provided program output within the panel.
     * 
     * @param output The program output to be displayed within the panel.
     */
    public void displayProgramOutput(String output){
        // determining if output is errornous
        if(this.outputIsErrornous(output)){
            // getting the error message
            this.getErrorMessageFromOutput(output);

            // displaying the error message to the panel
            this.displayErrorScreen(this.getErrorMessageFromOutput(output));
        }
        else{
            // setting the text area text
            this.codeArea.setCode(output);

            // creating container for toolbar and code editor
            VBox container = new VBox(this.terminalToolbar, this.codeArea);

            // displaying the container
            this.setCenter(container);
        }
    }

    /////////////////////
    // HANDLING ERRORS //
    /////////////////////

    /**
     * Determines if the provided program otuput was an error. Program otuput
     * is an error if it starts with the text "### EXECUTION ERROR ### ".
     * 
     * @param output The output being checked.
     * @return True if the otuput was an error, false otheriwse.
     */
    private boolean outputIsErrornous(String output){
        return output.startsWith("### EXECUTION ERROR ### ");
    }

    /**
     * Gathers the error message from a program output that was errornous.
     * 
     * @param output The program output that was errornous.
     * @return The error message from the program output.
     */
    private String getErrorMessageFromOutput(String output){
        // getting message parts
        String[] messageParts = output.split(" : ");

        // returning error message (second part)
        return messageParts[1];
    }

    /**
     * Changes the content displayed in the panel to reflect an error that
     * has occurred.
     * 
     * @param errorMessage The message associated with the error that has occured.
     */
    private void displayErrorScreen(String errorMessage){
        // creating error label
        Label errorLabel = new Label("Error", new ImageView(Images.ERROR));

        // creating error message label
        Label errorMessageLabel = new Label(errorMessage);

        // container for labels
        VBox container = new VBox(errorLabel, errorMessageLabel);
        container.setSpacing(10);
        container.setAlignment(Pos.CENTER);

        // setting container into panel
        this.setCenter(container);
    }

    ////////////
    // SAVING //
    ////////////

    /**
     * Saves the terminal content into a new file.
     */
    public void save(){
        try{
            FileManager.writeContentToNewFile(this.codeArea.getCode(), this.getScene().getWindow(), Terminal.initialFileName, FileType.TERMINAL.getExtensionFilters());
        }
        catch(Exception e){
            PopUpWindow.showErrorWindow(this.getScene().getWindow(), e);
        }
    }

    /////////////
    // COPYING //
    /////////////

    /**
     * Copies the Terminal's content to the system clipboard
     */
    public void copy(){
        // copying the content to the clipboard
        FileManager.copyContentToClipboard(this.codeArea.getCode());
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public CodeArea getCodeEditor(){
        return this.codeArea;
    }
}