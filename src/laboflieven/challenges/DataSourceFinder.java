package laboflieven.challenges;

import laboflieven.ProgramResolution;
import laboflieven.TestcaseInOutParameters;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.common.CommandLineConfigLoader;
import laboflieven.common.Configuration;
import laboflieven.examiners.AccumulatorProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.RandomSysOutAccFitnessLogger;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.recursionheuristics.AccHeuristic;
import laboflieven.runners.AccStatementRunner;
import laboflieven.runners.StatementRunner;
import laboflieven.statements.Register;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class DataSourceFinder {
    public static void main(String[] args) throws IOException {
        int nrInstructions = 6;
        if (args.length > 0)
        {
            nrInstructions = Integer.parseInt(args[0]);
            System.out.println("Setting nr instructions to " + nrInstructions);
        } else {
            System.out.println("Defaulting nr instructions to " + nrInstructions);
        }
        File sourceFile = new File(System.getProperty("java.io.tmpdir"), "source.csv");
        System.out.println("Searching for " + sourceFile);

        if (!sourceFile.exists()) {
            System.out.println("Source files not found "+ sourceFile);
            System.exit(1);
        }
        String lines = String.join("\n", Files.readAllLines(sourceFile.toPath()));
        List<TestcaseInOutParameters> collection = TestCases.loadFromCsvString(lines, 1);
        System.out.println(collection);

        CommandLineConfigLoader loader = new CommandLineConfigLoader();
        var conf = loader.loadFromCommandLine(args);
        // left = R1, left = nand(left, right), left = sin(left), left = 3n+1, R1 = left
        AccStatementRunner runner = new AccStatementRunner();
        ProgramFitnessExaminerInterface evaluator = new AccumulatorProgramFitnessExaminer(collection, runner);
        evaluator.addListener(new RandomSysOutAccFitnessLogger(10000));
        //var v = new RandomProgramIterator();
        var v = conf.getProgramIterator(new GeneralBruteForceProgramIterator());
        ProgramResolution res = v.iterate(conf);
        System.out.println(res);
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
