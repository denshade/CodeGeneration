package laboflieven.challenges;

import laboflieven.InOutParameters;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.SysOutAccFitnessLogger;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.programiterators.RandomGeneticProgramIterator;
import laboflieven.programiterators.RandomProgramIterator;
import laboflieven.recursionheuristics.AccHeuristic;
import laboflieven.recursionheuristics.CombinedHeuristic;
import laboflieven.recursionheuristics.ResultsNoDataAfterFiveHeuristic;
import laboflieven.runners.AccStatementRunner;
import laboflieven.statements.Register;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Palendrom implements ProgramTemplate
{
    /*
[ Jump if left >= right goto this + 2, left = nand(left, right),  left = R1,  Jump if left <= right goto this + 2, left = log(left), R1 = left, R1 = left, left = log(left),  left = R1,  right = R1,  right = R1,  right = R1, left = left * right, left = left ^ right, R1 = left]
     */
    public static void main(String[] args) throws IOException {

        int curMaxRegisters = 2;
        List<double[]> points = new ArrayList<>();
        points.add(new double[]{1445});
        points.add(new double[]{1441});
        points.add(new double[]{1111});
        points.add(new double[]{2222});
        points.add(new double[]{555555});
        points.add(new double[]{5555556});
        points.add(new double[]{9999});
        points.add(new double[]{1234});
        points.add(new double[]{4321});


        List<InOutParameters> collection = TestCases.getTestCases(new Palendrom(), points.toArray(new double[0][0]),curMaxRegisters);
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new SysOutAccFitnessLogger(10000));
        RandomProgramIterator iter = new RandomProgramIterator(evaluator);
        iter.instructionFactory = new InstructionFactory();

        /*GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator(evaluator,
                AccInstructionOpcodeEnum.values(),
                new CombinedHeuristic(List.of(
                        new AccHeuristic()
                        )));*/
        long start = System.currentTimeMillis();
        iter.iterate(curMaxRegisters, 20);
        //evaluator.writeAndClose();
        System.out.println(System.currentTimeMillis() - start + "ms");

        //mainT(15,3);
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
