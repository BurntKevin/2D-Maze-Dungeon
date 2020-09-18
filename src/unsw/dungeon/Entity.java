package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 */
public class Entity {
    private IntegerProperty x, y;

    /**
     * Create an entity positioned in square (x,y)
     * @param x x-coordinate of entity
     * @param y y-coordinate of entity
     */
    public Entity(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
    }

    /**
     * Updates x position of entity
     * @return IntegerProperty
     */
    public IntegerProperty x() {
        return x;
    }

    /**
     * Updates y position of entity
     * @return IntegerProperty
     */
    public IntegerProperty y() {
        return y;
    }

    /**
     * Gets the y position of entity
     * @return y coordinate (int)
     */
    public int getY() {
        return y().get();
    }

    /**
     * Gets the x position of entity
     * @return x coordinate (int)
     */
    public int getX() {
        return x().get();
    }

    /**
     * @return If the given coordinates match tile of entity (Boolean)
     */
    public boolean checkTilesCoincide(int givenX, int givenY) {
        // Check if the tile is used
        if (x.intValue() == givenX && y.intValue() == givenY) {
            return true; 
        } 
        return false;
    }
}
