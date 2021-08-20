package View.Editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import View.Tools.ConfirmationWindow;
import View.Tools.ErrorAlert;

/**
 * View that represents an individual program being edited in the editor.
 */
public class Program extends Tab{

    // static variables
    private static final String fileExtension = ".cql";
    private static final String unsavedLabel = "(Unsaved)";
    private static final KeyCombination keyCombCtrS = new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN);
    private static final KeyCombination keyCombCtrR = new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN);
    
    // member variables
    private ProgramContainer programContainer;
    private File file;
    private String name;
    private ProgramToolbar toolbar;
    private TextArea textArea;
    private String textAtLastSave;
    private boolean unsavedChanges;

    /**
     * Class constructor. Creates empty Program instance with the provided file name.
     * 
     * @param programContainer The program container associated with this program.
     * @param name The name of the program.
     */
    public Program(ProgramContainer programContainer, String name){
        // initializing
        this.programContainer = programContainer;
        this.file = null;
        this.name = name + fileExtension;
        this.toolbar = new ProgramToolbar(this);
        this.textArea = new TextArea();
        this.textAtLastSave = new String();
        this.unsavedChanges = true;
        this.init();

        /////////////////
        // CONFIGURING //
        /////////////////

        this.setText(this.name + unsavedLabel);
    }

    /**
     * Class constructor. Creates new Program instance from existing File object.
     * 
     * @param programContainer The program container associated with this program.
     * @param file The file associated with the program.
     */
    public Program(ProgramContainer programContainer, File file){
        // initializing
        this.programContainer = programContainer;
        this.file = file;
        this.name = file.getName();
        this.toolbar = new ProgramToolbar(this);
        this.textArea = new TextArea();
        this.unsavedChanges = false;

        // Configuring Member Variables //

        // Adding the file content to the text area //
        try{
            String content = this.getFileContent();
            
            // setting the text area
            this.textArea.setText(content);

            // setting the text at last save
            this.textAtLastSave = content;
        }
        // handling error
        catch(Exception e){
            ErrorAlert.showErrorAlert(this.programContainer.getScene().getWindow(), e);
        }

        // running init method
        this.init();

        /////////////////
        // CONFIGURING //
        /////////////////

        this.setText(this.name);
    }

    /**
     * Initalizer method due to multiple constructors.
     */
    public void init(){
        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        BorderPane container = new BorderPane();
        container.setTop(this.toolbar);
        container.setCenter(this.textArea);

        /////////////////
        // CONFIGURING //
        /////////////////

        this.toolbar.setPadding(new Insets(10));
        this.setContent(container);

        /////////////
        // ACTIONS //
        /////////////

        // Tab Selection
        this.setOnSelectionChanged((e) ->{
            this.programContainer.setCurrentProgram(this);
        });

        // Tab Close
        this.setOnCloseRequest((e) -> {
            // Unsaved Changes //
            if(this.unsavedChanges){
                // confirming the close
                boolean confirmClosed = ConfirmationWindow.showConfirmationWindow(this.programContainer.getScene().getWindow(), 
                                                                                "Close Unsaved Program", 
                                                                                "Are you sure you want to close '" + this.name + "' without saving?");

                if(confirmClosed){
                    // removing the program from the container
                    this.programContainer.removeProgram(this);
                }
                else{
                    e.consume();
                }
            }
            // Saved //
            else{
                // removing the program from the container
                this.programContainer.removeProgram(this);
            }
        });

        // Key Pressed
        this.textArea.setOnKeyPressed((e) -> {
            // CTRL + S 
            if(keyCombCtrS.match(e)){
                // saving program
                this.save();
            }
            // CTRL + R
            else if(keyCombCtrR.match(e)){
                // TODO run the program
            }
        });

        // Text area text changed
        this.textArea.textProperty().addListener((e) -> {
            // text has changed
            if(!this.textAtLastSave.equals(this.textArea.getText())){
               // there are no unsaved changes yet - need to update tab information
               if(!this.unsavedChanges){
                   this.unsavedChanges = true;
                   this.setText(this.name + unsavedLabel);
               }
           }
           // text has gone back to last save
           else{
               // there were previously unsaved changes - need to update tab information
               if(this.unsavedChanges){
                   this.unsavedChanges = false;
                   this.setText(this.name);
               }
           }
       });
    }

    //////////////////////////
    // READING FILE CONTENT //
    //////////////////////////

    /**
     * Returns the content stored within the file associated with the program at the time 
     * of the method being called.
     * 
     * @return The content from within the file as a String. 
     */
    public String getFileContent() throws Exception{
        // setting up file reader
        BufferedReader reader = new BufferedReader(new FileReader(this.file));

        // iterating through the file
        String content = "";
        while(reader.ready()){
            // getting next line
            String line = reader.readLine();

            // buildiing the content
            content += line + "\n";
        }

        // closiing the reader
        reader.close();

        // returning the content
        return content;
    }

    ////////////
    // SAVING //
    ////////////

    /**
     * Saves the program into a new file.
     */
    public void saveAs(){
        // getting the file to save the program to
        File chosenFile = this.getCqlFile();

        if(chosenFile != null){
            // saving program
            this.file = chosenFile;
            this.writeProgramToFile(this.file);
        }
    }

    /**
     * Saves the program into it's file. If the program does not have a file,
     * a pop-up is displayed that allows the user to select one.
     */
    public void save(){

        // Program Has File //

        if(this.file != null){
            // saving program
            this.writeProgramToFile(this.file);
        }

        // Program Has No File //

        else{
            // getting the file to save the program to
            File chosenFile = this.getCqlFile();

            if(chosenFile != null){
                // saving program
                this.file = chosenFile;
                this.writeProgramToFile(this.file);
            }
        }
    }

    /**
     * Writes the content of the program into the provided file.
     * 
     * @param file The file the program is being written into.
     * @return True if the file was writen, false if not.
     */
    private void writeProgramToFile(File file){
        try {
            // writing program content to file
            OutputStream out = new FileOutputStream(file);
            out.write(this.textArea.getText().getBytes());
            out.close();

            // updating tab information
            this.unsavedChanges = false;
            this.name = file.getName();
            this.setText(this.name);
            this.textAtLastSave = this.textArea.getText();
        }
        // handling error
        catch (Exception e) {
            ErrorAlert.showErrorAlert(this.programContainer.getScene().getWindow(), e);
        }
    }

    /**
     * Helper method to open a save a program that has not yet been saved. Opens
     * a file choser dialog and allows the user to select where the file is saved
     * to.
     * 
     * @param initialFilename The name of the file being exported.
     * @return The selected file if one was chosen, null if it was not.
     */
    private File getCqlFile(){
        // setting up the file choser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName(this.name);
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSVQL Files", "*.cql"));

        // showing the saving dialog
        return fileChooser.showSaveDialog(this.programContainer.getScene().getWindow());
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public String getName(){
        return this.name;
    }

    public TextArea getTextArea(){
        return this.textArea;
    }

    public File getFile(){
        return this.file;
    }
}
