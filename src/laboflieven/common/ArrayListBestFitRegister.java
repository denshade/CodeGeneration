package laboflieven.common;

import java.util.ArrayList;
import java.util.List;

public class ArrayListBestFitRegister {
    private List best;
    private Double bestScore;


    public boolean register(double bestScore, List bestObject)
    {
        boolean updated = false;
        if (this.bestScore == null || this.bestScore > bestScore) {
            this.bestScore = bestScore;
            best = new ArrayList(bestObject);
            updated = true;
            System.out.println(bestScore);
        }
        return updated;
    }

    public List getBest() {
        return best;
    }

    public Double getBestScore() {
        if (bestScore == null) {
            return Double.POSITIVE_INFINITY;
        }
        return bestScore;
    }

}
