package View.Editor;

import java.util.ArrayList;

import javafx.scene.control.*;

/**
 * View to contain all the programs being edited within the editor.
 */
public class EditorTabContainer extends TabPane{
    
    // member variables
    private Editor editor;
    private ArrayList<EditorTab> editorTabs;
    private EditorTab currentEditorTab;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     * 
     * @param editor The editor associated with this program container.
     */
    public EditorTabContainer(Editor editor){
        // initializing
        this.editor = editor;
        this.editorTabs = new ArrayList<EditorTab>(); 
        this.currentEditorTab = null;

        /////////////////
        // CONFIGURING //
        /////////////////

        // formatting the tab pane
        this.getStyleClass().add("floating"); // TODO should be done with CSS
    }

    //////////////////////////////
    // CONFIGURING EDITOR TABS //
    /////////////////////////////

    /**
     * Adds an editor tab into the container.
     * 
     * @param editorTab The editor tab being added into the container.
     */
    public void addEditorTab(EditorTab editorTab){
        // adding editor tab to system
        this.editorTabs.add(editorTab);
        this.getTabs().add(editorTab);
        this.getSelectionModel().select(editorTab);

        // setting current program
        this.setCurrentEditorTab(editorTab);

        // updating the contents of the editor view (display new tab)
        this.editor.updateContents();
    } 

    /**
     * Removes an editor tab from the container.
     * 
     * @param editorTab The editortab being removed.
     */
    public void removeEditorTab(EditorTab editorTab){
        // removing the editor tab from system
        this.editorTabs.remove(editorTab);
        this.getTabs().remove(editorTab);

        // updating the contents of the editor view (display no tabs screen if this is last tab)
        this.editor.updateContents();
    }

    /**
     * Replaces the old EditorTab with the new EditorTab.
     * 
     * @param oldDditorTab The EditorTab currently in the container.
     * @param newEditorTab The new EditorTab to be placed into the container.
     */
    public void replaceEditorTab(EditorTab oldEditorTab, EditorTab newEditorTab){
        // replacing in list
        int index = this.editorTabs.indexOf(oldEditorTab);
        this.editorTabs.set(index, newEditorTab);

        // replacing in tab pane
        index = this.getTabs().indexOf(oldEditorTab);
        this.getTabs().set(index, newEditorTab);

        // removing old editor tab
        this.removeEditorTab(oldEditorTab);

        // selecting new editor tab (because it was previously selected)
        this.getSelectionModel().select(newEditorTab);
    }

    ////////////////////
    // HELPER METHODS //
    ////////////////////

    /**
     * Determines if any of the files present in container have usaved changes.
     * 
     * @return True if at least one file within the container has unsaved changes.
     */
    public boolean hasUnsavedFiles(){
        // iterating through tabs
        for(EditorTab editorTab : this.editorTabs){
            if(editorTab.hasUnsavedChanges()){
                // returning true if file with unsaved changes found
                return true;
            }
        }

        // no unsaved changes found - returning false
        return false;
    }

    /////////////////////////
    // GETTERS AND SETTERS //
    /////////////////////////

    public ArrayList<EditorTab> getEditorTabs(){
        return this.editorTabs;
    }

    public EditorTab getCurrentEditorTab(){
        return this.currentEditorTab;
    }

    public void setCurrentEditorTab(EditorTab editorTab){
        this.currentEditorTab = editorTab;
    }
}