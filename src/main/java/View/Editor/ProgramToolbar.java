package View.Editor;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Toolbar for editing programs within the IDE.
 */
public class ProgramToolbar extends HBox{
    
    // member variables
    private Program program;
    private Button saveButton;
    private Button undoButton;
    private Button redoButton;
    private Button runButton;

    /**
     * Class constructor.
     * 
     * @param programEditor The editor associated with this toolbar.
     */
    public ProgramToolbar(Program program){
        // initializing
        this.program = program;
        this.saveButton = new Button("Save");
        this.undoButton = new Button("Undo");
        this.redoButton = new Button("Redo");
        this.runButton = new Button("Run");

        ////////////////
        // CONTAINERS //
        ////////////////

        // container for left side
        HBox leftContainer = new HBox(this.saveButton, this.undoButton, this.redoButton);
        HBox.setHgrow(leftContainer, Priority.ALWAYS);
        leftContainer.setSpacing(10);

        // container for right side
        HBox rightContainer = new HBox(this.runButton);
        rightContainer.setAlignment(Pos.TOP_RIGHT);

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding controls to toolbar
        this.getChildren().addAll(leftContainer, rightContainer);

        /////////////
        // ACTIONS //
        /////////////

        // save
        this.saveButton.setOnAction((e) -> {
            // TODO
        });

        // undo
        this.undoButton.setOnAction((e) -> {
            // undoing the last action in the text area
            this.program.getTextArea().undo();
        });

        // redo
        this.redoButton.setOnAction((e) -> {
            // redoing the last action in the text area
            this.program.getTextArea().redo();
        });

        // run
        this.runButton.setOnAction((e) -> {
            // TODO
        });
    }
}
