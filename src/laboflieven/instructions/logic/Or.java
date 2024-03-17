package laboflieven.instructions.logic;

public class Or extends DualParameterFormula
{
    @Override
    public boolean evaluate() {
        return left.evaluate() || right.evaluate();
    }
    public String toString() {
        return "("+left.toString() + " Or " + right.toString() + ")";
    }
}
