package laboflieven.instructions.logic;

public abstract class DualParameterFormula implements Formula
{
    protected Formula parent;
    protected Formula left;
    protected Formula right;

    public DualParameterFormula(Formula parent) {
        this.parent= parent;
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
        return right;
    }

    public void setLeft(Formula formula) {
        this.left = formula;
    }

    public void setRight(Formula formula) {
        right = formula;
    }


    @Override
    public boolean canHaveLeft() {
        return true;
    }

    @Override
    public boolean canHaveRight() {
        return true;
    }
}
