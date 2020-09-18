package unsw.dungeon;

import java.util.ArrayList;

/**
 * Exit of dungeon
 */
public class Exit extends Entity {
    private ArrayList<Mission> quests;

    /**
     * Initalises Exit
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Exit(int x, int y) {
        super(x, y);
        quests = new ArrayList<Mission>();
        quests.add(AllQuests.getInstance());
    }

    /**
     * Adds a mission for an exit
     * @param mission Mission
     */
    public void addMission(Mission mission) {
        quests.add(mission);
    }

    /**
     * Add sets of missions for an exit
     * @param mission Mission
     */
    public void addMission(ArrayList<Mission> mission) {
        for (Mission m : mission) {
            addMission(m);
        }
    }

    /**
     * Checks if a player is allowed to exit dungeon
     */
    public boolean enter() {
        // Checking if sufficient quests are satisfised
        for (Mission m : quests) {
            if (!m.complete()) {
                // Game not completed yet
                return false;
            }
        }

        // Game has been completed
        return true;
    }
}
