package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotTest {

    @Test
    void evaluate() {

        TemplateRegister<Boolean> a = new TemplateRegister<>("A", false);
        var not = new Not();
        not.setLeft(new RegisterFormula(a));
        assertTrue(not.evaluate());
        TemplateRegister<Boolean> b = new TemplateRegister<>("A", true);
        not = new Not();
        not.setLeft(new RegisterFormula(b));
        assertFalse(not.evaluate());
    }
}