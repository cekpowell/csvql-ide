package View.Editor;

import java.io.File;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import Controller.FileManager;
import Controller.SystemController;
import Model.FileType;
import Model.KeyCodes;
import View.Tools.CodeArea;
import View.Tools.PopUpWindow;

/**
 * Represents a file that is open within the editor.
 */
public abstract class EditorTab extends Tab{
    
    // constants
    private static final String unsavedLabel = "(Unsaved)";
    
    // member variables
    private EditorTabContainer editorTabContainer;
    private String name;
    private File file;
    private FileType fileType;
    private EditorTabToolbar toolbar;
    private CodeArea codeArea;
    private String textAtLastSave;
    private boolean unsavedChanges;

    //////////////////
    // INITIALIZING //
    //////////////////

    public EditorTab(EditorTabContainer editorTabContainer, String name, File file, FileType fileType){
        // initializinig
        this.editorTabContainer = editorTabContainer;
        this.name = name;
        this.file = file;
        this.fileType = fileType;
        this.toolbar = new EditorTabToolbar(this);
        this.codeArea = new CodeArea(this.fileType.getCodeMirrorTemplate(), "");
        this.textAtLastSave = "";
        if(this.file == null) {this.unsavedChanges = true;} else {this.unsavedChanges = false;}

        // Configuring Member Variables //

        if(this.file != null){
            /**
             * Editor tab set-up with file - need to load file content into the editor.
             */
            try{
                // getting content from the file
                String content = FileManager.getContentFromFile(this.file);
                
                // setting the content into the code area
                this.codeArea.setCode(content);

                // configuring the text at the last save
                this.textAtLastSave = content;
            }
            // handling error
            catch(Exception e){
                // displaying error window
                PopUpWindow.showErrorWindow(this.editorTabContainer.getScene().getWindow(), e);
            }
        }

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        BorderPane container = new BorderPane();
        container.setTop(this.toolbar);
        container.setCenter(this.codeArea);
        
        /////////////////
        // CONFIGURING //
        /////////////////

        // event handling
        this.configureEvents();

        // updating tab title
        this.updateTabTitleContent();

        // content
        this.setContent(container);
    }

    /**
     * Class constuctor. Initializes a new editor tab without an associated file.
     * 
     * @param editorTabContainer The container associated with this editor tab.
     * @param name The name of the file in the editor tab.
     * @param fileType The FileType associated with this EditorTab
     */
    public EditorTab(EditorTabContainer editorTabContainer, String name, FileType fileType){
        // initializng 
        this.name = name; 
        this.file = null;
        this.init(editorTabContainer, fileType);
    }

    /**
     * Class constructor. Initializes a new editor tab with an associated file.
     * 
     * @param editorTabContainer The container associated with this editor tab.
     * @param file The File associated with the editor tab.
     * @param fileType The FileType associated with this EditorTab
     */
    public EditorTab(EditorTabContainer editorTabContainer,File file, FileType fileType){
        // initializing
        this.name = file.getName();
        this.file = file;
        this.init(editorTabContainer, fileType);

        // Configuring Member Variables //

        /**
         * Editor tab set-up with file - need to load file content into the editor.
         */
        try{
            // getting content from the file
            String content = FileManager.getContentFromFile(this.file);
            
            // setting the content into the code area
            this.codeArea.setCode(content);

            // configuring the text at the last save
            this.textAtLastSave = content;
        }
        // handling error
        catch(Exception e){
            // displaying error window
            PopUpWindow.showErrorWindow(this.editorTabContainer.getScene().getWindow(), e);
        }
    }

    /**
     * Initializer method - used because of multiple constructors.
     * 
     * @param editorTabContainer The container associated with this editor tab.
     * @param ExtensionFilter The extension filter for this type of file when being saved.
     * @param fileType The FileType associated with this EditorTab
     */
    private void init(EditorTabContainer editorTabContainer, FileType fileType){
        // initializing
        this.editorTabContainer = editorTabContainer;
        this.fileType = fileType;
        this.toolbar = new EditorTabToolbar(this);
        this.codeArea = new CodeArea(this.fileType.getCodeMirrorTemplate(), "");
        this.textAtLastSave = "";
        if(this.file == null) {this.unsavedChanges = true;} else {this.unsavedChanges = false;}

        ///////////////////////////
        // CONTAINERS AND EXTRAS //
        ///////////////////////////

        BorderPane container = new BorderPane();
        container.setTop(this.toolbar);
        container.setCenter(this.codeArea);
        
        /////////////////
        // CONFIGURING //
        /////////////////

        // event handling
        this.configureEvents();

        // updating tab title
        this.updateTabTitleContent();

        // content
        this.setContent(container);
    }

