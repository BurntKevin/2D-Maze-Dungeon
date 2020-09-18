package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Allows the player to shoot long range
 */
public class Bow extends Weapon {
    /**
     * Uses left on the bow
     */
    private IntegerProperty usesLeft;
    /**
     * Reference to the dungeon for checking of hits
     */
    private Dungeon dungeon;

    /**
     * Empty initaliser for a broken bow
     */
    public Bow(Dungeon dungeon) {
        usesLeft = new SimpleIntegerProperty(0);
        this.dungeon = dungeon;
    }

    /**
     * Resets bow by adding two new uses
     * Typically called when a new bow is picked up
     */
    public void addNewBow() {
        usesLeft.setValue(2);
    }

    /**
     * Checks if a weapon is usable
     * @return Weapon usability status
     */
    public boolean checkWeaponUsable() {
        // Checks if a weapon is usable
        return usesLeft.getValue() > 0;
    }

    /**
     * Update weapon to be used
     */
    public void useWeapon() {
        // Reduce durability of weapon
        usesLeft.setValue(usesLeft.getValue() - 1);
    }

    /**
     * Fires the bow
     */
    public void fire(int x, int y, String direction) {
        // Checking if the bow can be used
        if (checkWeaponUsable()) {
            // Utilising weapon
            useWeapon();

            // Firing shot
            boolean hit = false;
            do {
                // Checking if the arrow hit a target
                Enemy enemy = dungeon.getEnemy(x, y);
                if (enemy != null) {
                    // Arrow hit a target
                    // Removing entity
                    dungeon.removeEntity(enemy);
                    enemy.attacked().set(false);

                    // Logging that the arrow hit
                    hit = true;
                    break;
                }

                // Moving arrow to the next step
                if (direction == "Up") {
                    y -= 1;
                } else if (direction == "Down") {
                    y += 1;
                } else if (direction == "Left") {
                    x -= 1;
                } else if (direction == "Right") {
                    x += 1;
                }
            } while (dungeon.validArrowLocation(x, y));

            // Logging the arrows activity
            dungeon.logRangedAttack(hit);
        } else {
            // Logging the mistakes of the player
            dungeon.logDryFireRanged();
        }
    }

    /**
     * Obtains the uses of the bow
     * @return Uses of the bow
     */
    public IntegerProperty getUsesProperty() {
        return usesLeft;
    }
}
