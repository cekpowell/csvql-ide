package View.Tools;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Represents a toolbar within the application. Defines methods that allow for
 * controls to be added into the toolbar at different positions (left, middle
 * right).
 */
public abstract class AppToolbar extends HBox{ 

    // member variables
    private int padding;
    private int sectionSpace;
    private int controlSpace;
    private HBox leftContainer;
    private HBox centerContainer;
    private HBox rightContainer;

    //////////////////
    // INITIALIZING //
    //////////////////

    /**
     * Class constructor.
     */
    public AppToolbar(int padding, int sectionSpace, int controlSpace){
        // initializing
        this.padding = padding;
        this.sectionSpace = sectionSpace;
        this.controlSpace = controlSpace;
        this.leftContainer = new HBox();
        this.centerContainer = new HBox();
        this.rightContainer = new HBox();

        // Configuring Member variables

        // left contanier
        HBox.setHgrow(this.leftContainer, Priority.ALWAYS);
        this.leftContainer.setSpacing(this.controlSpace);
        this.leftContainer.setAlignment(Pos.TOP_LEFT);

        // center container
        HBox.setHgrow(this.centerContainer, Priority.ALWAYS);
        this.centerContainer.setSpacing(this.controlSpace);
        this.centerContainer.setAlignment(Pos.CENTER);

        // right container
        HBox.setHgrow(this.rightContainer, Priority.ALWAYS);
        this.rightContainer.setSpacing(this.controlSpace);
        this.rightContainer.setAlignment(Pos.TOP_RIGHT);

        /////////////////
        // CONFIGURING //
        /////////////////

        // adding controls
        this.getChildren().addAll(this.leftContainer, this.centerContainer, this.rightContainer);

        // configuring
        this.setSpacing(this.sectionSpace);
        this.setPadding(new Insets(this.padding));
    }

    //////////
    // LEFT //
    //////////

    /**
     * Adds the provided Node into the left container of the toolbar.
     * 
     * @param node The Node to be added into the left container.
     */
    public void addLeftContainer(Node node){
        this.leftContainer.getChildren().add(node);
    }

    /**
     * Adds the provided list of nodes into the left container of
     * the toolbar.
     * 
     * @param nodes The list of nodes to be added into the toolbar.
     */
    public void addAllLeftContainer(Node[] nodes){
        this.leftContainer.getChildren().addAll(nodes);
    }

    /**
     * Adds the provided node into the left container of the toolbar
     * and places a separator after it.
     * 
     * @param node The node to be added to the toolbar.
     */
    public void addLeftContainerWithSep(Node node){
        this.leftContainer.getChildren().addAll(node, AppToolbar.getVSeparator());
    }

    /**
     * Adds the provided list of nodes nito the left container with a 
     * separator after them.
     * 
     * @param nodes The list of nodes to be added into the container.
     */
    public void addAllLeftContainerWithSep(Node[] nodes){
        this.leftContainer.getChildren().addAll(nodes);
        this.leftContainer.getChildren().add(AppToolbar.getVSeparator());
    }

    /**
     * Adds the provided list of nodes into the left container, with a
     * seperator between each node.
     * 
     * @param nodes The list of nodes to be added into the left container.
     */
    public void addAllLeftContainerWithSepSplice(Node[] nodes){
        for(Node node : nodes){
            this.leftContainer.getChildren().add(node);
            this.leftContainer.getChildren().add(AppToolbar.getVSeparator());
        }
    }

    /**
     * Adds a list of node groups into the left container, and places
     * a separator in between each group.
     * 
     * @param nodeGroups The groups of nodes being added into the left
     * container.
     */
    public void addGroupsLeftContainerWithSepSplice(Node[]... nodeGroups){
        for(Node[] nodeGroup : nodeGroups){
            this.leftContainer.getChildren().addAll(nodeGroup);
            this.leftContainer.getChildren().add(AppToolbar.getVSeparator());
        }
    }

    ////////////
    // CENTER //
    ////////////

    /**
     * Adds the provided Node into the center container of the toolbar.
     * 
     * @param node The Node to be added into the center container.
     */
    public void addCenterContainer(Node node){
        this.centerContainer.getChildren().add(node);
    }

    /**
     * Adds the provided list of nodes into the center container of
     * the toolbar.
     * 
     * @param nodes The list of nodes to be added into the center container.
     */
    public void addAllCenterContainer(Node[] nodes){
        this.centerContainer.getChildren().addAll(nodes);
    }

