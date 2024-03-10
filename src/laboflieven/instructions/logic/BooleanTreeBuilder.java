package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;

import java.text.Normalizer;
import java.util.List;
import java.util.Queue;

public class BooleanTreeBuilder {
    enum Symbol {
        And,
        Or,
        Not
    }

    public static class SymbolOrRegister {

        SymbolOrRegister(Symbol symbol) {
            this.symbol = symbol;
        }
        SymbolOrRegister(TemplateRegister<Boolean> register) {
            this.register = register;
        }

        public Symbol symbol;
        public TemplateRegister<Boolean> register;
    }

    Formula buildTree(Queue<SymbolOrRegister> symbols) {
        var symbolOrRegister = symbols.poll();
        if (symbolOrRegister == null) {
            throw new IllegalStateException("Insufficient tokens for formula");
        }
        if (symbolOrRegister.register != null) {
            return new RegisterFormula(symbolOrRegister.register);
        } else {
            Formula formula;
            if (symbolOrRegister.symbol == Symbol.And) {
                formula = new And(null);
                formula.setLeft(buildTree(symbols));
                formula.setRight(buildTree(symbols));
            }
            if (symbolOrRegister.symbol == Symbol.Or) {
                formula = new And(null);
                formula.setLeft(buildTree(symbols));
                formula.setRight(buildTree(symbols));
            }
            if (symbolOrRegister.symbol == Symbol.Not) {
                formula = new Not(null);
                formula.setLeft(buildTree(symbols));
            } else {
                throw new IllegalStateException("Unknown symbol "+ symbolOrRegister);
            }
            return formula;
        }
    }
}
