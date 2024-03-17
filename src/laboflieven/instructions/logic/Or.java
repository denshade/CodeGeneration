package laboflieven.instructions.logic;

public class Or extends DualParameterFormula
{
    @Override
    public boolean evaluate() {
        return left.evaluate() || right.evaluate();
    }

}
