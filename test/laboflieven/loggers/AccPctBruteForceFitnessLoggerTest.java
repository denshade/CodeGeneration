package laboflieven.loggers;

import laboflieven.accinstructions.AccLeftPull;
import laboflieven.accinstructions.InstructionEnum;
import laboflieven.accinstructions.Mod;
import laboflieven.accinstructions.Nand;
import laboflieven.statements.Register;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccPctBruteForceFitnessLoggerTest {

    @Test
    void addFitness() {
        AccPctBruteForceFitnessLogger logger = new AccPctBruteForceFitnessLogger(new InstructionEnum[]{InstructionEnum.Mod, InstructionEnum.AccLeftPull}, 1000, 2);
        assertEquals(0.00,logger.calculatePct(List.of(new Mod()), 2, 2));
        assertEquals(0.6666666666666666,logger.calculatePct(List.of(new AccLeftPull(new Register("r1"))), 2, 2));
        assertEquals(0.6666666666666666,logger.calculatePct(List.of(new AccLeftPull(new Register("r1")), new Mod()), 2, 2));
        assertEquals(0.8888888888888888,logger.calculatePct(List.of(new AccLeftPull(new Register("r1")), new AccLeftPull(new Register("r1"))), 2, 2));

    }
    @Test
    void bigProgram()
    {
        AccPctBruteForceFitnessLogger logger = new AccPctBruteForceFitnessLogger(new InstructionEnum[]{InstructionEnum.Mod, InstructionEnum.AccLeftPull, InstructionEnum.Nand}, 1000, 2);
        assertEquals(0.00,logger.calculatePct(List.of(new Mod(), new Mod(), new Mod(), new Mod(), new Mod()), 2, 2));
        assertEquals(0.19921875,logger.calculatePct(List.of(new Mod(), new Nand(), new Mod(), new Nand(), new Mod()), 2, 2));
//        assertEquals(0.24691358024691357,logger.calculatePct(List.of(new Mod(), new Nand(), new Mod(), new Nand(), new AccLeftPull(new Register("r1"))), 2, 2));

    }
}