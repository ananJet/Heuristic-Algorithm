import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Question question = new Question("data/city40.txt");
        SA sa = new SA(question, 1, 0.03, 1000, 0.8, 10000);
        sa.simulate();
        question.check(sa.bestState.s, sa.bestState.energy);
        System.out.println("--------------------------------------------------");
        System.out.println("the best sequence:" + Arrays.toString(sa.bestState.s));
        System.out.println("the best object:" + sa.bestState.energy);
    }
}
