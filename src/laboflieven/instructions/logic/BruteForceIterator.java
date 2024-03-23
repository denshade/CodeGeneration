package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class BruteForceIterator {

    private final Evaluator evaluator;
    private final BooleanTreeBuilder treeBuilder;

    public BruteForceIterator(Evaluator evaluator, int maxDepth) {
        this.evaluator = evaluator;
        treeBuilder = new BooleanTreeBuilder(maxDepth);
    }

    public Formula iterate(List<TemplateRegister<Boolean>> registers) {
        var currentIndexList = new ArrayList<Integer>();
        for (int i = 0; i < 1000; i++) {
            currentIndexList.add(0);
        }
        var options = new ArrayList<BooleanTreeBuilder.SymbolOrRegister>();
        registers.forEach(r -> options.add(new BooleanTreeBuilder.SymbolOrRegister(r)));
        options.add(new BooleanTreeBuilder.SymbolOrRegister(BooleanTreeBuilder.Symbol.And));
        options.add(new BooleanTreeBuilder.SymbolOrRegister(BooleanTreeBuilder.Symbol.Or));
        options.add(new BooleanTreeBuilder.SymbolOrRegister(BooleanTreeBuilder.Symbol.Not));
        var indexList = new IndexList(options.size());
        while(true) {
            try {
                var formula = treeBuilder.buildTree(new ArrayDeque<BooleanTreeBuilder.SymbolOrRegister>(indexList.convertIndicesToSymbol(currentIndexList, options)));
                if (evaluator.evaluate(formula)) {
                    return formula;
                }
            } catch (IllegalStateException e) {

            }
            indexList.update(currentIndexList);
        }
    }
}
