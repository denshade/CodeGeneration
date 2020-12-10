package laboflieven.genericsolutions;

import laboflieven.TestcaseInOutParameters;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.runners.AccStatementRunner;

import java.util.List;

public class GenericSolutionFinder
{
    public GenericSolution findSolution(List<TestcaseInOutParameters> parameters)
    {
        var clonedConfiguration = new Configuration();
        clonedConfiguration.setFitnessExaminer(new ProgramFitnessExaminer(parameters, new AccStatementRunner()));

        SelectorProgramFinder finder  = new SelectorProgramFinder();
        var selectorProgram = finder.findSolutions(clonedConfiguration);
        // generate SelectorProgram
        // for each single input, generate a program.
        //    //
        var genericSolution = new GenericSolution();
        genericSolution.programSelector = selectorProgram;
        return genericSolution;
    }
}
