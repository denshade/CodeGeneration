package laboflieven;

import laboflieven.examiners.ProgramFitnessExaminerInterface;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Lieven on 12-6-2016.
 */
public class ProgramClassScoringStrategy
{
    public static double evaluate(ProgramFitnessExaminerInterface evaluator, Program program)
    {
        Set<String> shortnames = new TreeSet<>();
        //programs with few instructions are unlikely.
        for (InstructionMark instruction : program.getInstructions())
        {
            shortnames.add(instruction.toString().substring(0,3));
        }

        return  evaluator.evaluateDifference(program)*100  / (shortnames.size() * program.getInstructions().size() * 500);
    }
}
