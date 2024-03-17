package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;

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
            Formula formula = switch (symbolOrRegister.symbol) {
                case And -> new And();
                case Or -> new Or();
                case Not -> new Not();
            };
            if (formula.canHaveLeft()) {
                formula.setLeft(buildTree(symbols));
            }
            if (formula.canHaveRight()) {
                formula.setRight(buildTree(symbols));
            }
            return formula;
        }
    }
}
