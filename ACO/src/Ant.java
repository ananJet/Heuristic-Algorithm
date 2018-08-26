import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ant {

    public Question question;
    public ACO aco;
    public Random random;

    public int[] tour;
    public boolean[] passed;
    public int[] allow;

    public int curCity;
    public int curIndex;
    public double pathLength;

    public Ant(Question question, ACO aco, int seed) {
        this.question = question;
        this.aco = aco;
        this.random = new Random(seed);
        this.tour = new int[this.question.n];
        this.passed = new boolean[this.question.n];
        this.initialize();
    }

    public void initialize() {
        int city0 = this.random.nextInt(this.question.n);
        this.tour[0] = city0;
        this.passed[city0] = true;
        this.curIndex = 0;
        this.curCity = city0;
    }

    public int select() {
        this.updateAllow();
        double[] sp = new double[this.allow.length];
        double sum = 0.0;
//        System.out.println(this.allow.length);
//        System.out.println(Arrays.toString(this.allow));
//        System.out.println(this.curCity);
        for (int i = 0; i < this.allow.length; i++) {
//            System.out.println(Arrays.deepToString(this.aco.PheromoneTrail));
//            System.out.println(this.aco.PheromoneTrail[this.curCity][this.allow[i]]);
            sp[i] = Math.pow(this.aco.PheromoneTrail[this.curCity][this.allow[i]], this.aco.alpha) *
                    Math.pow(1.0 / this.question.d[this.curCity][this.allow[i]], this.aco.beta);
            sum += sp[i];
        }
//        for (int i = 0; i < this.allow.length; i++) {
//            sum += Math.pow(this.aco.PheromoneTrail[this.curCity][this.allow[i]], this.aco.alpha) *
//                    Math.pow(1.0 / this.question.d[this.curCity][this.allow[i]], this.aco.beta);
//            sp[i] = sum;
//        }
//        System.out.println(sum);
//        System.exit(0);
        for (int i = 0; i < sp.length; i++) {
            sp[i] = sp[i] / sum;
        }
        for (int i = 1; i < sp.length; i++) {
            sp[i] += sp[i - 1];
        }
//        System.out.println(Arrays.toString(sp));
//        System.exit(0);
        double pr = this.random.nextDouble();
        for (int i = 0; i < sp.length; i++) {
            if (sp[i] >= pr) {
                return this.allow[i];
            }
        }
        return -1;
    }

    public void move(int nextCity) {
        this.tour[++this.curIndex] = nextCity;
        this.passed[nextCity] = true;
        this.curCity = nextCity;
    }

    public void updateAllow() {
        this.allow = new int[this.tour.length - this.curIndex - 1];
        for (int i = 0,j = 0; i < this.passed.length; i++) {
            if (passed[i])
                continue;
            this.allow[j] = i;
            j++;
        }
    }

    public void setPathLength() {
        this.pathLength = this.question.object(this.tour);
    }
}
