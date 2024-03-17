package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EvaluatorTest {
    @Test
    void evaluate_bad_data() {
        TemplateRegister<Boolean> r1 = new TemplateRegister<>("R1");
        var registers = List.of(r1);
        var evaluator = new Evaluator(registers, List.of(List.of(true, true)), List.of(List.of(false, false)));
        assertThrows(IllegalArgumentException.class, () -> evaluator.evaluate(new RegisterFormula(r1)));
    }
}