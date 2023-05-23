package src;
import java.util.*;

public class PuzzleSolver {

    // method to generate nodes
    private static List<Node> generateNeighbors(Node parent) {
        List<Node> neighbors = new ArrayList<>();
        for (int i = 0; i < parent.getState().getTiles().length; i++) {
            for (int j = 0; j < parent.getState().getTiles()[0].length; j++) {
                if (parent.getState().getTiles()[i][j].getValue() == 0) {
                    if (parent.getState().isMoveValid(i, j, i + 1, j)) {
                        Level newLevel = parent.getState().copy();
                        newLevel.swapTile(i, j, i + 1, j);
                        Node newNeighbor = new Node(newLevel, parent, parent.getCostSoFar(),0);
                        neighbors.add(newNeighbor);
                    }
                    if (parent.getState().isMoveValid(i, j, i, j + 1)) {
                        Level newLevel = parent.getState().copy();
                        newLevel.swapTile(i, j, i, j + 1);
                        Node newNeighbor = new Node(newLevel, parent, parent.getCostSoFar(),0);
                        neighbors.add(newNeighbor);
                    }
                    if (parent.getState().isMoveValid(i, j, i - 1, j)) {
                        Level newLevel = parent.getState().copy();
                        newLevel.swapTile(i, j, i - 1, j);
                        Node newNeighbor = new Node(newLevel, parent, parent.getCostSoFar(), 0);
                        neighbors.add(newNeighbor);
                    }
                    if (parent.getState().isMoveValid(i, j, i, j - 1)) {
                        Level newLevel = parent.getState().copy();
                        newLevel.swapTile(i, j, i, j - 1);
                        Node newNeighbor = new Node(newLevel, parent, parent.getCostSoFar(), 0);
                        neighbors.add(newNeighbor);
                    }

                }
            }
        }
        return neighbors;
    }

    // method of solving the  game
    public static void solvePuzzle(Level initialState) {
        Node initialNode = new Node(initialState, null, 0, calculateManhattanDistance(initialState));
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.getCostSoFar() + n.getEstimatedCost()));
        Set<Node> closedSet = new HashSet<>();

        openSet.add(initialNode);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();
            closedSet.add(currentNode);

            if (isGoalState(currentNode.getState())) {
                // Solution trouvée, afficher le chemin ou effectuer les actions nécessaires
                // selon votre implémentation spécifique
                System.out.println("Solution found!");
                return;
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
    }

    // Method to check if the given state corresponds to the desired final state
    public static boolean isGoalState(Level state) {
        Level solvedLevel = PuzzleGame.getLevel(state.getLevelNumber());
        for (int i = 0; i < state.getTiles().length; i++) {
            for (int j = 0; j < state.getTiles()[0].length; j++) {
                if(!(state.getTiles()[i][j].getValue()==solvedLevel.getTiles()[i][j].getValue())){
                    return false;
                }
            }
        }
        return true;
    }

    // Calculation of the estimated cost through the Manhattan distance
    private static int calculateManhattanDistance(Level stateLevel) {
        Tile[][] stateTile = stateLevel.getTiles();
        int sum = 0;
        int nLines = stateTile.length;
        int nColumn = stateTile[0].length;
        for (int i = 0; i < nLines; i++) {
            for (int j = 0; j < nColumn; j++) {
                int temp = stateTile[i][j].getValue();
                if (temp == 0) {
                    Level solvedLevel = PuzzleGame.getLevel(stateLevel.getLevelNumber());
                    Tile[][] solvedTile = solvedLevel.getTiles();
                    int tempSum = Integer.MAX_VALUE;
                    for (int k = 0; k < nLines; k++) {
                        for (int l = 0; l < nColumn; l++) {
                            if (solvedTile[k][l].getValue() == 0) {
                                if (Math.abs(i - k) + Math.abs(j - l) < tempSum) {
                                    tempSum = Math.abs(i - k) + Math.abs(j - l);
                                }
                            }
                        }
                    }
                    sum += tempSum;
                }
                else {
                    Level solvedLevel = PuzzleGame.getLevel(stateLevel.getLevelNumber());
                    Tile[][] solvedTile = solvedLevel.getTiles();
                    int compt1 = 0;
                    int compt2 = 0;
                    while (solvedTile[compt1][compt2].getValue() != temp && compt1 != nLines - 1) {
                        while (solvedTile[compt1][compt2].getValue() != temp && compt2 != nColumn - 1) {
                            compt1++;
                        }
                        compt2++;
                    }
                    sum += Math.abs(i - compt1) + Math.abs(j - compt2);
                }
            }
        }
        return (sum);
    }


}
