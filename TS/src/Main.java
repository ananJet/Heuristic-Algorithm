import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Question question = new Question("data/city40.txt");
        TS ts = new TS(question, 2000, 30);
        ts.search();
        System.out.println("--------------------------------------------------");
        System.out.println("the best sequence:" + Arrays.toString(ts.bestSolution.s));
        System.out.println("the best object:" + ts.bestSolution.o);
        System.out.println("the best iter:" + ts.bestIter);
    }
}
