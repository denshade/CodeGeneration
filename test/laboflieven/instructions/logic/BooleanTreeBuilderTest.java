package laboflieven.instructions.logic;

import laboflieven.registers.Register;
import laboflieven.registers.TemplateRegister;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BooleanTreeBuilderTest {

    @Test
    void checkFormulaReturnedForSingleRegister() {
        var stuff = new ArrayDeque(List.of(new BooleanTreeBuilder.SymbolOrRegister(new TemplateRegister<>("R1", true))));
        BooleanTreeBuilder builder = new BooleanTreeBuilder();
        var formula = builder.buildTree(stuff);
        assertNotNull(formula);
    }

    @Test
    void checkFormulaReturnedForAndOfTwoSingles() {
        TemplateRegister<Boolean> r1 = new TemplateRegister<>("R1", true);
        TemplateRegister<Boolean> r2 = new TemplateRegister<>("R2", true);
        var stuff = new ArrayDeque(List.of(new BooleanTreeBuilder.SymbolOrRegister(BooleanTreeBuilder.Symbol.And), new BooleanTreeBuilder.SymbolOrRegister(r1), new BooleanTreeBuilder.SymbolOrRegister(r2)));
        BooleanTreeBuilder builder = new BooleanTreeBuilder();
        var formula = builder.buildTree(stuff);
        assertNotNull(formula);
        assertTrue(formula.evaluate());
        r1.value = false;
        assertFalse(formula.evaluate());
    }

}