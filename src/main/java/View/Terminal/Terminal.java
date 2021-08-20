package View.Terminal;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import View.App.Dashboard;
import View.Tools.SectionTitle;

/**
 * View for viewing the result of running programs.
 */
public class Terminal extends BorderPane{

    // member variables
    private Dashboard dashboard;
    private TerminalToolbar terminalToolbar;
    private TableOutput tableOutput;
    private CSVOutput csvOutput;
    private ConsoleOutput consoleOutput;

    /**
     * Class constructor.
     * 
     * @param dashboard The dashboard associated with this terminal.
     */
    public Terminal(Dashboard dashboard){
        // initializing
        this.dashboard = dashboard;
        this.terminalToolbar = new TerminalToolbar(this);
        this.tableOutput = new TableOutput(this);
        this.csvOutput = new CSVOutput(this);
        this.consoleOutput = new ConsoleOutput(this);

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // configuring title label
        SectionTitle titleLabel = new SectionTitle("Terminal");

        // container for title and toolbar
        VBox container = new VBox(titleLabel, this.terminalToolbar);
        container.setPadding(new Insets(10));

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding controls to the editor
        this.setTop(container);
        this.setCenter(this.csvOutput);
    }

    //////////////////////////
    // CHANGING OUTPUT VIEW //
    //////////////////////////

    /**
     * Displays the TableOutput within the terminal.
     */
    public void showTableOutput(){
        this.setCenter(this.tableOutput);
    }

    /**
     * Displays the CSVOutput within the terminal.
     */
    public void showCsvOutput(){
        this.setCenter(this.csvOutput);
    }

    /**
     * Displays the ConsoleOutput within the terminal.
     */
    public void showConsoleOutput(){
        this.setCenter(this.consoleOutput);
    }

    ////////////////////////////
    // SHOWING PROGRAM OUTPUT //
    ////////////////////////////

    /**
     * displays the provided program output within the terminal views.
     * 
     * @param output The program output to be displayed.
     */
    public void displayProgramOutput(String output){
        // Tabular View //
        // TODO

        // CSV View //
        this.csvOutput.displayProgramOutput(output);

        // Console View //
        // TODO
    }
}
