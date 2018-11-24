import java.util.LinkedList;
import java.util.Queue;

/**
 * BFS algorithm uses a Queue as it's open list.
 * The queue allows it to develop each node's neiboghrs before their successors.
 */
public class BFS extends Algorithm {
    
    /**
     * Constructor.
     * @param size - the board size
     * @param initState - the matrix in a string form
     */
    public BFS(int size, String initState) {
        super(size, initState);
    }
    
    @Override
    public Node Search(MovesLogic logic) {
        // we use a queue as our data structure for open list
        Queue<Node> openList = new LinkedList<>();
        openList.add(this.initialState);
        while (!openList.isEmpty()) {
            Node current = openList.remove();
            this.developedNodes++;
            // if we reached the goal state, we return it
            if (current.equals(goalState)) {
                this.goalState = current;
                return current;
            }
            // add all the current node's successors to the queue
            openList.addAll(logic.GetPossibleMoves(current));
        }
        return null;
    }

    @Override
    public int Cost() {
        // as requested
        return 0;
    }

}
