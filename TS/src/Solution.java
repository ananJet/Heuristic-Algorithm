public class Solution {

    public Question question;
    public int[] s;
    public double o;

    public Solution(Question question) {
        this.question = question;
        this.s = new int[this.question.n];
        this.initialize();
    }

    public void initialize() {
        this.question.encoding(this.s);
        this.setO();
    }

    public void setO() {
        this.o = this.question.object(this.s);
    }

    public Solution copy() {
        Solution temp = new Solution(this.question);
        System.arraycopy(this.s, 0, temp.s, 0, this.s.length);
        temp.o = this.o;
        return temp;
    }

    public void check() {
        this.question.check(this.s, this.o);
    }

}
