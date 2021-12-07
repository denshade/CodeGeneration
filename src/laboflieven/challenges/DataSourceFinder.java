package laboflieven.challenges;

import laboflieven.ProgramResolution;
import laboflieven.common.CommandLineConfigLoader;
import laboflieven.examiners.AccumulatorProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.RandomSysOutAccFitnessLogger;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.runners.AccStatementRunner;

import java.io.File;
import java.io.IOException;

/**
 * NR_REGISTERS=1 CSV_FILE=C:\Users\densh\OneDrive\Documents\GitHub\CodeGeneration\src\laboflieven\challenges\primes.csv PROGRAM_ITERATOR=random MAX_NR_OF_INSTRUCTIONS=9 ACC_OPERATIONS=LoadIntoLeftAcc,LoadVectorSumIntoLeft,LoadAccLeftIntoVector,Dec,Jump2IfLte,Inc,LoadAccLeftIntoRegister,LoadAccRightIntoRegister,Quit
 NR_REGISTERS=1 CSV_FILE=C:\Users\densh\OneDrive\Documents\GitHub\CodeGeneration\src\laboflieven\challenges\primes.csv PROGRAM_ITERATOR=brute MAX_NR_OF_INSTRUCTIONS=7 RECURSION_HEURISTIC=Acc
 */
public class DataSourceFinder {
    public static void main(String[] args) throws IOException {
        CommandLineConfigLoader loader = new CommandLineConfigLoader();
        var conf = loader.loadFromCommandLine(args);
        // left = R1, left = nand(left, right), left = sin(left), left = 3n+1, R1 = left
        AccStatementRunner runner = new AccStatementRunner();

        ProgramFitnessExaminerInterface evaluator = new AccumulatorProgramFitnessExaminer(
                TestCases.loadFromCsvFile(new File(conf.getCsvFile("test.csv"))), runner,
                "R1");
        evaluator.addListener(new RandomSysOutAccFitnessLogger(10000));
        conf.setFitnessExaminer(evaluator);
        var v = conf.getProgramIterator(new GeneralBruteForceProgramIterator());
        ProgramResolution res = v.iterate(conf);
        System.out.println(res);
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
