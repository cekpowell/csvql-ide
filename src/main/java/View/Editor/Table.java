package View.Editor;

import java.io.File;

/**
 * Represents an EditorFile that is displaying a table.
 */
public class Table extends EditorFile{

    // constants
    private static final String resourcesPath = Program.class.getClassLoader().getResource("codeMirror/").toString();
    private static final String codeMirrorTemplate = 
                                                        "<!doctype html>" +
                                                        "<html>" +
                                                        "<head>" +
                                                        "   <script src=\"" + resourcesPath + "/lib/codemirror.js\"></script>" + 
                                                        "   <link rel=\"stylesheet\" href=\"" + resourcesPath + "lib/codemirror.css\">" + 
                                                        "   <script src=\"" + resourcesPath + "/mode/mathematica/mathematica.js\"></script>" + 
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
                                                        "    theme: \"3024-day\"" + 
                                                        "  });" +
                                                        "</script>" +
                                                        "</body>" +
                                                        "</html>";
    
    /**
     * Class constructor - instantiated a new EditorFile.
     * 
     * @param fileContainer The container associated with this table.
     * @param name The name of the table.
     */
    public Table(FileContainer fileContainer, String name){
        super(EditorFileType.TABLE, fileContainer, name, Table.codeMirrorTemplate);
    }

    /**
     * Class constructor - instantiated a new EditorFile.
     * 
     * @param fileContainer The container associated with this table.
     * @param file The File associated with the table.
     */
    public Table(FileContainer fileContainer, File file){
        super(EditorFileType.TABLE, fileContainer, file, Table.codeMirrorTemplate);
    }

    /**
     * Empty method - table cannot be run so no implementation required.
     */
    public void run(){}
}
