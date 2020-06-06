package laboflieven.loggers;

import laboflieven.accinstructions.AccRegisterInstruction;

import java.util.List;

public interface AccFitnessLogger
{
    public void addFitness(List<AccRegisterInstruction> instructions, int nrInstruction, int nrRegisters, double error);
}
