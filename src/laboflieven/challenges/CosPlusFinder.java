package laboflieven.challenges;

import laboflieven.*;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.BruteForceProgramIterator;
import laboflieven.programprinters.JavaProgramPrinter;
import laboflieven.programprinters.ProgramPrinter;
import laboflieven.recursionheuristics.NoInvertedHeuristic;
import laboflieven.registers.LetterNamingScheme;
import laboflieven.runners.RegularStatementRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CosPlusFinder implements ProgramTemplate
{
    public static double distance(double lat1, double lat2) {
        return Math.cos(2 * lat1+lat2) ;
    }

    public static void main(String[] args) throws IOException {
        var config = Configuration.getInstance();
        config.setMaxNrInstructions(3).setNumberOfRegisters(2)
                .setNamingScheme(new LetterNamingScheme());
        List<TestcaseInOutParameters> collection = new TestCases(config.getNamingScheme()).getAllTestCases(new CosPlusFinder(), TestCases.getExampleInput2D(50,10),2);

        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new RegularStatementRunner());
        BruteForceProgramIterator iter = new BruteForceProgramIterator(evaluator, new NoInvertedHeuristic());

        long start = System.currentTimeMillis();
        var results = iter.iterate(config);
        var programPrinter = new JavaProgramPrinter();
        for (Program result : results) {
            System.out.println(programPrinter.toProgram(result));
        }
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    @Override
    public double run(double[] args) {
        return distance(args[0], args[1]);
    }
}
