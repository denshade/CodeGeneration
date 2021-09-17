package laboflieven.challenges;

import laboflieven.InstructionMark;
import laboflieven.TestcaseInOutParameters;
import laboflieven.accinstructions.*;
import laboflieven.common.Configuration;
import laboflieven.examiners.AccumulatorProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.RandomSysOutAccFitnessLogger;
import laboflieven.programiterators.AccPriorityProgramIterator;
import laboflieven.recursionheuristics.AccHeuristic;
import laboflieven.runners.AccStatementRunner;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.List;

public class DataSourceFinder {
    public static void main(String[] args) {

        int curMaxRegisters = 1;
        List<double[]> points = new ArrayList<>();
        for (double i = -6; i < 6; i += 0.1) {
            points.add(new double[] { i });
        }
        List<TestcaseInOutParameters> collection = TestCases.getTestCases(new SigmoidChallenge(), points.toArray(new double[0][0]),curMaxRegisters);
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
                AccInstructionOpcodeEnum.LoadAccLeftIntoRegister,
                AccInstructionOpcodeEnum.E,
                AccInstructionOpcodeEnum.Inc,
                AccInstructionOpcodeEnum.Div,
                AccInstructionOpcodeEnum.Pow,
                AccInstructionOpcodeEnum.Swap,
                AccInstructionOpcodeEnum.LoadIntoLeftAcc,
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

    public double run(double[] args) {
        return F(args[0]);
    }

    public double F(double n)
    {
        Sigmoid sigmoid = new Sigmoid();
        return sigmoid.value(n);
    }
}
