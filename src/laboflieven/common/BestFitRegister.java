package laboflieven.common;

public class BestFitRegister<T>
{
    private T best;
    private Double bestScore;


    public boolean register(double bestScore, T bestObject)
    {
        boolean updated = false;
        if (this.bestScore == null || this.bestScore > bestScore) {
            this.bestScore = bestScore;
            best = bestObject;
            updated = true;
        }
        return updated;
    }

    public T getBest() {
        return best;
    }
}
