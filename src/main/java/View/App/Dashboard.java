package View.App;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

import View.Editor.*;
import View.Terminal.*;
import View.TableStore.*;


/**
 * The main view for the application - contains an Editor, Terminal
 * and FileStore.
 */
public class Dashboard extends BorderPane{

    // constants
    private static final double editorDividerRatio = 0.67;
    private static final double filestoreDividerRatio = 0.88;
    
    // member variables
    private Editor editor;
    private Terminal terminal;
    private TableStore tableStore;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     */
    public Dashboard(){
        // initializing
        this.editor = new Editor(this);
        this.terminal = new Terminal(this);
        this.tableStore = new TableStore(this);

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // splitpane for editor and terminal
        SplitPane editAndTerminal = new SplitPane(this.editor,this.terminal);
        editAndTerminal.setDividerPositions(editorDividerRatio);

        // splitpane for edittor + terminal and filestore
        SplitPane editAndTerminalAndFileStore = new SplitPane(editAndTerminal, this.tableStore);
        editAndTerminalAndFileStore.setDividerPositions(filestoreDividerRatio);
        editAndTerminalAndFileStore.setOrientation(Orientation.VERTICAL);

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding sections to dashboard
        this.setCenter(editAndTerminalAndFileStore);
    }


    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////


    public Editor getEditor(){
        return this.editor;
    }

    public Terminal getTerminal(){
        return this.terminal;
    }

    public TableStore getTableStore(){
        return this.tableStore;
    }
}