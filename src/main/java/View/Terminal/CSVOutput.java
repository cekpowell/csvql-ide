package View.Terminal;

import javafx.scene.control.TextArea;

/**
 * View that displays program output in CSV format.
 */
public class CSVOutput extends OutputPanel{

    // member variables
    private TextArea textArea;

    /**
     * Class constructor.
     * 
     * @param terminal The terminal associated with this CSVOutput panel.
     */
    public CSVOutput(Terminal terminal){
        // initializing
        super(terminal);
        this.textArea = new TextArea("CSV Output");

        /////////////////
        // CONFIGURING //
        /////////////////

        this.setCenter(this.textArea);
    }
}