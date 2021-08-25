package View.Editor;

import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import Controller.ProgramRunner;
import Controller.SystemController;
import Model.FileType;
import Model.Images;
import View.Tools.PopUpWindow;

/**
 * View that represents an individual program being edited in the editor.
 */
public class ProgramTab extends EditorTab{

    // member variables
    private Button runButton;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor
     * 
     * @param editorTabContainer The container associated with the ProgramTab.
     * @param name The name of the program.
     * @param file The file associated with the program.
     */
    public ProgramTab(EditorTabContainer editorTabContainer, String name, File file){
        // initializing
        super(editorTabContainer, name, file, FileType.PROGRAM);
        this.runButton = new Button("Run", new ImageView(Images.RUN));

        /////////////////
        // CONFIGURING //
        /////////////////

        // event handling
        this.configureEvents();

        // adding run button to toolbar
        this.getEditorTabToolbar().addRightContainerWithSep(this.runButton);
    }

    /**
     * Class constructor.
     * 
     * @param editorTabContainer The container associated with the ProgramTab.
     * @param name The name of the program.
     */
    public ProgramTab(EditorTabContainer editorTabContainer, String name){
        // initializing
        super(editorTabContainer, name, FileType.PROGRAM);
        this.init();
    }

    /**
     * Class constructor.
     * 
     * @param editorTabContainer The container associated with the ProgramTab
     * @param file The file associated with the program.
     */
    public ProgramTab(EditorTabContainer editorTabContainer, File file){
        super(editorTabContainer, file, FileType.PROGRAM);
        this.init();
    }

    /**
     * Init method - needed due to multiple constructors.
     */
    private void init(){
        // initializing
        this.runButton = new Button("Run", new ImageView(Images.RUN));

        /////////////////
        // CONFIGURING //
        /////////////////

        // event handling
        this.configureEvents();

        // adding run button to toolbar
        this.getEditorTabToolbar().addRightContainerWithSep(this.runButton);
    }

    /**
     * Defines the event handling for the events that can occur 
     * within the control.
     */
    private void configureEvents(){
        // RUN
        this.runButton.setOnAction((e) -> {
            // running the program through the system controller
            try{
                // trying to run the program
                ProgramRunner.runProgramTab(this);
            }
            catch(Exception ex){
                // showing error window
                PopUpWindow.showErrorWindow(this.getEditorTabToolbar().getScene().getWindow(), ex);
            }
        });
    } 
}