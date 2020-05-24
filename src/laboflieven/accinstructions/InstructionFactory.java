package laboflieven.accinstructions;


import laboflieven.statements.Register;

/**
 * Created by lveeckha on 4/06/2015.
 */
public class InstructionFactory
{
    public static AccRegisterInstruction createInstruction(InstructionEnum instructionEnum)
    {
        AccRegisterInstruction instruction;
        switch(instructionEnum){
            case Add:
                instruction = new Add();
                break;
            case Div:
                instruction = new Div();
                break;
            case Mod:
                instruction = new  Mod();
                break;
            case Mul:
                instruction = new Mul();
                break;
            case Sub:
                instruction = new  Sub();
                break;
            case Nand:
                instruction = new  Nand();
                break;
            case Invert:
                instruction = new Invert();
                break;
            case Sqrt:
                instruction = new Sqrt();
                break;
            case Sin:
                instruction = new Sin();
                break;
            case Cos:
                instruction = new Cos();
                break;
            case Log:
                instruction = new Log();
                break;
            case JumpIfGteStart:
                instruction = new JumpIfGteStart();
                break;
            case JumpIfLteStart:
                instruction = new JumpIfLteStart();
                break;
            case Jump2IfGte:
                instruction = new Jump2IfGte();
                break;
            default:
                throw new IllegalArgumentException("invalid instruction " + instructionEnum.toString());
        }
        return instruction;
    }

    public static AccRegisterInstruction createInstruction(InstructionEnum instructionEnum, Register register1)
    {
        AccRegisterInstruction instruction;
        switch(instructionEnum){
            case AccLeftPull:
                instruction = new AccLeftPull(register1);
                break;
            case AccLeftPush:
                instruction = new AccLeftPush(register1);
                break;
            case AccRightPush:
                instruction = new AccRightPush(register1);
                break;
            case AccRightPull:
                instruction = new AccRightPull(register1);
                break;
            /*case JumpIfGte:
                instruction = new JumpIfGte(register1);
                break;
            case JumpIfLte:
                instruction = new JumpIfLte(register1);
                break;*/
            default:
                throw new IllegalArgumentException("invalid instruction " + instructionEnum.toString());
        }
        return instruction;
    }

}
