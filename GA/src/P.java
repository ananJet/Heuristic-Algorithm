
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Double.*;

public class P {

    private int scale;// ��Ⱥ��ģ
    private int cityNum; // ����������Ⱦɫ�峤��
    private int MAX_GEN; // ���д���
    private int[][] distance; // �������
    private int bestT;// ��ѳ��ִ���
    private int bestLength; // ��ѳ���
    private int[] bestTour; // ���·��

    // ��ʼ��Ⱥ��������Ⱥ��������ʾ��Ⱥ��ģ��һ�д���һ�����壬��Ⱦɫ�壬�б�ʾȾɫ�����Ƭ��
    private int[][] oldPopulation;
    private int[][] newPopulation;// �µ���Ⱥ���Ӵ���Ⱥ
    private int[] fitness;// ��Ⱥ��Ӧ�ȣ���ʾ��Ⱥ�и����������Ӧ��

    private float[] Pi;// ��Ⱥ�и���������ۼƸ���
    private float Pc;// �������
    private float Pm;// �������
    private int t;// ��ǰ����

    private Random random;

    public P() {

    }

    /**
     * constructor of GA
     *
     * @param s
     *            ��Ⱥ��ģ
     * @param n
     *            ��������
     * @param g
     *            ���д���
     * @param c
     *            ������
     * @param m
     *            ������
     *
     **/
    public P(int s, int n, int g, float c, float m) {
        scale = s;
        cityNum = n;
        MAX_GEN = g;
        Pc = c;
        Pm = m;
    }

    // ��������һ��ָ��������Ա���ע�Ĵ���Ԫ���ڲ���ĳЩ���汣�־�Ĭ
    @SuppressWarnings("resource")
    /**
     * ��ʼ��GA�㷨��
     * @param filename �����ļ��������ļ��洢���г��нڵ���������
     * @throws IOException
     */
    private void init(String filename) throws IOException {
        // ��ȡ����
        int[] x;
        int[] y;
        String strbuff;
        BufferedReader data = new BufferedReader(new InputStreamReader(
                new FileInputStream(filename)));
        distance = new int[cityNum][cityNum];
        x = new int[cityNum];
        y = new int[cityNum];
        data.readLine();
        for (int i = 0; i < cityNum; i++) {
            // ��ȡһ�����ݣ����ݸ�ʽ1 6734 1453
            strbuff = data.readLine();
            // �ַ��ָ�
            String[] strcol = strbuff.split("\t");
            x[i] = (int) Double.parseDouble(strcol[1]);// x����
            y[i] = (int) Double.parseDouble(strcol[2]);// y����
        }
        // ����������
        // ����Ծ������⣬������㷽��Ҳ��һ�����˴��õ���att48��Ϊ����������48�����У�������㷽��Ϊαŷ�Ͼ��룬����ֵΪ10628
        for (int i = 0; i < cityNum - 1; i++) {
            distance[i][i] = 0; // �Խ���Ϊ0
            for (int j = i + 1; j < cityNum; j++) {
                double rij = Math
                        .sqrt(((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])
                                * (y[i] - y[j])) / 10.0);
                // �������룬ȡ��
                int tij = (int) Math.round(rij);
                if (tij < rij) {
                    distance[i][j] = tij + 1;
                    distance[j][i] = distance[i][j];
                } else {
                    distance[i][j] = tij;
                    distance[j][i] = distance[i][j];
                }
            }
        }
        distance[cityNum - 1][cityNum - 1] = 0;

        bestLength = Integer.MAX_VALUE;
        bestTour = new int[cityNum + 1];
        bestT = 0;
        t = 0;

        newPopulation = new int[scale][cityNum];
        oldPopulation = new int[scale][cityNum];
        fitness = new int[scale];
        Pi = new float[scale];

