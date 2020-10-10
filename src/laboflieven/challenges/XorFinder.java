package laboflieven.challenges;

import laboflieven.common.Configuration;

public class XorFinder implements ProgramTemplate
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
                            Configuration.ConfigurationKey.DATA_PROVIDER + "=laboflieven.challenges.XorFinder " +
                            Configuration.ConfigurationKey.PROGRAM_ITERATOR + "=brute"
                            ).split(" ");
            GeneralFinder.loadConfigAndRun(arguments);
        }

        @Override
        public double run(double[] args) {
            return distance(args[0], args[1]);
        }
}
