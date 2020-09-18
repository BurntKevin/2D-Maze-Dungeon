package unsw.dungeon;

import javafx.beans.property.SimpleBooleanProperty;

/**
 * Enemy in the Dungeon
 */
public abstract class Enemy extends Entity {
    private int speed;
    private int chargedSpeed;
    private MovementType movementType;
    private SimpleBooleanProperty view;

    /**
     * Initalises enemy
     * @param x x-coordinate
     * @param y y-coordinate
     * @param speed Speed of the enemy
     * @param movementType How the enemy moves
     */
    public Enemy(int x, int y, int speed, MovementType movementType) {
        super(x, y);
        this.speed = speed;
        this.chargedSpeed = 0;
        this.movementType = movementType;
        this.view = new SimpleBooleanProperty(true);
    }

    /**
     * Gets the enemy's x-position
     */
    public int getX() {
        return super.getX();
    }

    /**
     * Gets the enemy's y-position
     */
    public int getY() {
        return super.getY();
    }

    /**
     * Moves the enemy
     */
    public void move() {
        // Updating to new turn
        chargedSpeed += speed;
        
        // Checking if a move is required
        while (chargedSpeed >= 100) {
            int[] shift = movementType.move(super.getX(), super.getY());
            super.x().set(super.getX() + shift[0]);
            super.y().set(super.getY() + shift[1]);
            chargedSpeed -= 100;
        }
    }

    /**
     * Removes the player from front end UI
     * @return SimpleBooleanProeprty
     */
    public SimpleBooleanProperty attacked() {
        return view;
    }

    /**
     * Checks if the enemy is ready to attack
     * @return Ready status
     */
    public boolean readyToAttack() {
        return view.getValue();
    }
}
