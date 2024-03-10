package laboflieven.instructions.logic;

import laboflieven.registers.Register;
import laboflieven.registers.TemplateRegister;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class BooleanTreeBuilderTest {

    @Test
    void checkFormulaReturnedForSingleRegister() {
        var stuff = new ArrayDeque(List.of(new BooleanTreeBuilder.SymbolOrRegister(new TemplateRegister<>("R1", true))));
        BooleanTreeBuilder builder = new BooleanTreeBuilder();
        var formula = builder.buildTree(stuff);
        assertNotNull(formula);

    }

}