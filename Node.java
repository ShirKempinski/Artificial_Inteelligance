/**
 * Node is the data structure of the graph, that represent a specific state of the puzzle.
 * It holds it's state as a matrix, it's parent, this size of the matrix, the operator that led to it, and it's depth.
 */
public class Node {
    
    private int[][] stateMatrix;
    private int size;
    private Node parent;
    private Character operator;
    private int depth;
    private long time;
    
    /**
     * Constructor.
     * @param state - the matrix of tiles
     * @param size - the rows' and columns' size
     * @param parent - the previous node
     * @param operation - that led us to this state
     * @param depth - in the graph
     */
    public Node(int[][] state, int size, Node parent, Character operation, int depth) {
        this.operator = operation;
        this.parent = parent;
        this.size = size;
        this.stateMatrix = state;
        this.depth = depth;
    }

    /**
     * getStateMatrix
     * @return the state matrix
     */
    public int[][] getStateMatrix() {
        return this.stateMatrix;
    }

    /**
     * GetSize
     * @return the matrix's size
     */
    public int GetSize() {
        return this.size;
    }
    
    /**
     * getParent
     * @return the node's parent
     */
    public Node getParent() {
        return this.parent;
    }

    /**
     * getOperator
     * @return the node's operator
     */
    public char getOperator() {
        return this.operator;
    }
    
    /**
     * getDepth
     * @return the node's depth
     */
    public int getDepth() {
        return this.depth;
    }
    
    /**
     * setTime set the node's creation time
     * @param t the time
     */
    public void setTime(long t) {
        this.time = t;
    }
    
    /**
     * getTime
     * @return the node's creation time
     */
    public long getTime() {
        return this.time;
    }
    
    @Override
    public String toString() {
        // if the operator is null, this is the root of the graph and there was no operation that led to it,
        // so the string of it must be empty.
        if (this.operator == null)
            return "";
        return this.operator.toString();
    }
    
    /**
     * this method compares the nodes by their state matrix.
     */
    @Override
    public boolean equals(Object other) {
        Node n = (Node) other;
        int[][] otherMatrix = n.getStateMatrix();
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.stateMatrix[i][j] != otherMatrix[i][j])
                    return false;
            }
        }
        return true;
    }
}
