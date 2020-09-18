package unsw.dungeon;

/**
 * Exit quest to complete the dungeon
 */
public class TreasureQuest implements Mission {
    private Dungeon dungeon;

    /**
     * Initalises a quest
     * @param dungeon
     */
    public TreasureQuest(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    /**
     * Checks if the quest is complete
     * @return Question completion status
     */
    public Boolean complete() {
        return dungeon.getTreasureLeft() == 0;
    }

    /**
     * Transforms the quest into a string
     * @return Quest as a string
     */
    public String descript() {
        return "Collect all treasure";
    }
}
