package View.Terminal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import View.App.Dashboard;
import View.Editor.EditorFile.EditorFileType;
import View.Tools.CodeEditor;
import View.Tools.ErrorAlert;
import View.Tools.SectionTitle;

/**
 * View for viewing the result of running programs.
 */
public class Terminal extends BorderPane{

    // constants 
    private static final String resourcesPath = Terminal.class.getClassLoader().getResource("codeMirror/").toString();
    private static final String codeMirrorTemplate = 
                                                        "<!doctype html>" +
                                                        "<html>" +
                                                        "<head>" +
                                                        "   <script src=\"" + resourcesPath + "/lib/codemirror.js\"></script>" + 
                                                        "   <link rel=\"stylesheet\" href=\"" + resourcesPath + "lib/codemirror.css\">" + 
                                                        "   <script src=\"" + resourcesPath + "/mode/mathematica/mathematica.js\"></script>" + 
                                                        "   <link rel=\"stylesheet\" href=\"" + resourcesPath + "theme/3024-day.css\">" +
                                                        "</head>" +
                                                        "<body style='margin: 0', bgcolor=#ffffff>" +
                                                        "<form><textarea id=\"code\" name=\"code\">\n" +
                                                        "${code}" +
                                                        "</textarea></form>" +
                                                        "<script>" +
                                                        "  var editor = CodeMirror.fromTextArea(document.getElementById(\"code\"), {" +
                                                        "    mode: \"\"," + // no syntax for tables
                                                        "    lineNumbers: true," +
                                                        "    styleActiveLine: true," +
                                                        "    styleActiveSelected: true," + 
                                                        "    readOnly: true," + 
                                                        "    theme: \"3024-day\"," + 
                                                        "  });" +
                                                        "</script>" +
                                                        "</body>" +
                                                        "</html>";
    private static final Image terminalImage = new Image("terminal.png");
    private static final Image messageImage = new Image("message.png");
    private static final Image errorImage = new Image("error.png");
    private static final KeyCombination keyCombCtrS = new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN);
    private static final KeyCombination keyCombCtrPlus = new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.SHORTCUT_DOWN);
    private static final KeyCombination keyCombCtrMinus = new KeyCodeCombination(KeyCode.MINUS, KeyCombination.SHORTCUT_DOWN);

    // member variables
    private Dashboard dashboard;
    private TerminalToolbar terminalToolbar;
    private CodeEditor codeEditor;

    /**
     * Class constructor.
     * 
     * @param dashboard The dashboard associated with this terminal.
     */
    public Terminal(Dashboard dashboard){
        // initializing
        this.dashboard = dashboard;
        this.terminalToolbar = new TerminalToolbar(this);
        this.codeEditor = new CodeEditor(Terminal.codeMirrorTemplate, "");

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // configuring title label
        SectionTitle titleLabel = new SectionTitle("Terminal", new ImageView(terminalImage));

        // container for title 
        VBox container = new VBox(titleLabel);
        container.setPadding(new Insets(10));

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding controls to the editor
        this.setTop(container);
        this.displayNoOutputScreen();

        /////////////
        // ACTIONS //
        /////////////

        // Shortcuts
        this.codeEditor.setOnKeyPressed((e) -> {
            // CTRL + S 
            if(keyCombCtrS.match(e)){
                // saving file
                this.saveTerminalContent();
            }
            // CTRL + PLUS
            else if(keyCombCtrPlus.match(e)){
                // performing zoom-in
                this.codeEditor.zoomIn();;
            }
            // CTRL + MINUS
            else if(keyCombCtrMinus.match(e)){
                // performing zoom-out
                this.codeEditor.zoomOut();;
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
            this.codeEditor.setCode(output);

            // creating container for toolbar and code editor
            VBox container = new VBox(this.terminalToolbar, this.codeEditor);

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
        Label errorLabel = new Label("Error", new ImageView(errorImage));

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
    public void saveTerminalContent(){
        // getting the file to save the program to
        File chosenFile = this.getSaveFile();

        if(chosenFile != null){
            try{
                // saving program
                this.writeContentToFile(chosenFile);
            }
            // handling error
            catch (Exception e) {
                ErrorAlert.showErrorAlert(this.getScene().getWindow(), e);
            }
        }
    }

    /**
     * Writes the content of the EditorFile into the provided File.
     * 
     * @param file The File the EditorFile is being written into.
     * @return True if the file was writen, false if not.
     */
    private void writeContentToFile(File file){
        try {
            // content to save
            String saveContent = this.codeEditor.getCode();

            // writing save content to file
            OutputStream out = new FileOutputStream(file);
            out.write(saveContent.getBytes());
            out.close();
        }
        // handling error
        catch (Exception e) {
            ErrorAlert.showErrorAlert(this.getScene().getWindow(), e);
        }
    }

    /**
     * Helper method to save an EditorFile that has not yet been saved. Opens
     * a file choser dialog and allows the user to select where the EditorFile will
     * be saved.
     * 
     * @param initialFilename The initial name of the file being saved.
     * @return The selected file if one was chosen, null if it was not.
     */
    private File getSaveFile(){
        // setting up the file choser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("Output");
        fileChooser.getExtensionFilters().addAll(EditorFileType.TABLE.getExtensionFilter());

        // showing the saving dialog
        return fileChooser.showSaveDialog(this.getScene().getWindow());
    }

    /////////////
    // COPYING //
    /////////////

    /**
     * Copies the Terminal's content to the system clipboard
     */
    public void copyTerminalContent(){
        // setting up clipboard
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        // getting terminal content
        content.putString(this.codeEditor.getCode());

        // putting terminal content into system clipboard
        clipboard.setContent(content);
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public CodeEditor getCodeEditor(){
        return this.codeEditor;
    }
}
