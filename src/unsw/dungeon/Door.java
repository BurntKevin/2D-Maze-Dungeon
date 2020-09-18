package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Used to block off sections of the map
 */
public class Door extends Entity {
    private int keyID;
    private boolean locked; 
    private SimpleBooleanProperty view;
    private SimpleBooleanProperty highlight;

    /**
     * Initalises a door
     * @param x x-coordinate
     * @param y y-coordinate
     * @param keyID Corresponding key which opens the door
     */
    public Door(int x, int y, int keyID) {
        super(x, y);
        this.keyID = keyID;
        this.locked = true;
        this.view = new SimpleBooleanProperty(true);
        this.highlight = new SimpleBooleanProperty(false);
    }

    /**
     * Attempts to unlock a door
     * @param currKeyID Integer of potential corresponding key
     * @return boolean Indicating whether door was opened successfully
     */
    public boolean attemptUnlock(Key key) {
        // Checking if the key and door matches
        if (keyID == key.getKeyID()) {
            // Successfully opened door
            locked = false;
            doorOpened().set(false);
            return true;
        }

        // Key does not match door
        return false;
    }

    /**
     * Opens the door
     * @return
     */
    public SimpleBooleanProperty doorOpened() {
        return view;
    }

    /**
     * Checks whether the door is open
     */
    public boolean checkOpen() {
        return !locked;
    }

    /**
     * Obtains the door's corresponding key id
     * @return Key id
     */
    public int getKeyId() {
        return keyID;
    }

    /**
     * Used to highlight the door once a player picks up its corresponding
     * key for ease of use
     * @return
     */
    public BooleanProperty highlight() {
        return highlight;
    }
}
