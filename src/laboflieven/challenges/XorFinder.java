package laboflieven.challenges;

import laboflieven.TestcaseInOutParameters;
import laboflieven.common.Configuration;

import java.util.ArrayList;
import java.util.List;

public class XorFinder implements TestcaseSource, ProgramTemplate
    {
        public static double distance(double lat1, double lat2) {
            boolean a = lat1 > 0.0001;
            boolean b = lat2 > 0.0001;
            return (a|b)?1.0:0.0;
        }

        public static void main(String[] args) {
            String[] arguments =
                    (       Configuration.ConfigurationKey.MAX_NR_OF_INSTRUCTIONS + "=3 " +
                            Configuration.ConfigurationKey.NR_REGISTERS + "=2 "+
                            Configuration.ConfigurationKey.INSTRUCTION_FACTORY +"=Regular " +
                            Configuration.ConfigurationKey.DATA_SOURCE + "=laboflieven.challenges.XorFinder " +
                            Configuration.ConfigurationKey.PROGRAM_ITERATOR + "=brute"
                            ).split(" ");
            GeneralFinder.loadConfigAndRun(arguments);
        }

        @Override
        public double run(double[] args) {
            return distance(args[0], args[1]);
        }

        @Override
        public List<TestcaseInOutParameters> getTestCases() {
            List<double[]> points = new ArrayList<>();
            points.add(new double[] { 0,0});
            points.add(new double[] { 0,1});
            points.add(new double[] { 1,0});
            points.add(new double[] { 1,1});
            return TestCases.getTestCases(new XorFinder(), points.toArray(new double[0][0]), 2);
        }
    }
