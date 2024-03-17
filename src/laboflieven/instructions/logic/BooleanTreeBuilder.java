package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;

import java.util.Queue;

public class BooleanTreeBuilder {
    private final int maxDepth;

    enum Symbol {
        And,
        Or,
        Not
    }

    BooleanTreeBuilder(int maxDepth) {
        this.maxDepth = maxDepth;
    }
    BooleanTreeBuilder() {
        this.maxDepth = 10;
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

        public String toString() {
            if (symbol != null) {
                return symbol.toString();
            }
            if (register != null) {
                return register.toString();
            }
            return null;
        }
    }

    Formula buildTree(Queue<SymbolOrRegister> symbols) {
        return buildTree(symbols, 0);
    }

    Formula buildTree(Queue<SymbolOrRegister> symbols, int currentDepth) {
        if (currentDepth > maxDepth) {
            throw new IllegalStateException("Too deep");
        }
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
                formula.setLeft(buildTree(symbols, currentDepth - 1));
            }
            if (formula.canHaveRight()) {
                formula.setRight(buildTree(symbols, currentDepth - 1));
            }
            return formula;
        }
    }
}
