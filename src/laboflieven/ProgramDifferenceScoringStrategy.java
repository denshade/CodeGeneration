package laboflieven;

import laboflieven.examiners.ProgramFitnessExaminerInterface;

/**
 * Created by Lieven on 12-6-2016.
 */
public class ProgramDifferenceScoringStrategy
{

    public static double evaluate(ProgramFitnessExaminerInterface evaluator, Program program)
    {
        return  evaluator.evaluateDifference(program);
    }
}
