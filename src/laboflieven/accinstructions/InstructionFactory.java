package laboflieven.accinstructions;


import laboflieven.InstructionMark;
import laboflieven.common.InstructionOpcode;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.Register;

import java.util.List;
import java.util.Random;

/**
 * Created by lveeckha on 4/06/2015.
 */
public class InstructionFactory implements InstructionFactoryInterface {
    public AccRegisterInstruction createInstruction(InstructionOpcode instructionEnum, Register... registers) {
        AccInstructionOpcodeEnum instruct = ((laboflieven.common.AccInstructionOpcode) instructionEnum).getEnumer();
        if (registers.length == 0) {
            return createInstructionP(instruct);
        } else if (registers.length == 1) {
            return createInstructionP(instruct, registers[0]);
        }
        throw new RuntimeException("Too many registers");
    }

    public AccRegisterInstruction createInstructionP(AccInstructionOpcodeEnum accInstructionOpcodeEnum) {
        AccRegisterInstruction instruction;
        switch (accInstructionOpcodeEnum) {
            case Add:
                instruction = new Add();
                break;
            case Div:
                instruction = new Div();
                break;
            case Mod:
                instruction = new Mod();
                break;
            case Mul:
                instruction = new Mul();
                break;
            case Sub:
                instruction = new Sub();
                break;
            case Nand:
                instruction = new Nand();
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
            case PI:
                instruction = new PI();
                break;
            case E:
                instruction = new E();
                break;
            case Inc:
                instruction = new Inc();
                break;
            case Custom:
                instruction = new Custom();
                break;
            default:
                throw new IllegalArgumentException("invalid instruction " + accInstructionOpcodeEnum);
        }
        return instruction;
    }

    public AccRegisterInstruction createInstructionP(AccInstructionOpcodeEnum accInstructionOpcodeEnum, Register register1) {
        AccRegisterInstruction instruction;
        switch (accInstructionOpcodeEnum) {
            case LoadAccLeftIntoRegister:
                instruction = new LoadAccLeftIntoRegister(register1);
                break;
            case LoadIntoLeftAcc:
                instruction = new LoadIntoLeftAcc(register1);
                break;
            case LoadIntoRightAcc:
                instruction = new LoadIntoRightAcc(register1);
                break;
            case LoadAccRightIntoRegister:
                instruction = new LoadAccRightIntoRegister(register1);
                break;
            /*case JumpIfGte:
                instruction = new JumpIfGte(register1);
                break;
            case JumpIfLte:
                instruction = new JumpIfLte(register1);
                break;*/
            default:
                throw new IllegalArgumentException("invalid instruction " + accInstructionOpcodeEnum.toString());
        }
        return instruction;
    }

    @Override
    public InstructionMark generateRandomInstruction(List<Register> register) {
        Random r = new Random();
        int enumIndex = r.nextInt(AccInstructionOpcodeEnum.values().length);
        AccInstructionOpcodeEnum selectedEnum = AccInstructionOpcodeEnum.values()[enumIndex];
        InstructionMark mark;
        if (selectedEnum.isSingleRegister()) {
            int registerIndex1 = r.nextInt(register.size());
            mark = createInstruction(new laboflieven.common.AccInstructionOpcode(selectedEnum), register.get(registerIndex1));
        } else {
            mark = createInstruction(new laboflieven.common.AccInstructionOpcode(selectedEnum));
        }
        return mark;
    }

}
