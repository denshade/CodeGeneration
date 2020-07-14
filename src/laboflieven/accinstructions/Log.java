package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;

public class Log extends AccRegisterInstruction {


    public Log() {

    }



    public Integer execute(Register left, Register right, int ip)
    {
        left.value = Math.log(left.value);
        return null;
    }

    public String toString()
    {
        return  "left = log(left)";
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcode.Log);
    }
}