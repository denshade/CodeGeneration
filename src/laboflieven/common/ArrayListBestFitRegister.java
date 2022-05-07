package laboflieven.common;

import java.util.ArrayList;
import java.util.List;

public class ArrayListBestFitRegister<T> {
    private List<T> best;
    private Double bestScore;


    public boolean register(double bestScore, List<T> bestObject)
    {
        boolean updated = false;
        if (this.bestScore == null || this.bestScore > bestScore) {
            this.bestScore = bestScore;
            best = new ArrayList<T>(bestObject);
            updated = true;
            System.out.println(bestScore);
        }
        return updated;
    }

    public List<T> getBest() {
        return best;
    }

    public Double getBestScore() {
        if (bestScore == null) {
            return Double.POSITIVE_INFINITY;
        }
        return bestScore;
    }

}
