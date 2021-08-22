package View.Terminal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import View.App.Dashboard;
import View.Tools.SectionTitle;

/**
 * View for viewing the result of running programs.
 */
public class Terminal extends BorderPane{

    // constants
    private static final Image terminalImage = new Image("terminal.png");
    private static final Image messageImage = new Image("message.png");
    private static final Image errorImage = new Image("error.png");

    // member variables
    private Dashboard dashboard;
    private TerminalToolbar terminalToolbar;
    private TextArea textArea;

    /**
     * Class constructor.
     * 
     * @param dashboard The dashboard associated with this terminal.
     */
    public Terminal(Dashboard dashboard){
        // initializing
        this.dashboard = dashboard;
        this.terminalToolbar = new TerminalToolbar(this);
        this.textArea = new TextArea();

        // Configuring member variables //

        this.terminalToolbar.setDisable(true);

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // configuring title label
        SectionTitle titleLabel = new SectionTitle("Terminal", new ImageView(terminalImage));

        // container for title and toolbar
        VBox container = new VBox(titleLabel, this.terminalToolbar);
        container.setPadding(new Insets(10));

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding controls to the editor
        this.setTop(container);
        this.displayNoOutputScreen();
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
        Label noOutputLabel = new Label("No Output", new ImageView(messageImage));

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
            this.textArea.setText(output);

            // enabling output toolbar
            this.terminalToolbar.setDisable(false);

            // displaying the text area 
            this.setCenter(this.textArea);
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
        Label errorLabel = new Label("Error", new ImageView(errorImage));

        // creating error message label
        Label errorMessageLabel = new Label(errorMessage);

        // container for labels
        VBox container = new VBox(errorLabel, errorMessageLabel);
        container.setSpacing(10);
        container.setAlignment(Pos.CENTER);

        // setting container into panel
        this.setCenter(container);

        // disabling toolbar
        this.terminalToolbar.setDisable(true);
    }
}
