package unsw.dungeon;

import javafx.beans.property.SimpleBooleanProperty;

/**
 * An item entity which co-ordinates and a visual icon,
 * may be picked up by the player
 */
public class PickUp extends Entity {
    private Item obtainedItem;
    private String itemID; // itemID := "Sword" | "Bow" | "Potion" | "Treasure"
    private SimpleBooleanProperty view;

    /**
     * Initalises an item which can be picked up onto the board
     * @param x x-coordinate
     * @param y y-coordinate
     * @param item Item which can be picked up
     * @param itemID Quick identifier of item
     */
    public PickUp(int x, int y, Item item, String itemID) {
        super(x, y);
        this.obtainedItem = item;
        this.itemID = itemID;
        this.view = new SimpleBooleanProperty(true);
    }

    /**
     * Obtains item from the temporary pickup object
     * @return Item which exists on tile (Item)
     */
    public Item getItemFromPickUp() {
        return obtainedItem;
    }

    /**
     * Obtain's the item type
     * @return Item's identification (String)
     */
    public String getItemID() {
        return itemID; 
    }

    /**
     * Removes a pickup from the front end
    */
    public SimpleBooleanProperty confirmPickedUp() {
        return view;
    }
}
