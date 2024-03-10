package laboflieven.instructions.logic;

public class Or extends DualParameterFormula
{
    public Or(Formula parent) {
        super(parent);
    }

    @Override
    public boolean evaluate() {
        return left.evaluate() || right.evaluate();
    }

}
