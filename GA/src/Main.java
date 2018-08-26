import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Question question = new Question("data/city40.txt");
        GA ga = new GA(question, 100, 0.9, 0.01);
        ga.initialize();
        int max_iter = 10000;
        Individual best_ind = new Individual(question);
        best_ind.initialize();
        best_ind.setFitness();
        for (int i = 0; i < max_iter; i++) {
            ga.evolve();
            ga.show(i + 1);
            Individual temp = ga.getBest();
            if (temp.fitness < best_ind.fitness)
                best_ind = temp;
        }
        best_ind.check();
        System.out.println("--------------------------------------------------");
        System.out.println("the best sequence:" + Arrays.toString(best_ind.gen));
        System.out.println("the best object:" + best_ind.fitness);
//        System.out.println(Arrays.toString(best_ind.gen));
//        int[] ex = new int[]{84,86,120,141,113,58,67,140,144,90,43,93,2,70,121,3,22,38,56,115,78,133,85,106,29,142,119,25,97,16,24,128,31,30,21,15,66,27,35,126,69,100,13,135,149,44,36,5,132,75,112,99,136,131,108,110,71,143,77,4,61,116,33,102,49,63,80,37,74,111,72,104,123,51,40,0,8,42,11,91,145,52,39,65,54,147,138,117,105,9,28,19,53,146,124,79,48,45,92,122,10,81,23,130,32,89,109,68,60,73,83,14,139,59,127,125,101,107,64,82,62,47,76,114,18,12,129,103,148,88,34,118,57,41,50,26,137,87,7,95,17,94,6,96,20,46,134,98,1,55};
//        int[] ex = new int[]{7,6,0,4,1,5,8,3,2,9};
//        System.out.println(question.object(ex));
    }
}
