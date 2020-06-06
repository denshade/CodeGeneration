package laboflieven.loggers;

import laboflieven.accinstructions.AccRegisterInstruction;

import java.util.List;

public class SysOutAccFitnessLogger implements AccFitnessLogger
{
    public void addFitness(List<AccRegisterInstruction> instructions, int nrInstruction, int nrRegisters, double error)
    {
        System.out.println(error + ": " + instructions);
    }
}
