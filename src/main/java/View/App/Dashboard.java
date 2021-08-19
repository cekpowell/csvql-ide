package View.App;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

import View.Editor.*;
import View.FileStore.FileStore;
import View.Terminal.*;

/**
 * The main view for the application - contains an Editor, Terminal
 * and FileStore
 */
public class Dashboard extends BorderPane{
    // static variables
    private static final double editorDividerRatio = 0.67;
    private static final double filestoreDividerRatio = 0.88;
    
    // member variables
    private Editor editor;
    private Terminal terminal;
    private FileStore fileStore;

    /**
     * Class constructor.
     */
    public Dashboard(){
        // initialising sections
        this.editor = new Editor(this);
        this.terminal = new Terminal(this);
        this.fileStore = new FileStore(this);

        ////////////////
        // CONTAINERS //
        ////////////////

        // editor and outputter container
        SplitPane editAndOutput = new SplitPane(this.editor,this.terminal);
        editAndOutput.setDividerPositions(editorDividerRatio);

        // filestore container
        SplitPane editAndOutputAndFilestore = new SplitPane(editAndOutput, this.fileStore);
        editAndOutputAndFilestore.setDividerPositions(filestoreDividerRatio);
        editAndOutputAndFilestore.setOrientation(Orientation.VERTICAL);

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding sections to dashboard
        this.setCenter(editAndOutputAndFilestore);
    }


    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////


    public Editor getEditor(){
        return this.editor;
    }

    public Terminal getOutputter(){
        return this.terminal;
    }
}
