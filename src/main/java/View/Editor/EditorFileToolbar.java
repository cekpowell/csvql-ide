package View.Editor;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import View.Editor.EditorFile.EditorFileType;

/**
 * Toolbar for editing programs within the IDE.
 */
public class EditorFileToolbar extends HBox{

    // constants
    private static final Image saveAsImage = new Image("save-as.png");
    private static final Image saveImage = new Image("save.png");
    private static final Image undoImage = new Image("undo.png");
    private static final Image redoImage = new Image("redo.png");
    private static final Image zoomInImage = new Image("zoom-in.png");
    private static final Image zoomOutImage = new Image("zoom-out.png");
    private static final Image playImage = new Image("play.png");
    
    // member variables
    private EditorFile editorFile;
    private Button saveAsButton;
    private Button saveButton;
    private Button undoButton;
    private Button redoButton;
    private Button zoomInButton;
    private Button zoomOutButton;
    private Button runButton;

    /**
     * Class constructor.
     * 
     * @param programEditor The editor associated with this toolbar.
     */
    public EditorFileToolbar(EditorFile editorFile, EditorFileType type){
        // initializing
        this.editorFile = editorFile;
        this.saveAsButton = new Button("", new ImageView(saveAsImage));
        this.saveButton = new Button("", new ImageView(saveImage));
        this.undoButton = new Button("", new ImageView(undoImage));
        this.redoButton = new Button("", new ImageView(redoImage));
        this.zoomInButton = new Button("", new ImageView(zoomInImage));
        this.zoomOutButton = new Button("", new ImageView(zoomOutImage));
        this.runButton = new Button("Run", new ImageView(playImage));

        // Configuring Member Variables //

        // disabling 'run' button if type is table
        if(type == EditorFileType.TABLE){
            this.runButton.setDisable(true);
        }

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // seperators for content
        Separator saveSep = new Separator(Orientation.VERTICAL);
        Separator undoRedoSep = new Separator(Orientation.VERTICAL);

        // container for left side
        HBox leftContainer = new HBox(this.saveAsButton, this.saveButton, saveSep, this.undoButton, this.redoButton, undoRedoSep, this.zoomInButton, this.zoomOutButton);
        HBox.setHgrow(leftContainer, Priority.ALWAYS);
        leftContainer.setSpacing(10);

        // container for right side
        HBox rightContainer = new HBox(this.runButton);
        HBox.setHgrow(rightContainer, Priority.ALWAYS);
        rightContainer.setAlignment(Pos.TOP_RIGHT);

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding controls to toolbar
        this.getChildren().addAll(leftContainer, rightContainer);

        /////////////
        // ACTIONS //
        /////////////

        // save as
        this.saveAsButton.setOnAction((e) -> {
            // saving the program
            this.editorFile.saveAs();
        });

        // save
        this.saveButton.setOnAction((e) -> {
            // saving the program
            this.editorFile.save();
        });

        // undo
        this.undoButton.setOnAction((e) -> {
            // undoing the last action in the text area
            this.editorFile.getCodeEditor().undo();
        });

        // redo
        this.redoButton.setOnAction((e) -> {
            // redoing the last action in the text area
            this.editorFile.getCodeEditor().redo();
        });

        // zoom in
        this.zoomInButton.setOnAction((e) -> {
            // performing zoom in
            this.editorFile.getCodeEditor().zoomIn();
        });

        // zoom out
        this.zoomOutButton.setOnAction((e) -> {
            // performing zoom-out
            this.editorFile.getCodeEditor().zoomOut();
        });

        // run
        this.runButton.setOnAction((e) -> {
            // running the file
            this.editorFile.run();
        });
    }
}