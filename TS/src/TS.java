import java.util.Arrays;

public class TS {

    public Question question;
    public int iterCount;
    public int tenureLine;
    public int[][] tenureList;

    public Solution solution;
    public Solution bestSolution;
    public int bestIter;

    public TS(Question question, int iterCount, int tenureLine) {
        this.question = question;
        this.iterCount = iterCount;
        this.tenureLine = tenureLine;
        this.tenureList = new int[this.question.n][this.question.n];
        for (int i = 0; i < this.question.n; i++) {
            for (int j = i; j < this.question.n; j++) {
                this.tenureList[i][j] = this.tenureList[j][i] = -1000000000;
            }
        }
        this.solution = new Solution(this.question);
        this.bestSolution = this.solution.copy();
        this.bestIter = 0;
    }

    public void search() {
        int iter = 0;
        while (iter < this.iterCount) {
            double[] r = this.localSearch(iter);
            if (r[0] == -1)
                continue;
            this.move(r);
            if (this.solution.o < this.bestSolution.o) {
                this.bestSolution = this.solution.copy();
                this.bestIter = iter;
            }
            this.show(iter);
            this.solution.check();
            iter++;
        }
    }

    public double[] localSearch(int iter) {
        double[] r = new double[3];
        r[0] = r[1] = r[2] = -1;
        double min = 1e10;
        for (int i = 0; i < this.solution.s.length; i++) {
            for (int j = i + 1; j < this.solution.s.length; j++) {
                double delta = 0.0;
                int city1 = this.solution.s[i];
                int city2 = this.solution.s[j];

                int city1_before = -1;
                if (i == 0)
                    city1_before = this.solution.s[this.solution.s.length - 1];
                else
                    city1_before = this.solution.s[i - 1];

                int city2_after = -1;
                if (j == this.solution.s.length - 1)
                    city2_after = this.solution.s[0];
                else
                    city2_after = this.solution.s[j + 1];

                delta = -(this.question.d[city1_before][city1] + this.question.d[city2][city2_after]) +
                        (this.question.d[city1_before][city2] + this.question.d[city1][city2_after]);
                if (i == 0 && j == this.solution.s.length - 1)
                    delta = 0.0;
                if (iter - this.tenureList[city1][city2] < this.tenureLine && this.solution.o + delta > this.bestSolution.o)
                    continue;
                if (min > delta) {
//                    System.out.println(city1_before + "\t" + city1 + "\t" + city2 + "\t" + city2_after);
                    min = delta;
                    r[0] = i;
                    r[1] = j;
                    r[2] = min;
                }
            }
        }
        this.tenureList[this.solution.s[(int) r[0]]][this.solution.s[(int) r[1]]] = iter;
        return r;
    }

    public void move(double[] r) {
        int begin = (int) r[0];
        int end = (int) r[1];
        double delta = r[2];
//        System.out.println(begin + " " + end);

//        System.out.println(Arrays.toString(this.solution.s));
//        System.out.println(this.question.object(this.solution.s));
        int n = (end - begin) / 2 + 1;
        for (int i = 0; i < n; i++) {
            int temp = this.solution.s[begin + i];
            this.solution.s[begin + i] = this.solution.s[end - i];
            this.solution.s[end - i] = temp;
        }
//        int temp = this.solution.s[i];
//        this.solution.s[i] = this.solution.s[j];
//        this.solution.s[j] = temp;
//        System.out.println(Arrays.toString(this.solution.s));
//        System.out.println(this.question.object(this.solution.s));
//        System.out.println(delta);
        this.solution.o = this.solution.o + delta;
    }

    public void show(int iter) {
        System.out.println(iter + " " + this.solution.o);
    }

}
