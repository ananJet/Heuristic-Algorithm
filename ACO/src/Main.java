import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Question question = new Question("data/city40.txt");
        ACO aco = new ACO(question, 150, 100, 80, 1, 3, 0.4);
        aco.search();
        System.out.println("--------------------------------------------------");
        System.out.println("the best sequence:" + Arrays.toString(aco.bestTour));
        System.out.println("the best object:" + aco.bestLength);
        System.out.println("the best iter:" + aco.bestIter);
//        int[] a = new int[]{29, 5, 11, 36, 23, 15, 28, 30, 38, 1, 31, 10, 27, 37, 14, 9, 7, 34, 20, 39, 16, 32, 2, 6, 18, 8, 19, 13, 26, 22, 35, 17, 25, 21, 40, 33, 12, 24, 4, 3};
//        for (int i = 0; i < a.length; i++) {
//            a[i] -= 1;
//        }
//        System.out.println(question.object(a));

    }
}
