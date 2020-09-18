package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Checks if a Sword is in use
 */
public class Sword extends Weapon {
    private IntegerProperty usesLeft;
    
    /**
     * Default: initialize sword with no uses, as player does not yet have
     * a usable weapon
     */
    public Sword() {
        this.usesLeft = new SimpleIntegerProperty(0);
    }

    /**
     * Resets sword by adding two new uses
     */
    public void addNewSword() {
        assert usesLeft.getValue() == 0;
        usesLeft.setValue(5);
    }

    /**
     * Use weapon for attack if possible
     * @return boolean indicating whether attack suceeded
     */
    public boolean attemptMeleeAttack() {
        // Attempting attack
        if (checkWeaponUsable()) {
            // Weapon can be used
            usesLeft.setValue(usesLeft.getValue()-1);
            return true;
        }
        return false;
    }

    /**
     * Determine whether a usable weapon is equipped
     * @return boolean indicating whether another sword may be picked up
     */
    public boolean checkWeaponUsable() {
        return usesLeft.getValue() > 0;
    }

    /**
     * Returns the number of uses a sword has
     * @return Number of uses
     */
    public IntegerProperty getUsesProperty() {
        return usesLeft;
    }
}
