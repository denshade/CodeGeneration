package laboflieven.loggers;

import laboflieven.InstructionMark;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomSysOutAccFitnessLogger implements FitnessLogger
{
    private final int bound;
    private double bestErr = Double.MAX_VALUE;
    private List<InstructionMark> bestSolution = new ArrayList<>();

    public RandomSysOutAccFitnessLogger(int bound)
    {

        this.bound = bound;
    }

    public void addFitness(List<InstructionMark> instructions, int nrInstruction, int nrRegisters, double error)
    {
        if (error < bestErr) {
            bestErr = error;
            bestSolution = new ArrayList<>(instructions);
            System.out.println(error + ": " + instructions);
        } else {
            Random r = new Random();
            if (r.nextInt(bound) == 0) {
                System.out.println(bestErr + " " + bestSolution + " vs. " + error + ": " + instructions);
            }
        }
    }
}
