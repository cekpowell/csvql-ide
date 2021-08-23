package View.Editor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.File;

import Controller.SystemController;
import View.App.Dashboard;
import View.App.NewProgramForm;
import View.Tools.SectionTitle;

/**
 * View for editing programs within the IDE.
 */
public class Editor extends BorderPane{

    // constants
    private static final Image editorImage = new Image("img/editor.png");
    private static final Image openImage = new Image("img/open.png");
    private static final Image newImage = new Image("img/new.png");

    // member variables
    private Dashboard dashboard;
    private FileContainer fileContainer;

    /**
     * Class constructor.
     */
    public Editor(Dashboard dashboard){
        // initializing
        this.dashboard = dashboard;
        this.fileContainer = new FileContainer(this);

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // title label
        SectionTitle titleLabel = new SectionTitle("Editor", new ImageView(editorImage));

        // container for title and toolbar
        VBox container = new VBox(titleLabel);
        container.setPadding(new Insets(10));

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding controls to the editor
        this.setTop(container);
        this.showNoEditorFileScreen();
    }

    ///////////////////////////
    // NO EDITOR FILE SCREEN //
    ///////////////////////////

    /**
     * Shows a view for when their are no programs currently open.
     */
    public void showNoEditorFileScreen(){
        // buttons to open program or create new one
        Button openFileButton = new Button("Open", new ImageView(openImage));
        Button newFileButton = new Button("New", new ImageView(newImage));

        // container for these buttons 
        VBox container = new VBox(openFileButton, newFileButton);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        // Open Program Action //
        openFileButton.setOnAction((e) -> {
            // opening files through the system controller
            SystemController.openFile();
        });

        // New Program Action //
        newFileButton.setOnAction((e) -> {
            // creating input form
            NewProgramForm newProgramForm = new NewProgramForm();
            newProgramForm.initOwner(this.getScene().getWindow());
            newProgramForm.show();
        });

        // adding buttons to editor
        this.setCenter(container);
    }

    /////////////////////
    // ADDING PROGRAMS //
    /////////////////////

    /**
     * Adds a new program into the editor with the provided name.
     * 
     * @param name The name of the new program.
     */
    public void createNewProgram(String name) throws Exception{
        // creating new program
        this.fileContainer.createNewProgram(name);

        // setting file container into the control
        this.setCenter(this.fileContainer);
    }

    /**
     * Adds a new program into the editor with the provided name.
     * 
     * @param name The name of the new program.
     */
    public void loadProgram(File file) throws Exception{
        // creating new program
        this.fileContainer.loadProgram(file);

        // setting file container into the control
        this.setCenter(this.fileContainer);
    }

    ///////////////////
    // ADDING TABLES //
    ///////////////////

    /**
     * Adds a new table into the editor with the provided name.
     * 
     * @param name The name of the new table.
     */
    public void createNewTable(String name) throws Exception{
        // creating new table
        this.fileContainer.createNewTable(name);

        // setting file container into the control
        this.setCenter(this.fileContainer);
    }

    /**
     * Adds a new program into the editor with the provided name.
     * 
     * @param name The name of the new program.
     */
    public void loadTable(File file) throws Exception{
        // loading table
        this.fileContainer.loadTable(file);

        // setting file container into the control
        this.setCenter(this.fileContainer);
    }

    /////////////////////
    // REMOVING TABLES //
    /////////////////////

    /**
     * Removes the table associated with the provided file.
     * 
     * @param file The file associated with the table.
     */
    public void removeTable(File file){
        // removing the table from the file container
        this.fileContainer.removeTable(file);
    }

    ////////////
    // HELPERS //
    ////////////

    /**
     * Determines if the editor currently has unsaved files opened.
     * 
     * @return True if the editor has unsaved files open, false if not.
     */
    public boolean hasUnsavedFiles(){
        // running the method on the file container
        return this.fileContainer.hasUnsavedFiles();
    }


    /**
     * Determines if an open editor file already has the name provided.
     * 
     * @param name The name being checked for.
     * @return True if an open editor file has the name, false if not.
     */
    public boolean editorFileAlreadyHasName(String name){
        // checking open programs
        for(Program program : this.fileContainer.getPrograms()){
            if(program.getFile().getName().equals(name)){
                return true;
            }
        }

        // checking open tables
        for(Table table : this.fileContainer.getTables()){
            if(table.getFile().getName().equals(name)){
                return true;
            }
        }

        // no matches, returning false
        return false;
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public EditorFile getSelectedEditorFile(){
        return this.fileContainer.getCurrentEditorFile();
    }
}
