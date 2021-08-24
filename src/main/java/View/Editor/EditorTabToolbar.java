package View.Editor;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import Controller.SystemController;
import Model.Images;
import View.Tools.PopUpWindow;
import View.Tools.AppToolbar;

/**
 * Toolbar for editing programs within the IDE.
 */
public class EditorTabToolbar extends AppToolbar{

    // constants 
    private static final int toolbarPadding = 10;
    private static final int toolbarSectionSpace = 10;
    private static final int toolbarControlSpace = 10;
    
    // member variables
    private EditorTab editorTab;
    private Button saveAsButton;
    private Button saveButton;
    private Button renameButton;
    private Button undoButton;
    private Button redoButton;
    private Button zoomInButton;
    private Button zoomOutButton;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     * 
     * @param programEditor The editor associated with this toolbar.
     */
    public EditorTabToolbar(EditorTab editorTab){
        super(EditorTabToolbar.toolbarPadding, EditorTabToolbar.toolbarSectionSpace, EditorTabToolbar.toolbarControlSpace);
        // initializing
        this.editorTab = editorTab;
        this.saveAsButton = new Button("", new ImageView(Images.SAVE_AS));
        this.saveButton = new Button("", new ImageView(Images.SAVE));
        this.renameButton = new Button("", new ImageView(Images.RENAME));
        this.undoButton = new Button("", new ImageView(Images.UNDO));
        this.redoButton = new Button("", new ImageView(Images.REDO));
        this.zoomInButton = new Button("", new ImageView(Images.ZOOM_IN));
        this.zoomOutButton = new Button("", new ImageView(Images.ZOOM_OUT));

        /////////////////
        // CONFIGURING //
        /////////////////

        // event handling
        this.configureEvents();

        // adding controls to toolbar in three groups
        this.addGroupsLeftContainerWithSepSplice(new Node[] {this.saveAsButton, this.saveButton, this.renameButton}, // SAVING GROUP
                                                 new Node[] {this.undoButton, this.redoButton},                      // UNDO/REDO GROUP
                                                 new Node[] {this.zoomInButton, this.zoomOutButton});                // ZOOM GROUP

    }

    /**
     * Defines the event handling for the events that can occur 
     * within the control.
     */
    private void configureEvents(){
        // save as
        this.saveAsButton.setOnAction((e) -> {
            try {
                // saving the editor tab
                SystemController.getInstance().saveEditorTabAs(this.editorTab);
            } 
            catch (Exception ex) {
                // showing error alert
                PopUpWindow.showErrorWindow(this.getScene().getWindow(), ex);
            }
        });

        // save
        this.saveButton.setOnAction((e) -> {
            try {
                // saving the editor tab
                SystemController.getInstance().saveEditorTab(this.editorTab);
            } 
            catch (Exception ex) {
                // showing error alert
                PopUpWindow.showErrorWindow(this.getScene().getWindow(), ex);
            }
        });

        // rename
        this.renameButton.setOnAction((e) -> {
            // renaming the file through the system controller
            SystemController.getInstance().renameEditorTab(this.editorTab);
        });

        // undo
        this.undoButton.setOnAction((e) -> {
            // undoing the last action in the text area
            this.editorTab.getCodeArea().undo();

            // check for changes
            this.editorTab.checkForCodeChanges();
        });

        // redo
        this.redoButton.setOnAction((e) -> {
            // redoing the last action in the text area
            this.editorTab.getCodeArea().redo();

            // checking for changes
            this.editorTab.checkForCodeChanges();
        });

        // zoom in
        this.zoomInButton.setOnAction((e) -> {
            // performing zoom in
            this.editorTab.getCodeArea().zoomIn();
        });

        // zoom out
        this.zoomOutButton.setOnAction((e) -> {
            // performing zoom-out
            this.editorTab.getCodeArea().zoomOut();
        });
    }
}