        random = new Random(System.currentTimeMillis());
		/*
		 * for(int i=0;i<cityNum;i++) { for(int j=0;j<cityNum;j++) {
		 * System.out.print(distance[i][j]+","); } System.out.println(); }
		 */
        // ��ʼ����Ⱥ

    }

    // ��ʼ����Ⱥ
    void initGroup() {
        int i, j, k;
        // Random random = new Random(System.currentTimeMillis());
        for (k = 0; k < scale; k++)// ��Ⱥ��
        {
            oldPopulation[k][0] = random.nextInt(65535) % cityNum;
            for (i = 1; i < cityNum;)// Ⱦɫ�峤��
            {
                oldPopulation[k][i] = random.nextInt(65535) % cityNum;
                for (j = 0; j < i; j++) {
                    if (oldPopulation[k][i] == oldPopulation[k][j]) {
                        break;
                    }
                }
                if (j == i) {
                    i++;
                }
            }
        }

		/*
		 * for(i=0;i<scale;i++) { for(j=0;j<cityNum;j++) {
		 * System.out.print(oldPopulation[i][j]+","); } System.out.println(); }
		 */
    }

    public int evaluate(int[] chromosome) {
        // 0123
        int len = 0;
        // Ⱦɫ�壬��ʼ����,����1,����2...����n
        for (int i = 1; i < cityNum; i++) {
            len += distance[chromosome[i - 1]][chromosome[i]];
        }
        // ����n,��ʼ����
        len += distance[chromosome[cityNum - 1]][chromosome[0]];
        return len;
    }

    // ������Ⱥ�и���������ۻ����ʣ�ǰ�����Ѿ�����������������Ӧ��fitness[max]����Ϊ����ѡ�����һ���֣�Pi[max]
    void countRate() {
        int k;
        double sumFitness = 0;// ��Ӧ���ܺ�

        double[] tempf = new double[scale];

        for (k = 0; k < scale; k++) {
            tempf[k] = 10.0 / fitness[k];
            sumFitness += tempf[k];
        }

        Pi[0] = (float) (tempf[0] / sumFitness);
        for (k = 1; k < scale; k++) {
            Pi[k] = (float) (tempf[k] / sumFitness + Pi[k - 1]);
        }

		/*
		 * for(k=0;k<scale;k++) { System.out.println(fitness[k]+" "+Pi[k]); }
		 */
    }

    // ��ѡĳ����Ⱥ����Ӧ����ߵĸ��壬ֱ�Ӹ��Ƶ��Ӵ���
    // ǰ�����Ѿ�����������������Ӧ��Fitness[max]
    public void selectBestGh() {
        int k, i, maxid;
        int maxevaluation;

        maxid = 0;
        maxevaluation = fitness[0];
        for (k = 1; k < scale; k++) {
            if (maxevaluation > fitness[k]) {
                maxevaluation = fitness[k];
                maxid = k;
            }
        }

        if (bestLength > maxevaluation) {
            bestLength = maxevaluation;
            bestT = t;// ��õ�Ⱦɫ����ֵĴ���;
            for (i = 0; i < cityNum; i++) {
                bestTour[i] = oldPopulation[maxid][i];
            }
        }

        // System.out.println("���� " + t + " " + maxevaluation);
        // ����Ⱦɫ�壬k��ʾ��Ⱦɫ������Ⱥ�е�λ�ã�kk��ʾ�ɵ�Ⱦɫ������Ⱥ�е�λ��
        copyGh(0, maxid);// ��������Ⱥ����Ӧ����ߵ�Ⱦɫ��k���Ƶ�����Ⱥ�У����ڵ�һλ0
    }

    // ����Ⱦɫ�壬k��ʾ��Ⱦɫ������Ⱥ�е�λ�ã�kk��ʾ�ɵ�Ⱦɫ������Ⱥ�е�λ��
    public void copyGh(int k, int kk) {
        int i;
        for (i = 0; i < cityNum; i++) {
            newPopulation[k][i] = oldPopulation[kk][i];
        }
    }

    // ����ѡ�������ѡ
    public void select() {
        int k, i, selectId;
        float ran1;
        // Random random = new Random(System.currentTimeMillis());
        for (k = 1; k < scale; k++) {
            ran1 = (float) (random.nextInt(65535) % 1000 / 1000.0);
            // System.out.println("����"+ran1);
            // ������ʽ
            for (i = 0; i < scale; i++) {
                if (ran1 <= Pi[i]) {
                    break;
                }
            }
            selectId = i;
            // System.out.println("ѡ��" + selectId);
            copyGh(k, selectId);
        }
    }

    //���������������������
    public void evolution() {
        int k;
        // ��ѡĳ����Ⱥ����Ӧ����ߵĸ���
        selectBestGh();

        // ����ѡ�������ѡscale-1����һ������
        select();

        // Random random = new Random(System.currentTimeMillis());
        float r;

        // ���淽��
        for (k = 0; k < scale; k = k + 2) {
            r = random.nextFloat();// /��������
            // System.out.println("������..." + r);
            if (r < Pc) {
                // System.out.println(k + "��" + k + 1 + "���н���...");
                //OXCross(k, k + 1);// ���н���
                OXCross1(k, k + 1);
            } else {
                r = random.nextFloat();// /��������
                // System.out.println("������1..." + r);
                // ����
                if (r < Pm) {
                    // System.out.println(k + "����...");
                    OnCVariation(k);
                }
                r = random.nextFloat();// /��������
                // System.out.println("������2..." + r);
                // ����
                if (r < Pm) {
                    // System.out.println(k + 1 + "����...");
                    OnCVariation(k + 1);
                }
            }

        }
    }

    //�����������������Ⱦɫ�岻���н������
    public void evolution1() {
        int k;
        // ��ѡĳ����Ⱥ����Ӧ����ߵĸ���
        selectBestGh();

        // ����ѡ�������ѡscale-1����һ������
        select();

        // Random random = new Random(System.currentTimeMillis());
        float r;

        for (k = 1; k + 1 < scale / 2; k = k + 2) {
            r = random.nextFloat();// /��������
            if (r < Pc) {
                OXCross1(k, k + 1);// ���н���
                //OXCross(k,k+1);//���н���
            } else {
                r = random.nextFloat();// /��������
                // ����
                if (r < Pm) {
                    OnCVariation(k);
                }
                r = random.nextFloat();// /��������
                // ����
                if (r < Pm) {
                    OnCVariation(k + 1);
                }
            }
        }
        if (k == scale / 2 - 1)// ʣ���һ��Ⱦɫ��û�н���L-1
        {
            r = random.nextFloat();// /��������
            if (r < Pm) {
                OnCVariation(k);
            }
        }

    }

    // ��OX��������
    void OXCross(int k1, int k2) {
        int i, j, k, flag;
        int ran1, ran2, temp;
        int[] Gh1 = new int[cityNum];
        int[] Gh2 = new int[cityNum];
        // Random random = new Random(System.currentTimeMillis());

        ran1 = random.nextInt(65535) % cityNum;
        ran2 = random.nextInt(65535) % cityNum;
        // System.out.println();
        // System.out.println("-----------------------");
        // System.out.println("----"+ran1+"----"+ran2);

        while (ran1 == ran2) {
            ran2 = random.nextInt(65535) % cityNum;
        }

        if (ran1 > ran2)// ȷ��ran1<ran2
        {
            temp = ran1;
            ran1 = ran2;
            ran2 = temp;
        }
        // System.out.println();
        // System.out.println("-----------------------");
        // System.out.println("----"+ran1+"----"+ran2);
        // System.out.println("-----------------------");
        // System.out.println();
        flag = ran2 - ran1 + 1;// ɾ���ظ�����ǰȾɫ�峤��
        for (i = 0, j = ran1; i < flag; i++, j++) {
            Gh1[i] = newPopulation[k2][j];
            Gh2[i] = newPopulation[k1][j];
        }
        // �ѽ���ֵi=ran2-ran1������

        for (k = 0, j = flag; j < cityNum;)// Ⱦɫ�峤��
        {
            Gh1[j] = newPopulation[k1][k++];
            for (i = 0; i < flag; i++) {
                if (Gh1[i] == Gh1[j]) {
                    break;
                }
            }
            if (i == flag) {
                j++;
            }
        }

        for (k = 0, j = flag; j < cityNum;)// Ⱦɫ�峤��
        {
            Gh2[j] = newPopulation[k2][k++];
            for (i = 0; i < flag; i++) {
                if (Gh2[i] == Gh2[j]) {
                    break;
                }
            }
            if (i == flag) {
                j++;
            }
        }

        for (i = 0; i < cityNum; i++) {
            newPopulation[k1][i] = Gh1[i];// ������ϷŻ���Ⱥ
            newPopulation[k2][i] = Gh2[i];// ������ϷŻ���Ⱥ
        }

        // System.out.println("���н���--------------------------");
        // System.out.println(k1+"�����...");
        // for (i = 0; i < cityNum; i++) {
        // System.out.print(newPopulation[k1][i] + "-");
        // }
        // System.out.println();
        // System.out.println(k2+"�����...");
        // for (i = 0; i < cityNum; i++) {
        // System.out.print(newPopulation[k2][i] + "-");
        // }
        // System.out.println();
        // System.out.println("�������--------------------------");
    }

    // ��������,��ͬȾɫ�彻�������ͬ�Ӵ�Ⱦɫ��
    public void OXCross1(int k1, int k2) {
        int i, j, k, flag;
        int ran1, ran2, temp;
        int[] Gh1 = new int[cityNum];
        int[] Gh2 = new int[cityNum];
        // Random random = new Random(System.currentTimeMillis());

        ran1 = random.nextInt(65535) % cityNum;
        ran2 = random.nextInt(65535) % cityNum;
        while (ran1 == ran2) {
            ran2 = random.nextInt(65535) % cityNum;
        }

        if (ran1 > ran2)// ȷ��ran1<ran2
        {
            temp = ran1;
            ran1 = ran2;
            ran2 = temp;
        }

        // ��Ⱦɫ��1�еĵ��������Ƶ�Ⱦɫ��2���ײ�
        for (i = 0, j = ran2; j < cityNum; i++, j++) {
            Gh2[i] = newPopulation[k1][j];
        }

        flag = i;// Ⱦɫ��2ԭ����ʼλ��

        for (k = 0, j = flag; j < cityNum;)// Ⱦɫ�峤��
        {
            Gh2[j] = newPopulation[k2][k++];
            for (i = 0; i < flag; i++) {
                if (Gh2[i] == Gh2[j]) {
                    break;
                }
            }
            if (i == flag) {
                j++;
            }
        }

        flag = ran1;
        for (k = 0, j = 0; k < cityNum;)// Ⱦɫ�峤��
        {
            Gh1[j] = newPopulation[k1][k++];
            for (i = 0; i < flag; i++) {
                if (newPopulation[k2][i] == Gh1[j]) {
                    break;
                }
            }
            if (i == flag) {
                j++;
            }
        }

        flag = cityNum - ran1;

        for (i = 0, j = flag; j < cityNum; j++, i++) {
            Gh1[j] = newPopulation[k2][i];
        }

        for (i = 0; i < cityNum; i++) {
            newPopulation[k1][i] = Gh1[i];// ������ϷŻ���Ⱥ
            newPopulation[k2][i] = Gh2[i];// ������ϷŻ���Ⱥ
        }
    }

    // ��ζԻ���������
    public void OnCVariation(int k) {
        int ran1, ran2, temp;
        int count;// �Ի�����

        // Random random = new Random(System.currentTimeMillis());
        count = random.nextInt(65535) % cityNum;

        for (int i = 0; i < count; i++) {

            ran1 = random.nextInt(65535) % cityNum;
            ran2 = random.nextInt(65535) % cityNum;
            while (ran1 == ran2) {
                ran2 = random.nextInt(65535) % cityNum;
            }
            temp = newPopulation[k][ran1];
            newPopulation[k][ran1] = newPopulation[k][ran2];
            newPopulation[k][ran2] = temp;
        }

		/*
		 * for(i=0;i<L;i++) { printf("%d ",newGroup[k][i]); } printf("\n");
		 */
    }

    public void solve() {
        int i;
        int k;

        // ��ʼ����Ⱥ
        initGroup();
        // �����ʼ����Ⱥ��Ӧ�ȣ�Fitness[max]
        for (k = 0; k < scale; k++) {
            fitness[k] = evaluate(oldPopulation[k]);
            // System.out.println(fitness[k]);
        }
        // �����ʼ����Ⱥ�и���������ۻ����ʣ�Pi[max]
        countRate();
        System.out.println("��ʼ��Ⱥ...");
        double avg1 = 0.0;
        for (k = 0; k < scale; k++) {
            for (i = 0; i < cityNum; i++) {
                System.out.print(oldPopulation[k][i] + ",");
            }
            System.out.println();
            System.out.println("----" + fitness[k] + " " + Pi[k]);
            avg1 += fitness[k];
        }
        System.out.println(avg1 / fitness.length);
        for (t = 0; t < MAX_GEN; t++) {
            //evolution1();
            evolution();
            // ������ȺnewGroup���Ƶ�����ȺoldGroup�У�׼����һ������
            for (k = 0; k < scale; k++) {
                for (i = 0; i < cityNum; i++) {
                    oldPopulation[k][i] = newPopulation[k][i];
                }
            }
            // ������Ⱥ��Ӧ��
            for (k = 0; k < scale; k++) {
                fitness[k] = evaluate(oldPopulation[k]);
            }
            // ������Ⱥ�и���������ۻ�����
            countRate();
        }

        System.out.println("�����Ⱥ...");
        double avg2 = 0.0;
        for (k = 0; k < scale; k++) {
            for (i = 0; i < cityNum; i++) {
                System.out.print(oldPopulation[k][i] + ",");
            }
            System.out.println();
            System.out.println("---" + fitness[k] + " " + Pi[k]);
            avg2 += fitness[k];
        }
        System.out.println(avg2 / fitness.length);

        System.out.println("��ѳ��ȳ��ִ�����");
        System.out.println(bestT);
        System.out.println("��ѳ���");
        System.out.println(bestLength);
        System.out.println("���·����");
        for (i = 0; i < cityNum; i++) {
            System.out.print(bestTour[i] + ",");
        }

    }


    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Start....");
        P ga = new P(100, 40, 1000, 0.8f, 0.9f);
        ga.init("data/city40.txt");
        ga.solve();
    }

}
