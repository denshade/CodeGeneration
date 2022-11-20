package laboflieven.challenges;

import laboflieven.TestcaseInOutParameters;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.common.CommandLineConfigLoader;
import laboflieven.common.Configuration;
import laboflieven.examiners.AccumulatorMatchAnyRegisterProgramFitnessExaminer;
import laboflieven.examiners.MaxCostAccumulatorMatchAnyRegisterProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.TimingAccFitnessLogger;
import laboflieven.programiterators.*;
import laboflieven.runners.AccStatementRunner;

import java.util.List;

/**
 * MAX_NR_OF_INSTRUCTIONS=10 INSTRUCTION_FACTORY=Acc
 */
public class EllipseFinder implements ProgramTemplate
{
    public static double distance(double a, double b) {
        double h = ((a-b)*(a-b)) / ((a+b) * (a+b)); //
        return Math.PI * (a + b) * ( 1+ ( 3*h) / (10 + Math.sqrt(4 - 3 * h)));
    } // PI ADD MUL  A+B = h = ADD, ADD. SUB SUB. ~ 50
 // 170122: left = left * right, left = left % right, left = 3n+1,  if left <=  R then goto 0 , left = 3n+1, left++,  right = R2, left = E, left = sin(left),  Jump to start if L >= R ,  right = R1, left = -left, vectleft = popfirst(vectleft), Jump if left = 0 goto this + 2, left = left ^ right, R1 = right, left++, left = sqrt(left), right = combine(rightvector), left = sum(leftV), left++, left--, left = nand(left, right), left = cos(left), left = sin(left), left = sqrt(left), Jump if left >= right goto this + 2, left = -left, left = sqrt(left),  right = R1,  Jump to start if L >= R ,  if left <=  R then goto 0 , vectleft = popfirst(vectleft), left = combine(rightleft), left = combine(rightleft), Jump if left <= right goto this + 2, left = sin(left), vectleft = popfirst(vectleft), vectleft = popfirst(vectleft), R1 = right,  right = R2,  left = R1, left = -left, leftVector = split(left), left = sin(left), Quit, leftVector = split(left), left = combine(rightleft), Quit, R2 = left

    public static void main(String[] args) {
        CommandLineConfigLoader loader = new CommandLineConfigLoader();
        Configuration config = loader.loadFromCommandLine(args);
        System.out.println(distance(100,300));
        int curMaxRegisters = config.getNumberOfRegisters(2);
        List<TestcaseInOutParameters> collection = TestCases.getTestCases(new EllipseFinder(), TestCases.getExampleInput2D(10000,1000), curMaxRegisters);

        ProgramFitnessExaminerInterface evaluator = new MaxCostAccumulatorMatchAnyRegisterProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new TimingAccFitnessLogger(10000));

        config.setFitnessExaminer(evaluator);
        /*Configuration config = Configuration.getInstance();
        config.setMaxNrInstructions(nrInstructions);
        config.setFitnessExaminer(evaluator);
        config.setInstructionFactory(new InstructionFactory());*/
        config.setAccOperations(AccInstructionOpcodeEnum.allMathOperators().toArray(AccInstructionOpcodeEnum[]::new));
        config.setMaxNrInstructions(150);
        ProgramIterator iter = config.getProgramIterator(new RandomProgramIterator());
        long start = System.currentTimeMillis();
        iter.iterate(config);
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    @Override
    public double run(double[] args) {
        return distance(args[0], args[1]);
    }
}
