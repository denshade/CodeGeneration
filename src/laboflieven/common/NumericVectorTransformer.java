package laboflieven.common;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.InstructionFactory;

import java.util.ArrayList;
import java.util.List;

public class NumericVectorTransformer
{
    public List<Double> programToNumericList(List<InstructionMark> instructions, List<InstructionOpcode> opcodes)
    {
        List<Double> doubles = new ArrayList<>(instructions.size());
        for (InstructionMark instruction : instructions) {
            doubles.add(getIndex(opcodes, instruction));
        }
        return doubles;
    }

    public List<InstructionMark> numericListToProgram(List<Double> numericInstructions, List<InstructionOpcode> opcodes)
    {
        InstructionFactory factory = new InstructionFactory();
        List<InstructionMark> instructionMarks = new ArrayList<>(numericInstructions.size());
        for (Double numericInstruction : numericInstructions) {
            instructionMarks.add(factory.createInstruction(opcodes.get(numericInstruction.intValue())));
        }
        return instructionMarks;
    }


    private double getIndex(List<InstructionOpcode> opcodes, InstructionMark instruction) {
        for (int index = 0; index < opcodes.size(); index++) {
            InstructionOpcode opcode = opcodes.get(index);
            if (instruction.getInstructionOpcode().equals(opcode)) {
                return index;
            }
        }
        throw new RuntimeException("No opcode found for " + instruction + " in " + opcodes);
    }
}
