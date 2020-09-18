package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonScreen {
    private Stage stage;
    private TitleController controller;

    private TitleScreen main;
    private Scene scene;

    /**
     * Creates the dungeon scene
     * @param stage Stage of the game
     * @param log Log of the game
     * @param menu Main menu of game
     * @throws IOException
     * @throws FileNotFoundException
     */
    public DungeonScreen(Stage stage, Log log, TitleScreen menu) throws IOException, FileNotFoundException {
        this.stage = stage;
        String fname = "test";
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(fname + ".json");
        DungeonController controller = dungeonLoader.loadController();
        controller.setLog(log);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        controller.setMenu(menu);
        main = menu;

        Parent root = loader.load();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    /**
     * Starts the game
     * @param choice Chosen level
     * @param log Corresponding log
     */
    public void start(String choice, Log log) {
        try {
            setLevel(choice, log);
        } catch (Exception e) {
            System.out.println("Could not load level.");
        }
    }

    /**
     * Obtains the controller for the game
     * @return Title controller
     */
    public TitleController getController() {
        return controller;
    }

    /**
     * Sets the level for the game
     * @param choice Chosen level
     * @param log Corresponding log
     * @throws IOException
     */
    private void setLevel(String choice, Log log) throws IOException {
        String fname;
        if (choice.equals("Test")) {
            fname = "test";
        } else if (choice.equals("Maze Challenge")) {
            fname = "maze";
        } else {
            fname = "level"+(choice.charAt(choice.length()-1));
        }

        // Creating dungeon
        DungeonControllerLoader dungeonLoader = new DungeonControllerLoader(fname + ".json");
        DungeonController controller = dungeonLoader.loadController();
        controller.setLog(log);

        // Loading dungeon
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        loader.setController(controller);
        controller.setMenu(main);

        // Showing dungeon
        Parent root = loader.load();
        root.requestFocus();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
