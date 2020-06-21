package laboflieven.accinstructions;


import laboflieven.InstructionMark;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.Register;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

/**
 * Created by lveeckha on 4/06/2015.
 */
public class InstructionFactory implements InstructionFactoryInterface
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
            case Jump2IfLte:
                instruction = new Jump2IfLte();
                break;
            case Jump2IfEq:
                instruction = new Jump2IfEq();
                break;
            case Jump2IfNeq:
                instruction = new Jump2IfNeq();
                break;
            case Jump2IfZero:
                instruction = new Jump2IfZero();
                break;
            case Quit:
                instruction = new Quit();
                break;
            case Pow:
                instruction = new Pow();
                break;
            case Swap:
                instruction = new Swap();
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

    @Override
    public InstructionMark generateRandomInstruction(List<Register> register) {
        Random r = new Random();
        int enumIndex = r.nextInt(InstructionEnum.values().length);
        InstructionEnum selectedEnum = InstructionEnum.values()[enumIndex];
        InstructionMark mark;
        if (selectedEnum.isSingleRegister()) {
            int registerIndex1 = r.nextInt(register.size());
            mark = createInstruction(selectedEnum, register.get(registerIndex1));
        } else {
            mark = createInstruction(selectedEnum);
        }
        return mark;
    }

}
