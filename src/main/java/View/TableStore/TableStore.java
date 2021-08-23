package View.TableStore;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import View.App.Dashboard;
import View.Editor.EditorFile.EditorFileType;
import View.Tools.ConfirmationButton;
import View.Tools.ErrorAlert;
import View.Tools.SectionTitle;

/**
 * Represents the panel within the application where the user loads files.
 */
public class TableStore extends VBox{

    // constants
    private static final Image tableStoreImage = new Image("img/table-store.png");
    private static final Image openImage = new Image("img/open.png");
    private static final Image closeImage = new Image("img/remove.png");
    
    // member variables
    private Dashboard dashboard;
    private FlowPane tableContainer;
    private ArrayList<Table> tables;
    private Button loadTableButton;
    private ConfirmationButton clearStoreButton;

    /**
     * Constructor for the class.
     * 
     * @param dashboard The dashboard associated with the panel
     */
    public TableStore(Dashboard dashboard){
        // Initialising //
        this.dashboard = dashboard;
        this.tableContainer = new FlowPane();
        this.tables = new ArrayList<Table>();
        this.loadTableButton = new Button("Load Table", new ImageView(openImage));
        this.clearStoreButton = new ConfirmationButton("Clear Store", new ImageView(closeImage),
                                                       "Clear Table Store", 
                                                       "Are you sure you want to remove all loaded tables from the system? " + "\n" +
                                                       "Any unsaved progress in the Editor will be lost.");

        // Configuring Member Variables //

        // file container
        this.tableContainer.setHgap(10);
        this.tableContainer.setPadding(new Insets(10));
        this.tableContainer.setOrientation(Orientation.HORIZONTAL);

        // disabling clear store button (as no tables yet loaded)
        this.clearStoreButton.setDisable(true);

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////        

        // configuring title label
        SectionTitle titleLabel = new SectionTitle("Table Store", new ImageView(tableStoreImage));

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

        // adding main container to view
        this.getChildren().addAll(headerContainer, this.tableContainer);

        // formatting
        this.setAlignment(Pos.TOP_LEFT);
        this.setPadding(new Insets(10,10,10,10));

        /////////////
        // ACTIONS //
        /////////////

         // action for load file button
         this.loadTableButton.setOnAction((e) -> {
            // configuring the file chooser to load a new file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load CSV File");
            fileChooser.getExtensionFilters().addAll(EditorFileType.TABLE.getExtensionFilter());

            // showing the open dialog
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(this.getScene().getWindow());

            // adding the file to the filestore if a file was gathered
            if (selectedFiles != null) {
                for(File file : selectedFiles){
                    try{
                        // adding the table
                        this.addTable(file);

                        // enabling clear store button (as tables now present)
                        this.clearStoreButton.setDisable(false);
                    }
                    // handling error
                    catch(Exception ex){
                        ErrorAlert.showErrorAlert(this.getScene().getWindow(), ex);
                    }
                }
            }
        });

        // action for clear store button
        this.clearStoreButton.setOnAction((e) -> {
            // showing the confirmation button pop-up window
            boolean clearConfirmed = this.clearStoreButton.showConfirmationWindow(this.getScene().getWindow());

            // clearing if the use confirmed the clear
            if(clearConfirmed){
                // clearing
                this.clearTables();

                // disabling clear store button (no tables in system)
                this.clearStoreButton.setDisable(true);
            }
        });
    }

    ///////////////////
    // ADDING TABLES //
    ///////////////////

    /**
     * Adds a file into the Filestore.
     * 
     * @param file The file to be added into the store.
     * @throws Exception If a table with this name is already in the store.
     */
    public void addTable(File file) throws Exception{
        // testing if a file with this name already exists
        for(Table table : this.tables){
            if(table.getName().equals(file.getName())){
                throw new Exception("There is already a table with the name '" + file.getName() + "' in the store.");
            }
        }

        // creating object
        Table table = new Table(this, file);

        // adding loaded file to container
        this.tableContainer.getChildren().addAll(table);

        // adding loaded file to list
        this.tables.add(table);
    }

    /**
     * Adds a table into the store.
     * 
     * @param name The name of the table being added.
     * @throws Exception Thrown if the 
     */
    public void addTable(String name) throws Exception{
        // testing if a file with this name already exists
        for(Table table : this.tables){
            if(table.getName().equals(name)){
                throw new Exception("There is already a table with the name '" + name + "' in the store.");
            }
        }

        // adding the table into the filestore
        // TODO
    }

    ////////////////////
    // REMOVING FILES //
    ////////////////////

    /**
     * Removed a loaded table from the store.
     * 
     * @param table The Table being removed from the store.
     */
    public void removeTable(Table table){
        // removing table from the container
        this.tableContainer.getChildren().remove(table);

        // removing table from the list
        this.tables.remove(table);
    }

    /**
     * Removes all tables from the store.
     */
    public void clearTables(){
        // removing loaded files from container
        this.tableContainer.getChildren().clear();

        // removing loaded files from list
        this.tables.clear();
    }


    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public ArrayList<File> getFiles(){

        // list to store files
        ArrayList<File> files = new ArrayList<File>();

        // gathering all loaded files
        for(Table loadedFile : this.tables){
            files.add(loadedFile.getFile());
        }

        // returning files
        return files;
    }

    public ArrayList<Table> getTables(){
        return this.tables;
    }
}
