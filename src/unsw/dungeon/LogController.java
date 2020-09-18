package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.FloatProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class LogController {
    
    private TitleScreen mainMenu;

    private Label[] statValues;

    private Log currLog;

    public LogController(TitleScreen mainMenu, Log log) {
        statValues = new Label[6];
        this.mainMenu = mainMenu;
        currLog = log;
    }

    @FXML
    private GridPane logGrid;

    @FXML   
    private Button returnBtn;

    @FXML
    public void handleReturnPress(ActionEvent event) {
        mainMenu.start();
    }

    @FXML
    public void initialize() {
        
        ArrayList<FloatProperty> values = currLog.trackedProperties();
        for (int i = 0; i < 6; i++) {
            statValues[i] = new Label();
            statValues[i].textProperty().bind(values.get(i).asString());
            logGrid.add(statValues[i], 2, i);
        }
    }

    public void setLevel(TitleScreen mainMenu) {
        this.mainMenu= mainMenu;
    }
}
