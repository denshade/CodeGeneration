package laboflieven.genericsolutions;

import laboflieven.InstructionMark;
import laboflieven.common.Configuration;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;

import java.util.List;

public class SelectorProgramFinder
{
    public List<InstructionMark> findSolutions(Configuration configuration)
    {
        var clonedConfiguration = new Configuration();
        clonedConfiguration.getFitnessExaminer();
        var generalBruteForceProgramIterator = new GeneralBruteForceProgramIterator();
        var programResolution = generalBruteForceProgramIterator.iterate(clonedConfiguration);
        return programResolution.instructions;
    }
}
