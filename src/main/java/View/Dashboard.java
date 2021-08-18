package View;

import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * The main dashboard of the application
 */
public class Dashboard extends HBox{
    
    // member variables
    private TextArea programInput;
    private TextArea programOutput;

    /**
     * Class constructor.
     */
    public Dashboard(){
        // initialising sections
        this.programInput = new TextArea();
        this.programOutput = new TextArea();
        
        // adding sections to dashboard
        this.getChildren().addAll(this.programInput, this.programOutput);
    }
}
