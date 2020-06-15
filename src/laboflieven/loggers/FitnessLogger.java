package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.statements.Instruction;

import java.util.List;

public interface FitnessLogger
{
    public void addFitness(List<InstructionMark> instructions, int nrInstruction, int nrRegisters, double error);
}
