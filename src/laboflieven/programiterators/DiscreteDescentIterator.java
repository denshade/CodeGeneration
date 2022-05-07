package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.ProgramResolution;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.common.Configuration;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiscreteDescentIterator implements ProgramIterator
{

    @Override
    public ProgramResolution iterate(Configuration configuration) {
        int maximumInstructions = configuration.getMaxNrInstructions(10);
        InstructionFactoryInterface instructionFactory = configuration.getInstructionFactory(new InstructionFactory());
        int nrRegisters = configuration.getNumberOfRegisters(2);
        List<Register> registers = Register.createRegisters(nrRegisters);
        Object[] accenums = configuration.getAccOperations();
        List<InstructionMark> instructions = new ArrayList<>();
        for (int i = 0; i < maximumInstructions - 2; i++)
        {
            InstructionMark actualInstruction = instructionFactory.generateRandomInstruction(registers, accenums);
            instructions.add(actualInstruction);
        }

        return null;
    }
}
