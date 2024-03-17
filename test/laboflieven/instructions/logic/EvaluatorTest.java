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

    @Test
    void evaluate_success() {
        TemplateRegister<Boolean> r1 = new TemplateRegister<>("R1");
        var registers = List.of(r1);
        var evaluator = new Evaluator(registers, List.of(List.of(true)), List.of(List.of(false)));
        assertTrue(evaluator.evaluate(new RegisterFormula(r1)));
    }
    @Test
    void evaluate_fails() {
        TemplateRegister<Boolean> r1 = new TemplateRegister<>("R1");
        var registers = List.of(r1);
        var evaluator = new Evaluator(registers, List.of(List.of(false)), List.of(List.of(true)));
        assertFalse(evaluator.evaluate(new RegisterFormula(r1)));
    }

    @Test
    void evaluate_fails_secondary() {
        TemplateRegister<Boolean> r1 = new TemplateRegister<>("R1");
        var registers = List.of(r1);
        var evaluator = new Evaluator(registers, List.of(List.of(true)), List.of(List.of(true)));
        assertFalse(evaluator.evaluate(new RegisterFormula(r1)));
    }
}