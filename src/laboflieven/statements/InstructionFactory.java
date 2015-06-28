package laboflieven.statements;

import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 * Created by lveeckha on 4/06/2015.
 */
public class InstructionFactory
{
    public static Instruction createInstruction(InstructionEnum instructionEnum, Register register1, Register register2)
    {
        Instruction instruction;
        switch(instructionEnum){
            case Add:
                instruction = new  Add(register1, register2);
                break;
            case Div:
                instruction = new  Div(register1, register2);
                break;
            case Move:
                instruction = new  Move(register1, register2);
                break;
            case Mul:
                instruction = new  Mul(register1, register2);
                break;
            case Sub:
                instruction = new  Sub(register1, register2);
                break;
            default:
                throw new IllegalArgumentException("invalid instruction " + instructionEnum.toString());
        }
        return instruction;
    }
    public static Instruction createInstruction(InstructionEnum instructionEnum, Register register1)
    {
        Instruction instruction;
        switch(instructionEnum){
            case Invert:
                instruction = new Invert(register1);
                break;
            case Sqrt:
                instruction = new Sqrt(register1);
                break;
            default:
                throw new IllegalArgumentException("invalid instruction " + instructionEnum.toString());
        }
        return instruction;
    }

}
