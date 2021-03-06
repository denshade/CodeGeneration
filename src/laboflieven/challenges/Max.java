package laboflieven.challenges;

import laboflieven.*;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.loggers.RandomSysOutAccFitnessLogger;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.runners.AccStatementRunner;

import java.util.ArrayList;
import java.util.List;

public class Max implements ProgramTemplate
{
    /*
Found a program: [left = R1,  right = R2,  Jump to start if L >= R , R1 = right]

     */
    public static void main(String[] args) {
        int curMaxRegisters = 2;
        List<double[]> points = new ArrayList<>();
        points.add(new double[] { 1, 10});
        points.add(new double[] { 10, 10});
        points.add(new double[] { 100, 10});
        List<TestcaseInOutParameters> collection = TestCases.getTestCases(new Max(), points.toArray(new double[0][0]),curMaxRegisters);
        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new RandomSysOutAccFitnessLogger(10000));
        var conf = new Configuration();
        conf.setMaxNrInstructions(4).setFitnessExaminer(evaluator).setNumberOfRegisters(curMaxRegisters);
        GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator();
        long start = System.currentTimeMillis();
        var p = iter.iterate(conf);
        System.out.println(System.currentTimeMillis() - start + "ms");
        System.out.println(p);
    }

    @Override
    public double run(double[] args) {
        return Math.max(args[0], args[1]);
    }


}
