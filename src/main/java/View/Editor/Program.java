package View.Editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import View.Tools.ErrorAlert;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

/**
 * View that represents an individual program being edited in the editor.
 */
public class Program extends Tab{

    // static variables
    private static final String fileExtension = ".cql";
    private static final String unsavedChar = "*";
    
    // member variables
    private ProgramContainer programContainer;
    private File file;
    private String name;
    private ProgramToolbar toolbar;
    private TextArea textArea;

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

        // running init method
        this.init();
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

        // adding the file content to the text area.
        try{
            BufferedReader reader = new BufferedReader(new FileReader(this.file));

            String content = "";
            while(reader.ready()){
                // getting next line
                String line = reader.readLine();

                // buildiing the content
                content += line + "\n";
            }
            
            // setting the text area
            this.textArea.setText(content);
        }
        catch(Exception e){
            // showing error alert
            ErrorAlert errorAlert = new ErrorAlert(e);
            errorAlert.showWindow(this.programContainer.getScene().getWindow());
        }

        // running init method
        this.init();
    }

    /**
     * Initalizer method due to multiple constructors.
     */
    public void init(){
        ////////////////
        // CONTAINERS //
        ////////////////

        BorderPane container = new BorderPane();
        container.setTop(this.toolbar);
        container.setCenter(this.textArea);

        /////////////////
        // CONFIGURING //
        /////////////////

        this.toolbar.setPadding(new Insets(10));

        this.setContent(container);
        this.setText(this.name);

        /////////////
        // ACTIONS //
        /////////////

        // event for tab selection
        this.setOnSelectionChanged((e) ->{
            this.programContainer.setCurrentProgram(this);
        });

        // event for tab close
        this.setOnCloseRequest((e) -> {
            // TODO check if saved, display window asking to confirm if not

            // removing the program from the container
            this.programContainer.removeProgram(this);
        });
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
}
