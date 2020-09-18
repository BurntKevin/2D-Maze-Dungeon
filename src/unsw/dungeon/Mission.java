package unsw.dungeon;

/**
 * Used as a general interface for quests
 */
public interface Mission {
    /**
     * Checks if the mission has been complete
     * @return Complete status of mission (Boolean)
     */
    public Boolean complete();

    /**
     * Turns the mission into a readable string
     * @return Mission as string
     */
    public String descript();
}
