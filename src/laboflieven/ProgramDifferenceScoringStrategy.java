package laboflieven;

/**
 * Created by Lieven on 12-6-2016.
 */
public class ProgramDifferenceScoringStrategy
{

    public static double evaluate(ProgramEvaluator evaluator, Program program)
    {
        return  evaluator.evaluateDifference(program);
    }
}
