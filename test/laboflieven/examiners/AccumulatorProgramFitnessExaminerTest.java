package laboflieven.examiners;

import laboflieven.InOutParameters;
import laboflieven.InstructionMark;
import laboflieven.accinstructions.*;
import laboflieven.challenges.TestCases;
import laboflieven.challenges.XorFinder;
import laboflieven.runners.AccStatementRunner;
import laboflieven.statements.Register;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

class AccumulatorProgramFitnessExaminerTest {

    @Test
    void calculateFitness() {
        List<double[]> points = new ArrayList<>();
        points.add(new double[] { 0,0});
        points.add(new double[] { 0,1});
        points.add(new double[] { 1,0});
        points.add(new double[] { 1,1});
        var registers = Register.createRegisters(2, "R");
        List<InstructionMark> instructions = List.of(new LoadIntoLeftAcc(registers.get(0)),new LoadIntoRightAcc(registers.get(1)), new Log(), new Mul(), new Nand()); //Log R2, Mul R2 -> R1, Nand R1 -> R1
        List<InOutParameters> collection = TestCases.getTestCases(new XorFinder(), points.toArray(new double[0][0]),2);

        var f = new AccumulatorProgramFitnessExaminer(collection, new AccStatementRunner());
        assertEquals(1.0,f.calculateFitness(instructions, registers));
    }
}