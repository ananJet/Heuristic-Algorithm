import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Bird {

    public Question question;
    public PSO pso;
    public Random random;

    public int[] position;
    public ArrayList<double[]> v;
    public double food;

    public double r1;
    public double r2;

    public int[] pBestPosition;
    public double pBestFood;

    public Bird(Question question, PSO pso, int seed) {
        this.question = question;
        this.random = new Random(seed);
        this.pso = pso;
        this.position = new int[this.question.n];
        this.initialize();
    }

    public void initialize() {
        this.question.encoding(this.position);
        this.v = new ArrayList<>();
        this.setFood();
        this.pBestPosition = new int[this.question.n];
        System.arraycopy(this.position, 0, this.pBestPosition, 0, this.position.length);
        this.pBestFood = this.food;
    }

    public void setFood() {
        this.food = this.question.object(this.position);
    }

    public void updateSpeed() {
//        System.out.println(this.food);
        this.v.clear();
        boolean[] flags = new boolean[this.question.n];
        for (int i = 0; i < flags.length; i++) {
            double pr = this.random.nextDouble();
            if (pr >= this.pso.w) {
//                System.out.println(1);
                flags[i] = true;
            }
        }

        int[] pBest = new int[this.question.n];
        System.arraycopy(this.pBestPosition, 0, pBest, 0, this.pBestPosition.length);
//        System.out.println(Arrays.toString(pBest));
//        System.out.println(Arrays.toString(this.position));
        for (int i = 0; i < pBest.length; i++) {
            if (this.position[i] != pBest[i] && flags[i]) {
//                System.out.println(2);
                double[] operator = new double[3];
                operator[0] = i;
                operator[1] = this.getIndex(pBest, this.position[i]);
                this.r1 = this.random.nextDouble();
                operator[2] = this.pso.c1 * this.r1;
                this.v.add(operator);
                this.swap(pBest, (int)operator[0], (int)operator[1]);
            }
        }

        int[] gBest = new int[this.question.n];
        System.arraycopy(this.pso.gBestPosition, 0, gBest, 0, this.pso.gBestPosition.length);
//        System.out.println(Arrays.toString(gBest));
//        System.out.println(Arrays.toString(this.position));
//        System.out.println(Arrays.toString(flags));
        for (int i = 0; i < gBest.length; i++) {
            if (this.position[i] != gBest[i] && flags[i]) {
//                System.out.println(3);
                double[] operator = new double[3];
                operator[0] = i;
                operator[1] = this.getIndex(gBest, this.position[i]);
                this.r2 = this.random.nextDouble();
                operator[2] = this.pso.c2 * this.r2;
                this.v.add(operator);
                this.swap(gBest, (int)operator[0], (int)operator[1]);
            }
        }
//        for (double[] operator : this.v) {
//            System.out.println(Arrays.toString(operator));
//        }
    }

    public void updatePosition() {
//        System.out.println(Arrays.toString(this.position));
        for (double[] operator : this.v) {
            double pr = this.random.nextDouble();
            if (pr <= operator[2])
                this.swap(this.position, (int)operator[0], (int)operator[1]);
        }
//        System.out.println(Arrays.toString(this.position));
    }

    public int getIndex(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target)
                return i;
        }
        return -1;
    }

    public void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public void setPBest() {
//        System.out.println(this.food + " " + this.pBestFood);
        if (this.food < this.pBestFood) {
//            System.out.println("haha");
            this.pBestFood = this.food;
            System.arraycopy(this.position, 0, this.pBestPosition, 0, this.position.length);
        }
    }
}
