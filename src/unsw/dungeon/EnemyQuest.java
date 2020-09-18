package unsw.dungeon;

/**
 * Exit quest to complete the dungeon
 */
public class EnemyQuest implements Mission {
    private Dungeon dungeon;

    /**
     * Initalises a quest
     * @param dungeon
     */
    public EnemyQuest(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    /**
     * Checks if the quest is complete
     * @return Quest completion status
     */
    public Boolean complete() {
        return dungeon.enemiesRemaining() == 0;
    }

    /**
     * Turns the quest into a string
     * @return Quest as text
     */
    public String descript() {
        return "Defeat all enemies";
    }
}
