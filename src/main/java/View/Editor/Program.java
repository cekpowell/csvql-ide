package View.Editor;

import java.io.File;

import Controller.SystemController;
import View.Tools.ErrorAlert;

/**
 * View that represents an individual program being edited in the editor.
 */
public class Program extends EditorFile{

    /**
     * Class constructor.
     * 
     * @param fileContainer The container associated with this program.
     * @param name The name of the program.
     */
    public Program(FileContainer fileContainer, String name){
        super(EditorFileType.PROGRAM, fileContainer, name);
    }

    /**
     * Class constructor.
     * 
     * @param fileContainer The container associated with this Program.
     * @param file The file associated with the program.
     */
    public Program(FileContainer fileContainer, File file){
        super(EditorFileType.PROGRAM, fileContainer, file);
    }

    /**
     * Runninng the program.
     */
    public void run(){
        // running the program through the system controller
        try{
            SystemController.runProgram(this);
        }
        catch(Exception ex){
            ErrorAlert.showErrorAlert(this.getFileContainer().getScene().getWindow(), ex);
        }
    }
}
