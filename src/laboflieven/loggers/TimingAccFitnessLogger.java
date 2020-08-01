package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.common.BestFitRegister;

import java.util.List;
import java.util.Random;

public class TimingAccFitnessLogger implements FitnessLogger
{
    private final int bound;
    private long previousTiming = 0;
    private BestFitRegister<List<InstructionMark>> bestFit = new BestFitRegister<>();


    public TimingAccFitnessLogger(int bound)
    {
        this.bound = bound;
        previousTiming = System.currentTimeMillis();
    }

    public void addFitness(List<InstructionMark> instructions, int nrInstruction, int nrRegisters, double error)
    {
        bestFit.register(error, instructions);
        if (System.currentTimeMillis() - previousTiming > bound ) {
            previousTiming = System.currentTimeMillis();
            System.out.println(error + ": " + instructions);
            System.out.println( "Best: " + bestFit.getBestScore() + " "+ bestFit.getBest());

        }
    }
}
