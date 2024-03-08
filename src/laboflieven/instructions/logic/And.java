package laboflieven.instructions.logic;

public class And implements Formula
{
    private final FormulaOrRegister formulaOrRegister1;
    private final FormulaOrRegister formulaOrRegister2;

    public And(FormulaOrRegister formulaOrRegister1, FormulaOrRegister formulaOrRegister2) {
        this.formulaOrRegister1 = formulaOrRegister1;
        this.formulaOrRegister2 = formulaOrRegister2;
    }

    @Override
    public boolean evaluate() {
        return formulaOrRegister1.evaluate() && formulaOrRegister2.evaluate();
    }
}
