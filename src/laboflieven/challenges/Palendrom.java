package laboflieven.challenges;

import laboflieven.TestcaseInOutParameters;
import laboflieven.StoppedByUserException;
import laboflieven.accinstructions.*;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.programiterators.ProgramIterator;
import laboflieven.programiterators.RandomProgramIterator;
import laboflieven.runners.AccStatementRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Palendrom implements ProgramTemplate
{
    /*
[ Jump if left >= right goto this + 2, left = nand(left, right),  left = R1,  Jump if left <= right goto this + 2, left = log(left), R1 = left, R1 = left, left = log(left),  left = R1,  right = R1,  right = R1,  right = R1, left = left * right, left = left ^ right, R1 = left]
     */
    public static void main(String[] args) throws IOException {
        int nrInstructions = 30;
        if (args.length > 0) {
            nrInstructions = Integer.parseInt(args[0]);
        }
        int curMaxRegisters = 2;
        List<double[]> points = new ArrayList<>();
        for (int i: new int[]{1445,1441,1111,2222,555555,5555556,999999,1234,4321,12,11})
            points.add(new double[]{i});
        var random = new Random(12);
        for (int i = 0; i < 100; i++)
            points.add(new double[]{random.nextInt(99999999)});

        List<TestcaseInOutParameters> collection = TestCases.getTestCases(new Palendrom(), points.toArray(new double[0][0]),curMaxRegisters);
        for (int i = 0; i < 10; i++)
        {
            runIteration(nrInstructions, curMaxRegisters, collection);
        }
    }

    private static void runIteration(int nrInstructions, int curMaxRegisters, List<TestcaseInOutParameters> collection) {
        System.out.println("Running at #instructions: " + nrInstructions + " #nrRegisters:" + curMaxRegisters);

        var evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        Configuration config = Configuration.getInstance();
        config.setFitnessExaminer(evaluator).setInstructionFactory(new InstructionFactory()).setMaxNrInstructions(nrInstructions);
        ProgramIterator iter = new RandomProgramIterator();
        long start = System.currentTimeMillis();
        try {
            iter.iterate(config);
        } catch(StoppedByUserException stopped)
        {
            System.out.println("Stopped!");
        }
        //evaluator.writeAndClose();
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    @Override
    public double run(double[] args) {
        return isPalendrome((int)args[0])?1:0;
    }

    public boolean isPalendrome(int n) {
        StringBuilder builder = new StringBuilder();
        builder.append(n);
        return (builder.toString().equals(builder.reverse().toString()));
    }


}
