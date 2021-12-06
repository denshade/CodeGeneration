package laboflieven.accinstructions;


import laboflieven.InstructionMark;
import laboflieven.common.InstructionOpcode;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.Register;

import java.util.*;

/**
 * Created by lveeckha on 4/06/2015.
 */
public class InstructionFactory implements InstructionFactoryInterface {
    private static EnumMap<AccInstructionOpcodeEnum, AccRegisterInstruction> map = new EnumMap<>(AccInstructionOpcodeEnum.class) {{
        put(AccInstructionOpcodeEnum.Add, new Add());
        put(AccInstructionOpcodeEnum.Div, new Div());
        put(AccInstructionOpcodeEnum.Mod, new Mod());
        put(AccInstructionOpcodeEnum.Mul, new Mul());
        put(AccInstructionOpcodeEnum.Sub, new Sub());
        put(AccInstructionOpcodeEnum.Nand, new Nand());
        put(AccInstructionOpcodeEnum.Invert, new Invert());
        put(AccInstructionOpcodeEnum.Sqrt, new Sqrt());
        put(AccInstructionOpcodeEnum.Sin, new Sin());
        put(AccInstructionOpcodeEnum.Cos, new Cos());
        put(AccInstructionOpcodeEnum.Log, new Log());
        put(AccInstructionOpcodeEnum.JumpIfGteStart, new JumpIfGteStart());
        put(AccInstructionOpcodeEnum.JumpIfLteStart, new JumpIfLteStart());
        put(AccInstructionOpcodeEnum.Jump2IfGte, new Jump2IfGte());
        put(AccInstructionOpcodeEnum.Jump2IfLte, new Jump2IfLte());
        put(AccInstructionOpcodeEnum.Jump2IfEq, new Jump2IfEq());
        put(AccInstructionOpcodeEnum.Jump2IfNeq, new Jump2IfNeq());
        put(AccInstructionOpcodeEnum.Jump2IfZero, new Jump2IfZero());
        put(AccInstructionOpcodeEnum.Quit, new Quit());
        put(AccInstructionOpcodeEnum.Pow, new Pow());
        put(AccInstructionOpcodeEnum.Swap, new Swap());
        put(AccInstructionOpcodeEnum.PI, new PI());
        put(AccInstructionOpcodeEnum.E, new E());
        put(AccInstructionOpcodeEnum.Inc, new Inc());
        put(AccInstructionOpcodeEnum.Custom, new Custom());
        put(AccInstructionOpcodeEnum.LoadAccRightIntoVector, new LoadAccRightIntoVector());
        put(AccInstructionOpcodeEnum.LoadAccLeftIntoVector, new LoadAccLeftIntoVector());
        put(AccInstructionOpcodeEnum.LoadVectorIntoLeft, new LoadVectorIntoLeft());
        put(AccInstructionOpcodeEnum.LoadVectorIntoRight, new LoadVectorIntoRight());
        put(AccInstructionOpcodeEnum.LeftVectShift, new LeftVectShift());
        put(AccInstructionOpcodeEnum.LeftVectPushExponents, new LeftVectPushExponents());
        put(AccInstructionOpcodeEnum.Dec, new Dec());
        put(AccInstructionOpcodeEnum.LoadVectorSumIntoLeft, new LoadVectorSumIntoLeft());

    }};

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
        return map.get(accInstructionOpcodeEnum);
    }

    public AccRegisterInstruction createInstructionP(AccInstructionOpcodeEnum accInstructionOpcodeEnum, Register register1) {
        AccRegisterInstruction instruction = switch (accInstructionOpcodeEnum) {
            case LoadAccLeftIntoRegister -> new LoadAccLeftIntoRegister(register1);
            case LoadIntoLeftAcc -> new LoadIntoLeftAcc(register1);
            case LoadIntoRightAcc -> new LoadIntoRightAcc(register1);
            case LoadAccRightIntoRegister -> new LoadAccRightIntoRegister(register1);
            /*case JumpIfGte:
                instruction = new JumpIfGte(register1);
                break;
            case JumpIfLte:
                instruction = new JumpIfLte(register1);
                break;*/
            default -> throw new IllegalArgumentException("invalid instruction " + accInstructionOpcodeEnum.toString());
        };
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
