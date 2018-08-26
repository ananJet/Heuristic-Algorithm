import java.util.Arrays;
import java.util.Random;

public class State {

    public int[] s;
    public Question question;
    public double energy;
    public Random random;

    public State(Question question) {
        this.question = question;
        this.random = new Random(1);
        this.s = new int[this.question.n];
        this.question.encoding(this.s);
        this.setEnergy();
    }

    public void setEnergy() {
        this.energy = this.question.object(this.s);
    }

    public State getNeighbourState() {
        State neighbor = new State(this.question);
        System.arraycopy(this.s, 0, neighbor.s, 0, this.s.length);
        double pr = this.random.nextDouble();
//        System.out.println(Arrays.toString(neighbor.s));
//        System.out.println(neighbor.energy);
        int type = 0;
        if (pr >= 0.5) {
            type = 1;
            int begin = 0;
            int end = 0;
            int a = this.random.nextInt(neighbor.s.length);
            int b = this.random.nextInt(neighbor.s.length);
            if (a < b) {
                begin = a;
                end = b;
            }else {
                begin = b;
                end = a;
            }
//            System.out.println(begin);
//            System.out.println(end);
            for (int i = 0; i < (end - begin) / 2; i++) {
//                System.out.println((i + begin) + " " + (end - 1 - i));
                int temp = neighbor.s[i + begin];
                neighbor.s[i + begin] = neighbor.s[end - 1 - i];
                neighbor.s[end - 1 - i] = temp;
            }
        }else {
            type = 2;
            int a = this.random.nextInt(neighbor.s.length);
            int b = this.random.nextInt(neighbor.s.length);
            int temp = neighbor.s[a];
            neighbor.s[a] = neighbor.s[b];
            neighbor.s[b] = temp;
//            System.out.println(Arrays.toString(neighbor.s));
//            System.exit(0);
        }
        neighbor.setEnergy();
//        System.out.println(type);
//        System.out.println(Arrays.toString(neighbor.s));
//        System.out.println(neighbor.energy);
//        System.exit(0);
        return neighbor;
    }

    public void show() {
        System.out.println(String.format("%.4f",this.energy));
    }
}
