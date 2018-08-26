import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Question {

    public int n;
    public double[][] d;
    public Random random;


    //    import data
    public Question(String filename) throws FileNotFoundException {
        this.random = new Random(1);
        double coordinate[][];

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        Scanner cin = new Scanner(reader);

        this.n = cin.nextInt();
        coordinate = new double[this.n][2];
        for (int i = 0; i < this.n; i++) {
            cin.next();
            coordinate[i][0] = cin.nextDouble();
            coordinate[i][1] = cin.nextDouble();
        }

//        calulate the distance
        this.d = new double[this.n][this.n];
        for (int i = 0; i < this.n; i++) {
            for (int j = i; j < this.n; j++) {
                this.d[i][j] = this.d[j][i] = Math.sqrt((coordinate[i][0] - coordinate[j][0]) * (coordinate[i][0] - coordinate[j][0]) +
                        (coordinate[i][1] - coordinate[j][1]) * (coordinate[i][1] - coordinate[j][1]));
            }
        }

//        System.out.println(Arrays.deepToString(this.d));
    }

    //    initialize individual
    public void encoding(int[] gen) {
        for (int i = 1; i < this.n; i++) {
            gen[i] = i;
        }
        for (int i = 0; i < this.n; i++) {
            int poi = this.random.nextInt(this.n);
            int temp = gen[i];
            gen[i] = gen[poi];
            gen[poi] = temp;
        }
    }

    //    object
    public double object(int[] gen) {
        int temp = this.n - 1;
        double obj = 0.0;
        for (int i = 0; i < temp; i++) {
            obj += this.d[gen[i]][gen[i + 1]];
        }
        obj += this.d[gen[temp]][gen[0]];
        return obj;
    }

    public void check(int[] gen, double obj) {
        int temp = this.n - 1;
        double obj1 = 0.0;
        for (int i = 0; i < temp; i++) {
            obj1 += this.d[gen[i]][gen[i + 1]];
        }
        obj1 += this.d[gen[temp]][gen[0]];
        if (Math.abs(obj1 - obj) > 1e-6) {
            System.out.println("----------------Obj Error----------------");
            System.out.println("fake:" + obj + " fact:" + obj1);
            System.exit(0);
        }
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 0; i < gen.length; i++) {
            if (hashSet.contains(gen[i])) {
                System.out.println("----------------Sequence Error----------------");
                System.exit(0);
            }
            hashSet.add(gen[i]);
        }
    }

}