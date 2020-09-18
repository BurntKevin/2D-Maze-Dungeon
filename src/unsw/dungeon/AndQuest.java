package unsw.dungeon;

import java.util.ArrayList;

/**
 * A type of quest where all sub-quests need to be completed
 */
public class AndQuest implements Mission {
    private ArrayList<Mission> quests;

    /**
     * Initalises AndQuest with missions to complete
     * @param quests Missions to be done
     */
    public AndQuest(ArrayList<Mission> quests) {
        this.quests = quests;
    }

    /**
     * Initalise an array of quests
     */
    public AndQuest() {
        this.quests = new ArrayList<Mission>();
    }

    /**
     * Adds a quest to the list of quests to complete
     * @param quest Mission to be done
     */
    public void addQuest(Mission quest) {
        quests.add(quest);
    }

    /**
     * Checks if the quest has been complete
     */
    public Boolean complete() {
        for (Mission m : quests) {
            if (!m.complete()) {
                return false;
            }
        }
        return true;
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
                descriptStr = descriptStr + " AND " + m.descript();
            }
        }

        return descriptStr;
    }
}
