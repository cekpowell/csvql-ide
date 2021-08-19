package View.FileStore;

import java.util.ArrayList;
import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import View.App.Dashboard;
import View.Tools.ConfirmationButton;
import View.Tools.SectionTitle;
import View.Tools.ErrorAlert;

/**
 * Represents the panel within the application where the user loads files.
 */
public class FileStore extends VBox{
    
    // member variables
    private Dashboard dashboard;
    private FlowPane fileContainer;
    private ArrayList<LoadedFile> files;
    private Button loadFileButton;
    private ConfirmationButton clearStoreButton;

    /**
     * Constructor for the class.
     * 
     * @param dashboard The dashboard associated with the panel
     */
    public FileStore(Dashboard dashboard){
        // initialising member vairables
        this.dashboard = dashboard;
        this.fileContainer = new FlowPane();
        this.files = new ArrayList<LoadedFile>();
        this.loadFileButton = new Button("Load File");
        this.clearStoreButton = new ConfirmationButton("Clear Store", 
                                                       "Clear File Store", 
                                                       "Are you sure you want to remove all loaded files from the file store?");

        ////////////////
        // CONTAINERS //
        ////////////////        

        // configuring title label
        SectionTitle titleLabel = new SectionTitle("CSV File Store");

        // container for title
        HBox titleContainer = new HBox(titleLabel);
        HBox.setHgrow(titleContainer, Priority.ALWAYS);

        // container for load file button
        HBox buttonContainer = new HBox(this.loadFileButton, this.clearStoreButton);
        buttonContainer.setSpacing(10);
        buttonContainer.setAlignment(Pos.TOP_RIGHT);

        // title for container and header
        HBox headerContainer = new HBox();
        headerContainer.getChildren().addAll(titleContainer, buttonContainer);

        /////////////////
        // CONFIGURING //
        /////////////////

        // configuring file container
        this.fileContainer.setHgap(10);
        this.fileContainer.setOrientation(Orientation.HORIZONTAL);

        // adding main container to view
        this.getChildren().addAll(headerContainer, this.fileContainer);

        // formatting
        this.setAlignment(Pos.TOP_LEFT);
        this.setPadding(new Insets(10,10,10,10));

        /////////////
        // ACTIONS //
        /////////////

        // action for load file button
        this.loadFileButton.setOnAction((e) -> {
            // configuring the file chooser to load a new file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load CSV File");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));

            // showing the open dialog
            File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());

            // adding the file to the filestore if a file was gathered
            if (selectedFile != null) {
                try{
                    this.addFile(selectedFile);
                }
                // handling error
                catch(Exception ex){
                    ErrorAlert errorAlert = new ErrorAlert(ex);
                    errorAlert.showWindow(this.getScene().getWindow());
                }
            }
        });

        // action for clear store button
        this.clearStoreButton.setOnAction((e) -> {
            // showing the confirmation button pop-up window
            boolean clearConfirmed = this.clearStoreButton.showConfirmationWindow(this.getScene().getWindow());

            // clearing if the use confirmed the clear
            if(clearConfirmed){
                this.clearLoadedFiles();
            }
        });
    }

    /**
     * Adds a file into the Filestore.
     * 
     * @param file The file to be added into the store.
     */
    public void addFile(File file) throws Exception{
        // testing if a file with this name already exists
        for(LoadedFile loadedFile : this.files){
            if(loadedFile.getName().equals(file.getName())){
                throw new Exception("There is already a file with the name '" + file.getName() + "' in the filestore.");
            }
        }

        // creating object
        LoadedFile loadedFile = new LoadedFile(this, file);

        // adding loaded file to container
        this.fileContainer.getChildren().addAll(loadedFile);

        // adding loaded file to list
        this.files.add(loadedFile);
    }

    /**
     * Removed a loaded file from the Filestore.
     * 
     * @param loadedFile The LoadedFile being removed from the filestore.
     */
    public void removeLoadedFile(LoadedFile loadedFile){
        // removing loaded file from container
        this.fileContainer.getChildren().remove(loadedFile);

        // removing loaded file from list
        this.files.remove(loadedFile);
    }

    public void clearLoadedFiles(){
        // removing loaded files from container
        this.fileContainer.getChildren().clear();

        // removing loaded files from list
        this.files.clear();
    }
}
