package laboflieven.instructions.logic;

import laboflieven.registers.Register;
import laboflieven.registers.TemplateRegister;

import java.util.*;

public class RandomIterator
{
    private final Evaluator evaluator;

    public RandomIterator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    public Formula iterate(List<TemplateRegister<Boolean>> registers) {
        while (true) {
            try {
                var form = randomFormula(registers);
                if (evaluator.evaluate(form)) {
                    return form;
                }
            } catch (IllegalStateException ise) {
            }
        }
    }

    public static Formula randomFormula(List<TemplateRegister<Boolean>> registers) {
        Queue<BooleanTreeBuilder.SymbolOrRegister> q = new ArrayDeque();
        for (int i = 0; i < 100; i++){
            q.add(randomSymbol(registers));
        }
        var builder = new BooleanTreeBuilder();
        return builder.buildTree(q);
    }

    private static BooleanTreeBuilder.SymbolOrRegister randomSymbol(List<TemplateRegister<Boolean>> registers) {
        var results = new ArrayList<BooleanTreeBuilder.SymbolOrRegister>();
        for (var register: registers) {
            results.add(new BooleanTreeBuilder.SymbolOrRegister(register));
        }
        results.add(new BooleanTreeBuilder.SymbolOrRegister(BooleanTreeBuilder.Symbol.And));
        results.add(new BooleanTreeBuilder.SymbolOrRegister(BooleanTreeBuilder.Symbol.Or));
        results.add(new BooleanTreeBuilder.SymbolOrRegister(BooleanTreeBuilder.Symbol.Not));
        Random r = new Random();
        var l = r.nextInt(results.size() - 1);

        return results.get(l);
    }

}
