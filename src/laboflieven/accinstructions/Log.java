package laboflieven.accinstructions;

import laboflieven.statements.Register;

public class Log extends AccRegisterInstruction {


    public Log() {

    }



    public Integer execute(Register left, Register right)
    {
        left.value = Math.log(left.value);
        return null;
    }

    public String toString()
    {
        return  "left = log(left)";
    }
}