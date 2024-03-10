package laboflieven.instructions.logic;

public class And implements Formula
{

    private Formula left;
    private Formula right;

    public And(Formula parent) {
    }

    @Override
    public boolean evaluate() {
        return left.evaluate() && right.evaluate();
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
    public boolean canHaveLeft() {
        return false;
    }

    @Override
    public boolean canHaveRight() {
        return false;
    }

    public void setLeft(Formula left) {
        this.left = left;
    }

    public void setRight(Formula right) {
        this.right = right;
    }

}
