package unsw.dungeon;

/**
 * Exit quest to complete the dungeon
 */
public class BouldersQuest implements Mission {
    private Dungeon dungeon;

    /**
     * Initalises a quest
     * @param dungeon
     */
    public BouldersQuest(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    /**
     * Checks if the quest is complete
     * @return Completion status of quest
     */
    public Boolean complete() {
        return dungeon.allSwitchesActivated();
    }

    /**
     * Returns the quest as a string
     */
    public String descript() {
        return "Activate all switches";
    }
}
