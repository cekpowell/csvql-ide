package View.Editor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.util.List;

import Controller.SystemController;
import View.App.Dashboard;
import View.App.NewProgramForm;
import View.Tools.ErrorAlert;
import View.Tools.SectionTitle;

/**
 * View for editing programs within the IDE.
 */
public class Editor extends BorderPane{

    // member variables
    private Dashboard dashboard;
    private ProgramContainer programContainer;

    /**
     * Class constructor.
     */
    public Editor(Dashboard dashboard){
        // initializing
        this.dashboard = dashboard;
        this.programContainer = new ProgramContainer(this);

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // title label
        SectionTitle titleLabel = new SectionTitle("Editor");

        // container for title and toolbar
        VBox container = new VBox(titleLabel);
        container.setPadding(new Insets(10));

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding controls to the editor
        this.setTop(container);
        this.showNoProgramScreen();
    }

    /**
     * Shows a view for when their are no programs currently open.
     */
    public void showNoProgramScreen(){
        // buttons to open program or create new one
        Button openProgramButton = new Button ("Open Program");
        Button newProgramButton = new Button("New Program");

        // container for these buttons 
        VBox container = new VBox(openProgramButton, newProgramButton);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        // Open Program Action //
        openProgramButton.setOnAction((e) -> {
            // configuring the file chooser to load a new file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load CSV File");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("CSVQL Files", "*.cql"));

            // showing the open dialog
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(this.getScene().getWindow());

            // adding the file to the filestore if a file was gathered
            if (selectedFiles != null) {
                for(File file : selectedFiles){
                    try{
                        SystemController.addProgram(file);
                    }
                    // handling error
                    catch(Exception ex){
                        ErrorAlert.showErrorAlert(this.getScene().getWindow(), ex);
                    }
                }
            }
        });

        // New Program Action //
        newProgramButton.setOnAction((e) -> {
            // creating input form
            NewProgramForm newProgramForm = new NewProgramForm();
            newProgramForm.initOwner(this.getScene().getWindow());
            newProgramForm.show();
        });

        // adding buttons to editor
        this.setCenter(container);
    }

    /**
     * Adds a new program into the editor with the provided name.
     * 
     * @param name The name of the new program.
     */
    public void addProgram(String name) throws Exception{
        // creating new program
        this.programContainer.createNewProgram(name);

        // setting program container into the control
        this.setCenter(this.programContainer);
    }

    /**
     * Adds a new program into the editor with the provided name.
     * 
     * @param name The name of the new program.
     */
    public void addProgram(File file) throws Exception{
        // creating new program
        this.programContainer.loadProgram(file);

        // setting program container into the control
        this.setCenter(this.programContainer);
    }


    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////
}
