package Model;

import javafx.scene.image.Image;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Enumeration class for the type of Client object that can exist within the 
 * system. 
 * 
 * These Client types are specific to the Distributed Data Store system.
 */
public enum FileType { 
    // types
    PROGRAM(
            // DEFAULT EXTENSION
            ".cql",
            // EXTENSION FILTERS
            new ExtensionFilter[] {new ExtensionFilter("CSVQL Program (*.cql)", "*.cql")},
            // CODE MIRROR TEMPLATE
            "<!doctype html>" +                                                      
            "<html>" +
            "<head>" +
            "   <script src=\"" + Resources.CODE_MIRROR_PATH + "/lib/codemirror.js\"></script>" + 
            "   <link rel=\"stylesheet\" href=\"" + Resources.CODE_MIRROR_PATH + "lib/codemirror.css\">" + 
            "   <link rel=\"stylesheet\" href=\"" + Resources.CODE_MIRROR_PATH + "theme/3024-day.css\">" + 
            "   <link rel=\"stylesheet\" href=\"" + Resources.CODE_MIRROR_PATH + "hint/show-hint.css\">" + 
            "   <script src=\"" + Resources.CODE_MIRROR_PATH + "/hint/show-hint.js\"></script>" + 
            "   <script src=\"" + Resources.CODE_MIRROR_PATH + "/hint/sql-hint.js\"></script>" + 
            "   <script src=\"" + Resources.CODE_MIRROR_PATH + "/mode/sql/sql.js\"></script>" + 
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
            "</html>",
            // GRAPHIC
            new Image("img/program.png"),
            // GRAPHIC UNSAVED
            new Image("img/program-unsaved.png")), 
    TABLE(
            // DEFAULT EXTENSION
            ".csv",
            // EXTENSION FILTERS
            new ExtensionFilter[] {new ExtensionFilter("CSV Table (*.csv)", "*.csv"),
                                   new ExtensionFilter("TSV Table (*.tsv)", "*.tsv"),
                                   new ExtensionFilter("Text Table (*.txt)", "*.txt")}
            ,
            // CODE MIRROR TEMPLATE
            "<!doctype html>" +                                          
            "<html>" +
            "<head>" +
            "   <script src=\"" + Resources.CODE_MIRROR_PATH + "/lib/codemirror.js\"></script>" + 
            "   <link rel=\"stylesheet\" href=\"" + Resources.CODE_MIRROR_PATH + "lib/codemirror.css\">" + 
            "   <script src=\"" + Resources.CODE_MIRROR_PATH + "/mode/mathematica/mathematica.js\"></script>" + 
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
            "</html>",
            // GRAPHIC
            new Image("img/table.png"),
            // GRAPHIC UNSAVED
            new Image("img/table-unsaved.png")),
    TERMINAL(
            // DEFAULT EXTENSION
            ".csv",
            // EXTENSION FILTERS
            new ExtensionFilter[] {new ExtensionFilter("CSV Table (*.csv)", "*.csv"),
                                   new ExtensionFilter("TSV Table (*.tsv)", "*.tsv"),
                                   new ExtensionFilter("Text Table (*.txt)", "*.txt")},
            // CODE MIRROR TEMPLATE
            "<!doctype html>" +                                          
            "<html>" +
            "<head>" +
            "   <script src=\"" + Resources.CODE_MIRROR_PATH + "/lib/codemirror.js\"></script>" + 
            "   <link rel=\"stylesheet\" href=\"" + Resources.CODE_MIRROR_PATH + "lib/codemirror.css\">" + 
            "   <script src=\"" + Resources.CODE_MIRROR_PATH + "/mode/mathematica/mathematica.js\"></script>" + 
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
            "</html>",
            // GRAPHIC (null as not required for terminal)
            null,  
            // GRAPHIC UNSAVED (null as not required for terminal)
            null);  

    // member variables
    private String defaultExtension;
    private ExtensionFilter[] extensionFilters;
    private String codeMirrorTemplate;
    private Image graphic;
    private Image graphicUnsaved;

    /**
     * Constructor.
     * 
     * @param extensionFilter The extension filter associated with the type.
     * @param codeMirrorTemplate The CodeMirror template associated with the type.
     */
    private FileType(String defaultExtension, ExtensionFilter[] extensionFilters, String codeMirrorTemplate, Image graphic, Image graphicUnsaved){
        this.defaultExtension = defaultExtension;
        this.extensionFilters = extensionFilters;
        this.codeMirrorTemplate = codeMirrorTemplate;
        this.graphic = graphic;
        this.graphicUnsaved = graphicUnsaved;
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public String getDefaultExtension(){
        return this.defaultExtension;
    }

    public ExtensionFilter[] getExtensionFilters(){
        return this.extensionFilters;
    }

    public String getCodeMirrorTemplate(){
        return this.codeMirrorTemplate;
    }

    public Image getGraphic(){
        return this.graphic;
    }

    public Image getGraphicUnsaved(){
        return this.graphicUnsaved;
    }
}
