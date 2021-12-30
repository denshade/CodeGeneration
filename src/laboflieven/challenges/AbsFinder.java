package laboflieven.challenges;

import laboflieven.ProgramResolution;
import laboflieven.TestcaseInOutParameters;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.common.Configuration;
import laboflieven.examiners.AccumulatorMatchAnyRegisterProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.functional.examiners.AnyRegisterProgramTestcaseFitness;
import laboflieven.loggers.CsvFileFitnessLogger;
import laboflieven.loggers.JsonFileFitnessLogger;
import laboflieven.programiterators.AccPriorityProgramIterator;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.programiterators.SimulatedAnnealingIterator;
import laboflieven.runners.AccStatementRunner;
import laboflieven.statements.Register;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lieven on 8/07/2015.
 */
public class AbsFinder {

    /**
     * //No solutions for 2 -> 5.
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        /*if (args.length != 1)
        {
            System.err.println("Usage : "+AbsFinder.class+" <maxInstructions>");
            System.exit(1);
        }
        int nrInstructions = Integer.parseInt(args[0]);*/
        int nrInstructions = 2;
        List<TestcaseInOutParameters> collection = new ArrayList<>();
        collection.add(createParameter(2.0, 2.0));
        collection.add(createParameter(-15.0, 15.0));
        collection.add(createParameter(0.0, 0.0));
//        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        ProgramFitnessExaminerInterface evaluator = new AccumulatorMatchAnyRegisterProgramFitnessExaminer(collection, new AccStatementRunner());
        //JsonFileFitnessLogger csvLogger = new JsonFileFitnessLogger(new File("c:\\temp\\test.json"));
        //evaluator.addListener(csvLogger);
        //var iterator = new GeneralBruteForceProgramIterator();
        var iterator = new AccPriorityProgramIterator();
//        var iterator = new SimulatedAnnealingIterator(new InstructionFactory(), 100, 2);

        var config = new Configuration();
        config.setNumberOfRegisters(1);
        config.setFitnessExaminer(evaluator);
        config.setMaxNrInstructions(4);
        long start = System.currentTimeMillis();
        ProgramResolution r = iterator.iterate(config);
        //iterator.iterate(1000, 3, Register.createRegisters(1, "R"), evaluator);
        System.out.println("Timing: "+ (System.currentTimeMillis() - start));
        System.out.println(r);

        /*try {
            csvLogger.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private static TestcaseInOutParameters createParameter(double a, double result)
    {
        return TestcaseInOutParameters.createParameter(new double[]{a}, result, 1);
    }

}
