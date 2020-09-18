package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * The player entity
 */
public class Player extends Entity {
    private Dungeon dungeon;    
    private String facingDir; // facingDir := "Left" | "Right" | "Up" | "Down"
    private Sword melee;
    private Bow ranged;
    private Potion invStatus;
    private Key key;
    private SimpleBooleanProperty buff;
    private SimpleBooleanProperty view;
    private boolean alive;
    private ArrayList<Mission> quest;

    /**
     * Create a player positioned in square (x,y)
     * @param dungeon Dungeon which player lives in
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Player(Dungeon dungeon, ArrayList<Mission> quest, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.melee = new Sword();
        this.ranged = new Bow(dungeon);
        this.key = new Key();
        this.invStatus = new Potion();
        this.facingDir = "Right";
        this.buff = new SimpleBooleanProperty(false);
        this.view = new SimpleBooleanProperty(true);
        this.alive = true;
        this.quest = quest;
    }

    /**
     * Moves player vertically or horizontally from their current position
     * by one tile
     * @param x x-coordinate shift
     * @param y y-coordinate shift
     * @param direction Desired facing direction of new location
     */
    private void shift(int x, int y, String direction) {
        // Get next tile
        Entity nextTile = dungeon.getItem(getX() + x, getY() + y);

        // Moving player
        if (nextTile instanceof Door) {
            // Adjusting for door
            if (enterDoor((Door) nextTile)) {
                y().set(getY() + y);
                x().set(getX() + x);
            }
        } else if (nextTile instanceof Boulder) {
            // Adjusting for boulder
            Entity next = null;
            switch (direction) {
                case "Up":
                    next = dungeon.getItem(getX() + x, getY() + y - 1);
                    break;
                case "Down":
                    next = dungeon.getItem(getX() + x, getY() + y + 1);
                    break;
                case "Left":
                    next = dungeon.getItem(getX() + x - 1, getY() + y);
                    break;
                case "Right":
                    next = dungeon.getItem(getX() + x + 1, getY() + y);
                    break;
            }

            if (pushBoulder((Boulder) nextTile, next, direction)) {
                y().set(getY() + y);
                x().set(getX() + x);
            }
        }
        else if (getY() > 0 && !(nextTile instanceof Wall)) {
            // Adjusting for free slot
            y().set(getY() + y);
            x().set(getX() + x);

            // Accessing miscellaneous items
            if (nextTile instanceof PickUp) {
                pickUpItem((PickUp) nextTile);
            } else if (nextTile instanceof Portal) {
                portalTeleport((Portal) nextTile);
            }
        }

        // Updating player status
        facingDir = direction;
        nextTurn();

        // Updating log information
        dungeon.logStep();
    }

    /**
     * Causes the player to update its temporary effects
     */
    public void nextTurn() {
        // Updating potion
        if (!invStatus.checkPotionActive()) {
            buff.set(false);
        }
        invStatus.minusInvTimer();
    }

    /**
     * Player move up action
     */
    public void moveUp() {
        // Checking if player is still alive
        if (alive) {
            shift(0, -1, "Up");
        }
    }

    public boolean isAlive() {
        return alive;
    }

    /**
     * Player move down action
     */
    public void moveDown() {
        // Checking if player is still alive
        if (alive) {
            shift(0, 1, "Down");
        }
    }

    /**
     * Player move left action
     */
    public void moveLeft() {
        // Checking if player is still alive
        if (alive) {
            shift(-1, 0, "Left");
        }
    }

    /**
     * Moving the player right
     */
    public void moveRight() {
        // Checking if player is still alive
        if (alive) {
            shift(1, 0, "Right");
        }
    }

    /**
     * Player entering a door
     * @param nextDoor
     * @return Success of entering a door (Boolean)
     */
    private boolean enterDoor(Door nextDoor) {
        // Checking if a door can be used
        if (nextDoor.checkOpen()) {
            // Already unlocked
            return true;
        } else {
            // Locked
            if (nextDoor.attemptUnlock(key)) {
                // Door is unlocked
                key.useKey();

                return true;
            }
        }

        return false;
    }