    /**
     * Defines the event handling for the events that can occur 
     * within the control.
     */
    private void configureEvents(){
        // Tab Selection 
        this.setOnSelectionChanged((e) ->{
            this.editorTabContainer.setCurrentEditorTab(this);
        });

        // Tab Close
        this.setOnCloseRequest((e) -> {
            // Unsaved Changes //
            if(this.unsavedChanges){
                // confirming the close
                boolean confirmClosed = PopUpWindow.showConfirmationWindow(this.editorTabContainer.getScene().getWindow(), 
                                                                                "Close Unsaved File", 
                                                                                "Are you sure you want to close '" + this.name + "' without saving?");

                if(confirmClosed){
                    // removing the program from the container
                    this.editorTabContainer.removeEditorTab(this);
                }
                else{
                    e.consume();
                }
            }
            // Saved //
            else{
                // removing the program from the container
                this.editorTabContainer.removeEditorTab(this);
            }
        });

        // KeyBoard Shortcuts
        this.codeArea.setOnKeyPressed((e) -> {
            // SAVE - CTRL + S
            if(KeyCodes.CTRL_S.match(e)){
                try {
                    // saving file through system controller
                    SystemController.getInstance().saveEditorTab(this);
                }
                catch (Exception ex) {
                    // showing error alert
                    PopUpWindow.showErrorWindow(this.getEditorTabToolbar().getScene().getWindow(), ex);
                }
            }
            // UNDO - CTRL + Z
            else if(KeyCodes.CTRL_Z.match(e)){
                // performing undo
                this.codeArea.undo();
            }
            // REDO - CTRL + SHIFT + Z
            else if(KeyCodes.CTRL_SHIFT_Z.match(e)){
                // performing redo
                this.codeArea.redo();
            }
            // ZOOM-IN - CTRL + PLUS
            else if(KeyCodes.CTRL_PLUS.match(e)){
                // performing zoom-in
                this.codeArea.zoomIn();;
            }
            // ZOOM-OUT - CTRL + MINUS
            else if(KeyCodes.CTRL_MINUS.match(e)){
                // performing zoom-out
                this.codeArea.zoomOut();;
            }
        });

        // Key Released
        this.codeArea.setOnKeyReleased((e) -> {
            // checking for changes to code
            this.checkForCodeChanges();
        });
    }

    ////////////////////
    // HELPER METHODS //
    ////////////////////

    /**
     * Updates the graphic and name displayed in the tab title based on 
     * the save state of the file being edited in the tab.
     */
    private void updateTabTitleContent(){
        // unsaved changes - display unsaved name and graphic
        if(this.unsavedChanges){
            this.setText(this.name + EditorTab.unsavedLabel);
            this.setGraphic(new ImageView(this.fileType.getGraphicUnsaved()));
        }
        // saved changes - display normal graphic and name
        else{
            this.setText(this.name);
            this.setGraphic(new ImageView(this.fileType.getGraphic()));
        }
    }

    /**
     * Updates the properties after a save has been carried out
     */
    public void updateAfterSave(){
        // updating properties
        this.unsavedChanges = false;
        this.textAtLastSave = this.codeArea.getCode();

        // updating tab title
        this.updateTabTitleContent();
    }

    /**
     * Checks the EditorTab's CodeArea to see if there have been any changes to the text since the last save.
     */
    public void checkForCodeChanges(){
        if(!(this.file == null)) { // if there is no file, it must be unsaved, so no need to check
            // text has changed
            if(!this.textAtLastSave.equals(this.codeArea.getCode())){
                // there are no unsaved changes yet - need to update tab information
                if(!this.unsavedChanges){
                    // altering save state
                    this.unsavedChanges = true;
                    
                    // updating tab title
                    this.updateTabTitleContent();
                }
            }
            // text has gone back to last save
            else{
                // there were previously unsaved changes - need to update tab information
                if(this.unsavedChanges){
                    // upudating save state
                    this.unsavedChanges = false;

                    // updating tab title
                    this.updateTabTitleContent();
                }
            }
        }
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public String getName(){
        return this.name;
    }

    public File getFile(){
        return file;
    }

    public FileType getFileType(){
        return this.fileType;
    }

    public EditorTabToolbar getEditorTabToolbar(){
        return this.toolbar;
    }

    public CodeArea getCodeArea(){
        return this.codeArea;
    }

    public String getTextAtLastSave(){
        return this.textAtLastSave;
    }

    public boolean hasUnsavedChanges(){
        return this.unsavedChanges;
    }

    public void setName(String name){
        this.name = name;

        /**
         * Also need to update tab title.
         */
        this.updateTabTitleContent();
    }

    public void setFile(File file){
        this.file = file;

        /**
         * Also need to update name and tab title.
         */
        this.name = file.getName();
        this.updateTabTitleContent();
                
    }

    public void setTextAtLastSave(String textAtLastSave){
        this.textAtLastSave = textAtLastSave;
    }

    public void setUnsavedChanges(boolean unsavedChanges){
        this.unsavedChanges = unsavedChanges;
    }
}