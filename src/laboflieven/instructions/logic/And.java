package laboflieven.instructions.logic;

public class And extends DualParameterFormula
{

    public And(Formula parent) {
        super(parent);
    }

    @Override
    public boolean evaluate() {
        return left.evaluate() && right.evaluate();
    }

}
