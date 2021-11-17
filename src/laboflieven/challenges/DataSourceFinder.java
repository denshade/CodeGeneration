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
import laboflieven.programiterators.RandomProgramIterator;
import laboflieven.recursionheuristics.AccHeuristic;
import laboflieven.runners.AccStatementRunner;
import laboflieven.statements.Register;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataSourceFinder {
    public static void main(String[] args) throws IOException {

        int curMaxRegisters = 1;
        List<double[]> points = new ArrayList<>();
        String lines = String.join("\n", Files.readAllLines(new File("triple.csv").toPath()));
        List<TestcaseInOutParameters> collection = TestCases.loadFromCsvString(lines, 1);

        // left = R1, left = nand(left, right), left = sin(left), left = 3n+1, R1 = left
        AccStatementRunner runner = new AccStatementRunner();
        ProgramFitnessExaminerInterface evaluator = new AccumulatorProgramFitnessExaminer(collection, runner);
        evaluator.addListener(new RandomSysOutAccFitnessLogger(10000));
        var conf = new Configuration();
        conf.setInstructionFactory(new InstructionFactory());
        conf.setNumberOfRegisters(5);
        conf.setMaxNrInstructions(20)
                .setFitnessExaminer(evaluator).setNumberOfRegisters(curMaxRegisters).setAccOperations(AccInstructionOpcodeEnum.values())
                .setHeuristic( new AccHeuristic()
                );
        Register.createRegisters(2, "R");
        conf.setErrorTolerance(0.1);
        var v = new RandomProgramIterator();
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
