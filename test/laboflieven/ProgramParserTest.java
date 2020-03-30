package laboflieven;

import laboflieven.statements.*;
import laboflieven.statements.Instruction;
import org.junit.Test;

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
        List<Instruction> ins = Arrays.asList(new Instruction[]{add,cos, div, invert, jumpifzero, log, mod, move, mul, nand, sin, sqrt, sub});
        List<Instruction> instructs = ProgramParser.parse(ins.toString());
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
}