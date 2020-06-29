package laboflieven.challenges;

import laboflieven.*;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.statements.InstructionEnum;
import laboflieven.statements.Register;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XorFinder implements ProgramTemplate
    {
        public static double distance(double lat1, double lat2) {
            boolean a = lat1 > 0.0001;
            boolean b = lat2 > 0.0001;
            return (a|b)?1.0:0.0;
        }


        public static void main(String[] args) throws IOException {
            int curMaxRegisters = 2;
            List<double[]> points = new ArrayList<>();
            points.add(new double[] { 0,0});
            points.add(new double[] { 0,1});
            points.add(new double[] { 1,0});
            points.add(new double[] { 1,1});

            List<InstructionMark> instr = ProgramParser.parse("[R2 += R1, One R1, R2 += R1, Mod R2 -> R1]");
            StatementRunner runner = new StatementRunner();
            Map<String, Double> m = new HashMap<>();
            m.put("R1", 0.0);
            m.put("R2", 0.0);

            runner.execute(new Program(instr, Register.createRegisters(2, "R")), m);

            List<InOutParameters> collection = TestCases.getTestCases(new XorFinder(), points.toArray(new double[0][0]),curMaxRegisters);
            File f = new File("c:\\temp\\test.csv");
            ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection);
            BruteForceProgramIterator iter = new BruteForceProgramIterator(evaluator, new AlwaysRecursionHeuristic(), new InstructionEnum[] {InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Zero, InstructionEnum.One, InstructionEnum.Div, InstructionEnum.Mul, InstructionEnum.Invert, InstructionEnum.Mod});
            long start = System.currentTimeMillis();
            iter.iterate(curMaxRegisters, 3);
            //evaluator.writeAndClose();
            System.out.println(System.currentTimeMillis() - start + "ms");
        }

        @Override
        public double run(double[] args) {
            return distance(args[0], args[1]);
        }
}
