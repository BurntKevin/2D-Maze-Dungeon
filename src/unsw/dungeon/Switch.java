package unsw.dungeon;

import java.util.ArrayList;

/**
 * Used as a puzzle piece which is activated when under a boulder
 */
public class Switch extends Entity {
    /**
     * Initalising switch
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Switch(int x, int y) {
        super(x, y);
    }

    /**
     * Checks if a switch is activated
     * @param currBoulders
     * @return
     */
    public boolean checkActivated(ArrayList<Boulder> currBoulders) {
        // For all boulders
        for (Boulder b : currBoulders) {
            // Check if they match the switch
            if (b.getX() == getX() && b.getY() == getY()) {
                // Switch is activated
                return true;
            }
        }

        // Switch is not activated
        return false;
    }
}
