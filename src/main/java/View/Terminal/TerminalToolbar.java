package View.Terminal;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;

/**
 * Toolbar for editing programs within the IDE.
 */
public class TerminalToolbar extends FlowPane{
    
    // member variables
    private Terminal terminal;
    private ToggleGroup outputFormat;
    private ToggleButton tableButton;
    private ToggleButton csvButton;
    private ToggleButton consoleButton;

    /**
     * Class constructor.
     * 
     * @param programEditor The editor associated with this toolbar.
     */
    public TerminalToolbar(Terminal terminal){
        // initializing
        this.terminal = terminal;
        this.outputFormat = new ToggleGroup();
        this.tableButton = new ToggleButton("Table");
        this.csvButton = new ToggleButton("CSV");
        this.consoleButton = new ToggleButton("Console");

        // Configuring Member Variables //

        // disabling table and console
        this.tableButton.setDisable(true);
        this.consoleButton.setDisable(true);

        // configuriing toggle group
        this.tableButton.setToggleGroup(this.outputFormat);
        this.csvButton.setToggleGroup(this.outputFormat);
        this.consoleButton.setToggleGroup(this.outputFormat);
        this.outputFormat.selectToggle(this.csvButton);

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding controls to toolbar
        this.getChildren().addAll(this.tableButton, this.csvButton, this.consoleButton);

        // configuring the toolbar
        this.setOrientation(Orientation.HORIZONTAL);
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);

        /////////////
        // ACTIONS //
        /////////////

        // table
        this.tableButton.setOnAction((e) -> {
            // displaying the TableOutput
            this.terminal.showTableOutput();
        });

        // CSV
        this.csvButton.setOnAction((e) -> {
            // displaying the TableOutput
            this.terminal.showCsvOutput();
        });

        // console
        this.consoleButton.setOnAction((e) -> {
            // displaying the TableOutput
            this.terminal.showConsoleOutput();
        });
    }
}
