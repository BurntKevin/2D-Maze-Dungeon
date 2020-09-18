package unsw.dungeon;

/**
 * Creates a fast but randomly moving enemy
 */
public class Hound extends Enemy {
    /**
     * Initalising lost gnome
     * @param x x-coordinate
     * @param y y-coordinate
     * @param dungeon Dungeon which hound lives in
     */
    public Hound(int x, int y, Dungeon dungeon) {
        super(x, y, 200, new RandomMovement(dungeon));
    }

    /**
     * Make hound move
     */
    public void move() {
        super.move();
    }
}
