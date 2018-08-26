import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Question question = new Question("data/city40.txt");
        PSO pso = new PSO(question, 10000, 1000, 0.8, 0.9, 0.9);
        pso.fly();
        System.out.println("--------------------------------------------------");
        System.out.println("the best sequence:" + Arrays.toString(pso.bestPosition));
        System.out.println("the best object:" + pso.bestFood);
        System.out.println("the best iter:" + pso.bestIter);
    }
}
