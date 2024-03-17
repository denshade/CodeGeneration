package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class BruteForceIterator {

    private final Evaluator evaluator;
    private final int maxDepth;
    private final BooleanTreeBuilder treeBuilder;

    public BruteForceIterator(Evaluator evaluator, int maxDepth) {
        this.evaluator = evaluator;
        this.maxDepth = maxDepth;
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
        while(true) {
            var formula = treeBuilder.buildTree(new ArrayDeque<>(convertIndicesToSymbol(currentIndexList, options)));
            if (evaluator.evaluate(formula)) {
                return formula;
            }
            //bump index.
            boolean carry;
            int currentIndex = 0;
            do {
                carry = false;
                if (currentIndexList.get(currentIndex) + 1 == options.size()) {
                    currentIndexList.set(currentIndex,0);
                    currentIndex++;
                    carry = true;
                } else {
                    currentIndexList.set(currentIndex, currentIndexList.get(currentIndex) + 1);
                }
            } while(carry);
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
