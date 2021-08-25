package View.TableStore;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import Controller.SystemController;
import Model.FileType;
import Model.Images;
import View.App.Dashboard;
import View.Tools.ConfirmationButton;
import View.Tools.PopUpWindow;
import View.Tools.SectionTitle;

/**
 * Represents the panel within the application where the user loads files.
 */
public class TableStore extends VBox{
    
    // member variables
    private Dashboard dashboard;
    private FlowPane storedTablesContainer;
    private ArrayList<StoredTable> storedTables;
    private Button loadTableButton;
    private ConfirmationButton clearStoreButton;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Constructor for the class.
     * 
     * @param dashboard The dashboard associated with the panel
     */
    public TableStore(Dashboard dashboard){
        // Initialising //
        this.dashboard = dashboard;
        this.storedTablesContainer = new FlowPane();
        this.storedTables = new ArrayList<StoredTable>();
        this.loadTableButton = new Button("Load Table", new ImageView(Images.OPEN));
        this.clearStoreButton = new ConfirmationButton("Clear Store", new ImageView(Images.CLOSE),
                                                       "Clear Table Store", 
                                                       "Are you sure you want to remove all loaded tables from the system? " + "\n" +
                                                       "Any unsaved progress in the Editor will be lost.");

        // Configuring Member Variables //

        // file container
        this.storedTablesContainer.setHgap(10);
        this.storedTablesContainer.setPadding(new Insets(10));
        this.storedTablesContainer.setOrientation(Orientation.HORIZONTAL);

        // disabling clear store button (as no tables yet loaded)
        this.clearStoreButton.setDisable(true);

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////        

        // configuring title label
        SectionTitle titleLabel = new SectionTitle("Table Store", new ImageView(Images.TABLE_STORE));

        // container for title
        HBox titleContainer = new HBox(titleLabel);
        HBox.setHgrow(titleContainer, Priority.ALWAYS);

        // container for load file button
        HBox buttonContainer = new HBox(this.loadTableButton, this.clearStoreButton);
        buttonContainer.setSpacing(10);
        buttonContainer.setAlignment(Pos.TOP_RIGHT);

        // title for container and header
        HBox headerContainer = new HBox();
        headerContainer.getChildren().addAll(titleContainer, buttonContainer);

        /////////////////
        // CONFIGURING //
        /////////////////

        // event handling
        this.configureEvents();

        // adding main container to view
        this.getChildren().addAll(headerContainer, this.storedTablesContainer);

        // formatting
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(10,10,10,10));
    }

    /**
     * Defines the event handling for the events that can occur 
     * within the control.
     */
    private void configureEvents(){
        // Load Table
        this.loadTableButton.setOnAction((e) -> {
            // configuring the file chooser to load a new file into the system
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File");
            fileChooser.getExtensionFilters().addAll(FileType.TABLE.getExtensionFilters());

            // showing the open dialog
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(this.getScene().getWindow());

            // checking if files were opened
            if (selectedFiles != null) {
                // iterating through all selected files
                for(File selectedFile : selectedFiles){
                    try{
                        // loading file through system controller
                        SystemController.getInstance().loadFileIntoTableStore(selectedFile);
                    }
                    catch(Exception ex){
                        // handling errors
                        PopUpWindow.showErrorWindow(this.getScene().getWindow(), ex);
                    }
                }
            }
        });

        // Clear Store
        this.clearStoreButton.setOnAction((e) -> {
            // showing the confirmation button pop-up window
            boolean clearConfirmed = this.clearStoreButton.showConfirmationWindow(this.getScene().getWindow());

            // clearing if the use confirmed the clear
            if(clearConfirmed){
                // clearing
                this.clearStoredTables();

                // disabling clear store button (no tables in system)
                this.clearStoreButton.setDisable(true);
            }
        });

        // Files Dragged into TableStore
        this.setOnDragOver((e) -> {
            // checking drag did not originate from this and that drag has files
            if (e.getGestureSource() != this && e.getDragboard().hasFiles()) {
                // allow for file to be copied into the table store
                e.acceptTransferModes(TransferMode.COPY);
            }
            // event no longer needed
            e.consume();
        });

        // Files Dropped in TableStore
        this.setOnDragDropped((e) -> {
            Dragboard db = e.getDragboard();
            boolean success = false;

            // checking if file(s) were dropped
            if (db.hasFiles()) {
                // iterating through all selected files
                for(File selectedFile : db.getFiles()){
                    try{
                        // loading file through system controller
                        SystemController.getInstance().loadFileIntoTableStore(selectedFile);

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

    ////////////////////////////////
    // CONFIIGURING STORED TABLES //
    ////////////////////////////////

    /**
     * Adds the new stored table into the TableStore.
     * 
     * @param storedTable The TableStore the new stored table is contained within.
     */
    public void addStoredTable(StoredTable storedTable){
        // adding into list
        this.storedTables.add(storedTable);

        // adding into container
        this.storedTablesContainer.getChildren().add(storedTable);
    }

    /**
     * Removes a StoredTable from the TableStore.
     * 
     * @param storedTable The StoredTable being removed from the system.
     */
    public void removeStoredTable(StoredTable storedTable){
        // removing from list
        this.storedTables.remove(storedTable);

        // removing from editor
        SystemController.getInstance().removeStoredTableFromEditor(storedTable);

        // removing from container
        this.storedTablesContainer.getChildren().remove(storedTable);
    }

    /**
     * Removes all StoredTables from the TableStore.
     */
    private void clearStoredTables(){
        // removing stored tables from editor
        for(StoredTable storedTable : this.storedTables){
            SystemController.getInstance().removeStoredTableFromEditor(storedTable);
        }
        
        // clearing table container
        this.storedTablesContainer.getChildren().clear();
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public ArrayList<StoredTable> getStoredTables(){
        return this.storedTables;
    }

    public ArrayList<File> getFiles(){

        // list to store files
        ArrayList<File> files = new ArrayList<File>();

        // gathering all loaded files
        for(StoredTable loadedFile : this.storedTables){
            files.add(loadedFile.getFile());
        }

        // returning files
        return files;
    }
}
