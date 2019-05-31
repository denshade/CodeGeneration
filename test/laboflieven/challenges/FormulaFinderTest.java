package laboflieven.challenges;

import laboflieven.AccBruteForceProgramIterator;
import laboflieven.AccProgramFitnessExaminer;
import laboflieven.AccRandomGeneticProgramIterator;
import laboflieven.InOutParameters;
import laboflieven.accinstructions.InstructionEnum;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class FormulaFinderTest {

    @Test(expected = RuntimeException.class)
    public void mainBruteAccLog() {
        int curMaxRegisters = 2;
        double[][] doubles = TestCases.getExampleInput2D();
        List<InOutParameters> collection = TestCases.getTestCases(new ProgramTemplate() {
            @Override
            public double run(double[] args) {
                double a = args[0];
                double b = args[1];
                return Math.log(a)/Math.log(b);
            }
        }, doubles, curMaxRegisters);
        //laboflieven.accinstructions.InstructionEnum[] enums = laboflieven.accinstructions.InstructionEnum.values();
        InstructionEnum[] enums = new InstructionEnum[] {
                InstructionEnum.Log,
                InstructionEnum.Div,
                InstructionEnum.AccLeftPull,
                InstructionEnum.AccRightPull,
                InstructionEnum.AccLeftPush,
                InstructionEnum.AccRightPush
        };
        //enums = new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Log};
        AccProgramFitnessExaminer evaluator = new AccProgramFitnessExaminer(collection);
        AccBruteForceProgramIterator iter = new AccBruteForceProgramIterator(evaluator, enums);
        iter.iterate(curMaxRegisters, 8);
    }

    @Test(expected = RuntimeException.class)
    public void mainBruteSinLog() {
        int curMaxRegisters = 1;
        double[][] doubles = TestCases.getExampleInput1D();
        List<InOutParameters> collection = TestCases.getTestCases(new ProgramTemplate() {
            @Override
            public double run(double[] args) {
                double a = args[0];
                return Math.sin(a);
            }
        }, doubles, curMaxRegisters);
        //laboflieven.accinstructions.InstructionEnum[] enums = laboflieven.accinstructions.InstructionEnum.values();
        InstructionEnum[] enums = new InstructionEnum[] {
                InstructionEnum.Add, InstructionEnum.Div, InstructionEnum.Invert, InstructionEnum.Mul, InstructionEnum.Sqrt,
                InstructionEnum.Sub,  InstructionEnum.Cos, InstructionEnum.Mod, InstructionEnum.Nand, InstructionEnum.Log, InstructionEnum.AccLeftPull,
                InstructionEnum.AccLeftPush, InstructionEnum.AccRightPush, InstructionEnum.AccRightPull, InstructionEnum.JumpIfLte, InstructionEnum.JumpIfGte
        };
        //enums = new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Log};
        AccProgramFitnessExaminer evaluator = new AccProgramFitnessExaminer(collection);
        AccBruteForceProgramIterator iter = new AccBruteForceProgramIterator(evaluator, enums);
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
        InstructionEnum[] enums = new InstructionEnum[] {
                InstructionEnum.Add,
                InstructionEnum.Div,
                InstructionEnum.Invert,
                InstructionEnum.Mul,
                InstructionEnum.Sqrt,
                InstructionEnum.Sub,
                InstructionEnum.Sin,
                InstructionEnum.Cos,
                InstructionEnum.JumpIfGte,
                InstructionEnum.JumpIfLte,
                InstructionEnum.AccLeftPull,
                InstructionEnum.AccRightPull,
                InstructionEnum.AccLeftPush,
                InstructionEnum.AccRightPush
        };
        //enums = new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Log};
        AccProgramFitnessExaminer evaluator = new AccProgramFitnessExaminer(collection);
        AccBruteForceProgramIterator iter = new AccBruteForceProgramIterator(evaluator, enums);
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
        InstructionEnum[] enums = new InstructionEnum[] {
                InstructionEnum.Add,
                InstructionEnum.Sub,
                InstructionEnum.Mul,
                InstructionEnum.Div,
                InstructionEnum.AccLeftPull,
                InstructionEnum.AccRightPull,
                InstructionEnum.AccLeftPush,
                InstructionEnum.AccRightPush
        };
        //enums = new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Log};
        AccProgramFitnessExaminer evaluator = new AccProgramFitnessExaminer(collection);
        AccBruteForceProgramIterator iter = new AccBruteForceProgramIterator(evaluator, enums);
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
        laboflieven.accinstructions.InstructionEnum[] enums = laboflieven.accinstructions.InstructionEnum.values();
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
        AccProgramFitnessExaminer evaluator = new AccProgramFitnessExaminer(collection);
        AccRandomGeneticProgramIterator iter = new AccRandomGeneticProgramIterator(evaluator, enums, 1000000, 1.2, 0.6);
        iter.nrChildren = 5;
        iter.iterate(curMaxRegisters, 30);
    }


}