package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;

import java.util.*;

public class RandomIterator
{
    public Formula iterate(List<TemplateRegister<Boolean>> registers, List<List<Boolean>> solutionsThatYieldTrue, List<List<Boolean>> solutionsThatYieldFalse) {
        while (true) {
            try {
                var form = randomFormula(registers);
                if (!checkValuesThatYieldTrue(registers, solutionsThatYieldTrue, form))continue;
                if (!checkValuesThatYieldFalse(registers, solutionsThatYieldTrue, form))continue;
                return form;
            } catch (IllegalStateException ise) {
            }
        }
    }

    private static boolean checkValuesThatYieldTrue(List<TemplateRegister<Boolean>> registers, List<List<Boolean>> solutionsThatYieldTrue, Formula form) {
        for (int l = 0; l < solutionsThatYieldTrue.size(); l++) {
            var scenario = solutionsThatYieldTrue.get(l);
            for (int i = 0; i < scenario.size(); i++) {
                registers.get(i).value = scenario.get(i);
            }
            boolean trueVals = form.evaluate();
            if(!trueVals) {
                return false;
            }
        }
        return true;
    }
    private static boolean checkValuesThatYieldFalse(List<TemplateRegister<Boolean>> registers, List<List<Boolean>> solutionsThatYieldTrue, Formula form) {
        for (int l = 0; l < solutionsThatYieldTrue.size(); l++) {
            var scenario = solutionsThatYieldTrue.get(l);
            for (int i = 0; i < scenario.size(); i++) {
                registers.get(i).value = scenario.get(i);
            }
            boolean falseVals = form.evaluate();
            if(falseVals) {
                return false;
            }
        }
        return true;
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
