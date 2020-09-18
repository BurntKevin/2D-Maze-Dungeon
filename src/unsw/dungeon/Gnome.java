package unsw.dungeon;

/**
 * Gnome movement
 */
public class Gnome extends Enemy {
    /**
     * Initalise gnome
     * @param x x-coordinate
     * @param y y-coordinate
     * @param dungeon Dungeon which gnome is at
     */
    public Gnome(int x, int y, Dungeon dungeon) {
        super(x, y, 100, new TowardsPlayerMovement(dungeon));
    }

    /**
     * Gnome move
     */
    public void move() {
        super.move();
    }
}
