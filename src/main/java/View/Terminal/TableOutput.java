package View.Terminal;

import javafx.scene.control.TextArea;

/**
 * View that displays program output as a table.
 */
public class TableOutput extends OutputPanel{

    // member variables
    private TextArea textArea;

    /**
     * Class constructor.
     * 
     * @param terminal The terminal associated with this TableOutput.
     */
    public TableOutput(Terminal terminal){
        // initializing
        super(terminal);
        this.textArea = new TextArea("Table Output");

        /////////////////
        // CONFIGURING //
        /////////////////

        this.setCenter(this.textArea);
    }
}
