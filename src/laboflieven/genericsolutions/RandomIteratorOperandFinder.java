package laboflieven.genericsolutions;

import laboflieven.InstructionMark;
import laboflieven.TestcaseInOutParameters;
import laboflieven.common.Configuration;
import laboflieven.common.InstructionOpcode;
import laboflieven.programiterators.RandomProgramIterator;

import java.util.List;
import java.util.stream.Collectors;

public class RandomIteratorOperandFinder
{
    public List<InstructionOpcode> find(Configuration configuration)
    {
        var genIterator = new RandomProgramIterator();
        var result = genIterator.iterate(configuration);
        var operands = result.instructions.stream().map(InstructionMark::getInstructionOpcode).collect(Collectors.toList());
        return operands;
    }
}
