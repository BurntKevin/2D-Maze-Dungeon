package unsw.dungeon;

import java.util.ArrayList;

/**
 * Movement of enemy which goes for player unless they are buffed with a potion
 */
public class TowardsPlayerMovement implements MovementType {
    private Dungeon dungeon;

    /**
     * Initalise enemy movement
     * @param dungeon Dungeon which enemy is at
     */
    public TowardsPlayerMovement(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    /**
     * Returns a movement for the enemy
     * @param x Starting x-coordinate
     * @param y Starting y-coordinate
     */
    public int[] move(int x, int y) {
        if (dungeon.buffedPlayer()) {
            return awayPlayer(x, y);
        } else {
            return towardsPlayer(x, y);
        }
    }

    /**
     * Moves away from the player in a greedy fashion where it does not predict
     * future best placements but rather instantaneous ones
     * @param x Starting x-coordinate
     * @param y Starting y-coordinate
     * @return An array where the enemy should shift [x, y]
     */
    private int[] awayPlayer(int x, int y) {
        // Calculating all possible moves
        ArrayList<int[]> possibilities = new ArrayList<int[]>();
        possibilities.add(new int[] {-1, 0});
        possibilities.add(new int[] {1, 0});
        possibilities.add(new int[] {0, -1});
        possibilities.add(new int[] {0, 1});
        possibilities.add(new int[] {0, 0});

        // For all possibilities
        int furthest = 0;
        int[] bestEscape = new int[] {0, 0};
        for (int[] p : possibilities) {
            // Check if it is a possible location to be at
            if (dungeon.validEnemyTile(x + p[0], y + p[1])) {
                int steps = stepsToPlayer(x + p[0], y + p[1]);
                if (steps > furthest) {
                    bestEscape = p;
                    furthest = steps;
                }
            }
        }

        return bestEscape;
    }

    /**
     * Calculates the number of steps required to move to the player's current
     * position
     * @param x Starting x coordinate
     * @param y Starting y coordinate
     * @return Number of steps to player
     */
    public int stepsToPlayer(int x, int y) {
        // Setting up board
        Entity[][] boardStatus = dungeon.getBoard();
        int[] p1 = dungeon.getPlayerCoordinates();
        int[] p2 = dungeon.getPlayerCoopCoordinates();

        // Setting up initial coordinates
        ArrayList<int[]> queue = new ArrayList<int[]>();
        if (dungeon.firstPlayerExists()) {
            queue.add(new int[] {p1[0], p1[1], 0, 0});
        }
        if (dungeon.secondPlayerExists()) {
            queue.add(new int[] {p2[0], p2[1], 0, 0});
        }

        // While it is still possible to find the player
        while (queue.size() != 0) {
            int[] node = queue.remove(0);

            // Checking if point is in dungeon
            if (!dungeon.inDungeon(node[0], node[1])) {
                continue;
            }

            // Checking if the point is usable
            if (boardStatus[node[0]][node[1]] instanceof Obstacle || boardStatus[node[0]][node[1]] instanceof Door) {
                continue;
            }

            // Checking if the player has been found
            if (node[0] == x && node[1] == y) {
                return node[2];
            }

            // Finding next set of possible moves
            queue.add(new int[] {node[0] - 1, node[1], node[2] + 1}); // Left
            queue.add(new int[] {node[0] + 1, node[1], node[2] + 1}); // Right
            queue.add(new int[] {node[0], node[1] - 1, node[2] + 1}); // Up
            queue.add(new int[] {node[0], node[1] + 1, node[2] + 1}); // Down

            // Marking node as visited
            boardStatus[node[0]][node[1]] = new Wall(node[0], node[1]);
        }

        // Could not find a way to meet player
        return -1;
    }
    
    /**
     * Moves towards the person using the best possible move to the closest
     * player
     * @param x x-coordinate
     * @param y y-coordinate
     * @return An array where the enemy should shift [x, y]
     */
    private int[] towardsPlayer(int x, int y) {
        // Setting up board
        Entity[][] boardStatus = dungeon.getBoard();
        int[] p1 = dungeon.getPlayerCoordinates();
        int[] p2 = dungeon.getPlayerCoopCoordinates();

        // Setting up initial coordinates
        ArrayList<int[]> queue = new ArrayList<int[]>();
        if (dungeon.firstPlayerExists()) {
            queue.add(new int[] {p1[0], p1[1], 0, 0});
        }
        if (dungeon.secondPlayerExists()) {
            queue.add(new int[] {p2[0], p2[1], 0, 0});
        }

        // Calculating best move
        int[] move = {0, 0};
        while (queue.size() != 0) {
            int[] node = queue.remove(0);

            // Checking if point is in dungeon
            if (!dungeon.inDungeon(node[0], node[1])) {
                continue;
            }

            // Checking if the point is usable
            if (boardStatus[node[0]][node[1]] instanceof Obstacle || boardStatus[node[0]][node[1]] instanceof Door) {
                continue;
            }

            // Checking if the tile can be moved into by the enemy
            if (dungeon.validEnemyTile(x + node[2], y + node[3])) {
                if (node[0] == x && node[1] == y) {
                    move[0] = node[2];
                    move[1] = node[3];
                    break;
                }
            }

            // Finding next set of possible moves
            queue.add(new int[] {node[0] - 1, node[1], 1, 0}); // Left
            queue.add(new int[] {node[0] + 1, node[1], -1, 0}); // Right
            queue.add(new int[] {node[0], node[1] - 1, 0, 1}); // Up
            queue.add(new int[] {node[0], node[1] + 1, 0, -1}); // Down

            // Marking node as visited
            boardStatus[node[0]][node[1]] = new Wall(node[0], node[1]);
        }

        return move;
    }
}
