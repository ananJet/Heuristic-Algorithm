import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ACO {

    public Question question;
    public Random random;

    public int antCount;
    public int iterCount;

/*    q : 信息素增加强度系数
    alpha : 表征信息素重要程度的参数
    beta : 表征启发因子重要程度的参数
    rou : 信息素蒸发系数*/
    public double q;
    public double alpha;
    public double beta;
    public double rou;

    public double[][] PheromoneTrail;
    public ArrayList<Ant> ants;

    public double bestLength;
    public int[] bestTour;
    public int bestIter;

    public ACO(Question question, int antCount, int iterCount, double q, double alpha, double beta, double rou) {
        this.question = question;
        this.random = new Random(1);
        this.antCount = antCount;
        this.iterCount = iterCount;
        this.q = q;
        this.alpha = alpha;
        this.beta = beta;
        this.rou = rou;

        this.PheromoneTrail = new double[this.question.n][this.question.n];
        for (int i = 0; i < this.question.n; i++) {
            for (int j = 0; j < this.question.n; j++) {
                this.PheromoneTrail[i][j] = 100;
            }
        }
        this.ants = new ArrayList<>();

        this.bestLength = 1e10;
        this.bestTour = new int[this.question.n];
    }

    public void putAnts(int iter) {
        for (int i = 0; i < this.antCount; i++) {
            this.ants.add(new Ant(this.question, this, iter*i));
        }
    }

    public void search() {
        int iter = 0;
        while (iter < this.iterCount) {
            this.ants.clear();
            this.putAnts(iter);
            for (Ant ant : this.ants) {
                for (int i = 1; i < this.question.n; i++) {
                    int nextCity = ant.select();
                    ant.move(nextCity);
                }
                ant.setPathLength();
//                System.out.println(Arrays.toString(ant.tour) + ant.pathLength);
//                System.exit(0);
            }
//            System.exit(0);
            this.UpdatePheromoneTrail();
//            System.out.println(Arrays.deepToString(this.PheromoneTrail));
//            System.exit(0);
            this.show(iter);
            iter++;
        }
    }

    public void UpdatePheromoneTrail() {
        double[][] PheromoneDeltaTrail = new double[this.question.n][this.question.n];
        for (Ant ant : this.ants) {
//            System.out.println(Arrays.toString(ant.tour));
            for (int i = 1; i < ant.tour.length; i++) {
                PheromoneDeltaTrail[ant.tour[i - 1]][ant.tour[i]] += this.q / ant.pathLength;
                PheromoneDeltaTrail[ant.tour[i]][ant.tour[i - 1]] += this.q / ant.pathLength;
            }
            PheromoneDeltaTrail[ant.tour[0]][ant.tour[ant.tour.length - 1]] += this.q / ant.pathLength;
            PheromoneDeltaTrail[ant.tour[ant.tour.length - 1]][ant.tour[0]] += this.q / ant.pathLength;
        }
//        System.out.println(Arrays.deepToString(PheromoneDeltaTrail));
        for (int i = 0; i < this.PheromoneTrail.length; i++) {
            for (int j = 0; j < this.PheromoneTrail[0].length; j++) {
                this.PheromoneTrail[i][j] = (1 - this.rou) * this.PheromoneTrail[i][j] + PheromoneDeltaTrail[i][j];
                PheromoneDeltaTrail[i][j] = 0.0;
            }
        }
//        System.exit(0);
    }

    public void show(int iter) {
        double _bestLength = 1e10;
        int[] _bestTour = new int[this.question.n];
        for (Ant ant : this.ants) {
            if (ant.pathLength < _bestLength) {
                _bestLength = ant.pathLength;
                _bestTour = ant.tour;
            }
        }
        System.out.println(iter + " " + _bestLength);

        if (_bestLength < this.bestLength) {
            this.bestLength = _bestLength;
            this.bestTour = _bestTour;
            this.bestIter = iter;
        }
        this.check();
    }

    public void check() {
        this.question.check(this.bestTour, this.bestLength);
    }
}
