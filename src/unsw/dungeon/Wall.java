package unsw.dungeon;

/**
 * Used to segment parts of the map which cannot be accessed
 */
public class Wall extends Entity implements Obstacle {
    /**
     * Initalise wall
     * @param x x-coordinate
     * @param y y-cooordinate
     */
    public Wall(int x, int y) {
        super(x, y);
    }
}
