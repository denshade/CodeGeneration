package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.common.BestFitRegister;

import java.util.List;

public class TimingAccFitnessLogger implements FitnessLogger
{
    private final int nrMillis;
    private long previousTiming = 0;
    private final BestFitRegister<List<InstructionMark>> bestFit = new BestFitRegister<>();


    public TimingAccFitnessLogger(int nrMillis)
    {
        this.nrMillis = nrMillis;
        previousTiming = System.currentTimeMillis();
    }

    public void addFitness(List<InstructionMark> instructions, int nrInstruction, int nrRegisters, double error)
    {
        bestFit.register(error, instructions);
        if (System.currentTimeMillis() - previousTiming > nrMillis) {
            previousTiming = System.currentTimeMillis();
            System.out.println(error + ": " + instructions);
            System.out.println( "Best[" + System.currentTimeMillis()/1000 + "] : " + bestFit.getBestScore() + " "+ bestFit.getBest());

        }
    }
}
