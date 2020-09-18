package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {
    private int width, height;
    private List<Entity> entities;
    private Player player;
    private Player playerCoop;
    private Log log;

    /**
     * Initalises the Dungeon map
     * @param width Width of map
     * @param height Height of map
     */
    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<Entity>();
        this.player = null;
        this.playerCoop = null;
        this.log = new Log();
    }

    /**
     * Gets the width of the map
     * @return Width of map (int)
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the map
     * @return Height of map (int)
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the player of the dungeon
     * @return Player (Player)
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Obtains the second player of the dungeon
     * @return
     */
    public Player getPlayerCoop() {
        return playerCoop;
    }

    /**
     * Sets the new player of the dungeon
     * @param player Player (Player)
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Sets the new coop player of the dungeon
     * @param player Second player (Player)
     */
    public void setPlayerCoop(Player player) {
        this.playerCoop = player;
    }

    /**
     * Adds a new entity to the dungeon
     * @param entity Entity to add (Entity)
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * Removes an entity from the dungeon
     * @param entity Entity to remove (Entity)
     */
    public void removeEntity(Entity entity) {
        // Todo - Remove from front end interface
        entities.remove(entity);
    }

    /**
     * Obtains a potential interactable item from the map given its coordinates
     * @param x x-coordinate
     * @param y y-coordinate
     * @return Item on x, y
     */
    public Entity getItem(int x, int y) {
        // Checking all items
        for (Entity entity : entities) {
            // Checking if the item matches the desired coordinates
            if (entity.getX() == x && entity.getY() == y && ! (entity instanceof Switch)) {
                // Found item which can be interacted with
                return entity;
            }
        }

        // Could not find item
        return null;
    }

    /**
     * Obtains all enemies in the dungeon
     * @return List of enemies (ArrayList<Enemy>)
     */
    public ArrayList<Enemy> getEnemies() {
        // Creating a list of enemies
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();

        // For all entities
        for (Entity e : entities) {
            // Checking if the entity is an enemy
            if (e instanceof Enemy) {
                // Entity is an enemy
                enemies.add((Enemy) e);
            }
        }

        return enemies;
    }

    /**
     * Obtains portals
     * @return List of all portals (ArrayList<Portal>)
     */
    public ArrayList<Portal> getPortals() {
        // Initalising list of portals
        ArrayList<Portal> portals = new ArrayList<Portal>();

        // For all entities
        for (Entity e : entities) {
            // Checking if they are a portal
            if (e instanceof Portal) {
                // Found a portal
                portals.add((Portal) e);
            }
        }

        return portals;
    }

    /**
     * Obtains doors in dungeon
     * @return List of all doors (ArrayList<Door>)
     */
    public ArrayList<Door> getDoors() {
        // Initalising a list of doors
        ArrayList<Door> doors = new ArrayList<Door>();

        // For all entities
        for (Entity e : entities) {
            // Checking if they are a door
            if (e instanceof Door) {
                // Found a door
                doors.add((Door) e);
            }
        }

        return doors;
    }

    /**
     * Obtains the player's coordinates in a length 2 array
     * @return Coordinates ({x, y})
     */
    public int[] getPlayerCoordinates() {
        // First player does not exist
        if (!player.isAlive()) {
            return new int[] {-1, -1};
        }

        // Obtaining player's coordinates
        int[] coordinates = {player.getX(), player.getY()};

        return coordinates;
    }

    /**
     * Obtains the player's coordinates in a length 2 array
     * @return Coordinates ({x, y})
     */
    public int[] getPlayerCoopCoordinates() {
        // Second player does not exist
        if (playerCoop == null || !playerCoop.isAlive()) {
            return new int[] {-1, -1};
        }

        // Obtaining player's coordinates
        int[] coordinates = {playerCoop.getX(), playerCoop.getY()};

        return coordinates;
    }

    /**
     * Obtains all the players in the game currently which are still alive
     * and applicable
     * @return Alive players
     */
    public ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<Player>();
        if (firstPlayerExists()) {
            players.add(getPlayer());
        }
        if (secondPlayerExists()) {
            players.add(getPlayerCoop());
        }

        return players;
    }

    /**
     * Checks if the first player is still playable
     * @return Player one status
     */
    public boolean firstPlayerExists() {
        return player != null && player.isAlive();
    }

    /**
     * Checks if the second player is still playable
     * @return Player two status
     */
    public boolean secondPlayerExists() {
        return playerCoop != null && playerCoop.isAlive();
    }

    /**
     * Obtains all applicable player's coordinates
     * @return
     */
    public ArrayList<int[]> getPlayersCoordinates() {
        // Obtaining player coordinates
        int[] playerOne = getPlayerCoordinates();
        int[] playerTwo = getPlayerCoopCoordinates();

        // Obtaining player one's coordinates
        ArrayList<int[]> players = new ArrayList<int[]>();
        if (firstPlayerExists()) {
            players.add(playerOne);
        }

        // Obtaining player two's coordinates if applicable
        if (secondPlayerExists()) {
            players.add(playerTwo);
        }

        return players;
    }

    /**
     * Checks if a point is inside the dungeon
     * @param x x-coordinate
     * @param y y-coordinate
     * @return Whether the point is in the dungeon
     */
    public boolean inDungeon(int x, int y) {
        return 0 <= x && x < getWidth() && 0 <= y && y < getHeight();
    }

    /**
     * Checks for possible moves a player can do given a coordinate
     * @param x x-coordinate at dungeon
     * @param y y-coordinate at dungeon
     * @return Positions moveable: [moveLeft, moveRight, moveUp, moveDown]
     */
    public Boolean[] validAdjacentMoves(int x, int y) {
        // Starting location
        Boolean[] result = {x - 1 >= 0, x + 1 < width, y - 1 >= 0, y + 1 < height};

        // Checking all entities
        for (Entity entity : entities) {
            // Checking if there is an issue with it moving left or right
            if (entity instanceof Obstacle) {
                if (entity.getX() == x - 1 && entity.getY() == y) {
                    result[0] = false;
                } else if (entity.getX() == x + 1 && entity.getY() == y) {
                    result[1] = false;
                } else if (entity.getX() == x && entity.getY() == y - 1) {
                    result[2] = false;
                } else if (entity.getX() == x && entity.getY() == y + 1) {
                    result[3] = false;
                }
            }
        }

        return result;
    }

    /**
     * Obtains the dungeon as a 2D array
     * @return Dungeon as a 2D array (Entity[][])
     */
    public Entity[][] getBoard() {
        // Creating a board where every tile can be walked through
        Entity[][] board = new Entity[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = null;
            }
        }

        // Placing all entities
        for (Entity e: entities) {
            board[e.getX()][e.getY()] = e;
        }

        return board;
    }

    /**
     * Reports item activity occurring in the dungeon to a log
     * @param item Item which has been picked up
     */
    public void logItem(PickUp item) {
        log.logItem(item);
    }

    /**
     * Checks the remaining treasure in the Dungeon
     * @return Treasure remaining in the Dungeon
     */
    public int getTreasureLeft() {
        // Initalising treasure count
        int treasureCount = 0;

        // For all entities
        for (Entity e : entities) {
            // If it is a pickup item
            if (e instanceof PickUp) {
                // Turn the entity into a pickup object
                PickUp e_item = (PickUp) e;
                if (e_item.getItemFromPickUp() instanceof Treasure) {
                    // Item is a treasure
                    treasureCount++;
                }
            }
        }

        return treasureCount;
    }

    /**
     * Checks if a player is under effect of a potion
     * @return Player affected by potion
     */
    public Boolean buffedPlayer() {
        return player.isInvincible() || (secondPlayerExists() && playerCoop.isInvincible());
    }

    /**
     * Checks if a tile can be moved towards by a player
     * @param x x-coordinate
     * @param y y-coordinate
     * @return Availability of location for the player
     */
    public Boolean validPlayerTile(int x, int y) {
        // For all entities
        for (Entity e : entities) {
            // Checking if the tile can be moved towards
            if ((e instanceof Obstacle) && e.getX() == x && e.getY() == y) {
                // Cannot move into this tile
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a tile can be moved towards by an enemy
     * @param x x-coordinate
     * @param y y-coordinate
     * @return Availability of location for the player
     */
    public Boolean validEnemyTile(int x, int y) {
        // For all entities
        for (Entity e : entities) {
            // Checking if the tile can be moved towards
            if (e.getX() == x && e.getY() == y) {
                if (e instanceof Obstacle || e instanceof Enemy || e instanceof Door) {
                    // Cannot move into this tile
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if all switches are activated
     * @return Whether the switches are active or not
     */
    public Boolean allSwitchesActivated() {
        // Obtaining required data
        ArrayList<Boulder> boulders = getBoulders();
        ArrayList<Switch> switches = getSwitches();
        Boolean foundMatch = false;

        // Checking if all switches are activated
        for (Switch s : switches) {
            // Finding matching if possible
            for (Boulder b : boulders) {
                if (b.getX() == s.getX() && b.getY() == s.getY()) {
                    foundMatch = true;
                    break;
                }
            }

            // No match
            if (foundMatch != true) {
                return false;
            }
            foundMatch = false;
        }
        return true;
    }

    /**
     * Obtaining all switches
     * @return All switches in dungeon
     */
    public ArrayList<Switch> getSwitches() {
        // Initalising switches array
        ArrayList<Switch> switches = new ArrayList<Switch>();

        // Checking all entities in dungeon
        for (Entity e : entities) {
            // Adding all switches
            if (e instanceof Switch) {
                switches.add((Switch) e);
            }
        }

        return switches;
    }

    /**
     * Checks dungeon for all boulders
     * @return
     */
    public ArrayList<Boulder> getBoulders() {
        // Initalising all boulders
        ArrayList<Boulder> boulders = new ArrayList<Boulder>();

        // Checks all entities for boulders
        for (Entity e : entities) {
            // Adding boulders
            if (e instanceof Boulder) {
                boulders.add((Boulder) e);
            }
        }

        return boulders;
    }

    /**
     * Checks for all enemies left in the dungeon
     * @return Number of enemies remaining (int)
     */
    public int enemiesRemaining() {
        int count = 0;

        // Check all entities for enemies alive
        for (Entity e : entities) {
            if (e instanceof Enemy) {
                count++;
            }
        }

        return count;
    }

    /**
     * Obtain's the exit
     * @return Exit (Exit)
     */
    public Exit getExit() {
        // Check all entities for the exit
        for (Entity e : entities) {
            if (e instanceof Exit) {
                return (Exit) e;
            }
        }

        return null;
    }

    /**
     * Increments the time the player dry fired
     */
    public void logDryFireRanged() {
        log.logDryFireRanged();
    }

    /**
     * Increments the number of steps the player took
     */
    public void logStep() {
        log.logStep();
    }

    /**
     * Increments the number of deaths the player had
     */
    public void logDeath() {
        log.logDeath();
    }

    /**
     * Increments the number of kills the player had
     */
    public void logKill() {
        log.logKill();
    }

    /**
     * Increments the number of swords the player picked up
     */
    public void logSword() {
        log.logSword();
    }

    /**
     * Increments the number of bows the player picked up
     */
    public void logBow() {
        log.logBow();
    }

    /**
     * Increments the number of potions the player used
     */
    public void logPotion() {
        log.logPotion();
    }

    /**
     * Increments the number of bow shots the player used
     */
    public void logBowShot() {
        log.logBow();
    }

    /**
     * Adjusts the player's accuracy of hitting and missing
     * @param hit Whether the arrow hit a target
     */
    public void logRangedAttack(Boolean hit) {
        log.logRangedAtk(hit);
    }

    /**
     * Checks if an arrow can be placed in the tile
     */
    public boolean validArrowLocation(int x, int y) {
        for (Entity e : entities) {
            if (e.getX() == x && e.getY() == y && ! (e instanceof Enemy)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtains an enemy from the corresponding tile
     * @param x x coordinate
     * @param y y coordiante
     * @return Enemy at the location
     */
    public Enemy getEnemy(int x, int y) {
        // For all entities
        for (Entity e : entities) {
            // If an enemy is fount at the same coordinates
            if (e.getX() == x && e.getY() == y && e instanceof Enemy) {
                // Return enemy
                return (Enemy) e;
            }
        }

        // Could not find enemy
        return null;
    }

    /**
     * Sets the dungeon's log
     * @param log Log of the player's progress
     */
    public void setLog(Log log) {
        this.log = log;
    }

    /**
     * Obtains the player's inventory
     * @return Player's inventory
     */
    public ArrayList<IntegerProperty> getPlayerInventory() {
        return player.getInventoryStatus();
    }

    /**
     * Checks whether a game has been completed
     * @return Finished status
     */
    public boolean gameFinished() {
        return getPlayers().size() == 0 || exitReached();
    }

    /**
     * Checks whether the exit has been reached
     * @return Player reaached exit boolean
     */
    public boolean exitReached() {
        Exit exit = getExit();
        return exit.enter();
    }

    /**
     * Used as a way to highlight the doors of a corresponding key
     * @param Id Key id of door
     */
    public void highlightDoors(int Id) {
        // For all entities
        for (Entity e : entities) {
            // If it is a door
            if (e instanceof Door) {
                Door door = (Door) e;

                // Check if the key works with the door
                if (door.getKeyId() == Id) {
                    // Highlight door for player
                    door.highlight().set(true);
                }
            }
        }
    }
}
