package laboflieven.instructions.logic;

public class Not implements Formula
{
    protected Formula left;

    @Override
    public boolean evaluate() {
        return !left.evaluate();
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
    public void setRight(Formula right) {
        throw new UnsupportedOperationException("setRight not supported on Not");
    }


    @Override
    public boolean canHaveLeft() {
        return true;
    }

    @Override
    public boolean canHaveRight() {
        return false;
    }
    public String toString() {
        return "Not (" + left.toString() + ")";
    }
}
