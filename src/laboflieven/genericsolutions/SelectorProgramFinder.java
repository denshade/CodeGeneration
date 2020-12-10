package laboflieven.genericsolutions;

import laboflieven.InstructionMark;
import laboflieven.ProgramResolution;
import laboflieven.TestcaseInOutParameters;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.runners.AccStatementRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectorProgramFinder
{
    public List<InstructionMark> findSolutions(Configuration configuration)
    {
        var clonedConfiguration = new Configuration();
        clonedConfiguration.setFitnessExaminer(new ProgramFitnessExaminer(configuration.getFitnessExaminer().getTestcases(), new AccStatementRunner()));
        var generalBruteForceProgramIterator = new GeneralBruteForceProgramIterator();
        var programResolution = new ProgramResolution(new ArrayList<>(), 400);
        int nrInstructs = 1;
        while (programResolution == null || programResolution.weight > 0.001){
            clonedConfiguration.setMaxNrInstructions(nrInstructs++);
            programResolution = generalBruteForceProgramIterator.iterate(clonedConfiguration);
        }
        return programResolution.instructions;
    }

    public List<TestcaseInOutParameters> adjustTestcases(List<TestcaseInOutParameters> testcases) {
        var parameters = new ArrayList<TestcaseInOutParameters>();
        int counter = 0;
        for (TestcaseInOutParameters param : testcases) {
            var scenario = new TestcaseInOutParameters();
            scenario.expectedOutput.put("R1", (double) counter++);
            scenario.input = new HashMap<>(param.input);
            parameters.add(scenario);
        }
        return parameters;
    }
}
