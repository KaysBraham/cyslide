package src;

public class Node {
    private final Level state;
    private final Node parent;
    private int costSoFar;
    private int estimatedCost;

    Node(Level state, Node parent, int costSoFar, int estimatedCost) {
        this.state = state;
        this.parent = parent;
        this.costSoFar = costSoFar;
        this.estimatedCost = estimatedCost;
    }

    public int getCostSoFar() {
        return costSoFar;
    }

    public void setCostSoFar(int costSoFar) {
        this.costSoFar = costSoFar;
    }

    public int getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(int estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public Level getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }
}