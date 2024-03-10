package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;

public class RegisterFormula implements Formula {

    private TemplateRegister<Boolean> register;
    private Formula parent;

    RegisterFormula(Formula parent) {
        this.parent = parent;
    }

    RegisterFormula() {
    }

    RegisterFormula(TemplateRegister<Boolean> register) {
        this.register = register;
    }

    @Override
    public boolean evaluate() {
        return register.value;
    }

    @Override
    public Formula parent() {
        return parent;
    }

    @Override
    public Formula left() {
        return null;
    }

    @Override
    public Formula right() {
        return null;
    }

    @Override
    public boolean canHaveLeft() {
        return false;
    }

    @Override
    public boolean canHaveRight() {
        return false;
    }
}
