package View.Terminal;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

/**
 * Toolbar placed at the top of each output view.
 */
public class OutputToolbar extends FlowPane{
    
    // member variables
    private OutputPanel outputPanel;
    private Button saveButton;
    

    /**
     * Class constructor.
     * 
     * @param outputPanel The output panel associated with this toolbar.
     */
    public OutputToolbar(OutputPanel outputPanel){
        // initializing
        this.outputPanel = outputPanel;
        this.saveButton = new Button("Save");

        /////////////////
        // CONFIGURING //
        /////////////////
        
        this.getChildren().addAll(this.saveButton);
        this.setAlignment(Pos.TOP_RIGHT);
        this.setPadding(new Insets(10));
    }
}
