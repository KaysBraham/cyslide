package src;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Represents a puzzle solver for a game.
 */
public class PuzzleSolver {

    /**
     * Generates the neighbor nodes for a given parent node.
     *
     * @param parent The parent node.
     * @return The list of neighbor nodes.
     */
    public static List<Node> generateNeighbors(Node parent) {
        List<Node> neighbors = new ArrayList<>();
        for (int i = 0; i < parent.getState().getTiles().length; i++) {
            for (int j = 0; j < parent.getState().getTiles()[i].length; j++) {
                if (parent.getState().getTiles()[i][j].getValue() == 0) {
                    if (parent.getState().isMoveValid(i, j, i + 1, j)) {
                        Level newLevel = parent.getState().copy();
                        newLevel.swapTile(i, j, i + 1, j);
                        Node newNeighbor = new Node(newLevel, parent, parent.getCostSoFar(),calculateManhattanDistance(newLevel));
                        neighbors.add(newNeighbor);
                    }
                    if (parent.getState().isMoveValid(i, j, i, j + 1)) {
                        Level newLevel = parent.getState().copy();
                        newLevel.swapTile(i, j, i, j + 1);
                        Node newNeighbor = new Node(newLevel, parent, parent.getCostSoFar(),calculateManhattanDistance(newLevel));
                        neighbors.add(newNeighbor);
                    }
                    if (parent.getState().isMoveValid(i, j, i - 1, j)) {
                        Level newLevel = parent.getState().copy();
                        newLevel.swapTile(i, j, i - 1, j);
                        Node newNeighbor = new Node(newLevel, parent, parent.getCostSoFar(), calculateManhattanDistance(newLevel));
                        neighbors.add(newNeighbor);
                    }
                    if (parent.getState().isMoveValid(i, j, i, j - 1)) {
                        Level newLevel = parent.getState().copy();
                        newLevel.swapTile(i, j, i, j - 1);
                        Node newNeighbor = new Node(newLevel, parent, parent.getCostSoFar(), calculateManhattanDistance(newLevel));
                        neighbors.add(newNeighbor);
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Solves the puzzle given an initial state.
     *
     * @param initialState The initial state of the puzzle.
     * @return True if a solution is found, false otherwise.
     */
    public static boolean solvePuzzle(Level initialState) {
        Node initialNode = new Node(initialState, null, 0, calculateManhattanDistance(initialState));
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.getCostSoFar() + n.getEstimatedCost()));
        Set<Node> closedSet = new LinkedHashSet<>();

        openSet.add(initialNode);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();
            closedSet.add(currentNode);

            if (isGoalState(currentNode.getState())) {
                // Solution trouvée, afficher le chemin ou effectuer les actions nécessaires
                // selon votre implémentation spécifique
                System.out.println("Solution found!");
                for (Node node : closedSet){
                    PuzzleGame.solvingMoves.add(node.getState().getTiles());
                    System.out.println("Etape");
                    node.getState().print();
                }
                return true;
            }

            List<Node> neighbors = generateNeighbors(currentNode);

            for (Node neighbor : neighbors) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int newCostSoFar = currentNode.getCostSoFar() + 1;
                if (openSet.contains(neighbor) && newCostSoFar < neighbor.getCostSoFar()) {
                    openSet.remove(neighbor);
                }

                if (!openSet.contains(neighbor)) {
                    neighbor.setCostSoFar(newCostSoFar);
                    neighbor.setEstimatedCost(calculateManhattanDistance(neighbor.getState()));
                    openSet.add(neighbor);
                }
            }
        }

        // No solution
        System.out.println("No solution found.");
        return false;
    }

    /**
     * Checks if the given state corresponds to the desired final state.
     *
     * @param state The state to check.
     * @return True if the state is the goal state, false otherwise.
     */
    public static boolean isGoalState(Level state) {
        Level solvedLevel = PuzzleGame.getLevel(state.getLevelNumber());
        return state.equals(solvedLevel);
    }

    /**
     * Calculates the estimated cost of a level using the Manhattan distance heuristic.
     *
     * @param stateLevel The level to calculate the estimated cost for.
     * @return The estimated cost based on the Manhattan distance.
     */
    public static int calculateManhattanDistance(Level stateLevel) {
        Tile[][] stateTile = stateLevel.getTiles();
        int sum = 0;
        int nLine = stateTile.length;
        int nColumn = stateTile[0].length;
        for (int i = 0; i < nLine; i++) {
            for (int j = 0; j < nColumn; j++) {
                int temp = stateTile[i][j].getValue();
                if (temp == 0) {
                    Level solvedLevel = PuzzleGame.getLevel(stateLevel.getLevelNumber());
                    Tile[][] solvedTile = solvedLevel.getTiles();
                    int tempSum = Integer.MAX_VALUE;
                    for (int k = 0; k < nLine; k++) {
                        for (int l = 0; l < nColumn; l++) {
                            if (solvedTile[k][l].getValue() == 0) {
                                if (Math.abs(i - k) + Math.abs(j - l) < tempSum) {
                                    tempSum = Math.abs(i - k) + Math.abs(j - l);
                                }
                            }
                        }
                    }
                    sum += tempSum;
                } else {
                    Level solvedLevel = PuzzleGame.getLevel(stateLevel.getLevelNumber());
                    Tile[][] solvedTile = solvedLevel.getTiles();
                    outerLoop :
                    for (int k = 0; k < nLine; k++) {
                        for (int l = 0; l < nColumn; l++) {
                            if (solvedTile[k][l].getValue() == temp) {
                                sum += Math.abs(i - k) + Math.abs(j - l);
                                break outerLoop;
                            }
                        }
                    }
                }
            }
        }
        return (sum);
    }
}
