package com.anchorage.demo;

import com.anchorage.docks.node.DockNode;
import com.anchorage.docks.stations.DockStation;
import com.anchorage.system.AnchorageSystem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.util.Random;

/**
 *
 * @author keenlight
 */
public class AnchorFX_tab extends Application {

    @Override
    public void start(Stage primaryStage) {

        //AnchorageSettings.setDockingPositionPreview(false);
        
        DockStation station = AnchorageSystem.createStation();

        Scene scene = new Scene(station,  1024, 768);

        DockNode node1 = AnchorageSystem.createTabDock("1", generateRandomTree());
        node1.dock(station, DockNode.DockPosition.LEFT);
        
        DockNode node2 = AnchorageSystem.createTabDock("2", generateRandomTree());
        node2.dock(station, DockNode.DockPosition.RIGHT);
        
        DockNode node3 = AnchorageSystem.createTabDock("3", generateRandomTree());
        node3.dock(station, DockNode.DockPosition.TOP);
        
        DockNode node4 = AnchorageSystem.createTabDock("4", generateRandomTree());
        node4.dock(station, DockNode.DockPosition.BOTTOM);

        primaryStage.setTitle("AnchorFX ");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        AnchorageSystem.installDefaultStyle();
        
    }

    private TreeView<String> generateRandomTree() {
        // create a demonstration tree view to use as the contents for a dock node
        TreeItem<String> root = new TreeItem<String>("Root");
        TreeView<String> treeView = new TreeView<String>(root);
        treeView.setShowRoot(false);

        // populate the prototype tree with some random nodes
        Random rand = new Random();
        for (int i = 4 + rand.nextInt(8); i > 0; i--) {
            TreeItem<String> treeItem = new TreeItem<String>("Item " + i);
            root.getChildren().add(treeItem);
            for (int j = 2 + rand.nextInt(4); j > 0; j--) {
                TreeItem<String> childItem = new TreeItem<String>("Child " + j);
                treeItem.getChildren().add(childItem);
            }
        }

        return treeView;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
