package laboflieven.challenges;

import laboflieven.InstructionMark;
import laboflieven.ProgramResolution;
import laboflieven.TestcaseInOutParameters;
import laboflieven.accinstructions.*;
import laboflieven.common.AccInstructionOpcode;
import laboflieven.common.CommandLineConfigLoader;
import laboflieven.common.Configuration;
import laboflieven.examiners.AccumulatorMatchAnyRegisterProgramFitnessExaminer;
import laboflieven.examiners.MaxCostAccumulatorMatchAnyRegisterProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.TimingAccFitnessLogger;
import laboflieven.programiterators.*;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.runners.AccStatementRunner;
import laboflieven.statements.Register;

import java.util.List;
import java.util.Set;

/**
 * MAX_NR_OF_INSTRUCTIONS=10 INSTRUCTION_FACTORY=Acc
 */
public class RoundFinder implements ProgramTemplate
{
    public static double distance(double a) {
        return Math.floor(a);
    } // PI ADD MUL  A+B = h = ADD, ADD. SUB SUB. ~ 50
 // 7.000000000000001 [ left = R1, left = left - right, Jump if left = 0 goto this + 2, right = combine(rightvector), Jump if left >= right goto this + 2, left++, R1 = left, left--, left = left ^ right, left = nand(left, right), Jump if left >= right goto this + 2, leftVector = split(left), left = E, vectleft = popfirst(vectleft), Quit,  right = R1, Jump if left >= right goto this + 2, Quit, left = 3n+1, left = PI, left = sum(leftV), left = combine(rightleft), left = sin(left), swap = left, left = right, right = swap, Jump if left = 0 goto this + 2, left = -left, rightVector = split(right),  right = R1, left = E, left = -left, left = nand(left, right),  left = R1, R1 = left, right = combine(rightvector), swap = left, left = right, right = swap, Jump if left = 0 goto this + 2,  Jump to start if L >= R , left = log(left), left = left * right, left = sin(left), vectleft = popfirst(vectleft), right = combine(rightvector), Jump if left == right goto this + 2, left = left - right, Quit, left = log(left), left = -left, right = combine(rightvector), leftVector = split(left), R1 = left]

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
        /*config.setAccOperations(AccInstructionOpcodeEnumBuilder.make()
                .with(AccInstructionOpcodeEnum.PI,  AccInstructionOpcodeEnum.Swap, AccInstructionOpcodeEnum.Cos,
                        AccInstructionOpcodeEnum.Inc, AccInstructionOpcodeEnum.Div, AccInstructionOpcodeEnum.Sub
                        )
                .with(AccInstructionOpcodeEnum.allAccLoaders().toArray(new AccInstructionOpcodeEnum[0])).build());*/
        var registers = Register.createRegisters(1);
        List<InstructionMark> solution = List.of(new PI(), new Swap(), new Inc(), new Inc(), new Swap(), new Div(),
                                new LoadIntoRightAcc(registers.get(0)), new Sub(), new Cos(), new LoadAccLeftIntoRegister(registers.get(0)));
        //
        config.setMaxNrInstructions(50);
        config.setRandomAdded(false);
        config.setNumberOfRegisters(1);
        ProgramIterator iter = config.getProgramIterator(new RandomProgramIterator());
        long start = System.currentTimeMillis();
        ProgramResolution solutionFound = iter.iterate(config);
        System.out.println(solution);
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    @Override
    public double run(double[] args) {
        return distance(args[0]);
    }
}
