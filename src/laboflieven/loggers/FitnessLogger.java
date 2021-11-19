package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.statements.Instruction;

import java.util.List;

public interface FitnessLogger
{
    void addFitness(List<InstructionMark> instructions, int instructionsetSize, int nrRegisters, double error);
}
