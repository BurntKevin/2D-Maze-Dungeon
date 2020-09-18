package unsw.dungeon;

/**
 * Used to unlock doors
 */
public class Key implements Item {
    private boolean carryingKey;
    private int keyID;

    /**
     * An standard key which cannot open doors yet
     */
    public Key() {
        carryingKey = false; 
        keyID = -1;
    }

    /**
     * Changing keys to another key
     * @param id KeyID
     */
    public Key(int id) {
        carryingKey = true;
        keyID = id;
    }

    /**
     * Changing key to another Key
     * @param nextKey Key
     */
    public void equipKey(Key nextKey) {
        keyID = nextKey.getKeyID();
        carryingKey = true;
    }

    /**
     * Returns the current key
     * @return
     */
    public boolean checkCarryingKey() {
        return carryingKey;
    }

    /**
     * Returns the key's identification number
     * @return
     */
    public int getKeyID() {
        return keyID; 
    }

    /**
     * Sets the key as used
     */
    public void useKey() {
        carryingKey = false;
    }
}