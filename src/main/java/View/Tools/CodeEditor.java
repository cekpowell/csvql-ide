package View.Tools;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;


/**
 * A syntax editor created by wrapping a CodeMirrow application in a JavaFX
 * WebView.
 */
public class CodeEditor extends StackPane {

    // constants
    private static final double startZoom = 1.4;
    private static final double maxZoom = 2.0;
    private static final double minZoom = 0.8;
    private static final double zoomIncrement = 0.2;

    // member variables
    private WebView webview;
    private final String codeMirrorTemplate;

    /////////////////
    // CONSTRUCTOR //
    /////////////////

    /**
     * Create a new code editor.
     * @param editingCode the initial code to be edited in the code editor.
     */
    public CodeEditor(String codeMirrorTemplate, String initialCode) {
        // initializing
        this.codeMirrorTemplate = codeMirrorTemplate;
        this.webview = new WebView();

        // Configuring Member Variables //

        //configuring the zoom of the webview
        this.webview.setZoom(startZoom);

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // seperator
        Separator sep = new Separator(Orientation.HORIZONTAL);

        // container for webview and sep
        VBox container = new VBox(sep, this.webview);

        /////////////////
        // CONFIGURING //
        /////////////////

        // setting the code into the webview (loading the code mirror)
        this.setCode(initialCode);

        // dispaying the container in the pane
        this.getChildren().add(container);
    }

    ////////////////////////
    // MANAGEMENT METHODS //
    ////////////////////////

    /**
     * Updates the CodeMirror template to include the provided code.
     * 
     * @param code The code being placed in the code mirror templatee.
     * @return The updated CodeMirror template that includes the provided code.
     */
    private String updateCodeMirrorTemplate(String code) {
        return codeMirrorTemplate.replace("${code}", code);
    }

    /**
     * Performs an undo action on the code editor.
     */
    public void undo(){
        // running the undo method
        this.webview.getEngine().executeScript("editor.undo()");
    }

    /**
     * Performs a redo action on the code editor.
     */
    public void redo(){
        // running the undo method
        webview.getEngine().executeScript("editor.redo()");
    }

    /**
     * Increases the size of the font within the editor.
     */
    public void zoomIn(){
        // zooming the webview
        if(this.webview.getZoom() <= maxZoom){
            this.webview.setZoom(this.webview.getZoom() + zoomIncrement);
        }
    }

    /**
     * Decreases the size of the font within the editor.
     */
    public void zoomOut(){
        // zooming the webview
        if(this.webview.getZoom() >= minZoom){
            this.webview.setZoom(this.webview.getZoom() - zoomIncrement);
        }
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public String getCode() {
        return (String ) this.webview.getEngine().executeScript("editor.getValue();");
    }

    public void setCode(String code) {
        // updating the code-editor
        this.webview.getEngine().loadContent(this.updateCodeMirrorTemplate(code ));
    }
}