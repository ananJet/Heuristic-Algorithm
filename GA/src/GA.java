import java.util.*;

public class GA {

    public Question question;
    public int size;
    public ArrayList<Individual> population;
    public Random random;
    public double cp;
    public double mp;

    public GA(Question question, int size, double cp, double mp) {
        this.random = new Random(1);
        this.question = question;
        this.size = size;
        this.population = new ArrayList<>();
        this.cp = cp;
        this.mp = mp;
    }

    public void initialize() {
        for (int i = 0; i < this.size; i++) {
            Individual temp = new Individual(this.question);
            temp.initialize();
            this.population.add(temp);
        }
        this.evaluate();
    }

    public void evaluate() {
        for (Individual ind : this.population) {
            ind.setFitness();
        }
    }

    public void evolve() {
        this.selection();
        this.crossover();
        this.mutation();
        this.evaluate();
    }

    public void selection() {
//        double sum1 = 0;
//        for (Individual individual : this.population) {
////            System.out.println(Arrays.toString(individual.gen));
//            sum1 += individual.getObject();
//        }
//        System.out.println(sum1 / this.population.size());
        double[] sp = new double[this.size];
        double sum = 0.0;
        for (int i = 0; i < this.size; i++) {
            sp[i] = 1.0 / this.population.get(i).fitness;
            sum += sp[i];
        }
//        System.out.println(Arrays.toString(sp));
        for (int i = 0; i < this.size; i++) {
            sp[i] = sp[i] / sum;
        }
//        System.out.println(Arrays.toString(sp));
        for (int i = 1; i < this.size; i++) {
            sp[i] += sp[i - 1];
        }
//        int size1 = this.size - 1;
//        for (int i = 0; i < this.size; i++){
//            sp[i] = (sp[size1] - sp[i]) / sp[size1];
//        }
//        System.out.println(Arrays.toString(sp));
//        System.exit(0);
        ArrayList<Individual> temp = new ArrayList<>();
        for (int i = 0; i < this.size / 10; i++) {
            temp.add(this.getBest().copy());
        }
        while (temp.size() < this.size) {
            double pr = this.random.nextDouble();
            for (int j = 0; j < this.size; j++) {
                if (sp[j] > pr) {
                    temp.add(this.population.get(j).copy());
                    break;
                }
            }
        }
        this.population = temp;
//        double sum2 = 0;
//        for (Individual individual : this.population) {
////            System.out.println(Arrays.toString(individual.gen));
//            sum2 += individual.getObject();
//        }
//        System.out.println(sum2 / this.population.size());
//        if (sum1 / this.population.size() < sum2 / this.population.size())
//            System.out.println("hahahaha");
//            System.exit(0);
//        System.exit(0);

    }

//    order crossover
    public void crossover() {
        int[] sequence = new int[this.size];
        for (int i = 0; i < this.size; i++) {
            sequence[i] = i;
        }
        for (int i = 0; i < this.size; i++) {
            int poi = this.random.nextInt(this.size);
            int temp = sequence[i];
            sequence[i] = sequence[poi];
            sequence[poi] = temp;
        }
        for (int i = 0; i < this.size; i+=2) {
            double pr = this.random.nextDouble();
            if (this.cp > pr) {
//                System.out.println(sequence[i]);
//                System.out.println(sequence[i + 1]);
//                System.out.println(this.population.size());
                Individual i1 = this.population.get(sequence[i]);
                Individual i2 = this.population.get(sequence[i + 1]);
//                System.out.println(Arrays.toString(i1.gen));
//                System.out.println(Arrays.toString(i2.gen));
                int len = i1.gen.length;
                int[] gen1 = new int[len];
//                System.arraycopy(i1.gen, 0, gen1, 0, len);
                int[] gen2 = new int[len];
//                System.arraycopy(i2.gen, 0, gen2, 0, len);
                int nc = len / 2;
//                int begin = 2;
//                int end = 7;
//                int begin = this.random.nextInt(nc);
//                int end = begin + nc;
                int begin = 0;
                int end = 0;
                int a = this.random.nextInt(len);
                int b = this.random.nextInt(len);
                if (a < b) {
                    begin = a;
                    end = b;
                }else {
                    begin = b;
                    end = a;
                }
                for (int j = begin; j < end; j++) {
                    gen1[j] = i2.gen[j];
                    gen2[j] = i1.gen[j];
                }
//                System.out.println(Arrays.toString(i1.gen));
//                System.out.println(Arrays.toString(i2.gen));
//                System.out.println(begin);
//                System.out.println(end);
//                System.out.println(Arrays.toString(gen1));
//                System.out.println(Arrays.toString(gen2));
//                System.exit(0);
//                i1 vs gen1 i2 vs gen2
                Queue<Integer> queue1 = new LinkedList<>();
                Queue<Integer> queue2 = new LinkedList<>();
                for (int j = 0; j < len; j++) {
                    boolean flag1 = true;
                    boolean flag2 = true;
                    for (int k = begin; k < end; k++) {
                        if (gen1[k] == i1.gen[j])
                            flag1 = false;
                        if (gen2[k] == i2.gen[j])
                            flag2 = false;
                    }
                    if (flag1)
                        queue1.add(i1.gen[j]);
                    if (flag2)
                        queue2.add(i2.gen[j]);
                }
                for (int j = 0; j < len; j++) {
                    if (j >= begin && j < end)
                        continue;
                    gen1[j] = queue1.poll();
                    gen2[j] = queue2.poll();
                }

//                int index1 = 0;
//                int index2 = 0;
//                for (int j = 0; j < len; j++) {
//                    if (j >= begin && j < end)
//                        continue;
//                    for (int k = index1; k < len; k++) {
//                        boolean flag = true;
//                        for (int o = begin; o < end; o++) {
//                            if (i2.gen[k] == gen2[o])
//                                flag = false;
//                        }
//                        if (flag) {
//                            gen2[j] = i2.gen[k];
//                            index1 = k + 1;
//                            break;
//                        }
//                    }
//                    for (int k = index2; k < len; k++) {
//                        boolean flag = true;
//                        for (int o = begin; o < end; o++) {
//                            if (i1.gen[k] == gen1[o])
//                                flag = false;
//                        }
//                        if (flag) {
//                            gen1[j] = i1.gen[k];
//                            index2 = k + 1;
//                            break;
//                        }
//                    }
//                }
//                System.out.println(Arrays.toString(gen2));
//                System.exit(0);
                i1.gen = gen1;
                i2.gen = gen2;
//                System.out.println(Arrays.toString(i1.gen));
//                System.out.println(Arrays.toString(i2.gen));
//                System.exit(0);
            }
        }

    }

//    public void crossover() {
//        double pick;
//        double pick1,pick2;
//        int choice1,choice2;
//        int pos1,pos2;
//        int temp;
//        int l = this.population.get(0).gen.length;
//        int[] conflict1 = new int[l]; // 冲突位置
//        int[] conflict2 = new int[l];
//        int num1,num2;
//        int index1,index2;
//        int move = 0; // 当前移动的位置
//        while(move<l-1)
//        {
//            pick = this.random.nextDouble(); // 用于决定是否进行交叉操作
//            if(pick > this.cp)
//            {
//                move += 2;
//                continue; // 本次不进行交叉
//            }
//            // 采用部分映射杂交
//            choice1 = move; // 用于选取杂交的两个父代
//            choice2 = move+1; // 注意避免下标越界
//            pick1 = this.random.nextDouble() / 2.0;
//            pick2 = this.random.nextDouble() / 2.0;
//            pos1 = (int)(pick1*l); // 用于确定两个杂交点的位置
//            pos2 = (int)(pick2*l);
//            while(pos1 > l -2 || pos1 < 1)
//            {
//                pick1 = this.random.nextDouble() / 2.0;
//                pos1 = (int)(pick1*l);
//            }
//            while(pos2 > l -2 || pos2 < 1)
//            {
//                pick2 = this.random.nextDouble() / 2.0;
//                pos2 = (int)(pick2*l);
//            }
//            if(pos1 > pos2)
//            {
//                temp = pos1;
//                pos1 = pos2;
//                pos2 = temp; // 交换pos1和pos2的位置
//            }
//            for(int j=pos1;j<=pos2;j++)
//            {
//                temp = this.population.get(choice1).gen[j];
//                this.population.get(choice1).gen[j] = this.population.get(choice2).gen[j];
//                this.population.get(choice2).gen[j] = temp; // 逐个交换顺序
//            }
//            num1 = 0;
//            num2 = 0;
//            if(pos1 > 0 && pos2 < l-1)
//            {
//                for(int j =0;j<=pos1-1;j++)
//                {
//                    for(int k=pos1;k<=pos2;k++)
//                    {
//                        if(this.population.get(choice1).gen[j] == this.population.get(choice1).gen[k])
//                        {
//                            conflict1[num1] = j;
//                            num1++;
//                        }
//                        if(this.population.get(choice2).gen[j] == this.population.get(choice2).gen[k])
//                        {
//                            conflict2[num2] = j;
//                            num2++;
//                        }
//                    }
//                }
//                for(int j=pos2+1;j<l;j++)
//                {
//                    for(int k=pos1;k<=pos2;k++)
//                    {
//                        if(this.population.get(choice1).gen[j] == this.population.get(choice1).gen[k])
//                        {
//                            conflict1[num1] = j;
//                            num1++;
//                        }
//                        if(this.population.get(choice2).gen[j] == this.population.get(choice2).gen[k])
//                        {
//                            conflict2[num2] = j;
//                            num2++;
//                        }
//                    }
//
//                }
//            }
//            if((num1 == num2) && num1 > 0)
//            {
//                for(int j=0;j<num1;j++)
//                {
//                    index1 = conflict1[j];
//                    index2 = conflict2[j];
//                    temp = this.population.get(choice1).gen[index1]; // 交换冲突的位置
//                    this.population.get(choice1).gen[index1] = this.population.get(choice2).gen[index2];
//                    this.population.get(choice2).gen[index2] = temp;
//                }
//            }
//            move += 2;
//        }
//    }

