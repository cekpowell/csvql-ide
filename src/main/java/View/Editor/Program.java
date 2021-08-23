package View.Editor;

import java.io.File;

import Controller.SystemController;
import View.Tools.ErrorAlert;

/**
 * View that represents an individual program being edited in the editor.
 */
public class Program extends EditorFile{

    // constants
    private static final String resourcesPath = Program.class.getClassLoader().getResource("codeMirror/").toString();
    private static final String codeMirrorTemplate = 
                                                        "<!doctype html>" +
                                                        "<html>" +
                                                        "<head>" +
                                                        "   <script src=\"" + resourcesPath + "/lib/codemirror.js\"></script>" + 
                                                        "   <link rel=\"stylesheet\" href=\"" + resourcesPath + "lib/codemirror.css\">" + 
                                                        "   <link rel=\"stylesheet\" href=\"" + resourcesPath + "theme/3024-day.css\">" + 
                                                        "   <link rel=\"stylesheet\" href=\"" + resourcesPath + "hint/show-hint.css\">" + 
                                                        "   <script src=\"" + resourcesPath + "/hint/show-hint.js\"></script>" + 
                                                        "   <script src=\"" + resourcesPath + "/hint/sql-hint.js\"></script>" + 
                                                        "   <script src=\"" + resourcesPath + "/mode/sql/sql.js\"></script>" + 
                                                        "</head>" +
                                                        "<body style='margin: 0', bgcolor=#ffffff>" +
                                                        "<form><textarea id=\"code\" name=\"code\">\n" +
                                                        "${code}" +
                                                        "</textarea></form>" +
                                                        "<script>" +
                                                        "  var editor = CodeMirror.fromTextArea(document.getElementById(\"code\"), {" +
                                                        "    mode: \"text/x-csvql\"," + 
                                                        "    lineNumbers: true," +
                                                        "    spellcheck: true," +
                                                        "    styleActiveLine: {nonEmpty: true}," +
                                                        "    styleActiveSelected: true," + 
                                                        "    theme: \"3024-day\"," + 
                                                        "    extraKeys: {\"Ctrl-Space\": \"autocomplete\"}" + 
                                                        "  });" +
                                                        "</script>" +
                                                        "</body>" +
                                                        "</html>";

    /**
     * Class constructor.
     * 
     * @param fileContainer The container associated with this program.
     * @param name The name of the program.
     */
    public Program(FileContainer fileContainer, String name){
        super(EditorFileType.PROGRAM, fileContainer, name, Program.codeMirrorTemplate);
    }

    /**
     * Class constructor.
     * 
     * @param fileContainer The container associated with this Program.
     * @param file The file associated with the program.
     */
    public Program(FileContainer fileContainer, File file){
        super(EditorFileType.PROGRAM, fileContainer, file, Program.codeMirrorTemplate);
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
