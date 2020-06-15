package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.AccRegisterInstruction;

import java.util.List;

public interface AccFitnessLogger extends FitnessLogger
{
    public void addFitness(List<InstructionMark> instructions, int nrInstruction, int nrRegisters, double error);
}
