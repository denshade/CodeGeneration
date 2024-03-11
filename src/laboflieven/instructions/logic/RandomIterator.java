package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;

import java.util.*;

public class RandomIterator
{
    public Formula iterate(List<Boolean> solutionsThatYieldTrue, List<Boolean> solutionsThatYieldFalse) {
        while (true) {
            //generate a random program
            //check if this is an exact match.
            //if exact match then return that formula.
        }
    }

    public static Formula randomFormula() {
        Queue<BooleanTreeBuilder.SymbolOrRegister> q = new ArrayDeque();
        for (int i = 0; i < 100; i++){
            q.add(randomSymbol(2));
        }
        var builder = new BooleanTreeBuilder();
        return builder.buildTree(q);
    }

    private static BooleanTreeBuilder.SymbolOrRegister randomSymbol(int nrRegisters) {
        var results = new ArrayList<BooleanTreeBuilder.SymbolOrRegister>();
        for (int l = 0; l < nrRegisters; l++) {
            results.add(new BooleanTreeBuilder.SymbolOrRegister(new TemplateRegister<>("R" + l)));
        }
        results.add(new BooleanTreeBuilder.SymbolOrRegister(BooleanTreeBuilder.Symbol.And));
        results.add(new BooleanTreeBuilder.SymbolOrRegister(BooleanTreeBuilder.Symbol.Or));
        results.add(new BooleanTreeBuilder.SymbolOrRegister(BooleanTreeBuilder.Symbol.Not));
        Random r = new Random();
        var l = r.nextInt(results.size() - 1);

        return results.get(l);
    }

}
