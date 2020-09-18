package unsw.dungeon;

public class RandomMovement implements MovementType {
    private Dungeon dungeon;

    /**
     * Initalises random movement of enemy
     * @param dungeon Dungeon which enemy is at
     */
    public RandomMovement(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    /**
     * Moves enemy
     */
    public int[] move(int x, int y) {
        // Getting valid squares to move into
        Boolean[] moves = dungeon.validAdjacentMoves(x, y);

        // Counting number of valid moves
        int valid_moves = 0;
        for (Boolean m : moves) {
            if (m) {
                valid_moves++;
            }
        }

        int move = (int) (Math.random() * (valid_moves));

        // Getting index of where the selected move is
        int move_number = 0;
        for (Boolean m : moves) {
            if (m) {
                move--;
                if (move == -1) {
                    break;
                }
            }

            move_number++;
        }

        // Finding next
        if (move_number == 0) {
            return new int[] {-1, 0};
        } else if (move_number == 1) {
            return new int[] {1, 0};
        } else if (move_number == 2) {
            return new int[] {0, -1};
        } else if (move_number == 3) {
            return new int[] {0, 1};
        } else {
            // Trapped
            return new int[] {0, 0};
        }
    }
}
