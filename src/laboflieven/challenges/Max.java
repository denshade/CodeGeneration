package laboflieven.challenges;

import laboflieven.*;
import laboflieven.accinstructions.AccLeftPull;
import laboflieven.accinstructions.InstructionEnum;
import laboflieven.examiners.AccProgramFitnessExaminer;
import laboflieven.loggers.SysOutAccFitnessLogger;
import laboflieven.programiterators.AccRandomGeneticProgramIterator;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.statements.Register;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Max implements ProgramTemplate
{
    /*
Found a program: [left = R1,  right = R2,  Jump to start if L >= R , R1 = right]

     */
    public static void main(String[] args) throws IOException {
        int curMaxRegisters = 2;
        List<double[]> points = new ArrayList<>();
        points.add(new double[] { 1, 10});
        points.add(new double[] { 10, 10});
        points.add(new double[] { 100, 10});
        List<InOutParameters> collection = TestCases.getTestCases(new Max(), points.toArray(new double[0][0]),curMaxRegisters);
        AccProgramFitnessExaminer evaluator = new AccProgramFitnessExaminer(collection);
        evaluator.addListener(new SysOutAccFitnessLogger(10000));
        GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator(evaluator,  InstructionEnum.values());
        long start = System.currentTimeMillis();
        iter.stopAtFirstSolution = false;
        List<List<InstructionMark>> p = iter.iterate(curMaxRegisters, 4);
        //AccStatementRunner runner = new AccStatementRunner();
        //List<Register> regs = Register.createRegisters(2, "R");
        //runner.execute(new Program(p.get(0), regs), collection.get(0).input);
        //evaluator.writeAndClose();
        System.out.println(System.currentTimeMillis() - start + "ms");
        System.out.println(p);

        //mainT(15,3);
    }

    @Override
    public double run(double[] args) {
        return Math.max(args[0], args[1]);
    }


}
