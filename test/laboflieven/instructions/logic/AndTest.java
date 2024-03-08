package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AndTest {

    @Test
    void andCheck() {
        var and = new And(
                new FormulaOrRegister(
                        new TemplateRegister<>("A", true)),
                new FormulaOrRegister(
                        new TemplateRegister<>("B", false))
                );
        assertFalse(and.evaluate());
    }

    @Test
    void andCheckTrue() {
        var and = new And(
                new FormulaOrRegister(
                        new TemplateRegister<>("A", true)),
                new FormulaOrRegister(
                        new TemplateRegister<>("B", true))
        );
        assertTrue(and.evaluate());
    }

}