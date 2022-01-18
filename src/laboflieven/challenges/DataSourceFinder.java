package laboflieven.challenges;

import laboflieven.ProgramResolution;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.common.CommandLineConfigLoader;
import laboflieven.common.InstructionOpcode;
import laboflieven.examiners.AccumulatorProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.genericsolutions.RandomIteratorOperandFinder;
import laboflieven.loggers.RandomSysOutAccFitnessLogger;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.runners.AccStatementRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * NR_REGISTERS=1 CSV_FILE=C:\Users\densh\OneDrive\Documents\GitHub\CodeGeneration\src\laboflieven\challenges\primes.csv PROGRAM_ITERATOR=random MAX_NR_OF_INSTRUCTIONS=9 ACC_OPERATIONS=LoadIntoLeftAcc,LoadVectorSumIntoLeft,LoadAccLeftIntoVector,Dec,Jump2IfLte,Inc,LoadAccLeftIntoRegister,LoadAccRightIntoRegister,Quit
 NR_REGISTERS=1 CSV_FILE=C:\Users\densh\OneDrive\Documents\GitHub\CodeGeneration\src\laboflieven\challenges\primes.csv PROGRAM_ITERATOR=brute MAX_NR_OF_INSTRUCTIONS=7 RECURSION_HEURISTIC=Acc

 517 NR_REGISTERS=1 CSV_FILE=C:\Users\densh\OneDrive\Documents\GitHub\CodeGeneration\src\laboflieven\challenges\primes.csv PROGRAM_ITERATOR=priority MAX_NR_OF_INSTRUCTIONS=7 RECURSION_HEURISTIC=Acc RND_ADDED=false CUT_POPULATION_AT_MAX=10000000 ACC_OPERATIONS=LoadIntoLeftAcc,LoadVectorSumIntoLeft,LoadAccLeftIntoVector,Dec,Jump2IfLte,Inc,LoadAccLeftIntoRegister,LoadAccRightIntoRegister,Quit
 1001 NR_REGISTERS=1 CSV_FILE=C:\Users\densh\OneDrive\Documents\GitHub\CodeGeneration\src\laboflieven\challenges\primes.csv PROGRAM_ITERATOR=brute MAX_NR_OF_INSTRUCTIONS=7 RECURSION_HEURISTIC=Acc RND_ADDED=false CUT_POPULATION_AT_MAX=10000000 ACC_OPERATIONS=LoadIntoLeftAcc,LoadVectorSumIntoLeft,LoadAccLeftIntoVector,Dec,Jump2IfLte,Inc,LoadAccLeftIntoRegister,LoadAccRightIntoRegister,Quit
 NR_REGISTERS=3 CSV_FILE=C:\temp\slingersummary.csv PROGRAM_ITERATOR=brute MAX_NR_OF_INSTRUCTIONS=7 RECURSION_HEURISTIC=Acc RND_ADDED=false CUT_POPULATION_AT_MAX=10000000 ACC_OPERATIONS=LoadIntoLeftAcc,LoadVectorSumIntoLeft,LoadAccLeftIntoVector,Dec,Jump2IfLte,Inc,LoadAccLeftIntoRegister,LoadAccRightIntoRegister,Quit
 */
public class DataSourceFinder {
    public static void main(String[] args) throws IOException {
        CommandLineConfigLoader loader = new CommandLineConfigLoader();
        var conf = loader.loadFromCommandLine(args);
        // left = R1, left = nand(left, right), left = sin(left), left = 3n+1, R1 = left
        AccStatementRunner runner = new AccStatementRunner();

        ProgramFitnessExaminerInterface evaluator = new AccumulatorProgramFitnessExaminer(
                TestCases.loadFromCsvFile(new File(conf.getCsvFile("C:\\temp\\slingersummary.csv"))), runner,
                "R2");
        var finder = new RandomIteratorOperandFinder();
        evaluator.addListener(new RandomSysOutAccFitnessLogger(10000));
        conf.setFitnessExaminer(evaluator);
        var v = conf.getProgramIterator(new GeneralBruteForceProgramIterator());
        long start = System.currentTimeMillis();
        conf.setMaxDurationSeconds(2);
        List<InstructionOpcode> codes = finder.find(conf);
        List<AccInstructionOpcodeEnum> opcodes = codes.stream().map(o -> (AccInstructionOpcodeEnum)o.getEnumeration()).collect(Collectors.toList());
        System.out.println("Setting opcodes to " + opcodes);
        conf.setAccOperations(opcodes.toArray(new AccInstructionOpcodeEnum[opcodes.size()]));
        ProgramResolution res = v.iterate(conf);
        System.out.println(res);
        long stop = System.currentTimeMillis();
        System.out.println("Timing:" + (stop - start));
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
