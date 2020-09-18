package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Potion for the user to use
 */
public class Potion implements Item {
    private IntegerProperty invTurnsLeft;

    /**
     * Initalises a potion
     */
    public Potion() { 
        invTurnsLeft = new SimpleIntegerProperty(0);
    }

    /**
     * Activating the potion
     */
    public void usePotion() {
        invTurnsLeft.setValue(10);
    }

    /**
     * Potion expiring
     */
    public void minusInvTimer() {
        if (invTurnsLeft.getValue() > 0) {
            invTurnsLeft.setValue(invTurnsLeft.getValue()-1);
        }
    }

    /**
     * Check if the potion has been fully used
     * @return Potion active (boolean)
     */
    public boolean checkPotionActive() {
        return invTurnsLeft.getValue() > 0;
    }

    /**
     * Returns the number of uses left
     * @return
     */
    public IntegerProperty getUsesProperty() {
        return invTurnsLeft;
    }
}
