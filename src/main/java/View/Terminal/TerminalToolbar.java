package View.Terminal;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Toolbar placed at the top of each output view.
 */
public class TerminalToolbar extends HBox{

    // constants
    private static final Image saveAsImage = new Image("img/save-as.png");
    private static final Image zoomInImage = new Image("img/zoom-in.png");
    private static final Image zoomOutImage = new Image("img/zoom-out.png");
    private static final Image copyImage = new Image("img/copy.png");
    
    // member variables
    private Terminal terminal;
    private Button saveAsButton;
    private Button zoomInButton;
    private Button zoomOutButton;
    private Button copyButton;
    

    /**
     * Class constructor.
     * 
     * @param outputPanel The output panel associated with this toolbar.
     */
    public TerminalToolbar(Terminal terminal){
        // initializing
        this.terminal = terminal;
        this.saveAsButton = new Button("", new ImageView(saveAsImage));
        this.zoomInButton = new Button("", new ImageView(zoomInImage));
        this.zoomOutButton = new Button("", new ImageView(zoomOutImage));
        this.copyButton = new Button("", new ImageView(copyImage));

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // seperators for content
        Separator saveSep = new Separator(Orientation.VERTICAL);
        Separator undoRedoSep = new Separator(Orientation.VERTICAL);

        // container for left side
        HBox leftContainer = new HBox(this.saveAsButton, saveSep, this.zoomInButton, this.zoomOutButton);
        HBox.setHgrow(leftContainer, Priority.ALWAYS);
        leftContainer.setSpacing(10);

        // container for right side
        HBox rightContainer = new HBox(this.copyButton);
        HBox.setHgrow(rightContainer, Priority.ALWAYS);
        rightContainer.setAlignment(Pos.TOP_RIGHT);

        /////////////////
        // CONFIGURING //
        /////////////////
        
        // adding content
        this.getChildren().addAll(leftContainer, rightContainer);
        this.setPadding(new Insets(10));

        /////////////
        // ACTIONS //
        /////////////

        // save as
        this.saveAsButton.setOnAction((e) -> {
            // saving the program
            this.terminal.saveTerminalContent();
        });

        // zoom in
        this.zoomInButton.setOnAction((e) -> {
            // zooming in on the terminal
            this.terminal.getCodeEditor().zoomIn();
        });

        // zoom out
        this.zoomOutButton.setOnAction((e) -> {
            // zooming out of the terminal
            this.terminal.getCodeEditor().zoomOut();
        });

        // copy
        this.copyButton.setOnAction((e) -> {
            // copying the output
            this.terminal.copyTerminalContent();
        });
    }
}
