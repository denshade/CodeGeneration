package laboflieven.genericsolutions;

import laboflieven.InstructionMark;
import laboflieven.ProgramResolution;
import laboflieven.TestcaseInOutParameters;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.humanresource.BruteForceProgramIterator;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.runners.AccStatementRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenericSolutionFinder
{
    public GenericSolution findSolution(List<TestcaseInOutParameters> testcases)
    {
        var clonedConfiguration = new Configuration();
        clonedConfiguration.setFitnessExaminer(new ProgramFitnessExaminer(testcases, new AccStatementRunner()));

        SelectorProgramFinder finder  = new SelectorProgramFinder();
        var selectorProgram = finder.findSolutions(clonedConfiguration);
        // generate SelectorProgram
        List<List<InstructionMark>> programs = new ArrayList<>();
        for(TestcaseInOutParameters t : testcases) {
            var bfpi = new GeneralBruteForceProgramIterator();
            clonedConfiguration.setFitnessExaminer(new ProgramFitnessExaminer(List.of(t), new AccStatementRunner()));
            ProgramResolution res = new ProgramResolution(Collections.emptyList(), 60000);
            int nrInstructions = 1;
            while(res == null || res.weight > 0.001) {
                clonedConfiguration.setMaxNrInstructions(nrInstructions++);
                res = bfpi.iterate(clonedConfiguration);
            }
            programs.add(res.instructions);
        }

        // for each single input, generate a program.
        //    //
        var genericSolution = new GenericSolution();
        genericSolution.programSelector = selectorProgram;
        genericSolution.programs = programs;
        return genericSolution;
    }
}
