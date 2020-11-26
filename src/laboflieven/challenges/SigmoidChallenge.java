package laboflieven.challenges;

import laboflieven.InOutParameters;
import laboflieven.InstructionMark;
import laboflieven.accinstructions.*;
import laboflieven.common.Configuration;
import laboflieven.examiners.AccumulatorProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.RandomSysOutAccFitnessLogger;
import laboflieven.programiterators.AccPriorityProgramIterator;
import laboflieven.programiterators.FakeProgramIterator;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.recursionheuristics.AccHeuristic;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.runners.AccStatementRunner;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.List;
//20
//10 ~7.6@ = Jump if left <= right goto this + 2, left = E, left = left ^ right, left = sin(left),  right = R1,  Jump to start if L >= R ,  Jump if left >= right goto this + 2,  left = R1, left = left / right, left = cos(left)
//4 13 @ = right = R1,  Jump if left <= right goto this + 2, Quit, left++
//20 left = left * right,  Jump if left = 0 goto this + 2,  Jump if left >= right goto this + 2, swap = left, left = right, right = swap, left = left % right, left = left * right, left = left + right, left = sqrt(left), left = PI,  right = R1, left = log(left), left = -left, left++, left = left - right, left = left % right, left = left + right, left = left + right, R1 = right, left = left % right, left = left / right
//30 4.8 right = R1, left = -left,  Jump if left >= right goto this + 2,  right = R1, left = -left, left = log(left), left = left * right, left = sqrt(left),  Jump if left >= right goto this + 2,  Jump to start if L >= R , R1 = right,  Jump if left >= right goto this + 2, left = nand(left, right), left = -left, left = log(left), left = -left, left = nand(left, right), left = sin(left), left = left - right, left = sin(left), left = left / right, left = cos(left), left = cos(left), R1 = right, left = PI, left = left ^ right,  Jump to start if L >= R ,  Jump if left >= right goto this + 2, R1 = left,  if left <=  R then goto 0 ] vs. Infinity: [ Jump if left <= right goto this + 2, R1 = left, left = sin(left), left = left % right,  Jump to start if L >= R , left = left ^ right, left = left - right,  Jump if left >= right goto this + 2, left = log(left),  Jump if left = 0 goto this + 2, left = left / right,  Jump if left >= right goto this + 2, left = sin(left), left = sin(left), swap = left, left = right, right = swap, left = PI, left = PI, left = left ^ right, left = sin(left), left = left + right, R1 = left, R1 = right,  Jump if left >= right goto this + 2,  Jump to start if L >= R ,  if left <=  R then goto 0 , left = left - right, left = left - right,  Jump if left = 0 goto this + 2, swap = left, left = right, right = swap, swap = left, left = right, right = swap]
public class SigmoidChallenge implements ProgramTemplate
{
    public static void main(String[] args) {

        int curMaxRegisters = 1;
        List<double[]> points = new ArrayList<>();
        for (double i = -6; i < 6; i += 0.1) {
            points.add(new double[] { i });
        }
        List<InOutParameters> collection = TestCases.getTestCases(new SigmoidChallenge(), points.toArray(new double[0][0]),curMaxRegisters);
        ProgramFitnessExaminerInterface evaluator = new AccumulatorProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new RandomSysOutAccFitnessLogger(10000));
        var conf = new Configuration();
        conf.setInstructionFactory(new InstructionFactory());
        conf.setMaxNrInstructions(9)
        .setFitnessExaminer(evaluator).setNumberOfRegisters(curMaxRegisters).setAccOperations(AccInstructionOpcodeEnum.values())
        .setHeuristic( new AccHeuristic()
                /*new CombinedHeuristic(List.of(
                new ResultsNoDataAfterFiveHeuristic(
                        new AccStatementRunner(),
                        evaluator,
                        Register.createRegisters(curMaxRegisters, "R")
                )
                ,
                new AccHeuristic()
        ))*/);
        var registers = new ArrayList<Register>();
        registers.add(new Register("R1"));
        var solution = new ArrayList<InstructionMark>();
        solution.add(new LoadIntoRightAcc(registers.get(0))); //e^x / (e^x + 1)
        solution.add(new E()); //e^x / (e^x + 1)
        solution.add(new Pow()); //left = e^x //  / (e^x + 1)
        solution.add(new LoadAccLeftIntoRegister(registers.get(0)));
        solution.add(new LoadIntoRightAcc(registers.get(0)));//e^x
        solution.add(new Inc()); //left = e^x + 1, right = e^x;
        solution.add(new Swap());//left = e^x, right = e^x + 1;
        solution.add(new Div());//left = e^x, right = e^x + 1;
        solution.add(new LoadAccLeftIntoRegister(registers.get(0)));

        conf.setRandomAdded(false);
        conf.setAccOperations(new AccInstructionOpcodeEnum[]{
                AccInstructionOpcodeEnum.AccLeftPull,
                AccInstructionOpcodeEnum.E,
                AccInstructionOpcodeEnum.Inc,
                AccInstructionOpcodeEnum.Div,
                AccInstructionOpcodeEnum.Pow,
                AccInstructionOpcodeEnum.Swap,
                AccInstructionOpcodeEnum.AccLeftPush,
        });
        conf.setCutPopulationAtMax(Integer.MAX_VALUE);
        //var v = new FakeProgramIterator(evaluator, solution, conf.getHeuristic(new AlwaysRecursionHeuristic()));
        var v = new AccPriorityProgramIterator();
        v.iterate(conf);
        //GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator();
        //long start = System.currentTimeMillis();
        //System.out.println(iter.iterate(conf));
        //evaluator.writeAndClose();*/
        //System.out.println(System.currentTimeMillis() - start + "ms");

        //mainT(15,3);
    }

    @Override
    public double run(double[] args) {
        return F(args[0]);
    }

    public double F(double n)
    {
        Sigmoid sigmoid = new Sigmoid();
        return sigmoid.value(n);
    }


}
