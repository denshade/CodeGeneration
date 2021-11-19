package laboflieven.loggers;

import laboflieven.InstructionMark;

import java.util.List;

public interface FitnessLogger
{
    void addFitness(List<InstructionMark> instructions, int instructionsetSize, int nrRegisters, double error);
}
