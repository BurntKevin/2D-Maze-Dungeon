package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.io.File;
import javafx.util.Duration; 
/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 */
public class DungeonController {
    
    private Image sword;
    private Image bow;
    private Image potion;
    private Image quest;

    private Tooltip questList = new Tooltip();

    @FXML
    private Button returnBtn;

    @FXML
    private ImageView questImg;

    @FXML
    private ImageView swordImg;

    @FXML
    private ImageView bowImg;

    @FXML
    private ImageView potionImg;

    @FXML
    private Label swordVal;

    @FXML
    private Label bowVal;

    @FXML
    private Label potionVal;

    @FXML
    private GridPane squares;

    private List<ImageView> initialEntities;

    private Player player;

    private Player playerCoop;

    private ArrayList<Enemy> enemies;

    private ArrayList<PickUp> itemPickUps;

    private TitleScreen mainMenu;

    private Dungeon dungeon;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.playerCoop = dungeon.getPlayerCoop();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.itemPickUps = new ArrayList<PickUp>();
        this.enemies = dungeon.getEnemies();
    }

    @FXML
    public void initialize() {
        // Obtaining images
        Image ground = new Image((new File("images/dirt_0_new.png")).toURI().toString());
        potion = new Image((new File("images/bubbly.png")).toURI().toString());
        sword = new Image((new File("images/greatsword_1_new.png")).toURI().toString());
        bow = new Image((new File("images/bow.png")).toURI().toString());
        quest = new Image((new File("images/exit.png")).toURI().toString());

        // Setting images
        potionImg.setImage(potion);
        swordImg.setImage(sword);
        bowImg.setImage(bow);
        questImg.setImage(quest);

        // Setting quests HUD
        questList.setText(getQuestsStr());
        questList.setShowDelay(Duration.millis(100));
        Tooltip.install(questImg, questList);
        ArrayList<IntegerProperty> uses = player.getInventoryStatus();

        // Setting inventory HUD
        potionVal.textProperty().bind(uses.get(0).asString());
        swordVal.textProperty().bind(uses.get(1).asString());
        bowVal.textProperty().bind(uses.get(2).asString());

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        // Adding rest of entities
        for (ImageView entity : initialEntities) {
            squares.getChildren().add(entity);
        }
    }

    /**
     * Called on death or level completion to return to menu
     */
    public void gameEnd() {
        mainMenu.start();
    }

    @FXML
    /**
     * Handles main menu action
     * @param event Click
     */
    public void handleReturnPress(ActionEvent event) {
        mainMenu.start();
    }

    @FXML
    /**
     * Handles player movement
     * @param event Player action key
     */
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                player.moveUp();
                nextTurnPlayer2();
                break;
            case DOWN:
                player.moveDown();
                nextTurnPlayer2();
                break;
            case LEFT:
                player.moveLeft();
                nextTurnPlayer2();
                break;
            case RIGHT:
                player.moveRight();
                nextTurnPlayer2();
                break;
            case M:
                player.fireRanged();
                nextTurnPlayer2();
                break;
            case W:
                if (playerCoop != null) {
                    playerCoop.moveUp();
                    nextTurnPlayer1();
                }
                break;
            case A:
                if (playerCoop != null) {
                    playerCoop.moveLeft();
                    nextTurnPlayer1();
                }
                break;
            case S:
                if (playerCoop != null) {
                    playerCoop.moveDown();
                    nextTurnPlayer1();
                }
                break;
            case D:
                if (playerCoop != null) {
                    playerCoop.moveRight();
                    nextTurnPlayer1();
                }
                break;
            case F:
                if (playerCoop != null) {
                    playerCoop.fireRanged();
                    nextTurnPlayer1();
                }
                break;
            case R:
                mainMenu.controllerRestart();
                break;
            default:
                break;
        }

        // Updating board state
        moveEnemies();
        checkPlayersStatus();
        checkFinishedGame();
    }

    /**
     * Notifies the player 1 to go to the next turn
     */
    private void nextTurnPlayer1() {
        if (dungeon.firstPlayerExists()) {
            player.nextTurn();
        }
    }

    /**
     * Notifies the player 2 to go to the next turn
     */
    private void nextTurnPlayer2() {
        if (dungeon.secondPlayerExists()) {
            playerCoop.nextTurn();
        }
    }

    /**
     * Makes the enemies move
     */
    private void moveEnemies() {
        // For all enemies
        for (Enemy e : enemies) {
            // Move
            e.move();
        }
    }

    /**
     * Used by the player to remove an enemy
     * @param e Enemy to be removed
     */
    public void killEnemy(Enemy e) {
        // Removing enemy
        enemies.remove(e);
        dungeon.removeEntity(e);
        e.attacked().set(false);

        // Logging enemy death
        dungeon.logKill();
    }

    /**
     * Determines what actions to take for current turn
     * depending on what entities are in the same tile as the player
     */
    private void checkPlayersStatus() {
        // Obtaining players
        ArrayList<Player> players = dungeon.getPlayers();

        // For all players
        for (Player p : players) {
            // Check if they have been attacked
            for (Enemy e : new ArrayList<Enemy>(enemies)) {
                if (e.getX() == p.getX() && e.getY() == p.getY()) {
                    if (e.readyToAttack()) {
                        if (p.attacked()) {
                            killEnemy(e);
                        }
                    }
                }
            }

            // Checking for potential items to pickup
            for (PickUp i : itemPickUps) {
                if (i.checkTilesCoincide(p.getX(), p.getY())) {
                    player.pickUpItem(i);
                }
            }
        }
    }

    /**
     * Checks if the level has been completed and if it is, goes back to the
     * main menu
     */
    private void checkFinishedGame() {
        if (dungeon.gameFinished()) {
            mainMenu.start();
        }
    }

    /**
     * Sets the main menu
     * @param menu Main menu to go back to
     */
    public void setMenu(TitleScreen menu) {
        this.mainMenu = menu;
    }

    /**
     * Sets the log for the dungeon controller
     * @param log Log of the game
     */
    public void setLog(Log log) {
        dungeon.setLog(log);
    }

    /**
     * Obtains the quests of the game
     * @return Quests as a string
     */
    private String getQuestsStr() {
        String questStr = "";
        for (Mission m : player.getQuests()) {
            questStr = questStr + m.descript() + "\n";
        }

        return questStr;
    }
}
