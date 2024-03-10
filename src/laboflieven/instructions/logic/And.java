package laboflieven.instructions.logic;

public class And implements Formula
{
    private Formula formulaOrRegister1;
    private Formula formulaOrRegister2;

    public And(Formula parent) {
    }

    @Override
    public boolean evaluate() {
        return formulaOrRegister1.evaluate() && formulaOrRegister2.evaluate();
    }

    @Override
    public Formula parent() {
        return null;
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
    public boolean hasLeft() {
        return false;
    }

    @Override
    public boolean hasRight() {
        return false;
    }
}
