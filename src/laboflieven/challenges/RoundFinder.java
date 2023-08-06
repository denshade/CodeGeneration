package laboflieven.challenges;

import laboflieven.InstructionMark;
import laboflieven.ProgramResolution;
import laboflieven.TestcaseInOutParameters;
import laboflieven.accinstructions.*;
import laboflieven.common.CommandLineConfigLoader;
import laboflieven.common.Configuration;
import laboflieven.examiners.MaxCostAccumulatorMatchAnyRegisterProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.TimingAccFitnessLogger;
import laboflieven.programiterators.*;
import laboflieven.registers.NumberNamingScheme;
import laboflieven.runners.AccStatementRunner;
import laboflieven.registers.Register;

import java.util.List;

/**
 * MAX_NR_OF_INSTRUCTIONS=10 INSTRUCTION_FACTORY=Acc
 */
public class RoundFinder implements ProgramTemplate
{
    public static double distance(double a) {
        return Math.floor(a);
    } // PI ADD MUL  A+B = h = ADD, ADD. SUB SUB. ~ 50
 //ceil = [ left = R1, swap = left, left = right, right = swap, Jump if left >= right goto this + 2, left++, swap = left, left = right, right = swap,  Jump to start if L >= R , R1 = right]
 //[ left = R1, Jump if left == right goto this + 2, left = left % right,  right = R1, left = left - right, R1 = left,  if left <=  R then goto 0 , left = cos(left), left = sin(left), R1 = right, left = left + right, left = PI, left--, right = combine(rightvector), Jump if left = 0 goto this + 2, left++, rightVector = split(right), left = sum(leftV), Jump if left >= right goto this + 2, left = cos(left),  Jump to start if L >= R ,  if left <=  R then goto 0 ]
 //[ left = R1, left = left - right, R1 = left, right = combine(rightvector), left = left % right, swap = left, left = right, right = swap, left = log(left), left = nand(left, right),  Jump to start if L >= R ]
    public static void main(String[] args) {
        CommandLineConfigLoader loader = new CommandLineConfigLoader();
        Configuration config = loader.loadFromCommandLine(args);

        int curMaxRegisters = config.getNumberOfRegisters(1);
        List<TestcaseInOutParameters> collection = new TestCases().getAllTestCases(new RoundFinder(), TestCases.getExampleInput1D(30,0.3), curMaxRegisters);

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
        var registers = new NumberNamingScheme().createRegisters(1);
        List<InstructionMark> solution = List.of(new PI(), new Swap(), new Inc(), new Inc(), new Swap(), new Div(),
                                new LoadIntoRightAcc(registers.get(0)), new Sub(), new Cos(), new LoadAccLeftIntoRegister(registers.get(0)));
        //
        config.setMaxNrInstructions(100);
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
