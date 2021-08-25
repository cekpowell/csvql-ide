package View.Terminal;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import Model.Images;
import View.Tools.AppToolbar;

/**
 * Toolbar placed at the top of the Terminal when an output is 
 * displayed.
 */
public class TerminalToolbar extends AppToolbar{

    // constants 
    private static final int toolbarPadding = 10;
    private static final int toolbarSectionSpace = 10;
    private static final int toolbarControlSpace = 10;
    
    // member variables
    private Terminal terminal;
    private Button saveAsButton;
    private Button zoomInButton;
    private Button zoomOutButton;
    private Button copyButton;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     * 
     * @param outputPanel The output panel associated with this toolbar.
     */
    public TerminalToolbar(Terminal terminal){
        // initializing
        super(TerminalToolbar.toolbarPadding, TerminalToolbar.toolbarSectionSpace, TerminalToolbar.toolbarControlSpace);
        this.terminal = terminal;
        this.saveAsButton = new Button("", new ImageView(Images.SAVE_AS));
        this.zoomInButton = new Button("", new ImageView(Images.ZOOM_IN));
        this.zoomOutButton = new Button("", new ImageView(Images.ZOOM_OUT));
        this.copyButton = new Button("", new ImageView(Images.COPY));

        /////////////////
        // CONFIGURING //
        /////////////////

        // event handling
        this.configureEvents();
        
        // LHS controls
        this.addGroupsLeftContainerWithSepSplice(new Node[] {this.saveAsButton},                      // SAVING GROUP
                                                 new Node[] {this.zoomInButton, this.zoomOutButton}); // ZOOM GROUP
        // RHS controls
        this.addRightContainerWithSep(this.copyButton);
                                
    }

    /**
     * Defines the event handling for the events that can occur 
     * within the control.
     */
    private void configureEvents(){
        // save as
        this.saveAsButton.setOnAction((e) -> {
            // saving the program
            this.terminal.save();
        });

        // zoom in
        this.zoomInButton.setOnAction((e) -> {
            // zooming in on the terminal
            this.terminal.getCodeEditor().zoomIn();
        });

        // zoom out
        this.zoomOutButton.setOnAction((e) -> {
            // zooming out of the terminal
            this.terminal.getCodeEditor().zoomOut();
        });

        // copy
        this.copyButton.setOnAction((e) -> {
            // copying the output
            this.terminal.copy();
        });
    }
}