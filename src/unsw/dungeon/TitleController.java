package unsw.dungeon;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TitleController {
   
    private DungeonScreen level;
    private LogScreen stats;

    private Log log;

    @FXML
    private Label title;

    @FXML
    private ChoiceBox<String> lvlChoiceBox;

    @FXML
    private Button startBtn;

    @FXML
    private Button statsBtn;

    @FXML
    private Button exitBtn;

    @FXML
    public void handleStartPress(ActionEvent event) {
        level.start(lvlChoiceBox.getSelectionModel().getSelectedItem(), log);
    }

    @FXML
    public void handleStatsPress(ActionEvent event) {
        stats.start();
    }

    @FXML
    public void handleExitPress(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        lvlChoiceBox.getItems().addAll("Level 1", "Level 2", "Level 3", "Maze Challenge", "Test");

        // Set to first level initially
        lvlChoiceBox.setValue("Level 1");
    }

    public void setLevel(DungeonScreen level) {
        this.level = level;
    }

    public void setStats(LogScreen stats) {
        this.stats = stats; 
    }

    public void setLog(Log log) {
        this.log = log; 
    }

    public void restartLevel() {
        level.start(lvlChoiceBox.getSelectionModel().getSelectedItem(), log);
    }
}
