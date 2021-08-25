package View.Editor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import Controller.SystemController;
import Model.FileType;
import Model.Images;
import View.App.Dashboard;
import View.Forms.NewFileForm;
import View.Tools.PopUpWindow;
import View.Tools.SectionTitle;

/**
 * View for editing programs within the IDE.
 */
public class Editor extends BorderPane{

    // member variables
    private Dashboard dashboard; 
    private EditorTabContainer editorTabContainer;
    private VBox noEditorTabScreen;
    private Button newFileButton;
    private Button openFileButton;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     */
    public Editor(Dashboard dashboard){
        // initializing
        this.dashboard = dashboard;
        this.editorTabContainer = new EditorTabContainer(this);
        this.noEditorTabScreen = new VBox();
        this.newFileButton = new Button("New", new ImageView(Images.NEW_FILE));
        this.openFileButton = new Button("Open", new ImageView(Images.OPEN));

        // Configuring Member Variables //

        // no EditorTab screen
        this.noEditorTabScreen.setAlignment(Pos.CENTER);
        this.noEditorTabScreen.setSpacing(10);

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // no editor tab label
        Label noFileLabel = new Label("No Open Files", new ImageView(Images.MESSAGE));

        // creating error message label
        Label messageLabel = new Label("Create a new file or load an existing one!");

        // adding controls to no EditorTab screen
        this.noEditorTabScreen.getChildren().addAll(noFileLabel, messageLabel, this.newFileButton, this.openFileButton);

        // title label
        SectionTitle titleLabel = new SectionTitle("Editor", new ImageView(Images.EDITOR));

        // container for title and toolbar
        VBox container = new VBox(titleLabel);
        container.setPadding(new Insets(10));

        /////////////////
        // CONFIGURING //
        /////////////////

        // event handling
        this.configureEvents();

        // adding controls to the editor
        this.setTop(container);

        // updating the contents of the editor
        this.updateContents();
    }

    /**
     * Defines the event handling for the events that can occur 
     * within the control.
     */
    private void configureEvents(){
        // New File
        this.newFileButton.setOnAction((e) -> {
            // displaying new file form
            NewFileForm.showForm(this.getScene().getWindow());
        });

        // Open File
        this.openFileButton.setOnAction((e) -> {
            // configuring the file chooser to load a new file into the system
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");
            fileChooser.getExtensionFilters().addAll(FileType.PROGRAM.getExtensionFilters());
            fileChooser.getExtensionFilters().addAll(FileType.TABLE.getExtensionFilters());

            // showing the open dialog
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(this.getScene().getWindow());

            // checking if files were opened
            if (selectedFiles != null) {
                // iterating through all selected files
                for(File selectedFile : selectedFiles){
                    try{
                        // loading file through system controller
                        SystemController.getInstance().loadFile(selectedFile, FileType.PROGRAM, FileType.TABLE);
                    }
                    catch(Exception ex){
                        // handling errors
                        PopUpWindow.showErrorWindow(this.getScene().getWindow(), ex);
                    }
                }
            }
        });

        // Files Dragged into Editor
        this.setOnDragOver((e) -> {
            // checking drag did not originate from this and that drag has files
            if (e.getGestureSource() != this && e.getDragboard().hasFiles()) {
                // allow for file to be copied into the table store
                e.acceptTransferModes(TransferMode.COPY);
            }
            // event no longer needed
            e.consume();
        });

        // Files Dropped in Editor
        this.setOnDragDropped((e) -> {
            Dragboard db = e.getDragboard();
            boolean success = false;

            // checking if file(s) were dropped
            if (db.hasFiles()) {
                // iterating through all selected files
                for(File selectedFile : db.getFiles()){
                    try{
                        // loading file through system controller
                        SystemController.getInstance().loadFile(selectedFile, FileType.PROGRAM, FileType.TABLE);

                        // updating success status
                        success = true;
                    }
                    catch(Exception ex){
                        // handling errors
                        PopUpWindow.showErrorWindow(this.getScene().getWindow(), ex);
                    }
                }
            }
            /* let the source know whether the file was successfully 
            * transferred and used */
            e.setDropCompleted(success);

            // event no longer needed
            e.consume();
        });
    }

    /////////////////////////////////
    // CONFIGURING EDITOR CONTENTS //
    /////////////////////////////////

    /**
     * Updates the editor to display the editor tab container if it has tabs
     * or the no editor tab screen if it has none.
     */
    public void updateContents(){
        // editor tab container has no tabs
        if(this.editorTabContainer.getEditorTabs().size() == 0){
            // showing no editor tab screen
            this.showNoEditorTabScreen();
        }
        // editor tab container has tabs
        else{
            // setting file container into the control
            this.setCenter(this.editorTabContainer);
        }
    }

    /**
     * Shows a view for when their are no programs currently open.
     */
    private void showNoEditorTabScreen(){
        // adding buttons to editor
        this.setCenter(this.noEditorTabScreen);
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public EditorTabContainer getEditorTabContainer(){
        return this.editorTabContainer;
    }
}
