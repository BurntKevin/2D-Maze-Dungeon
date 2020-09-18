package unsw.dungeon;

/**
 * Creates a general interface for enemy movement
 */
public interface MovementType {
    /**
     * Movement of enemy
     * @param x Starting x-coordinate
     * @param y Starting y-coordinate
     * @return Displacement from start position ([x displacement, y displacement])
     */
    public int[] move(int x, int y);
}
