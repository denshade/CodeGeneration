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
        for (int i = 0; i < 100; i++) {
            currentIndexList.add(0);
        }
        var options = new ArrayList<BooleanTreeBuilder.SymbolOrRegister>();
        for (var register : registers) {
            options.add(new BooleanTreeBuilder.SymbolOrRegister(register));
        }
        options.add(new BooleanTreeBuilder.SymbolOrRegister(BooleanTreeBuilder.Symbol.And));
        options.add(new BooleanTreeBuilder.SymbolOrRegister(BooleanTreeBuilder.Symbol.Or));
        options.add(new BooleanTreeBuilder.SymbolOrRegister(BooleanTreeBuilder.Symbol.Not));
        var indexList = new IndexList(options.size());
        while(true) {
            var formula = treeBuilder.buildTree(new ArrayDeque<BooleanTreeBuilder.SymbolOrRegister>(indexList.convertIndicesToSymbol(currentIndexList, options)));
            if (evaluator.evaluate(formula)) {
                return formula;
            }
            indexList.update(currentIndexList);
        }
    }

    private List<BooleanTreeBuilder.SymbolOrRegister> convertIndicesToSymbol(ArrayList<Integer> indices, List<BooleanTreeBuilder.SymbolOrRegister> options) {
        var symbols = new ArrayList<BooleanTreeBuilder.SymbolOrRegister>();
        for (int index: indices) {
            symbols.add(options.get(index));
        }
        return symbols;
    }
}
