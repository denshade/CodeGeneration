package laboflieven.challenges;

import laboflieven.*;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.BruteForceProgramIterator;
import laboflieven.recursionheuristics.NoInvertedHeuristic;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CosPlusFinder implements ProgramTemplate
{
    public static double distance(double lat1, double lat2) {
        return Math.cos(2 * lat1+lat2) ;
    }


    public static void main(String[] args) throws IOException {
        int curMaxRegisters = 2;
        List<InOutParameters> collection = TestCases.getTestCases(new CosPlusFinder(), TestCases.getExampleInput2D(50,10),curMaxRegisters);


        File f = new File("c:\\temp\\test.csv");
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection);
        BruteForceProgramIterator iter = new BruteForceProgramIterator(evaluator, new NoInvertedHeuristic());
        long start = System.currentTimeMillis();
        iter.iterate(curMaxRegisters, 5);
        //evaluator.writeAndClose();
        System.out.println(System.currentTimeMillis() - start + "ms");

        /*iter = new BruteForceProgramIterator(evaluator, new AlwaysRecursionHeuristic());
        start = System.currentTimeMillis();
        iter.iterate(curMaxRegisters, 4);
        //evaluator.writeAndClose();
        */
        System.out.println( "19000ms");

    }

    @Override
    public double run(double[] args) {
        return distance(args[0], args[1]);
    }
}
