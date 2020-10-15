package laboflieven.challenges;

import laboflieven.InOutParameters;
import laboflieven.InstructionMark;
import laboflieven.accinstructions.*;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.TimingAccFitnessLogger;
import laboflieven.programiterators.*;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.recursionheuristics.HashedResultsHeuristic;
import laboflieven.recursionheuristics.NoInvertedHeuristic;
import laboflieven.runners.AccStatementRunner;
import laboflieven.runners.RegularStatementRunner;
import laboflieven.statements.Register;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CosAltFinder implements ProgramTemplate
{
    public static double distance(double lat1) {
        return 1.0/Math.cos(lat1);
    }
    //left ++, left++, swap, left=PI, left = left / right(DIV), right = R1, left = left - right, R1 = left.
    //Pi / 2.

    public static void main(String[] args) throws IOException {
        int curMaxRegisters = 1;
        List<InOutParameters> collection = TestCases.getTestCases(new CosAltFinder(), TestCases.getExampleInput1D(50,10),curMaxRegisters);

        Configuration configuration = new Configuration();
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new TimingAccFitnessLogger(10000));
        configuration.setFitnessExaminer(evaluator);
        configuration.setMaxNrInstructions(10);
        configuration.setNumberOfRegisters(1);
        configuration.setRandomAdded(false);
        configuration.setInstructionFactory(new InstructionFactory());
        //configuration.setHeuristic(new HashedResultsHeuristic(collection, new AccStatementRunner()));
        configuration.setHeuristic(new AlwaysRecursionHeuristic());
        configuration.setAccOperations(
                new AccInstructionOpcodeEnum[] {
                AccInstructionOpcodeEnum.Inc,
                        AccInstructionOpcodeEnum.Swap,
                        AccInstructionOpcodeEnum.PI,
                        AccInstructionOpcodeEnum.Div,
                        AccInstructionOpcodeEnum.AccRightPush,
                        AccInstructionOpcodeEnum.Sub,
                        AccInstructionOpcodeEnum.Sin,
                        AccInstructionOpcodeEnum.AccLeftPull,
                }
        );
        /*List<Register> registers = new ArrayList<>();
        registers.add(new Register("R1"));
        List<InstructionMark> m = List.of(new Inc(), new Inc(), new Swap(), new PI(), new Div(), new AccRightPush(registers.get(0)), new Sub(), new Cos(), new AccLeftPull(registers.get(0)));
        //System.out.println(evaluator.calculateFitness(m, registers));*/
        ProgramIterator iter = new GeneralBruteForceProgramIterator();
        long start = System.currentTimeMillis();
        iter.iterate(configuration);
        //evaluator.writeAndClose();
        System.out.println(System.currentTimeMillis() - start + "ms");

    }

    @Override
    public double run(double[] args) {
        return distance(args[0]);
    }
}
