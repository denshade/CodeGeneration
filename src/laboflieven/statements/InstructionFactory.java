package laboflieven.statements;


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
            case Mod:
                instruction = new  Mod(register1, register2);
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
            case Nand:
                instruction = new  Nand(register1, register2);
                break;
            case JmpIfZero:
                instruction = new JumpIfRegister1Zero(register1, register2);
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
            case Sin:
                instruction = new Sin(register1);
                break;
            case Cos:
                instruction = new Cos(register1);
                break;
            case Log:
                instruction = new Log(register1);
                break;
            case PI:
                instruction = new PI(register1);
                break;
            case Zero:
                instruction = new Zero(register1);
                break;
            case One:
                instruction = new One(register1);
                break;
            case JmpIfZeroBegin:
                instruction = new JumpIfRegister1ZeroToBegin(register1);
                break;
            case JmpIfZeroEnd:
                instruction = new JumpIfRegister1ZeroToEnd(register1);
                break;
            case JmpIfNotZeroBegin:
                instruction = new JumpIfRegister1NotZeroToBegin(register1);
                break;
            case JmpIfNotZeroEnd:
                instruction = new JumpIfRegister1NotZeroToEnd(register1);
                break;
            case JmpIfRegister2Steps:
                instruction = new JumpIfRegister2Steps(register1);
                break;
            default:
                throw new IllegalArgumentException("invalid instruction " + instructionEnum.toString());
        }
        return instruction;
    }

}
