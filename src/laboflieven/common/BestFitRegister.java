package laboflieven.common;

public class BestFitRegister<T>
{
    private T best;
    private Double bestScore;


    public void register(double bestScore, T bestObject)
    {
        if (this.bestScore == null || this.bestScore > bestScore) {
            this.bestScore = bestScore;
            best = bestObject;
        }
    }

    public T getBest() {
        return best;
    }
}
