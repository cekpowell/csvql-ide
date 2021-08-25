package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.image.Image;
import javafx.stage.FileChooser.ExtensionFilter;

import Controller.FileManager;

/**
 * Enumeration class for the type of Client object that can exist within the 
 * system. 
 * 
 * These Client types are specific to the Distributed Data Store system.
 */
public enum FileType { 
    // types
    PROGRAM_CSVQL(
            // DEFAULT EXTENSION
            ".cql",
            // ALL EXTENSIONS
            new ArrayList<String>(Arrays.asList(".cql")),
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
            new Image("img/csvql.png"),
            // GRAPHIC UNSAVED
            new Image("img/csvql-unsaved.png")), 
    PROGRAM_PYTHON(
            // DEFAULT EXTENSION
            ".py",
            // ALL EXTENSIONS
            new ArrayList<String>(Arrays.asList(".py")),
            // EXTENSION FILTERS
            new ExtensionFilter[] {new ExtensionFilter("Python Program (*.py)", "*.py")},
            // CODE MIRROR TEMPLATE
            "<!doctype html>" +                                                      
            "<html>" +
            "<head>" +
            "   <script src=\"" + Resources.CODE_MIRROR_PATH + "/lib/codemirror.js\"></script>" + 
            "   <link rel=\"stylesheet\" href=\"" + Resources.CODE_MIRROR_PATH + "lib/codemirror.css\">" + 
            "   <link rel=\"stylesheet\" href=\"" + Resources.CODE_MIRROR_PATH + "theme/3024-day.css\">" + 
            "   <link rel=\"stylesheet\" href=\"" + Resources.CODE_MIRROR_PATH + "hint/show-hint.css\">" + 
            "   <script src=\"" + Resources.CODE_MIRROR_PATH + "/hint/show-hint.js\"></script>" + 
            "   <script src=\"" + Resources.CODE_MIRROR_PATH + "/mode/python/python.js\"></script>" + 
            "</head>" +
            "<body style='margin: 0', bgcolor=#ffffff>" +
            "<form><textarea id=\"code\" name=\"code\">\n" +
            "${code}" +
            "</textarea></form>" +
            "<script>" +
            "  var editor = CodeMirror.fromTextArea(document.getElementById(\"code\"), {" +
            "    mode: \"python\"," + 
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
            new Image("img/python.png"),
            // GRAPHIC UNSAVED
            new Image("img/python-unsaved.png")), 
    PROGRAM_JAVA(
            // DEFAULT EXTENSION
            ".java",
            // ALL EXTENSIONS
            new ArrayList<String>(Arrays.asList(".java")),
            // EXTENSION FILTERS
            new ExtensionFilter[] {new ExtensionFilter("Java Program (*.java", "*.java")},
            // CODE MIRROR TEMPLATE
            "<!doctype html>" +                                                      
            "<html>" +
            "<head>" +
            "   <script src=\"" + Resources.CODE_MIRROR_PATH + "/lib/codemirror.js\"></script>" + 
            "   <link rel=\"stylesheet\" href=\"" + Resources.CODE_MIRROR_PATH + "lib/codemirror.css\">" + 
            "   <link rel=\"stylesheet\" href=\"" + Resources.CODE_MIRROR_PATH + "theme/3024-day.css\">" + 
            "   <link rel=\"stylesheet\" href=\"" + Resources.CODE_MIRROR_PATH + "hint/show-hint.css\">" + 
            "   <script src=\"" + Resources.CODE_MIRROR_PATH + "/hint/show-hint.js\"></script>" + 
            "   <script src=\"" + Resources.CODE_MIRROR_PATH + "/mode/c-like/clike.js\"></script>" + 
            "</head>" +
            "<body style='margin: 0', bgcolor=#ffffff>" +
            "<form><textarea id=\"code\" name=\"code\">\n" +
            "${code}" +
            "</textarea></form>" +
            "<script>" +
            "  var editor = CodeMirror.fromTextArea(document.getElementById(\"code\"), {" +
            "    mode: \"text/x-java\"," + 
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
            new Image("img/java.png"),
            // GRAPHIC UNSAVED
            new Image("img/java-unsaved.png")), 
    TABLE(
            // DEFAULT EXTENSION
            ".csv",
            // ALL EXTENSIONS
            new ArrayList<String>(Arrays.asList(".csv", ".txt")),
            // EXTENSION FILTERS
            new ExtensionFilter[] {new ExtensionFilter("CSV Table (*.csv)", "*.csv"),
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
            "    mode: \"\"," + // no language for tables - just plaintext
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
            new Image("img/table-unsaved.png"));

    // member variables
    private String defaultExtension;
    private ArrayList<String> allExtensions;
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
    
    /**
     * Constructor.
     * 
     * @param defaultExtension The default/initial extension associated with this filetype.
     * @param allExtensions All extensions associated with this file type.
     * @param extensionFilter The extension filter associated with the type.
     * @param codeMirrorTemplate The CodeMirror template associated with the type.
     * @param graphic The Image assocciated with this filetype.
     * @param graphicUnsaved The image associated with this filetype being in the unsaved status.
     */
    private FileType(String defaultExtension, ArrayList<String> allExtensions, ExtensionFilter[] extensionFilters, String codeMirrorTemplate, Image graphic, Image graphicUnsaved){
        // initializing
        this.defaultExtension = defaultExtension;
        this.allExtensions = allExtensions;
        this.extensionFilters = extensionFilters;
        this.codeMirrorTemplate = codeMirrorTemplate;
        this.graphic = graphic;
        this.graphicUnsaved = graphicUnsaved;
    }

    //////////////////////////
    // GETTING TYPE OF FILE //
    //////////////////////////

    /**
     * Attempts to gather the FileType of a given file from it's name. Works by
     * comparing the file extension of the filename to the extensions documented
     * in the enum class.
     * 
     * Returns null if no matching filetype is found.
     * 
     * @param filename The name of the file who's type is being checked.
     * @return The FileType associated with the given filename.
     */
    public static FileType getFileType(String filename){
        // CSVQL PROGRAM
        if(FileType.PROGRAM_CSVQL.fileHasSameFileType(filename)){
            return FileType.PROGRAM_CSVQL;
        }
        // PYTHON PROGRAM
        else if(FileType.PROGRAM_PYTHON.fileHasSameFileType(filename)){
            return FileType.PROGRAM_PYTHON;
        }
        // JAVA PROGRAM
        else if(FileType.PROGRAM_JAVA.fileHasSameFileType(filename)){
            return FileType.PROGRAM_JAVA;
        }
        // TABLE
        else if(FileType.TABLE.fileHasSameFileType(filename)){
            return FileType.TABLE;
        }
        // NO MATCHING FILETYPE
        else{
            return null;
        }
    }

    /**
     * Attempts to gather the FileType of a given file from it's name. Works by
     * comparing the file extension of the filename to the extensions documented
     * in the enum class.
     * 
     * Returns null if no matching filetype is found.
     * 
     * @param filename The name of the file who's type is being checked.
     * @return The FileType associated with the given filename.
     */
    public static FileType getFileType(File file){
        // running the method on the filename
        return FileType.getFileType(file.getName());
    }

    /////////////////////////////////////
    // CHECKING FILE HAS SAME FILETYPE //
    /////////////////////////////////////

    /**
     * Determines if the given filename has the same type as the enumerator
     * based on the file's extension.
     * 
     * @param file The file who's type is being checked against the enumerator.
     * @return True if the file and enumerator have the same type, false otherwise.
     */
    public boolean fileHasSameFileType(String filename){
        // getting file extension
        try{
            String fileExtension = FileManager.getFileExtensionWith(filename);

            // determining if the file types are  match
            if(this.allExtensions.contains("." + fileExtension)){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            // exception thrown when file does not have an extension - so no match possible
            return false;
        }
    }

    /**
     * Determines if the given file has the same type as the enumerator file
     * based on the file's extension.
     * 
     * @param file The file who's type is being checked against the enumerator.
     * @return True if the file and enumerator have the same type, false otherwise.
     */
    public boolean fileHasSameFileType(File file){
        // running the method on the filename
        return this.fileHasSameFileType(file.getName());
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public String getDefaultExtension(){
        return this.defaultExtension;
    }

    public ArrayList<String> getAllExtensions(){
        return this.allExtensions;
    }

    public ExtensionFilter[] getExtensionFilters(){
        return this.extensionFilters;
    }

    public static ArrayList<ExtensionFilter> getAllExtensionFilters(){
        ArrayList<ExtensionFilter> extensionFilters = new ArrayList<ExtensionFilter>();

        for(FileType fileType : FileType.values()){
            for(ExtensionFilter extensionFilter : fileType.getExtensionFilters()){
                extensionFilters.add(extensionFilter);
            }
        }

        return extensionFilters;
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