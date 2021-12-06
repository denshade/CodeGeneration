package laboflieven.challenges;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.accinstructions.*;
import laboflieven.runners.AccStatementRunner;
import laboflieven.runners.StatementRunner;
import laboflieven.statements.Register;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PrimeTest {

    @Test
    void run() {
        var r1 = new Register("R1");
        List<InstructionMark> v =
                List.of(
                new LoadIntoLeftAcc(r1),
                new LoadAccLeftIntoVector(),
                new LoadVectorSumIntoLeft(),
                new LoadAccLeftIntoRegister(r1));
        AccStatementRunner runner = new AccStatementRunner();
        runner.verbose = true;
        runner.execute(new Program(v, List.of(r1)), Map.of("R1", 7.0));
    }
}