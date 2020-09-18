package unsw.dungeon;

/**
 * Boulder which is placed around the map
 */
public class Boulder extends Entity implements Obstacle {
    /**
     * Initalises a boulder onto the board
     * @param x Start x Coordinate
     * @param y Start y Coordinate
     */
    public Boulder(int x, int y) {
        super(x, y);
    }

    /**
     * Attempts to push an object
     * @param next Entity to be pushed
     * @param moveDir Direction which the entity is pushed
     * @return Whether the push was completed
     */
    public boolean attemptPush(Entity next, String moveDir) {
        // Checking whether the push can be completed
        if (next instanceof Obstacle || next instanceof Door || next instanceof Portal || next instanceof Exit) {
            return false;
        }

        // Successfully pushing the entity
        if (moveDir.equals("Left")) {
            x().set(getX() - 1);
        } else if (moveDir.equals("Right")) {
            x().set(getX() + 1);
        } else if (moveDir.equals("Up")) {
            y().set(getY() - 1);
        } else if (moveDir.equals("Down")) {
            y().set(getY() + 1);
        }
        return true;
    }
}
