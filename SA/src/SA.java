import java.util.ArrayList;
import java.util.Random;

public class SA {

    public Question question;
    public Random random;

    public double MIN_SYSTEM_TEMP;
    public double COOLING_RATE;
    public double INITIAL_TEMP;
    public double PROBABILITY;
    public int MARKOV_LENGTH;

    public State currentState;
    public State bestState;
    public double Temp;

    public SA(Question question, double MIN_SYSTEM_TEMP, double COOLING_RATE, double INITIAL_TEMP, double PROBABILITY, int MARKOV_LENGTH) {
        this.question = question;
        this.random = new Random(1);
        this.MIN_SYSTEM_TEMP = MIN_SYSTEM_TEMP;
        this.COOLING_RATE = COOLING_RATE;
        this.INITIAL_TEMP = INITIAL_TEMP;
        this.PROBABILITY = PROBABILITY;
        this.MARKOV_LENGTH = MARKOV_LENGTH;
        this.Temp = this.INITIAL_TEMP;
    }

    public void simulate() {
        this.currentState = new State(this.question);
        this.bestState = this.currentState;

        while (this.systemIsNotCooled()) {
            State neighbourState = new State(this.question);
            double best = 1e10;
            for (int i = 0; i < this.MARKOV_LENGTH; i++) {
                State temp = this.currentState.getNeighbourState();
//                System.out.println(temp.energy);
                if (best > temp.energy) {
                    neighbourState = temp;
                    best = neighbourState.energy;
                }
            }
//            State neighbourState = this.currentState.getNeighbourState();
//            System.out.println(neighbourState.energy);
//            System.exit(0);
            if (this.newStateShouldBeAccepted(neighbourState)) {
                this.currentState = neighbourState;
            }
            if (this.currentStateIsBetterThenBestState()) {
                this.bestState = this.currentState;
            }
            this.Temp *= 1 - this.COOLING_RATE;
            this.bestState.show();
        }
    }

    public boolean systemIsNotCooled() {
        return this.Temp > this.MIN_SYSTEM_TEMP;
    }

    public boolean newStateShouldBeAccepted(State neighborState) {
        return calculateAcceptanceProbability(neighborState.energy, this.currentState.energy) > this.random.nextDouble();
    }

    public double calculateAcceptanceProbability(double newEnergy, double currentEnergy) {
        if (newEnergy < currentEnergy)
            return this.PROBABILITY;
        return Math.exp((currentEnergy - newEnergy) / this.Temp);
    }

    public boolean currentStateIsBetterThenBestState() {
        return this.currentState.energy < this.bestState.energy;
    }
}
