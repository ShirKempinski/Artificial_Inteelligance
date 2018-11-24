import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * AStar Algorithm uses priority queue, and compares the nodes by the sum of their cost and their approximated
 * distance from the goal.
 */
public class AStar extends Algorithm {
    
    /**
     * Constructor.
     * @param size - board size
     * @param initState - the matrix as a string
     */
    public AStar(int size, String initState) {
        super(size, initState);
    }

    @Override
    public int Cost() {
        // the cost here is simply the depth of the result node.
        return this.goalState.getDepth();
    }
    
    @Override
    public Node Search(MovesLogic logic) {
        // the priority of the queue is set by the Distane comperator
        PriorityQueue<Node> openList = new PriorityQueue<Node>(new Distance());
        openList.add(this.initialState);
        while (!openList.isEmpty()) {
            Node current = openList.remove();
            this.developedNodes++;
            if (current.equals(this.goalState)) {
                this.goalState = current;
                return current;
            }
            // add the successors of the developed node to the open list
            openList.addAll(logic.GetPossibleMoves(current));
        }
        return null;
    }

    /**
     * This Comperator implements f(n) = g(n) + h(n), such that g(n) is the node's depth,
     * and h(n) is the heuristic function is Manhattan's distance. It is an inner class beacause
     * it has no use outside of the AStar class.
     */
    private class Distance implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            // first, compare by the heuristic function
            int estimatedDistance1 = o1.getDepth() + Manhattan(o1);
            int estimatesDistance2 = o2.getDepth() + Manhattan(o2);
            if (estimatesDistance2 < estimatedDistance1) return 1;
            if (estimatesDistance2 > estimatedDistance1) return -1;
            // then, compare by the nodes creation time
            if (o2.getTime() < o1.getTime()) return 1;
            if (o2.getTime() > o1.getTime()) return -1;
            // then comare by the direction
            int rank1 = GetRank(o1);
            int rank2 = GetRank(o2);
            if (rank2 < rank1) return 1;
            if (rank2 > rank1) return -1;
            
            return 0;
        }

        /**
         * GetRank ranks the node by it's operator
         * @param node the node
         * @return the rank, highest for U and lowest for R, -1 if there was a mistake
         */
        private int GetRank(Node node) {
            Character operator = node.getOperator();
            if (operator.equals('U')) return 4;
            if (operator.equals('D')) return 3;
            if (operator.equals('L')) return 2;
            if (operator.equals('R')) return 1;
            return -1;
        }

        /**
         * Manhattan calculates the distance between a node's state and the goal state.
         * @param current node
         * @return numeric value of distance from goal state
         */
        private int Manhattan(Node current) {
            int distanceFromGoal = 0;
            int size = current.GetSize();
            // go over the matrix
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    // for each value in the matrix that's not 0
                    int val = current.getStateMatrix()[i][j];
                    if (val == 0)
                        continue;
                    // calculate it's distance from it's place in the goal state and add it to the sum
                    int rowGoal = Math.floorDiv((val - 1), size);
                    int columnGoal = (val - 1) % size;
                    distanceFromGoal += Math.abs(i - rowGoal) + Math.abs(j - columnGoal);
                }
            }
            return distanceFromGoal;
        }
    }
}
