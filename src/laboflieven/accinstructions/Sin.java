package laboflieven.accinstructions;

import laboflieven.statements.Register;

/**
 * Created by Lieven on 23-4-2016.
 */
public class Sin extends AccRegisterInstruction {

    public Integer execute(Register left, Register right, int ip)
    {
        left.value = Math.sin(left.value);
        return null;
    }

    public String toString()
    {
        return  "left = sin(left)";    }
    @Override
    public Object getInstructionOpcode() {
        return AccInstructionOpcode.Sin;
    }
}
