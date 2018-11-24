/**
 * main class, holds the main function from which the program is ran.
 */
public class java_ex1 {
    
    /**
     * main method, to run the program from
     * @param args not used
     */
    public static void main(String[] args) {
        SearchEngine engine = new SearchEngine();
        engine.Initialize();
        engine.Solve();
        engine.WriteOutput();
    }
}
