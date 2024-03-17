package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomIteratorTest {

    @Test
    void randomTree() {
        assertNotNull(RandomIterator.randomFormula(List.of(new TemplateRegister<>("R1"))));
    }

    @Test
    void randomIterate() {
        TemplateRegister<Boolean> r1 = new TemplateRegister<>("R1");
        var registers = List.of(r1);
        var evaluator = new Evaluator(registers, List.of(List.of(true)), List.of(List.of(false)));
        var i = new RandomIterator(evaluator);
        var formula = i.iterate(registers);
        r1.value = true;
        assertTrue(formula.evaluate());
        r1.value = false;
        assertFalse(formula.evaluate());

    }


}