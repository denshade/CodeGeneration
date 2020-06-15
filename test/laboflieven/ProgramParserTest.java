package laboflieven;

import laboflieven.statements.*;
import laboflieven.statements.Instruction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ProgramParserTest {

    @Test
    public void testParse() {
        Instruction add  = new Add(new Register("R1"), new Register("R2"));
        Instruction cos  = new Cos(new Register("R1"));
        Instruction div  = new Div(new Register("R1"), new Register("R2"));
        Instruction invert  = new Invert(new Register("R1"));
        Instruction jumpifzero  = new JumpIfRegister1Zero(new Register("R1"), new Register("R2"));
        Instruction log  = new Log(new Register("R1"));
        Instruction mod  = new Mod(new Register("R1"), new Register("R2"));
        Instruction move  = new Move(new Register("R1"), new Register("R2"));
        Instruction mul  = new Mul(new Register("R1"), new Register("R2"));
        Instruction nand  = new Nand(new Register("R1"), new Register("R2"));
        Instruction sin  = new Sin(new Register("R1"));
        Instruction sqrt  = new Sqrt(new Register("R1"));
        Instruction sub  = new Sub(new Register("R1"), new Register("R2"));
        List<InstructionMark> ins = Arrays.asList(new Instruction[]{add,cos, div, invert, jumpifzero, log, mod, move, mul, nand, sin, sqrt, sub});
        List<InstructionMark> instructs = ProgramParser.parse(ins.toString());
        assertEquals(13, instructs.size());
        int i = 0;
        assertEquals(add, instructs.get(i++));
        assertEquals(cos, instructs.get(i++));
        assertEquals(div, instructs.get(i++));
        assertEquals(invert, instructs.get(i++));
        assertEquals(jumpifzero, instructs.get(i++));
        assertEquals(log, instructs.get(i++));
        assertEquals(mod, instructs.get(i++));
        assertEquals(move, instructs.get(i++));
        assertEquals(mul, instructs.get(i++));
        assertEquals(nand, instructs.get(i++));
        assertEquals(sin, instructs.get(i++));
        assertEquals(sqrt, instructs.get(i++));
        assertEquals(sub, instructs.get(i++));
    }

    @Test
    public void testSqrt()
    {
        //-b + sqrt((b² - 4ac)) / 2a
        // 1 R4 = R2
        // 2 R4 *= R4 => b²
        // 3 R3 = R3 * R1
        // 4 R3 += R3
        // 5 R3 += R3. 4AC
        // 6 R3 = Invert(R3) = -4AC
        // 7 R3 += R4
        // 8 R3 = sqrt(R3) =
        // 9 R2 = invert(R2) = -b
        // 10 R1 = R1 + R1
        // 11 R2 = R2 + R3 = -b + sqrt(b² - 4ac)
        // 12 R2 = R2 / R1 . Final.
        List<Register> registers = Register.createRegisters(4,"R");
        InOutParameters io = new InOutParameters();
        List<InOutParameters> collection = new ArrayList<>();
        List<InstructionMark> instr = ProgramParser.parse("[Move R2 -> R4, Mult R4 -> R4, Mult R1 -> R3, R3 += R3, R3 += R3, Invert R3, R3 += R4, Sqrt R3 " +
                ", Invert R2, R1 += R1, R2 += R3, R2 /= R1, Move R2 -> R1 ]");
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {2.0,-8.0,-24.0,0}, 4), calcQuad(new double [] {2.0,-8.0,-24.0}),1, "R"));
        ProgramFitnessExaminerInterface e = new ProgramFitnessExaminer(collection);
        assertTrue(e.calculateFitness(instr, registers) < 0.001);
    }

    private static double calcQuad(double[] args)
    {
        double a = args[0];
        double b = args[1];
        double c = args[2];
        return (-b + (Math.sqrt(b*b - 4*a*c))) / (2*a);
    }
}