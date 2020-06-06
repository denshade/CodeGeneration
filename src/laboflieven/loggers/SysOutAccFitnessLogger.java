package laboflieven.loggers;

import laboflieven.accinstructions.AccRegisterInstruction;

import java.util.List;
import java.util.Random;

public class SysOutAccFitnessLogger implements AccFitnessLogger
{
    private final int bound;

    public SysOutAccFitnessLogger(int bound)
    {

        this.bound = bound;
    }

    public void addFitness(List<AccRegisterInstruction> instructions, int nrInstruction, int nrRegisters, double error)
    {
        Random r = new Random();
        if (r.nextInt(bound) == 0) {
            System.out.println(error + ": " + instructions);
        }
    }
}
