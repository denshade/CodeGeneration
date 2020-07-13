package laboflieven.challenges;

import laboflieven.*;
import laboflieven.accinstructions.AccProgramResolution;
import laboflieven.accinstructions.AccInstructionOpcode;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.AccRandomGeneticProgramIterator;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.programiterators.GeneralRandomGeneticProgramIterator;
import laboflieven.recursionheuristics.AccHeuristic;
import laboflieven.runners.AccStatementRunner;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class FormulaFinderTest {

    @Test
    public void mainBruteAccLog() {
        int curMaxRegisters = 2;
        double[][] doubles = TestCases.getExampleInput2D();
        List<InOutParameters> collection = TestCases.getTestCases(args -> {
            double a = args[0];
            double b = args[1];
            return Math.log(a)/Math.log(b);
        }, doubles, curMaxRegisters);
        //laboflieven.accinstructions.InstructionEnum[] enums = laboflieven.accinstructions.InstructionEnum.values();
        AccInstructionOpcode[] enums = new AccInstructionOpcode[] {
                AccInstructionOpcode.Log,
                AccInstructionOpcode.Div,
                AccInstructionOpcode.AccLeftPull,
                AccInstructionOpcode.AccRightPull,
                AccInstructionOpcode.AccLeftPush,
                AccInstructionOpcode.AccRightPush,
                AccInstructionOpcode.Swap
        };
        //enums = new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Log};
        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection,new AccStatementRunner());
        GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator(evaluator, enums, new AccHeuristic());
        long now = System.currentTimeMillis();

        assertEquals(1,iter.iterate(curMaxRegisters, 7).size());
        System.out.println("timing: " + (System.currentTimeMillis() - now));
        //timing: 127327 => 115903 => 107699 => 99132 => 67878(Desktop) => 13949
    }

    @Test
    public void mainGeneticAccLog() {
        int curMaxRegisters = 2;
        double[][] doubles = TestCases.getExampleInput2D();
        List<InOutParameters> collection = TestCases.getTestCases(args -> {
            double a = args[0];
            double b = args[1];
            return Math.log(a)/Math.log(b);
        }, doubles, curMaxRegisters);
        //laboflieven.accinstructions.InstructionEnum[] enums = laboflieven.accinstructions.InstructionEnum.values();
        AccInstructionOpcode[] enums = new AccInstructionOpcode[] {
                AccInstructionOpcode.Log,
                AccInstructionOpcode.Div,
                AccInstructionOpcode.AccLeftPull,
                AccInstructionOpcode.AccRightPull,
                AccInstructionOpcode.AccLeftPush,
                AccInstructionOpcode.AccRightPush,
                AccInstructionOpcode.Swap
        };
        //enums = new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Log};
        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection,new AccStatementRunner());
        GeneralRandomGeneticProgramIterator iter = new GeneralRandomGeneticProgramIterator(evaluator, enums, 10000, 1.4, 0.4, new AccHeuristic());
        long now = System.currentTimeMillis();

        assertEquals(0,iter.iterate(curMaxRegisters, 7).weight, 0.001);
        System.out.println("timing: " + (System.currentTimeMillis() - now));
        //timing: 127327 => 115903 => 107699 => 99132 => 67878(Desktop)
    }


    @Test
    public void mainBruteSinLog() {
        int curMaxRegisters = 1;
        double[][] doubles = TestCases.getExampleInput1D();
        List<InOutParameters> collection = TestCases.getTestCases(args -> {
            double a = args[0];
            return Math.sin(a);
        }, doubles, curMaxRegisters);
        //laboflieven.accinstructions.InstructionEnum[] enums = laboflieven.accinstructions.InstructionEnum.values();
        AccInstructionOpcode[] enums = new AccInstructionOpcode[] {
                AccInstructionOpcode.Add, AccInstructionOpcode.Div, AccInstructionOpcode.Invert, AccInstructionOpcode.Mul, AccInstructionOpcode.Sqrt,
                AccInstructionOpcode.Sub,  AccInstructionOpcode.Cos, AccInstructionOpcode.Mod, AccInstructionOpcode.Nand, AccInstructionOpcode.Log, AccInstructionOpcode.AccLeftPull,
                AccInstructionOpcode.AccLeftPush, AccInstructionOpcode.AccRightPush, AccInstructionOpcode.AccRightPull, AccInstructionOpcode.JumpIfLteStart, AccInstructionOpcode.JumpIfGteStart
        };
        //enums = new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Log};
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection,new AccStatementRunner());
        GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator(evaluator, enums, new AccHeuristic());
        iter.iterate(curMaxRegisters, 10);
    }

    @Test
    public void mainBruteAccLogAlternative() {
        int curMaxRegisters = 1;
        double[][] doubles = TestCases.getExampleInput1D();
        List<InOutParameters> collection = TestCases.getTestCases(new ProgramTemplate() {
            @Override
            public double run(double[] args) {
                double a = args[0];
                return Math.log(a);
            }
        }, doubles, curMaxRegisters);
        //laboflieven.accinstructions.InstructionEnum[] enums = laboflieven.accinstructions.InstructionEnum.values();
        //Add, Div, Invert, Mul, Sqrt, Sub, Sin, Cos, Mod, Nand, Log, AccLeftPull, AccLeftPush, AccRightPush, AccRightPull, JumpIfLte, JumpIfGte
        AccInstructionOpcode[] enums = new AccInstructionOpcode[] {
                AccInstructionOpcode.Add,
                AccInstructionOpcode.Div,
                AccInstructionOpcode.Invert,
                AccInstructionOpcode.Mul,
                AccInstructionOpcode.Sqrt,
                AccInstructionOpcode.Sub,
                AccInstructionOpcode.Sin,
                AccInstructionOpcode.Cos,
                AccInstructionOpcode.JumpIfGteStart,
                AccInstructionOpcode.JumpIfLteStart,
                AccInstructionOpcode.AccLeftPull,
                AccInstructionOpcode.AccRightPull,
                AccInstructionOpcode.AccLeftPush,
                AccInstructionOpcode.AccRightPush
        };
        //enums = new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Log};
        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection,new AccStatementRunner());
        GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator(evaluator, enums);
        iter.iterate(curMaxRegisters, 9);
    }

    @Test
    public void mainBruteAccBpowerm4ac() {
        int curMaxRegisters = 2;
        double[][] doubles = TestCases.getExampleInput2D();
        List<InOutParameters> collection = TestCases.getTestCases(new ProgramTemplate() {
            @Override
            public double run(double[] args) {
                double a = args[0];
                double b = args[1];
                double c = 1;
                return Math.sqrt(b*b-4*a*c)/(2*a);
            }
        }, doubles, curMaxRegisters);
        //laboflieven.accinstructions.InstructionEnum[] enums = laboflieven.accinstructions.InstructionEnum.values();
        AccInstructionOpcode[] enums = new AccInstructionOpcode[] {
                AccInstructionOpcode.Add,
                AccInstructionOpcode.Sub,
                AccInstructionOpcode.Mul,
                AccInstructionOpcode.Div,
                AccInstructionOpcode.AccLeftPull,
                AccInstructionOpcode.AccRightPull,
                AccInstructionOpcode.AccLeftPush,
                AccInstructionOpcode.AccRightPush
        };
        //enums = new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Log};
        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection,new AccStatementRunner());
        GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator(evaluator, enums);
        iter.iterate(curMaxRegisters, 10);
    }

    @Test
    public void mainGeneticAccBpowerm4ac() {
        int curMaxRegisters = 2;
        double[][] doubles = TestCases.getExampleInput2D();
        List<InOutParameters> collection = TestCases.getTestCases(new ProgramTemplate() {
            @Override
            public double run(double[] args) {
                double a = args[0];
                double b = args[1];
                double c = 1;
                return Math.sqrt(b*b-4*a*c)/(2*a);
            }
        }, doubles, curMaxRegisters);
        AccInstructionOpcode[] enums = AccInstructionOpcode.values();
        /*InstructionEnum[] enums = new InstructionEnum[] {
                InstructionEnum.Add,
                InstructionEnum.Sub,
                InstructionEnum.Mul,
                InstructionEnum.Div,
                InstructionEnum.AccLeftPull,
                InstructionEnum.AccRightPull,
                InstructionEnum.AccLeftPush,
                InstructionEnum.AccRightPush
        };*/
        //enums = new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Log};
        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection,new AccStatementRunner());
        AccRandomGeneticProgramIterator iter = new AccRandomGeneticProgramIterator(evaluator, enums, 1000000, 1.2, 0.6);
        iter.nrChildren = 5;
        iter.initialPopSize = 100000;
        iter.iterate(curMaxRegisters, 30);
    }

    @Test
    public void mainGeneticAccSqrt() {
        int curMaxRegisters = 1;
        double[][] doubles = TestCases.getExampleInput1D();
        List<InOutParameters> collection = TestCases.getTestCases(args -> {
            double a = args[0];
            return Math.sqrt(a);
        }, doubles, curMaxRegisters);

        //enums = new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Log};
        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        AccRandomGeneticProgramIterator iter = new AccRandomGeneticProgramIterator(evaluator, AccInstructionOpcode.allInstructionsExcept(AccInstructionOpcode.Sqrt), 1000000, 1.2, 0.6);
        for (int i = 0; i< 7; i++) {
            iter.nrChildren = 10;
            iter.initialPopSize = 1000;
            System.out.println(iter.iterate(curMaxRegisters, 30).weight);
        }
    }

    @Test
    public void mainGeneticAccPI() {
        int curMaxRegisters = 1;
        double[][] doubles = TestCases.getExampleInput1D();
        List<InOutParameters> collection = TestCases.getTestCases(args -> {
            double a = args[0];
            return Math.PI;
        }, doubles, curMaxRegisters);

        //enums = new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Log};
        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        AccRandomGeneticProgramIterator iter = new AccRandomGeneticProgramIterator(evaluator, AccInstructionOpcode.values(), 1000000, 1.2, 0.6);
        for (int i = 0; i< 7; i++) {
            iter.nrChildren = 10;
            iter.initialPopSize = 1000;
            AccProgramResolution solution = iter.iterate(curMaxRegisters, 10);
            System.out.println(solution.weight + " " + solution.instructions);
        }
    }


    @Test
    public void mainGeneticAccSqrtRegular() {
        int curMaxRegisters = 1;
        double[][] doubles = TestCases.getExampleInput1D();
        List<InOutParameters> collection = TestCases.getTestCases(args -> {
            double a = args[0];
            return Math.sqrt(a);
        }, doubles, curMaxRegisters);
        AccInstructionOpcode[] enums = AccInstructionOpcode.values();
        for (int i = 0; i< 7; i++) {
            ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
            AccRandomGeneticProgramIterator iter = new AccRandomGeneticProgramIterator(evaluator, enums, 1000000, 1.2, 0.6);
            iter.nrChildren = 5;
            iter.initialPopSize = 100000;
            iter.iterate(curMaxRegisters, 3);
        }
    }



}