package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Used to launch the dungeon game
 */
public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        TitleScreen titleScreen = new TitleScreen(primaryStage);

        // Creating log
        Log gameLog = new Log();
        titleScreen.setControllerLog(gameLog);

        // Creating dungeon screen
        DungeonScreen testLevel = new DungeonScreen(primaryStage, gameLog, titleScreen);
        titleScreen.setControllerLevel(testLevel);

        // Set the initial log, and pass to current level
        LogScreen logScreen = new LogScreen(primaryStage, titleScreen, gameLog);
        titleScreen.setControllerStats(logScreen);

        // Starting game
        titleScreen.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
