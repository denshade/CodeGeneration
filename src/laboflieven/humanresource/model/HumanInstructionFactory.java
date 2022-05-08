package laboflieven.humanresource.model;


import laboflieven.humanresource.instructions.*;
import laboflieven.humanresource.instructions.Add;
import laboflieven.humanresource.instructions.Sub;

/**
 * Created by lveeckha on 4/06/2015.
 */
public class HumanInstructionFactory
{
    public static HumanInstruction createInstruction(HumanInstructionSet instructionEnum, HumanRegister register1)
    {
        HumanInstruction instruction;
        switch(instructionEnum){
            case CopyFrom:
                instruction = new CopyFrom(register1);
                break;
            case CopyTo:
                instruction = new CopyTo(register1);
                break;
            case ADD:
                instruction = new Add(register1);
                break;
            case Sub:
                instruction = new Sub(register1);
                break;
            case BumpMin:
                instruction = new BumpMin(register1);
                break;
            case BumpPlus:
                instruction = new BumpPlus(register1);
                break;

            default:
                throw new IllegalArgumentException("invalid instruction " + instructionEnum);
        }
        return instruction;
    }

    public static HumanInstruction createLoopInstruction(HumanInstructionSet instructionEnum, int jumpToInstruction)
    {
        HumanInstruction instruction;
        switch(instructionEnum){
            case JUMP:
                instruction = new Jump(jumpToInstruction);
                break;
            case JumpIfZero:
                instruction = new JumpIfZero(jumpToInstruction);
                break;
            case JumpIfNegative:
                instruction = new JumpIfNegative(jumpToInstruction);
                break;
            default:
                throw new IllegalArgumentException("invalid instruction " + instructionEnum);
        }
        return instruction;
    }

    public static HumanInstruction createInstruction(HumanInstructionSet instructionEnum)
    {
        HumanInstruction instruction;
        switch(instructionEnum){
            case INBOX:
                instruction = new Inbox();
                break;
            case OUTBOX:
                instruction = new Outbox();
                break;
            default:
                throw new IllegalArgumentException("invalid instruction " + instructionEnum);
        }
        return instruction;
    }

}
