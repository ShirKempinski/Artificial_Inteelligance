import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The SearchEngine gets an unsolved puzzel and solves it, using the requested algorithm.
 * It holds a solver - Algorithm, and String representations of the input and output files.
 */
public class SearchEngine {
    
    private Algorithm solver;
    private final String input;
    private final String output;
    
    /**
     * Constructor.
     */
    public SearchEngine() {
        this.input = "input.txt";
        this.output = "output.txt";
    }
    
    /**
     * Initialize reads the data from the input text file and initializes the solver.
     * the excercise defined the input file to be:
     * first row: algorithm's code. 1 - BFS, 2 - IDS, 3 - A star.
     * second row: size of board
     * third row: the initial state as one string, the numbers seperated by '-'.
     */
    public void Initialize() {
        BufferedReader reader = null; 
        try {
            // read data from input file and get the right algorithm as solver.
            reader = new BufferedReader(new FileReader(new File(this.input)));
            GetSolver(reader.readLine(), reader.readLine(), reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * GetSolver chooses the matching algorithm as solver, and initialize it with the size and initState.
     * @param algorithm - string to interpret
     * @param size - string to interpret
     * @param initState - string to interpret
     */
    private void GetSolver(String algorithm, String size, String initState) {
        int id = Integer.parseInt(algorithm);
        int boardSize = Integer.parseInt(size);
        if (id == 1) this.solver = new IDS(boardSize, initState);
        if (id == 2) this.solver = new BFS(boardSize, initState);
        if (id == 3) this.solver = new AStar(boardSize, initState);
    }

    /**
     * Solve calls the algorithm's search method with the board's moves logic.
     */
    public void Solve() {
        this.solver.Search(new MovesLogic());
    }
    
    /**
     * WriteOutput write the requested output to the outpu file.
     */
    public void WriteOutput() {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(this.output));
            // it writes the solution track, the number of developed nodes and the solution cost.
            writer.write(
                    this.solver.SolutionTrack() + " "
                    + solver.DevelopedNodesCount() + " "
                    + solver.Cost());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
