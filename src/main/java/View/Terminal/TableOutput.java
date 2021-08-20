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

    /**
     * Displays the provided program output within the panel.
     * 
     * @param output The program output to be displayed within the panel.
     */
    public void displayProgramOutput(String output){
        // TODO
    }
}
