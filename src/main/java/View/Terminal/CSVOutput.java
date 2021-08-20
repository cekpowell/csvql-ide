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

        // configuring text area
        this.textArea.setEditable(false);

        // adding text area into panel
        this.setCenter(this.textArea);
    }

    /**
     * Displays the provided program output within the panel.
     * 
     * @param output The program output to be displayed within the panel.
     */
    public void displayProgramOutput(String output){
        // setting the text area text
        this.textArea.setText(output);
    }
}