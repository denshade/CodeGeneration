package laboflieven.challenges;

import laboflieven.Program;
import laboflieven.ProgramResolution;
import laboflieven.TestcaseInOutParameters;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.common.AccInstructionOpcode;
import laboflieven.common.CommandLineConfigLoader;
import laboflieven.common.InstructionOpcode;
import laboflieven.examiners.AccumulatorProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.genericsolutions.RandomIteratorOperandFinder;
import laboflieven.loggers.ErrorCsvFileFitnessLogger;
import laboflieven.loggers.TimingAccFitnessLogger;
import laboflieven.programiterators.*;
import laboflieven.registers.NumberNamingScheme;
import laboflieven.runners.AccStatementRunner;
import laboflieven.registers.Register;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

        int columnToPredict = 2;

        File sourceFile = new File(conf.getCsvFile("C:\\temp\\p128.txt"));
        var contents = Files.readString(sourceFile.toPath());
        TestCases testCases = new TestCases();
        List<TestcaseInOutParameters> conditions = testCases.loadFromCsvFile(sourceFile, false, columnToPredict);
        System.out.println("Conditions");
        System.out.println(conditions);
        ProgramFitnessExaminerInterface evaluator = new AccumulatorProgramFitnessExaminer(
                conditions, runner,
                "R1");

        var finder = new RandomIteratorOperandFinder();
        //evaluator.addListener(new RandomSysOutAccFitnessLogger(100000));
        evaluator.addListener(new TimingAccFitnessLogger(10000));
        conf.setFitnessExaminer(evaluator);
        conf.setNumberOfRegisters(2);
        conf.setMaxNrInstructions(50);
        var v = conf.getProgramIterator(new PredefinedStartAndEndAccPriorityProgramIterator());
        //var v = new GeneralBruteForceProgramIterator();
        //var v = new AccRandomGeneticProgramIterator(evaluator,  AccInstructionOpcodeEnum.allMathOperators().toArray(new AccInstructionOpcodeEnum[0]), 1000,1.1,0.4);
        //var v = conf.getProgramIterator(new GeneralBruteForceProgramIterator());
        long start = System.currentTimeMillis();
        boolean findCodes = false;
        List<AccInstructionOpcodeEnum> opcodes = List.of(AccInstructionOpcodeEnum.values());
        //List<AccInstructionOpcodeEnum> opcodes = AccInstructionOpcodeEnum.allMathOperators();
                //Stream.concat(
                //AccInstructionOpcodeEnum.allAccLoaders().stream(),
                //AccInstructionOpcodeEnum.allMathOperators().stream()).collect(Collectors.toList());

        if (findCodes)
        {
            conf.setMaxDurationSeconds(2);
            List<InstructionOpcode> codes = finder.find(conf);
            opcodes = codes.stream().map(o -> (AccInstructionOpcodeEnum)o.getEnumeration()).collect(Collectors.toList());
        }
        System.out.println("Setting opcodes to " + opcodes);
        conf.setAccOperations(opcodes.toArray(new AccInstructionOpcodeEnum[0]));
        ErrorCsvFileFitnessLogger logger = new ErrorCsvFileFitnessLogger(new File("c:\\temp\\out.csv"), opcodes.stream().map(AccInstructionOpcode::new).collect(Collectors.toList()));
        //evaluator.addListener(logger);
        ProgramResolution res = v.iterate(conf);
        System.out.println(res);
        double bestScore = evaluator.evaluateDifference(new Program(res.instructions, new NumberNamingScheme().createRegisters(1)));
        long stop = System.currentTimeMillis();
        double defaultScore = testCases.getDefaultError(conditions);
        System.out.println("The following config was used");
        System.out.println(conf);
        System.out.println("From the csv the column "+ columnToPredict + " is predicted.");
        System.out.println(contents);
        System.out.println("score:"+ bestScore);
        System.out.println("default score:"+ defaultScore);
        System.out.println("relative score:"+ (bestScore / defaultScore) * 100 + "%");

        System.out.println("Timing:" + (stop - start));
        logger.finish();
    }
}
