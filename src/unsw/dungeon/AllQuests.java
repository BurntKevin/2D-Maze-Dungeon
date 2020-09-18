package unsw.dungeon;

import java.util.ArrayList;

/**
 * Easy reference to all quests for the game
 */
public class AllQuests implements Mission {
    private static AllQuests instance = null;
    private ArrayList<Mission> quests;

    /**
     * Creating AllQuests
     * @param quests All quests for game
     */
    public AllQuests(ArrayList<Mission> quests) {
        this.quests = quests;
    }

    /**
     * Creating instance of AllQuests and returns the result
     * @param quests All quests for game
     * @return AllQuests class
     */
    public static synchronized AllQuests createInstance(ArrayList<Mission> quests) {
        instance = new AllQuests(quests);
        return instance;
    }

    /**
     * Returns the AllQuests instance
     * @return AllQuests instance, can be null if uninitalised
     */
    public static synchronized AllQuests getInstance() {
        return instance;
    }

    /**
     * Checks if the quest has been completed
     * @return Quest completion as boolean
     */
    public Boolean complete() {
        // For all quests
        for (Mission m : quests) {
            // Check if they are complete
            if (!m.complete()) {
                // Quest not complete
                return false;
            }
        }

        // Quest is complete
        return true;
    }

    // Returns all quests from singleton
    public ArrayList<Mission> getQuests() {
        return quests;
    }

    // Transforms quests into string information
    public String descript() {
        // Starting string
        String descriptStr = "";

        // For all missions, add their corresponding string
        for (Mission m : quests) {
            // Creating string
            if (descriptStr.equals("")) {
                // First addition
                descriptStr = m.descript();
            } else {
                // Not first addition
                descriptStr = descriptStr + " " + m.descript();
            }
        }

        return descriptStr;
    }
}
