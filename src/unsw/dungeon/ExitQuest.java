package unsw.dungeon;

import java.util.ArrayList;

/**
 * Exit quest to complete the dungeon
 */
public class ExitQuest implements Mission {
    private Dungeon dungeon;

    /**
     * Initalises a quest
     * @param dungeon
     */
    public ExitQuest(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    /**
     * Checks if the exit quest is complete
     * @return Completion status
     */
    public Boolean complete() {
        // Obtaining dungeon environment information
        Exit exit = dungeon.getExit();
        ArrayList<int[]> coordinates = dungeon.getPlayersCoordinates();

        // Checking if a player has finished the game
        for (int[] c : coordinates) {
            if (c[0] == exit.getX() && c[1] == exit.getY()) {
                return true;
            }
        }

        // Mission not complete
        return false;
    }

    /**
     * Transforms exit quest into text
     */
    public String descript() {
        return "Reach the exit";
    }
}
