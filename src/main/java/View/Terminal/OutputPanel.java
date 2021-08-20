package View.Terminal;

import javafx.scene.layout.BorderPane;

/**
 * Abstract class to represent the panels that program output is shown ini.
 */
public abstract class OutputPanel extends BorderPane{
    
    // member variables
    private Terminal terminal;
    private OutputToolbar toolbar;

    /**
     * Class constructor.
     * 
     * @param terminal The terminal associated with this output panel.
     */
    public OutputPanel(Terminal terminal){
        // initializing
        this.terminal = terminal;
        this.toolbar = new OutputToolbar(this);

        /////////////////
        // CONFIGURING //
        /////////////////

        this.setTop(this.toolbar);
    }

    /**
     * Displays the provided program output within the panel.
     * 
     * @param output The program output to be displayed within the panel.
     */
    public abstract void displayProgramOutput(String output);
}
