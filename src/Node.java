package src;

/**
 * Represents a node in a search algorithm.
 */
public class Node {
    private final Level state;
    private final Node parent;
    private int costSoFar;
    private int estimatedCost;

    /**
     * Constructs a new node with the specified state, parent node, cost so far, and estimated cost.
     *
     * @param state          The state of the node.
     * @param parent         The parent node.
     * @param costSoFar      The cost accumulated so far to reach this node.
     * @param estimatedCost  The estimated cost from this node to the goal.
     */
    Node(Level state, Node parent, int costSoFar, int estimatedCost) {
        this.state = state;
        this.parent = parent;
        this.costSoFar = costSoFar;
        this.estimatedCost = estimatedCost;
    }

    /**
     * Returns the cost accumulated so far to reach this node.
     *
     * @return The cost so far.
     */
    public int getCostSoFar() {
        return costSoFar;
    }

    /**
     * Sets the cost accumulated so far to reach this node.
     *
     * @param costSoFar The new cost so far.
     */
    public void setCostSoFar(int costSoFar) {
        this.costSoFar = costSoFar;
    }

    /**
     * Returns the estimated cost from this node to the goal.
     *
     * @return The estimated cost.
     */
    public int getEstimatedCost() {
        return estimatedCost;
    }

    /**
     * Sets the estimated cost from this node to the goal.
     *
     * @param estimatedCost The new estimated cost.
     */
    public void setEstimatedCost(int estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    /**
     * Returns the state of the node.
     *
     * @return The state of the node.
     */
    public Level getState() {
        return state;
    }

    /**
     * Returns the parent node.
     *
     * @return The parent node.
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Generates the hash code for the node based on its state.
     *
     * @return The hash code value.
     */
    @Override
	public int hashCode() {
		return getState().hashCode();
	}

    /**
     * Checks if this node is equal to another object.
     * The equality of 2 Nodes is based on their state.
     *
     * @param obj The object to compare.
     * @return True if the nodes are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        
        final Node otherNode = (Node) obj;

        return this.getState().equals(otherNode.getState());
    }

}