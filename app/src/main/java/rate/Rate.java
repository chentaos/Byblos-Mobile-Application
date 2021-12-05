package rate;

public class Rate {
    double nbRate;
    double averageRate;
    int sumRate;

    public Rate(){

    }

    public Rate(int nbRate, int averageRate, int sumRate) {
        this.nbRate = nbRate;
        this.averageRate = averageRate;
        this.sumRate = sumRate;
    }

    public double getNbRate() {
        return nbRate;
    }

    public void setNbRate() {
        this.nbRate += 1;
    }

    public double getAverageRate() {
        return averageRate;
    }

    public void setRate() {
        this.averageRate = sumRate / nbRate;
    }

    public int getSumRate() {
        return sumRate;
    }

    public void setSumRate(int sumRate) {
        this.sumRate += sumRate;
    }

    @Override
    public String toString() {
        return "rate{" +
                "nbRate=" + nbRate +
                ", rate=" + averageRate +
                ", sumRate=" + sumRate +
                '}';
    }
}
