package laboflieven.challenges;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.instructions.accinstructions.LoadAccLeftIntoRegister;
import laboflieven.instructions.accinstructions.LoadAccLeftIntoVector;
import laboflieven.instructions.accinstructions.LoadIntoLeftAcc;
import laboflieven.instructions.accinstructions.LoadVectorSumIntoLeft;
import laboflieven.runners.AccStatementRunner;
import laboflieven.registers.Register;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

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