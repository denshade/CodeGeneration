package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;

import java.util.List;

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

    Formula buildTree(List<SymbolOrRegister> symbols) {
        var symbol = symbols.get(0);
        if (symbol.register != null) {
            return new RegisterFormula(symbol.register);
        }
        return null;
    }
}
