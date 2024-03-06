package laboflieven.runners;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.instructions.accinstructions.Cos;
import laboflieven.registers.NumberNamingScheme;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SumSeriesAccStatementRunnerTest {

    @Test
    public void testSeries()
    {
        var registers = new NumberNamingScheme().createRegisters(1);
        var instructions = new ArrayList<InstructionMark>();
        var programs = new Program(instructions, registers);
        var series = new SumSeriesAccStatementRunner();
        Map<String, Double> doubleMap = new HashMap<>();
        doubleMap.put("R1", 0.0);
        instructions.add(new Cos());
        var map = series.execute(programs, doubleMap);
        assertEquals(-0.39460748051807437, map.get("R1"));
    }

}