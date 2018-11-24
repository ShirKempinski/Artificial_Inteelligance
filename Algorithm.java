/**
 * The super class of all search algorithms. It holds the initial and goal states, the board size
 * and a counter of the developed nodes. it is also responsible to return the final course to the goal and it's cost.
 */
public abstract class Algorithm {
    
    protected int boardSize;
    protected Node initialState;
    protected Node goalState;
    protected int developedNodes;
    
    /**
     * Constructor.
     * @param boardSize - the size of the state matrix for each node
     * @param initState - the initial state matrix, in string form
     */
    protected Algorithm(int boardSize, String initState) {
        this.developedNodes = 0;
        this.boardSize = boardSize;
        GenerateFirstNode(initState);
        GenerateGoalState();
    }
    
    /**
     * Find the Goal state while saving the solution.
     * @param logic - the tiles game moving logic
     * @return the goal state
     */
    public abstract Node Search(MovesLogic logic);
    
    /**
     * SolutionTrack starts with the goal Node and track down the course led to it, going from child to parent.
     * @return the solution string, composed of U, D, R and L.
     */
    public String SolutionTrack() {
        StringBuilder builder = new StringBuilder();
        Node current = this.goalState;
        while (!current.equals(this.initialState)) {
            // add the current's operator that led to it
            builder.append(current.getOperator());
            current = current.getParent();
        }
        // reverse the track so it's from start to end
        return builder.reverse().toString(); 
    }
    
    /**
     * the number of nodes that were developed during the running operation
     * @return that number
     */
    public int DevelopedNodesCount() {
        return this.developedNodes;
    }
    
    /**
     * abstract method, implemented differently between the algorithms
     * @return the cost or the depth of the final solution
     */
    public abstract int Cost();
    
    /**
     * Generates the first node from the string of the first state.
     * @param s - that string
     */
    private void GenerateFirstNode(String s) {
        int[][] matrix = new int[this.boardSize][this.boardSize];
        String[] values = s.split("-");
        int k = 0; // the values index
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                matrix[i][j] = Integer.parseInt(values[k]);
                k++;
            }
        }
        // ganerate the initial state with this matrix, set the parent node and the operator led to it to be 'null'.
        this.initialState = new Node(matrix, this.boardSize, null, null, 0);
    }

    /**
     * Generate the goal state of the board.
     */
    private void GenerateGoalState() {
        int[][] matrix = new int[this.boardSize][this.boardSize];
        int counter = 1;
        int i,j = 0;
        // fill the matrix with the right values
        for (i = 0; i < this.boardSize; i++) {
            for (j = 0; j < this.boardSize; j++) {
                matrix[i][j] = counter;
                counter++;
            }
        }
        // set the last cell to 0.
        matrix[this.boardSize - 1][this.boardSize - 1] = 0;
        this.goalState = new Node(matrix, this.boardSize, null, null, -1);
    }
}
