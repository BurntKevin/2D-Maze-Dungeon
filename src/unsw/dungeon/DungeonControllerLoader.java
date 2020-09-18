package unsw.dungeon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;
    
    // Images
    private Image playerImage;
    private Image buffedPlayerImage;
    private Image wallImage;
    private Image gnomeImage;
    private Image treasureImage;
    private Image swordImage;
    private Image exitImage;
    private Image houndImage;
    private Image camoGnomeImage;
    private Image closedDoorImage;
    private Image openDoorImage;
    private Image highlightDoorImage;
    private Image portalImage;
    private Image keyImage;
    private Image potionImage;
    private Image boulderImage;
    private Image switchImage;
    private Image bowImage;

    public DungeonControllerLoader(String filename) throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image((new File("images/human_new.png")).toURI().toString());
        buffedPlayerImage = new Image((new File("images/human_inv.png")).toURI().toString());
        wallImage = new Image((new File("images/brick_brown_0.png")).toURI().toString());
        treasureImage = new Image((new File("images/gold_pile.png")).toURI().toString());
        gnomeImage = new Image((new File("images/gnome.png")).toURI().toString());
        swordImage = new Image((new File("images/greatsword_1_new.png")).toURI().toString());
        exitImage = new Image((new File("images/exit.png")).toURI().toString());
        houndImage = new Image((new File("images/hound.png")).toURI().toString());
        camoGnomeImage = new Image((new File("images/camo_gnome.png")).toURI().toString());
        closedDoorImage = new Image((new File("images/closed_door.png")).toURI().toString());
        openDoorImage = new Image((new File("images/open_door.png")).toURI().toString());
        highlightDoorImage = new Image((new File("images/highlight_door.png")).toURI().toString());
        portalImage = new Image((new File("images/portal.png")).toURI().toString());
        keyImage = new Image((new File("images/key.png")).toURI().toString());
        potionImage = new Image((new File("images/bubbly.png")).toURI().toString());
        boulderImage = new Image((new File("images/boulder.png")).toURI().toString());
        switchImage = new Image((new File("images/pressure_plate.png")).toURI().toString());
        bowImage = new Image((new File("images/bow.png")).toURI().toString());
    }

    @Override
    public void onLoad(Player player) {
        ImageView view = new ImageView(playerImage);
        addEntity(player, view);
        trackStatus(player, view);
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        addEntity(wall, view);
    }

    @Override
    public void onLoad(Gnome gnome) {
        ImageView view = new ImageView(gnomeImage);
        addEntity(gnome, view);
        trackEnemyStatus(gnome, view);
    }

    @Override
    public void onLoad(Hound hound) {
        ImageView view = new ImageView(houndImage);
        addEntity(hound, view);
        trackEnemyStatus(hound, view);
    }
    
    @Override
    public void onLoad(CamoGnome camoGnome) {
        ImageView view = new ImageView(camoGnomeImage);
        addEntity(camoGnome, view);
        trackEnemyStatus(camoGnome, view);
    }

    @Override
    public void onLoad(Portal portal) {
        ImageView view = new ImageView(portalImage);
        addEntity(portal, view);
    }

    @Override
    public void onLoad(Door door) {
        ImageView view = new ImageView(closedDoorImage);
        addEntity(door, view);
        trackDoorStatus(door, view);
    }

    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        addEntity(exit, view);
    }

    @Override
    public void onLoad(Boulder boulder) {
        ImageView view = new ImageView(boulderImage);
        addEntity(boulder, view);
    }

    @Override
    public void onLoad(Switch plate) {
        ImageView view = new ImageView(switchImage);
        addEntity(plate, view);
    }

    @Override
    public void onLoad(PickUp item) {
        ImageView view = null;
        if (item.getItemFromPickUp() instanceof Sword) {
            view = new ImageView(swordImage);
        } else if (item.getItemFromPickUp() instanceof Treasure) {
            view = new ImageView(treasureImage);
        } else if (item.getItemFromPickUp() instanceof Potion) {
            view = new ImageView(potionImage);
        } else if (item.getItemFromPickUp() instanceof Key) {
            view = new ImageView(keyImage);
        } else if (item.getItemFromPickUp() instanceof Bow) {
            view = new ImageView(bowImage);
        }
        addEntity(item, view);
    }

    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entities.add(view);
    }

    private void addEntity(PickUp item, ImageView view) {
        trackPosition(item, view);
        trackStatus(item, view);
        entities.add(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
    }

    /**
     * Tracks the status of an item
     * @param item
     * @param node
     */
    private void trackStatus(PickUp item, Node node) {
        item.confirmPickedUp().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                node.setVisible(false);
            }
        });
    }

    /**
     * Tracks the status of the player
     * @param item
     * @param node
     */
    private void trackStatus(Player player, Node node) {
        player.death().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                node.setVisible(false);
            }
        });
        player.buffed().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                for (ImageView i : entities) {
                    if (i == node) {
                        if (newValue) {
                            // Setting player under potion effect
                            i.setImage(buffedPlayerImage);
                        } else {
                            // Setting player to normal status
                            i.setImage(playerImage);
                        }
                        break;
                    }
                }
            }
        });
    }

    /**
     * Track enemy status
     * @param enemy
     * @param node
     */
    private void trackEnemyStatus(Enemy enemy, Node node) {
        enemy.attacked().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                node.setVisible(false);
            }
        });
        
    }

    /**
     * Track door open status
     * @param door
     * @param node
     */
    private void trackDoorStatus(Door door, Node node) {
        door.doorOpened().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                for (ImageView i : entities) {
                    if (i == node) {
                        i.setImage(openDoorImage);
                        break;
                    }
                }
            }
        });
        door.highlight().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                for (ImageView i : entities) {
                    if (i == node) {
                        if (newValue) {
                            i.setImage(highlightDoorImage);
                        } else {
                            i.setImage(closedDoorImage);
                        }
                    }
                }
            }
        });
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController() throws FileNotFoundException {
        return new DungeonController(load(), entities);
    }
}
