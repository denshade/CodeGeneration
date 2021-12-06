package laboflieven.accinstructions;

import laboflieven.Program;
import laboflieven.humanresource.instructions.JumpIfZero;
import laboflieven.runners.AccStatementRunner;
import laboflieven.runners.StatementRunner;
import laboflieven.statements.Register;
import laboflieven.statements.VectorRegister;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LoadVectorSumIntoLeftTest {

    @Test
    void execute() {
        var v = new LoadVectorSumIntoLeft();
        Register left = new Register("L1");
        Register right = new Register("R1");
        VectorRegister leftV = new VectorRegister("L1");
        leftV.value.add(1.0);
        leftV.value.add(1.0);
        VectorRegister rightV = new VectorRegister("R1");
        v.execute(left, right, leftV, rightV, 1);
        assertEquals(2, left.value);
    }
    @Test
    void testPrimes() {
        var r1 = new Register("R1");
        var loadIntoLeftAcc = new LoadIntoLeftAcc(r1);
        var vectorSumIntoLeft = new LoadVectorSumIntoLeft();
        var loadAccLeftIntoVector = new LoadAccLeftIntoVector();
        var dec = new Dec();
        var jumpIfZero = new Jump2IfLte();
        var inc = new Inc();
        var loadIntoRegister = new LoadAccLeftIntoRegister(r1);
        var loadZeroIntoRegister = new LoadAccRightIntoRegister(r1);
        var quit = new Quit();

        var runner = new AccStatementRunner();
        Program p = new Program(List.of(
                loadIntoLeftAcc,
                loadZeroIntoRegister,
                loadAccLeftIntoVector,
                vectorSumIntoLeft,
                dec,//make sure primes are 0
                jumpIfZero,
                quit,
                inc,
                loadIntoRegister
        ), List.of(r1));

        runner.execute(p, Map.of("R1", 7.0));
        assertEquals(p.getRegisters().get(0).value, 1);
        runner.execute(p, Map.of("R1", 8.0));
        assertEquals(p.getRegisters().get(0).value, 0);

    }

}