package laboflieven.challenges;

import laboflieven.InstructionMark;
import laboflieven.ProgramResolution;
import laboflieven.TestcaseInOutParameters;
import laboflieven.accinstructions.*;
import laboflieven.common.Configuration;
import laboflieven.examiners.AccumulatorProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.RandomSysOutAccFitnessLogger;
import laboflieven.programiterators.AccPriorityProgramIterator;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.recursionheuristics.AccHeuristic;
import laboflieven.runners.AccStatementRunner;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.List;

public class DataSourceFinder {
    public static void main(String[] args) {

        int curMaxRegisters = 1;
        List<double[]> points = new ArrayList<>();
        List<TestcaseInOutParameters> collection = TestCases.loadFromCsvString("3.723333333,3.53,9.80665\n" +
                "3.513333333,3.53,9.80665\n" +
                "3.563333333,3.53,9.80665\n" +
                "3.753333333,3.53,9.80665\n" +
                "3.643333333,3.53,9.80665\n" +
                "3.639333333,3.53,9.80665\n" +
                "3.5475,3.53,9.80665\n" +
                "3.555,3.53,9.80665\n" +
                "3.5375,3.53,9.80665\n" +
                "3.495,3.53,9.80665\n" +
                "3.45,3.53,9.80665\n" +
                "3.786,3.53,9.80665\n" +
                "3.68,3.53,9.80665\n" +
                "3.812,3.53,9.80665", 2);
        AccStatementRunner runner = new AccStatementRunner();
        ProgramFitnessExaminerInterface evaluator = new AccumulatorProgramFitnessExaminer(collection, runner);
        evaluator.addListener(new RandomSysOutAccFitnessLogger(10000));
        var conf = new Configuration();
        conf.setInstructionFactory(new InstructionFactory());
        conf.setNumberOfRegisters(2);
        conf.setMaxNrInstructions(5)
                .setFitnessExaminer(evaluator).setNumberOfRegisters(curMaxRegisters).setAccOperations(AccInstructionOpcodeEnum.values())
                .setHeuristic( new AccHeuristic()
                );
        Register.createRegisters(2, "R");
        conf.setErrorTolerance(0.073);
        var v = new GeneralBruteForceProgramIterator();
        ProgramResolution res = v.iterate(conf);
        List<Register> registers = Register.createRegisters(2, "R");
        registers.get(0).value = 3.812;
        runner.verbose = true;
        evaluator.calculateFitness(res.instructions, registers);
        //GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator();
        //long start = System.currentTimeMillis();
        //System.out.println(iter.iterate(conf));
        //evaluator.writeAndClose();*/
        //System.out.println(System.currentTimeMillis() - start + "ms");

        //mainT(15,3);
    }

    public double run(double[] args) {
        return F(args[0]);
    }

    public double F(double n)
    {
        Sigmoid sigmoid = new Sigmoid();
        return sigmoid.value(n);
    }
}
