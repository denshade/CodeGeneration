package laboflieven.instructions.logic;

public class Not implements Formula
{

    protected Formula parent;
    protected Formula left;

    public Not(Formula parent) {
        this.parent= parent;
    }


    @Override
    public boolean evaluate() {
        return !left.evaluate();
    }

    @Override
    public Formula parent() {
        return parent;
    }

    @Override
    public Formula left() {
        return left;
    }

    @Override
    public Formula right() {
        return null;
    }

    public void setLeft(Formula formula) {
        this.left = formula;
    }


    @Override
    public boolean canHaveLeft() {
        return true;
    }

    @Override
    public boolean canHaveRight() {
        return false;
    }
}
