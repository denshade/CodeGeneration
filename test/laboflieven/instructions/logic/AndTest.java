package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AndTest {

    @Test
    void andCheck() {

        TemplateRegister<Boolean> a = new TemplateRegister<>("A", true);
        TemplateRegister<Boolean> b = new TemplateRegister<>("B", false);
        var and = new And(null);
                /*new RegisterFormula(a),
                new RegisterFormula(b)
                );*/
        assertFalse(and.evaluate());
    }

    @Test
    void andCheckTrue() {
        /*var and = new And(
                new FormulaOrRegister(
                        new TemplateRegister<>("A", true)),
                new FormulaOrRegister(
                        new TemplateRegister<>("B", true))
        );
                assertTrue(and.evaluate());
         */
    }

}