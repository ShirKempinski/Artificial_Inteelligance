import java.util.ArrayList;
import java.util.List;

/**
 * MovesLogic is responsible for the logic of the tiles game, specifically the movements of the tiles.
 * It holds the axes of the zero in a given state, and every time it's GetPossibleMoves() method
 * is being called, it should be re-initialized .
 */
public class MovesLogic {

    private int x0;
    private int y0;
    private int size;
    private int[][] currentState;
    
    /**
     * Constructor.
     */
    public MovesLogic() { }
    
    /**
     * GetPossibleMoves calculates the possible next moves of a given node with state.
     * @param current - the node
     * @return list of nodes that are possible moves.
     */
    public List<Node> GetPossibleMoves(Node current) { 
        // find the 0 axes
        SetZeroAxes(current);
        // find the optional directions
        List<Character> optionalDirections = GetOptionalDirections();
        // generate nodes out of them
        List<Node> moves = new ArrayList<Node>();
        for (int i = 0; i < optionalDirections.size(); i++)
            moves.add(GenerateMove(current, optionalDirections.get(i)));
        // set the moves' creation time
        long time = System.nanoTime();
        for (Node n : moves)
            n.setTime(time);
        return moves;
    }
    
    /**
     * SetZeroAxes finds the 0 axes
     * @param current the given node
     */
    private void SetZeroAxes(Node current) {
        this.size = current.GetSize();
        this.currentState = current.getStateMatrix();
        this.x0 = 0;
        this.y0 = 0;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j <this.size; j++) {
                if (this.currentState[i][j] == 0) {
                    this.x0 = j;
                    this.y0 = i;
                    break;
                }
            }
        }
    }

    /**
     * GenerateMove gets the previous node and a direction, and generates the next node
     * @param previous - parent node
     * @param direction - chosen direction
     * @return - the new node
     */
    private Node GenerateMove(Node previous, Character direction) {
        // initialize the new state to be as the parent's
        int[][] newState = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newState[i][j] = this.currentState[i][j];
            }
        }
        // acording to the direction, make the move
        if (direction == 'U') {
            newState[this.y0][this.x0] = this.currentState[this.y0 + 1][this.x0];
            newState[this.y0 + 1][this.x0] = 0;
        } else if (direction == 'D') {
            newState[this.y0][this.x0] = this.currentState[this.y0 - 1][this.x0];
            newState[this.y0 - 1][this.x0] = 0;
        } else if (direction == 'L') {
            newState[this.y0][this.x0] = this.currentState[this.y0][this.x0 + 1];
            newState[this.y0][this.x0 + 1] = 0;
        } else if (direction == 'R') {
            newState[this.y0][this.x0] = this.currentState[this.y0][this.x0 - 1];
            newState[this.y0][this.x0 - 1] = 0;
        }
        // return the new node
        return new Node(newState, this.size, previous, direction, previous.getDepth() + 1);
    }

    /**
     * GetOptionalDirections calculates the next possible directions from the current node.
     * @return a list of Characters that represents the optional directions.
     */
    private List<Character> GetOptionalDirections() {
        List<Character> directions = new ArrayList<Character>();  
        // if the 0 is on the first column
        if (this.x0 == 0) {
            if (this.y0 == 0)
                directions.add(new Character('U'));
            else if (this.y0 == this.size - 1)
                directions.add(new Character('D'));
            else {
                directions.add(new Character('U'));
                directions.add(new Character('D'));
            }
            directions.add(new Character('L'));
            return directions;
        // if the 0 is on the last column    
        } else if (this.x0 == this.size - 1) {
            if (this.y0 == 0)
                directions.add(new Character('U'));
            else if (this.y0 == this.size - 1)
                directions.add(new Character('D'));
            else {
                directions.add(new Character('U'));
                directions.add(new Character('D'));
            }
            directions.add(new Character('R'));
            return directions;   
        }
        // if the zero is on the first row
        else if (this.y0 == 0)
            directions.add(new Character('U'));
        // if the 0 is on the last row
        else if (this.y0 == this.size - 1)
            directions.add(new Character('D'));
        else {
            directions.add(new Character('U'));
            directions.add(new Character('D'));
        }
        directions.add(new Character('L'));
        directions.add(new Character('R'));
        return directions;
    }

}
