import java.util.ArrayList;
import java.util.Arrays;

public class PSO {

    public Question question;
    public int birdCount;
    public int iterCount;

    public ArrayList<Bird> birds;

    public double w;
    public double c1;
    public double c2;

    public int[] gBestPosition;
    public double gBestFood;

    public int[] bestPosition;
    public double bestFood;
    public int bestIter;


    public PSO(Question question, int birdCount, int iterCount, double w, double c1, double c2) {
        this.question = question;
        this.birdCount = birdCount;
        this.iterCount = iterCount;

        this.w = w;
        this.c1 = c1;
        this.c2 = c2;

        this.birds = new ArrayList<>();
        this.gBestPosition = new int[this.question.n];
        this.gBestFood = 1e10;
        this.initBirds();

        this.bestPosition = new int[this.question.n];
        this.bestFood = 1e10;
        this.bestIter = 0;
    }

    public void initBirds() {
        for (int i = 0; i < this.birdCount; i++) {
            this.birds.add(new Bird(this.question, this, i));
        }
        this.updateBest();
    }

    public void fly() {
        int iter = 0;
        while (iter < this.iterCount) {
//            System.out.println(this.gBestFood);
//            System.out.println(Arrays.toString(this.bestPosition));
            for (Bird bird : this.birds) {
                bird.updateSpeed();
                bird.updatePosition();
                bird.setFood();
                bird.setPBest();
//                if (iter == 10)
//                    System.exit(0);
            }
            this.show(iter);
            this.updateBest();
            iter++;
        }
    }

    public void show(int iter) {
        double _bestFood = 1e10;
        int[] _bestPosition = new int[this.question.n];
        for (Bird bird : this.birds) {
//            this.question.check(bird.position, bird.food);
            if (bird.food < _bestFood) {
                _bestFood = bird.food;
                System.arraycopy(bird.position, 0, _bestPosition, 0, _bestPosition.length);
            }
        }
        System.out.println(iter + " " + _bestFood);

        if (_bestFood < this.bestFood) {
            this.bestFood = _bestFood;
            System.arraycopy(_bestPosition, 0, this.bestPosition, 0, _bestPosition.length);
            this.bestIter = iter;
//            System.out.println(this.bestFood);
//            System.out.println(Arrays.toString(this.bestPosition));
//            this.question.check(this.bestPosition, this.bestFood);
        }
//        System.out.println(Arrays.toString(this.bestPosition));
//        this.question.check(this.bestPosition, this.bestFood);
        this.check();
    }

    public void updateBest() {
        Bird best = this.birds.get(0);
        for (Bird bird : this.birds) {
            if (bird.food < best.food)
                best = bird;
        }
        System.arraycopy(best.position, 0, this.gBestPosition, 0, best.position.length);
        this.gBestFood = best.food;
    }

    public void check() {
        this.question.check(this.bestPosition, this.bestFood);
    }

}
