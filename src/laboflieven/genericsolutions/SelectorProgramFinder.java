package laboflieven.genericsolutions;

import laboflieven.InstructionMark;
import laboflieven.TestcaseInOutParameters;
import laboflieven.common.Configuration;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;

import java.util.List;

public class SelectorProgramFinder
{
    public List<InstructionMark> findSolutions(List<TestcaseInOutParameters> parameters, Configuration configuration)
    {
        var generalBruteForceProgramIterator = new GeneralBruteForceProgramIterator();
        var programResolution = generalBruteForceProgramIterator.iterate(configuration);
        return programResolution.instructions;
    }
}
