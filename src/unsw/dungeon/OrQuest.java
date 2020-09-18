package unsw.dungeon;

import java.util.ArrayList;

/**
 * Quest where only one mission has to be fulfiled
 */
public class OrQuest implements Mission {
    private ArrayList<Mission> quests;

    /**
     * Initalise an array of quests
     * @param quests
     */
    public OrQuest(ArrayList<Mission> quests) {
        this.quests = quests;
    }

    /**
     * Initalise an array of quests
     */
    public OrQuest() {
        this.quests = new ArrayList<Mission>();
    }

    /**
     * Add a quest to the list of possible quests
     * @param quest
     */
    public void addQuest(Mission quest) {
        quests.add(quest);
    }

    /**
     * Check whether the mission is complete
     */
    public Boolean complete() {
        // For all missions
        for (Mission m : quests) {
            // Check if they are complete
            if (m.complete() == true) {
                // Found a mission which was complete
                return true;
            }
        }

        // Did not find a mission which was complete
        return false;
    }

    /**
     * Turns the quest into a string
     */
    public String descript() {
        // Starting string
        String descriptStr = "";

        // For all quests
        for (Mission m : quests) {
            if (descriptStr.equals("")) {
                // If it is the first quest
                descriptStr = m.descript();
            } else {
                // Not the first quest
                descriptStr = descriptStr + " OR " + m.descript();
            }
        }

        return descriptStr;
    }
}
