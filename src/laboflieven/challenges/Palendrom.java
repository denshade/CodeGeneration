package laboflieven.challenges;

import laboflieven.InOutParameters;
import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.accinstructions.*;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.SysOutAccFitnessLogger;
import laboflieven.loggers.TimingAccFitnessLogger;
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
        int nrInstructions = 50;
        if (args.length > 0) {
            nrInstructions = Integer.parseInt(args[0]);
        }
        int curMaxRegisters = 2;
        System.out.println("Running at #instructions: " + nrInstructions + " #nrRegisters:" + curMaxRegisters);
        List<double[]> points = new ArrayList<>();
        points.add(new double[]{1445});
        points.add(new double[]{1441});
        points.add(new double[]{1111});
        points.add(new double[]{2222});
        points.add(new double[]{555555});
        points.add(new double[]{5555556});
        points.add(new double[]{999999});
        points.add(new double[]{1234});
        points.add(new double[]{4321});
        points.add(new double[]{12});
        points.add(new double[]{11});


        List<InOutParameters> collection = TestCases.getTestCases(new Palendrom(), points.toArray(new double[0][0]),curMaxRegisters);
        var evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new TimingAccFitnessLogger(10000));
        RandomProgramIterator iter = new RandomProgramIterator(evaluator);
        iter.instructionFactory = new InstructionFactory();

        /*GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator(evaluator,
                AccInstructionOpcodeEnum.values(),
                new CombinedHeuristic(List.of(
                        new AccHeuristic()
                        )));*/
        long start = System.currentTimeMillis();
        iter.iterate(curMaxRegisters, nrInstructions);
        //evaluator.writeAndClose();
        System.out.println(System.currentTimeMillis() - start + "ms");
//[ left = R1, R1 = left, left = log(left),  right = R2,
// swap = left, left = right, right = swap,
// Jump if left <= right goto this + 2,  Jump if left >= right goto this + 2,
// left = R1,
// left = left * right,
// R1 = right, left = log(left), left = left - right, left = left ^ right, left = cos(left), left = sqrt(left),
// left = left ^ right, left = left * right, left = left - right, left = nand(left, right), R1 = left]
        /*var registers = Register.createRegisters(2, "R");
        List<InstructionMark> instructions = List.of(
                new AccLeftPush(registers.get(0)),
                //new AccLeftPull(registers.get(0)),
                new Log(),
                new AccRightPush(registers.get(1)),
                new Swap(),
                new Jump2IfLte(),
                new Jump2IfGte(),
                //new Mul(),
                new AccLeftPush(registers.get(0)),
                new Mul(),
               // new AccRightPull(registers.get(0)),
                new Log(),
                new Sub(),
                new Pow(),
                new Cos(),
                new Sqrt(),
                new Pow(),
                new Mul(),
                new Sub(),
                new Nand(),
                new AccLeftPull(registers.get(0))
        );
        var runner = new AccStatementRunner();
        runner.verbose = true;
        var program = new Program(instructions, registers);
        runner.execute(program, collection.get(4).input);
        System.out.println(instructions);
        //System.out.println("vs");
        //System.out.println("[ left = R1, R1 = left, left = log(left),  right = R2, swap = left, left = right, right = swap,  Jump if left <= right goto this + 2,  Jump if left >= right goto this + 2,  left = R1, left = left * right, R1 = right, left = log(left), left = left - right, left = left ^ right, left = cos(left), left = sqrt(left), left = left ^ right, left = left * right, left = left - right, left = nand(left, right), R1 = left]);");
        System.out.println(registers.get(0).value);
        System.out.println("Error " + evaluator.calculateFitness(instructions, registers));
        System.out.println(collection.get(4).expectedOutput);
*/
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
