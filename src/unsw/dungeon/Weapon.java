package unsw.dungeon;

/**
 * General weapon class for weapons
 */
public abstract class Weapon implements Item {
    private int usesLeft;

    /**
     * Used to attack
     * @return Success of attack
     */
    public void useWeapon() {
        usesLeft--;
    }

    /**
     * Checks if a weapon is usable
     * @return Status of weapon (boolean)
     */
    public boolean checkWeaponUsable() {
        if (usesLeft <= 0) {
            return false;
        }
        return true;
    }
}
