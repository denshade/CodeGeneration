package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.common.ArrayListBestFitRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSysOutAccFitnessLogger implements FitnessLogger
{
    private final int bound;
    private final ArrayListBestFitRegister bestFit = new ArrayListBestFitRegister();

    public RandomSysOutAccFitnessLogger(int bound)
    {
        this.bound = bound;
    }

    public void addFitness(List<InstructionMark> instructions, int nrInstruction, int nrRegisters, double error)
    {
        if (error < bestFit.getBestScore()) {
            System.out.println(error + ": " + instructions);
        } else {
            if (new Random().nextInt(bound) == 0) {
                System.out.println(bestFit.getBestScore() + " " + bestFit.getBest() + " vs. " + error + ": " + instructions);
            }
        }
        bestFit.register(error, new ArrayList<>(instructions));
    }
}
