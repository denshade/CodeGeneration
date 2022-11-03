package laboflieven.challenges;

import laboflieven.TestcaseInOutParameters;
import laboflieven.common.CommandLineConfigLoader;
import laboflieven.common.Configuration;
import laboflieven.examiners.MaxCostAccumulatorMatchAnyRegisterProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.TimingAccFitnessLogger;
import laboflieven.programiterators.AccPriorityProgramIterator;
import laboflieven.programiterators.BruteForceProgramIterator;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.programiterators.ProgramIterator;
import laboflieven.runners.AccStatementRunner;

import java.util.List;

/**
 * MAX_NR_OF_INSTRUCTIONS=10 INSTRUCTION_FACTORY=Acc
 */
public class RoundFinder implements ProgramTemplate
{
    public static double distance(double a) {
        return Math.round(a);
    } // PI ADD MUL  A+B = h = ADD, ADD. SUB SUB. ~ 50
 // 170122: left = left * right, left = left % right, left = 3n+1,  if left <=  R then goto 0 , left = 3n+1, left++,  right = R2, left = E, left = sin(left),  Jump to start if L >= R ,  right = R1, left = -left, vectleft = popfirst(vectleft), Jump if left = 0 goto this + 2, left = left ^ right, R1 = right, left++, left = sqrt(left), right = combine(rightvector), left = sum(leftV), left++, left--, left = nand(left, right), left = cos(left), left = sin(left), left = sqrt(left), Jump if left >= right goto this + 2, left = -left, left = sqrt(left),  right = R1,  Jump to start if L >= R ,  if left <=  R then goto 0 , vectleft = popfirst(vectleft), left = combine(rightleft), left = combine(rightleft), Jump if left <= right goto this + 2, left = sin(left), vectleft = popfirst(vectleft), vectleft = popfirst(vectleft), R1 = right,  right = R2,  left = R1, left = -left, leftVector = split(left), left = sin(left), Quit, leftVector = split(left), left = combine(rightleft), Quit, R2 = left

    public static void main(String[] args) {
        CommandLineConfigLoader loader = new CommandLineConfigLoader();
        Configuration config = loader.loadFromCommandLine(args);

        int curMaxRegisters = config.getNumberOfRegisters(1);
        List<TestcaseInOutParameters> collection = TestCases.getTestCases(new RoundFinder(), TestCases.getExampleInput1D(10,0.3), curMaxRegisters);

        ProgramFitnessExaminerInterface evaluator = new MaxCostAccumulatorMatchAnyRegisterProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new TimingAccFitnessLogger(10000));

        config.setFitnessExaminer(evaluator);
        /*Configuration config = Configuration.getInstance();
        config.setMaxNrInstructions(nrInstructions);
        config.setFitnessExaminer(evaluator);
        config.setInstructionFactory(new InstructionFactory());*/
        config.setMaxNrInstructions(5);
        config.setNumberOfRegisters(1);
        ProgramIterator iter = config.getProgramIterator(new GeneralBruteForceProgramIterator());
        long start = System.currentTimeMillis();
        iter.iterate(config);
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    @Override
    public double run(double[] args) {
        return distance(args[0]);
    }
}
