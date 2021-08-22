package View.Editor;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.control.*;

/**
 * View to contain all the programs being edited within the editor.
 */
public class FileContainer extends TabPane{
    
    // member variables
    private Editor editor;
    private ArrayList<Program> programs;
    private ArrayList<Table> tables;
    private EditorFile currentEditorFile;

    /**
     * Class constructor.
     * 
     * @param editor The editor associated with this program container.
     */
    public FileContainer(Editor editor){
        // initializing
        this.editor = editor;
        this.programs = new ArrayList<Program>(); 
        this.tables = new ArrayList<Table>();
        this.currentEditorFile = null;

        /////////////////
        // CONFIGURING //
        /////////////////

        // formatting the tab pane
        this.getStyleClass().add("floating"); // TODO should be done with CSS
    }

    //////////////////////////
    // REMOVING EDITOR FILE //
    //////////////////////////

    /**
     * Removed an editor file from the container. 
     * 
     * @param editorFile The EditorFile being removed from the container.
     */
    public void removeEditorFile(EditorFile editorFile){
        // dealing with file type
        if(editorFile instanceof Program){
            this.removeProgram((Program) editorFile);
        }
        else{
            this.removeTable((Table) editorFile);
        }
    }

    /////////////////////
    // ADDING PROGRAMS //
    /////////////////////

    /**
     * Adds a new program into the program container
     * 
     * @param name The name of the new program.
     */
    public void createNewProgram(String name) throws Exception{
        // creating new program
        Program program = new Program(this, name);

        // adding program to system
        this.addProgram(program);
    }

    /**
     * Loads an existing program from a file.
     * 
     * @param file The file containing the existing program.
     */
    public void loadProgram(File file) throws Exception{
        // creating new program
        Program program = new Program(this,file);

        // adding program to system
        this.addProgram(program);
    }

    /**
     * Adds a created program into the system (used by createNewProgram and loadProgram).
     * 
     * @param program The program being added into the system.
     */
    private void addProgram(Program program) throws Exception{
        // checking if program with this name already exists
        for(Program prog : this.programs){
            if(prog.getName().equals(program.getName())){
                throw new Exception("A program with the name'" + program.getName() + "' is already present in the editor!");
            }
        }

        // adding program to system
        this.programs.add(program);
        this.getTabs().add(program);
        this.getSelectionModel().select(program);

        // setting current program
        this.setCurrentEditorFile(program);
    }

    //////////////////////
    // REMOING PROGRAMS //
    //////////////////////

    /**
     * Removes a program from the system.
     * 
     * @param program The program being removed.
     */
    private void removeProgram(Program program){
        // removing the program
        this.programs.remove(program);

        // displaying no programs screen if there are no more programs
        if(this.programs.size() == 0 && this.tables.size() == 0){
            this.editor.showNoEditorFileScreen();
        }
    }

    ///////////////////
    // ADDING TABLES //
    ///////////////////

    /**
     * Adds a new table into the file container
     * 
     * @param name The name of the new Table.
     */
    public void createNewTable(String name) throws Exception{
        // creating new program
        Table table = new Table(this, name);

        // adding program to system
        this.addTable(table);
    }

    /**
     * Loads an existing table from a file.
     * 
     * @param file The file containing the existing Table.
     */
    public void loadTable(File file) throws Exception{
        // creating new program
        Table program = new Table(this,file);

        // adding program to system
        this.addTable(program);
    }

    /**
     * Adds a created Table into the system (used by createNewTable and loadTable).
     * 
     * @param table The table being added into the system.
     */
    private void addTable(Table table) throws Exception{
        // checking if program with this name already exists in the editor
        for(Table tab : this.tables){
            if(tab.getName().equals(table.getName())){
                throw new Exception("A table with the name'" + table.getName() + "' is already present in the editor!");
            }
        }
        // checking if a program with this name already exists in the table store
        // TODO

        // adding program to system
        this.tables.add(table);
        this.getTabs().add(table);
        this.getSelectionModel().select(table);

        // setting current program
        this.setCurrentEditorFile(table);
    }

    /////////////////////
    // REMOVING TABLES //
    /////////////////////

    /**
     * Removes a table from the system.
     * 
     * @param table The table being removed.
     */
    private void removeTable(Table table){
        // removing the program
        this.tables.remove(table);

        // removing the tab from the pane
        this.getTabs().remove(table);

        // displaying no programs screen if there are no more programs
        if(this.tables.size() == 0 && this.programs.size() == 0){
            this.editor.showNoEditorFileScreen();
        }
    }

    /**
     * Removes the table associated with the provided file.
     * 
     * @param file The file of the table being removed.
     */
    public void removeTable(File file){
        // iterating through tables and findnig the matching file
        Table matchedTable = null;
        for(Table table : this.tables){
            if(table.getFile() == file){
                matchedTable = table;
            }
        }

        // removing the table if a match was found
        this.removeTable(matchedTable);
    }


    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////


    public EditorFile getCurrentEditorFile(){
        return this.currentEditorFile;
    }

    public void setCurrentEditorFile(EditorFile editorFile){
        this.currentEditorFile = editorFile;
    }
}
