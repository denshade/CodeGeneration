package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;

public class FormulaOrRegister implements Formula{
    private TemplateRegister<Boolean> register;
    private Formula formula;

    FormulaOrRegister(Formula formula) {
        this.formula = formula;
    }
    FormulaOrRegister(TemplateRegister<Boolean> register) {
        this.register = register;
    }

    @Override
    public boolean evaluate() {
        if (register != null) return register.value;
        return formula.evaluate();
    }
}
