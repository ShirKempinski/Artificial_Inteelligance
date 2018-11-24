import java.util.List;
import java.util.Stack;

/**
 * IDS algorithm uses a stack as it's open list, thus searching depth-first, but is also considering a depth limit
 * (that increases each iteration), so the search time would be optimal.
 */
public class IDS extends Algorithm {

    private int depthLimit;
    
    /**
     * Constructor.
     * @param size - board size
     * @param initState - the matrix in a string form
     */
    public IDS(int size, String initState) {
        super(size, initState);
        this.depthLimit = -1;
    }

    @Override
    public int Cost() {
        // the cost here is simply the last depth that was reached. 
        return this.depthLimit;
    }

    @Override
    public Node Search(MovesLogic logic) {
        Node result = null;
        // while the result is not found, increase the depth limit and keep searching
        while (result == null) {
            this.depthLimit++;
            this.developedNodes = 0;
            result = LimitedSearch(logic, this.depthLimit);
        }
        this.goalState = result;
        return result;
    }
    
    /**
     * Limited Search searches the graph depth-first, with a limit.
     * @param logic - the tiles game moving logic
     * @param limit - the current depth limit
     * @return the result node, or null if nothing was found
     */
    public Node LimitedSearch(MovesLogic logic, int limit) {
        // like DFS, we use a stack as our open list
        Stack<Node> openList = new Stack<Node>();
        openList.push(this.initialState);
        while (!openList.isEmpty()) {
            Node current = openList.pop();
            if (current.getDepth() > limit)
                continue;
            this.developedNodes++;
            if (current.equals(this.goalState)) {
                this.goalState = current;
                return current;
            }
            // since this is stack and not a queue, we must reverse the nodes order before pushing them
            openList = ReverseAndPush(openList, logic.GetPossibleMoves(current));
        }
        return null;
    }
    
    /**
     * ReverseAndPush reverses the given list of nodes using an other stack
     * @param openList - the list to push to
     * @param movesToReverse - the nodes to revers and push
     * @return the given open list, with the new nodes
     */
    private Stack<Node> ReverseAndPush(Stack<Node> openList, List<Node> movesToReverse) {
        // use a mid-stack to reverse the nodes' order 
        Stack<Node> midStack = new Stack<Node>();
        midStack.addAll(movesToReverse);
        // push them all to the open list
        while (!midStack.empty()) {
            Node move = midStack.pop();
            openList.push(move);
        }
        return openList;
    }
}