    /**
     * Updates the position of the player
     * @param teleX New x coordinate
     * @param teleY New y coordinate
     */
    private void setPosition(int teleX, int teleY) {
        x().set(teleX);
        y().set(teleY);
    }

    /**
     * Updates the player's position after they have travelled through given portal
     * @param EntryPortal portal which is entered by player
     */
    private void portalTeleport(Portal EntryPortal) {
        // For all portals
        for (Portal p: dungeon.getPortals()) {
            // Check for a matching portal
            if (EntryPortal.checkPortalsMatch(p)) {
                // Teleport to corresponding portal
                setPosition(p.getEntryX(), p.getEntryY());
            }
        }
    }

    /**
     * Returns if the player is invincible
     * @return Player invincibility status
     */
    public boolean isInvincible() {
        return invStatus.checkPotionActive();
    }

    /**
     * Pushes a boulder
     * @param b Boulder
     * @param behind Entity behind
     * @param pushDir Pushing direction
     * @return Success of push (boolean)
     */
    public boolean pushBoulder(Boulder b, Entity behind, String pushDir) {
        return b.attemptPush(behind, pushDir);
    }

    /**
     * Fires a bow shot
     */
    public void fireRanged() {
        ranged.fire(super.getX(), super.getY(), facingDir);
    }

    /**
     * Used when the player is attacked
     * @return boolean whether player's counteratk was successful (false indicates game over)
     */
    public boolean attacked() {
        // Checking if the player can defend themselves
        if (invStatus.checkPotionActive()) {
            // Has a potion
            return true;
        } else if (melee.attemptMeleeAttack()) {
            // Has a weapon
            return true;
        }

        // Updating status of player as they died
        dungeon.logDeath();
        death().set(false);
        alive = false;

        return false;
    }

    /**
     * Removes a player from the front end
    */
    public SimpleBooleanProperty death() {
        return view;
    }

    /**
     * Pick up an item
     * @param item
     */
    public void pickUpItem(PickUp item) {
        // Obtaining item from wrapper
        Item curr = item.getItemFromPickUp();

        // Picking up item
        if (curr instanceof Sword) {
            if (!melee.checkWeaponUsable()) {
                // Able to pick up a new weapon
                melee.addNewSword();
                dungeon.logItem(item);
                dungeon.removeEntity(item);
                item.confirmPickedUp().set(false);
                dungeon.logSword();
            }
        } else if (curr instanceof Bow) {
            if (!ranged.checkWeaponUsable()) {
                // Able to pick up a new weapon
                ranged.addNewBow();
                dungeon.logItem(item);
                dungeon.removeEntity(item);
                item.confirmPickedUp().set(false);
                dungeon.logBow();
            }
        } else if (curr instanceof Potion) {
            if (!invStatus.checkPotionActive()) {
                invStatus.usePotion();
                dungeon.removeEntity(item);
                item.confirmPickedUp().set(false);
                buffed().set(true);
                dungeon.logPotion();
            }
        } else if (curr instanceof Key) {
            if (!key.checkCarryingKey()) {
                key.equipKey((Key) curr);
                dungeon.removeEntity(item);
                item.confirmPickedUp().set(false);
                dungeon.highlightDoors(key.getKeyID());
            }
        } else if (curr instanceof Treasure) {
            dungeon.logItem(item);
            dungeon.removeEntity(item);
            item.confirmPickedUp().set(false);
        }
    }

    /**
     * Returns the sword of the player
     */
    public Sword getSword() {
        return melee;
    }

    /**
     * Updates UI that the player is under the effect of a potation
     * @return Status of player under potion
     */
    public SimpleBooleanProperty buffed() {
        return buff;
    }

    /**
     * Obtain inventory information
     * @return Inventory
     */
    public ArrayList<IntegerProperty> getInventoryStatus() {
        // Obtaining inventory
        ArrayList<IntegerProperty> inven = new ArrayList<>();

        // Adding inventory items
        inven.add(invStatus.getUsesProperty());
        inven.add(melee.getUsesProperty());
        inven.add(ranged.getUsesProperty());

        return inven;
    }

    public ArrayList<Mission> getQuests() {
        return quest;
    }
}
