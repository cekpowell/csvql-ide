package View.Terminal;

import javafx.scene.control.TextArea;

/**
 * View that displays program output through an interactive console.
 */
public class ConsoleOutput extends OutputPanel{

    // member variables
    private TextArea textArea;

    /**
     * Class constructor.
     * 
     * @param terminal The terminal associated with this ConsoleOutput.
     */
    public ConsoleOutput(Terminal terminal){
        // initializing
        super(terminal);
        this.textArea = new TextArea("Console Output");

        /////////////////
        // CONFIGURING //
        /////////////////

        this.setCenter(this.textArea);
    }
}
