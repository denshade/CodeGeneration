package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterFormulaTest {

    @Test
    void registerHasNoParent() {
        var formula = new RegisterFormula();
        assertNull(formula.parent());
    }

    @Test
    void registerHasParent() {
        var formula = new RegisterFormula(new And(null));
        assertNotNull(formula.parent());
    }

    @Test
    void valueIsValueOfRegister() {
        TemplateRegister<Boolean> a = new TemplateRegister<>("A", true);
        var formula = new RegisterFormula(a);
        assertTrue(formula.evaluate());
        TemplateRegister<Boolean> b = new TemplateRegister<>("B", false);
        formula = new RegisterFormula(b);
        assertFalse(formula.evaluate());
    }

    @Test
    void templateRegisterHasNoLeftOrRight() {
        TemplateRegister<Boolean> a = new TemplateRegister<>("A", true);
        var formula = new RegisterFormula(a);

    }
}