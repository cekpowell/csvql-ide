package View.Editor;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import Controller.SystemController;
import Model.Images;
import View.App.Dashboard;
import View.Tools.SectionTitle;

/**
 * View for editing programs within the IDE.
 */
public class Editor extends BorderPane{

    // member variables
    private Dashboard dashboard; 
    private EditorTabContainer editorTabContainer;

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

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        // title label
        SectionTitle titleLabel = new SectionTitle("Editor", new ImageView(Images.EDITOR));

        // container for title and toolbar
        VBox container = new VBox(titleLabel);
        container.setPadding(new Insets(10));

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding controls to the editor
        this.setTop(container);

        // updating the contents of the editor
        this.updateContents();
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
        // buttons to open program or create new one
        Button newFileButton = new Button("New", new ImageView(Images.NEW_FILE));
        Button openFileButton = new Button("Open", new ImageView(Images.OPEN));

        // container for these buttons 
        VBox container = new VBox(newFileButton, openFileButton);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        // Event Handling //

        // New File
        newFileButton.setOnAction((e) -> {
            // creating new file through system controller
            SystemController.getInstance().createNewFile();
        });

        // Open File
        openFileButton.setOnAction((e) -> {
            // opening files through the system controller
            SystemController.getInstance().openFile();
        });

        // adding buttons to editor
        this.setCenter(container);
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public EditorTabContainer getEditorTabContainer(){
        return this.editorTabContainer;
    }
}
