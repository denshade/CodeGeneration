package laboflieven.instructions.logic;

public class And extends DualParameterFormula
{
    @Override
    public boolean evaluate() {
        return left.evaluate() && right.evaluate();
    }
    public String toString() {
        return "(" + left.toString() + " And " + right.toString() + ")";
    }

}
