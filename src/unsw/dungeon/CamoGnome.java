package unsw.dungeon;

/**
 * A gnome who is wearing a treasure costume
 */
public class CamoGnome extends Enemy {
    /**
     * Creates a camo gnome
     * @param x x-coordinate on board
     * @param y y-coordinate on board
     * @param dungeon Dungeon Dungeon which the gnome is placed
     */
    public CamoGnome(int x, int y, Dungeon dungeon) {
        super(x, y, 50, new TowardsPlayerMovement(dungeon));
    }

    /**
     * Allow the gnome to move
     */
    public void move() {
        super.move();
    }
}
