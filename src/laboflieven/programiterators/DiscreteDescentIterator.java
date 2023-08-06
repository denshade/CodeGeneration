package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.ProgramResolution;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.registers.Register;

import java.util.ArrayList;
import java.util.List;

public class DiscreteDescentIterator implements ProgramIterator
{

    @Override
    public ProgramResolution iterate(Configuration configuration) {
        int maximumInstructions = configuration.getMaxNrInstructions(10);
        InstructionFactoryInterface instructionFactory = configuration.getInstructionFactory(new InstructionFactory());
        int nrRegisters = configuration.getNumberOfRegisters(2);
        List<Register> registers = Register.createRegisters(nrRegisters);
        AccInstructionOpcodeEnum[] accenums = configuration.getAccOperations();
        List<InstructionMark> instructions = new ArrayList<>();
        for (int i = 0; i < maximumInstructions; i++)
        {
            instructions.add(instructionFactory.generateRandomInstruction(registers, accenums));
        }
        ProgramFitnessExaminerInterface fitnessExaminer = configuration.getFitnessExaminer();
        double weight = fitnessExaminer.evaluateDifference(new Program(instructions, registers));
        //for each instruction. Try to swap in for different.
        for (int index = 0; index < instructions.size(); index++)
        {
            InstructionMark instructionMark = instructions.get(index);
            //find the opcode
            //try the one before
            //try the one after
        }
        return null;
    }
}
