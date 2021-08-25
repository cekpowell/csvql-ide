package View.Editor;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.io.File;

import Controller.FileManager;
import Controller.SystemController;
import Model.Images;
import View.Tools.PopUpWindow;
import View.Forms.RenameFileForm;
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
        this.addGroupsLeftContainerWithSepSplice(new Node[] {this.saveAsButton, this.saveButton},                  // FILE
                                                 new Node[] {this.undoButton, this.redoButton, this.renameButton}, // EDIT
                                                 new Node[] {this.zoomInButton, this.zoomOutButton});              // VIEW

    }

    /**
     * Defines the event handling for the events that can occur 
     * within the control.
     */
    private void configureEvents(){
        // save as
        this.saveAsButton.setOnAction((e) -> {
            try {
                // getting the file to save the program to
                File chosenFile = FileManager.getNewSaveFile(this.getScene().getWindow(), 
                                                             this.editorTab.getName(), 
                                                             this.editorTab.getFileType().getExtensionFilters());

                // making sure file was selected
                if(chosenFile != null){
                    // saving file through system controller
                    SystemController.getInstance().saveEditorTabAs(this.editorTab, chosenFile); 
                }
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
            // displaying renaming file form
            RenameFileForm.showForm(this.getScene().getWindow(), this.editorTab);
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