package View.Editor;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.control.*;

/**
 * View to contain all the programs being edited within the editor.
 */
public class ProgramContainer extends TabPane{
    
    // member variables
    private Editor editor;
    private ArrayList<Program> programs;
    private Program currentProgram;

    /**
     * Class constructor.
     * 
     * @param editor The editor associated with this program container.
     */
    public ProgramContainer(Editor editor){
        // initializing
        this.editor = editor;
        this.programs = new ArrayList<Program>(); 
        this.currentProgram = null;

        // Connfiguriing
        this.getStyleClass().add("floating"); // TODO should be done with CSS
    }

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
                throw new Exception("A program with this name is already present in the editor!");
            }
        }

        // adding program to system
        this.programs.add(program);
        this.getTabs().add(program);
        this.getSelectionModel().select(program);

        // setting current program
        this.setCurrentProgram(program);
    }

    /**
     * Removed a program from the system.
     * 
     * @param program The program being removed.
     */
    public void removeProgram(Program program){
        // removing the program
        this.programs.remove(program);

        // displaying no programs screen if there are no more programs
        if(this.programs.size() == 0){
            this.editor.showNoProgramScreen();
        }
    }


    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////


    public Program getCurrentProgram(){
        return this.currentProgram;
    }

    public void setCurrentProgram(Program program){
        this.currentProgram = program;
    }
}
