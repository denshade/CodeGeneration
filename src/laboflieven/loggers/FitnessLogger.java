package laboflieven.loggers;

import laboflieven.statements.Instruction;

import java.util.List;

public interface FitnessLogger
{
    public void addFitness(List<Instruction> instructions, int nrInstruction, int nrRegisters, double error);
}
