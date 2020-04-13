package laboflieven.challenges;

import laboflieven.*;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.recursionheuristics.NoInvertedHeuristic;
import laboflieven.statements.InstructionEnum;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XorFinder implements ProgramTemplate
    {
        public static double distance(double lat1, double lat2) {
            boolean a = lat1 > 0.0001;
            boolean b = lat2 > 0.0001;
            return (a^b)?1.0:0.0;
        }


        public static void main(String[] args) throws IOException {
            int curMaxRegisters = 2;
            List<double[]> points = new ArrayList<>();
            points.add(new double[] { 0,0});
            points.add(new double[] { 0,1});
            points.add(new double[] { 1,0});
            points.add(new double[] { 1,1});

            List<InOutParameters> collection = TestCases.getTestCases(new XorFinder(), points.toArray(new double[0][0]),curMaxRegisters);
            File f = new File("c:\\temp\\test.csv");
            ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection);
            BruteForceProgramIterator iter = new BruteForceProgramIterator(evaluator, new AlwaysRecursionHeuristic(), InstructionEnum.anyExcept(InstructionEnum.JmpIfZero));
            long start = System.currentTimeMillis();
            iter.iterate(curMaxRegisters, 2);
            //evaluator.writeAndClose();
            System.out.println(System.currentTimeMillis() - start + "ms");
        }

        @Override
        public double run(double[] args) {
            return distance(args[0], args[1]);
        }
}
