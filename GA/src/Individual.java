public class Individual {

    public int[] gen;
    public Question question;
    public double fitness;

    public Individual(Question question) {
        this.question = question;
        this.gen = new int[this.question.n];
    }

    public void initialize() {
        this.question.encoding(this.gen);
    }

    public void setFitness() {
        this.fitness = this.question.object(this.gen);
    }

    public Individual copy() {
        Individual individual = new Individual(this.question);
        System.arraycopy(this.gen, 0, individual.gen, 0, this.gen.length);
        individual.fitness = this.fitness;
        return individual;
    }

    public void check() {
        this.question.check(this.gen, this.fitness);
    }

}
