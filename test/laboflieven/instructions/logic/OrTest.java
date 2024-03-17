package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrTest {
    @Test
    void orCheck() {

        TemplateRegister<Boolean> a = new TemplateRegister<>("A", false);
        TemplateRegister<Boolean> b = new TemplateRegister<>("B", false);
        var or = new Or();
        or.setLeft(new RegisterFormula(a));
        or.setRight(new RegisterFormula(b));
        assertFalse(or.evaluate());
    }

    @Test
    void orCheckTrue() {
        TemplateRegister<Boolean> a = new TemplateRegister<>("A", true);
        TemplateRegister<Boolean> b = new TemplateRegister<>("B", false);

        var or = new Or();
        or.setLeft(new RegisterFormula(a));
        or.setRight(new RegisterFormula(b));
        assertTrue(or.evaluate());
    }


}