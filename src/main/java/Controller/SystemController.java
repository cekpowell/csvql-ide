package Controller;

import java.io.File;

import View.App.Dashboard;
import View.App.Toolbar;

/**
 * Uses static methods to manage the system.
 */
public class SystemController {
    
    // static variables
    private static Toolbar toolbar;
    private static Dashboard dashboard;

    /**
     * Sets the Toolbar for the system controller.
     * 
     * @param toolbar The Toolbar for the system controller.
     */
    public static void setToolbar(Toolbar toolbar){
        SystemController.toolbar = toolbar;
    }

    /**
     * Sets the Dashboord for the system contoller.
     * 
     * @param dashboard The Dashboard for the system controller.
     */
    public static void setDashboard(Dashboard dashboard){
        SystemController.dashboard = dashboard;
    }

    /**
     * Adds a new program into the editor with the provided name.
     * 
     * @param name The name of the new program
     */
    public static void addProgram(String name) throws Exception{
        SystemController.dashboard.getEditor().addProgram(name);
    }

    /**
     * Loads a program into the system from within an existing file.
     * 
     * @param file The file the program is being loaded from.
     */
    public static void addProgram(File file) throws Exception{
        SystemController.dashboard.getEditor().addProgram(file);
    }
}
