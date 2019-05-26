package laboflieven.accinstructions;

import laboflieven.statements.Register;

public class Nand extends AccRegisterInstruction {

    public Integer execute(Register left, Register right)
    {
        boolean sourceB = left.value < 0.0000001 ? false: true;
        boolean destinationB = right.value < 0.0000001 ? false: true;
        boolean evaluation = !(sourceB && destinationB);
        left.value = evaluation?1.0:0.0;
        return null;
    }

    public String toString()
    {
        return  "left = left nand right";
    }
}
