package laboflieven.loggers;

import laboflieven.accinstructions.AccRegisterInstruction;

import java.util.List;
import java.util.Random;

public class SysOutAccFitnessLogger implements AccFitnessLogger
{
    private final int bound;
    private double bestErr = Double.MAX_VALUE;

    public SysOutAccFitnessLogger(int bound)
    {

        this.bound = bound;
    }

    public void addFitness(List<AccRegisterInstruction> instructions, int nrInstruction, int nrRegisters, double error)
    {
        if (error < bestErr) {
            bestErr = error;
            System.out.println(error + ": " + instructions);
        } else {
            Random r = new Random();
            if (r.nextInt(bound) == 0) {
                System.out.println(bestErr +" vs. " + error + ": " + instructions);
            }
        }
    }
}