    /**
     * Adds the provided node into the center container of the toolbar
     * and places a separator after it.
     * 
     * @param node The node to be added to the center container.
     */
    public void addCenterContainerWithSep(Node node){
        if(this.centerContainer.getChildren().size() == 0){
            this.centerContainer.getChildren().add(AppToolbar.getVSeparator());
        }

        this.centerContainer.getChildren().addAll(node, AppToolbar.getVSeparator());
    }

    /**
     * Adds the provided list of nodes nito the center container with a 
     * separator after them.
     * 
     * @param nodes The list of nodes to be added into the center container.
     */
    public void addAllCenterContainerWithSep(Node[] nodes){
        if(this.centerContainer.getChildren().size() == 0){
            this.centerContainer.getChildren().add(AppToolbar.getVSeparator());
        }
        this.centerContainer.getChildren().addAll(nodes);
        this.centerContainer.getChildren().add(AppToolbar.getVSeparator());
    }

    /**
     * Adds the provided list of nodes into the center container, with a
     * seperator between each node.
     * 
     * @param nodes The list of nodes to be added into the center container.
     */
    public void addAllCenterContainerWithSepSplice(Node[] nodes){
        if(this.centerContainer.getChildren().size() == 0){
            this.centerContainer.getChildren().add(AppToolbar.getVSeparator());
        }
        for(Node node : nodes){
            this.centerContainer.getChildren().add(node);
            this.centerContainer.getChildren().add(AppToolbar.getVSeparator());
        }
    }

    /**
     * Adds a list of node groups into the center container, and places
     * a separator in between each group.
     * 
     * @param nodeGroups The groups of nodes being added into the center
     * container.
     */
    public void addCenterLeftContainerWithSepSplice(Node[]... nodeGroups){
        if(this.centerContainer.getChildren().size() == 0){
            this.centerContainer.getChildren().add(AppToolbar.getVSeparator());
        }
        for(Node[] nodeGroup : nodeGroups){
            this.centerContainer.getChildren().addAll(nodeGroup);
            this.centerContainer.getChildren().add(AppToolbar.getVSeparator());
        }
    }

    ///////////
    // RIGHT //
    ///////////

    /**
     * Adds the provided Node into the right container of the toolbar.
     * 
     * @param node The Node to be added into the right container.
     */
    public void addRightContainer(Node node){
        this.rightContainer.getChildren().add(node);
    }

    /**
     * Adds the provided list of nodes into the right container of
     * the toolbar.
     * 
     * @param nodes The list of nodes to be added into the right container.
     */
    public void addAllRightContainer(Node[] nodes){
        this.rightContainer.getChildren().addAll(nodes);
    }

    /**
     * Adds the provided node into the right container of the toolbar
     * and places a separator after it.
     * 
     * @param node The node to be added to the right container.
     */
    public void addRightContainerWithSep(Node node){
        this.rightContainer.getChildren().addAll(AppToolbar.getVSeparator(), node);
    }

    /**
     * Adds the provided list of nodes nito the right container with a 
     * separator after them.
     * 
     * @param nodes The list of nodes to be added into the right container.
     */
    public void addAllRightContainerWithSep(Node[] nodes){
        this.rightContainer.getChildren().add(AppToolbar.getVSeparator());
        this.rightContainer.getChildren().addAll(nodes);
    }

    /**
     * Adds the provided list of nodes into the right container, with a
     * seperator between each node.
     * 
     * @param nodes The list of nodes to be added into the right container.
     */
    public void addAllRightContainerWithSepSplice(Node[] nodes){
        for(Node node : nodes){
            this.rightContainer.getChildren().add(AppToolbar.getVSeparator());
            this.rightContainer.getChildren().add(node);
        }
    }

    /**
     * Adds a list of node groups into the right container, and places
     * a separator in between each group.
     * 
     * @param nodeGroups The groups of nodes being added into the right
     * container.
     */
    public void addGroupsRightContainerWithSepSplice(Node[]... nodeGroups){
        for(Node[] nodeGroup : nodeGroups){
            this.rightContainer.getChildren().add(AppToolbar.getVSeparator());
            this.rightContainer.getChildren().addAll(nodeGroup);
        }
    }

    ////////////////////
    // HELPER METHODS //
    ////////////////////

    /**
     * Returns a new vertical separator.
     * 
     * @return The new vertical separator
     */
    private static Separator getVSeparator(){
        return new Separator(Orientation.VERTICAL);
    }

    /**
     * Returns a new horizontal separator.
     * 
     * @return The new horizontal separator
     */
    private static Separator getHSeparator(){
        return new Separator(Orientation.HORIZONTAL);
    }
}