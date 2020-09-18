package unsw.dungeon;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LogScreen {

    private Stage stage;
    private String title;
    private LogController controller;

    private Scene scene;

    /**
     * Creates log screen of game
     * @param stage Stage of game
     * @param mainMenu Main menu of game
     * @param log Log of player's actions
     * @throws IOException
     */
    public LogScreen(Stage stage, TitleScreen mainMenu, Log log) throws IOException {
        // Stage information
        this.stage = stage;
        title = "Game Statistics";

        // Controller for game
        controller = new LogController(mainMenu, log);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("log.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        root.requestFocus();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    /**
     * Starts the scene
     */
    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Obtains the controller
     * @return Log controller
     */
    public LogController getController() {
        return controller;
    }
}
