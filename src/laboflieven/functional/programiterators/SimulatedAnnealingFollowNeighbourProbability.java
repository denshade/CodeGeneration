package laboflieven.functional.programiterators;

public class SimulatedAnnealingFollowNeighbourProbability
{
    public static double probabilityFollowNeighbour(double currentScore, double neighbourScore, double t, double saturatedMax) {
        currentScore = Math.min(saturatedMax, currentScore);
        neighbourScore = Math.min(saturatedMax, neighbourScore);
        double currentHighScore = saturatedMax - currentScore; // higher scores are better.
        double neighbourHighScore = saturatedMax - neighbourScore;
        double exp = Math.exp(-1 * (currentHighScore - neighbourHighScore) / t);
        //System.out.println(exp + " current: " + currentScore + " " + neighbourScore);
        return exp;
    }
}
