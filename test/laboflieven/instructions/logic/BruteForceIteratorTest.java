package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BruteForceIteratorTest {

    @Test
    void checkXor() {
        TemplateRegister<Boolean> r1 = new TemplateRegister<>("R1");
        TemplateRegister<Boolean> r2 = new TemplateRegister<>("R2");

        //
        var registers = List.of(r1, r2);
        var evaluator = new Evaluator(registers,
                List.of(
                        List.of(true, false),
                        List.of(false, true)
                ),
                List.of(
                        List.of(true, true),
                        List.of(false, false))
        );
        var i = new BruteForceIterator(evaluator,2);
        var formula = i.iterate(registers);

    }

}