    public void mutation() {
        for (Individual ind : this.population) {
            int[] sequence = new int[this.population.get(0).gen.length];
            for (int i = 0; i < sequence.length; i++) {
                sequence[i] = i;
            }
            for (int i = 0; i < sequence.length; i++) {
                int poi = this.random.nextInt(sequence.length);
                int temp = sequence[i];
                sequence[i] = sequence[poi];
                sequence[poi] = temp;
            }
            for (int i = 0; i < sequence.length; i+=2) {
                double pr = this.random.nextDouble();
                if (this.mp > pr) {
                    int temp = ind.gen[sequence[i]];
                    ind.gen[sequence[i]] = ind.gen[sequence[i + 1]];
                    ind.gen[sequence[i + 1]] = temp;
                }
            }
        }
    }

    public double show(int iter) {
        double best = 1e10;
        double avg = 0.0;
        for (Individual ind : this.population) {
            double temp = ind.fitness;
            if (best > temp) {
                best = temp;
            }
            avg += temp;
        }
        avg = avg / this.size;
        System.out.println(iter + ": best:" + best + ", avg:" + avg);
        return best;
    }

    public Individual getBest() {
        double best = 1e10;
        Individual best_ind = new Individual(this.question);
        for (Individual ind : this.population) {
            double temp = ind.fitness;
            if (best > temp) {
                best = temp;
                best_ind = ind;
            }
        }
        return best_ind;
    }

}
