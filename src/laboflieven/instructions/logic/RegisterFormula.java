package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;

public class RegisterFormula implements Formula {

    private TemplateRegister<Boolean> register;

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

    @Override
    public void setLeft(Formula left) {
        throw new UnsupportedOperationException("setLeft not supported on register");
    }

    @Override
    public void setRight(Formula right) {
        throw new UnsupportedOperationException("setRight not supported on register");
    }

    public String toString() {
        return register.name;
    }
}
