package laboflieven.statements;


import laboflieven.InstructionMark;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.common.InstructionOpcode;

import java.util.List;
import java.util.Random;

/**
 * Created by lveeckha on 4/06/2015.
 */
public class InstructionFactory implements InstructionFactoryInterface
{
    public Instruction createInstructionP(RegularInstructionOpcodeEnum regularInstructionOpcodeEnum, Register register1, Register register2)
    {
        Instruction instruction;
        switch(regularInstructionOpcodeEnum){
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
                throw new IllegalArgumentException("invalid instruction " + regularInstructionOpcodeEnum.toString());
        }
        return instruction;
    }

    public Instruction createInstructionP(RegularInstructionOpcodeEnum regularInstructionOpcodeEnum, Register register1)
    {
        Instruction instruction;
        switch(regularInstructionOpcodeEnum){
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
                throw new IllegalArgumentException("invalid instruction " + regularInstructionOpcodeEnum.toString());
        }
        return instruction;
    }

    @Override
    public InstructionMark generateRandomInstruction(List<Register> register) {
        Random r = new Random();
        int enumIndex = r.nextInt(RegularInstructionOpcodeEnum.values().length);
        RegularInstructionOpcodeEnum selectedEnum = RegularInstructionOpcodeEnum.values()[enumIndex];
        InstructionMark mark;
        if (selectedEnum.isDualRegister()) {
            int registerIndex1 = r.nextInt(register.size());
            int registerIndex2 = r.nextInt(register.size());
            mark = createInstructionP(selectedEnum, register.get(registerIndex1), register.get(registerIndex2));
        } else {
            int registerIndex1 = r.nextInt(register.size());
            mark = createInstructionP(selectedEnum, register.get(registerIndex1));
        }
        return mark;
    }

    @Override
    public InstructionMark generateRandomInstruction(List<Register> register, Object[] enums) {
        Random r = new Random();
        int enumIndex = r.nextInt(enums.length);
        RegularInstructionOpcodeEnum selectedEnum = ((RegularInstructionOpcodeEnum[])enums)[enumIndex];
        InstructionMark mark;
        if (selectedEnum.isDualRegister()) {
            int registerIndex1 = r.nextInt(register.size());
            int registerIndex2 = r.nextInt(register.size());
            mark = createInstructionP(selectedEnum, register.get(registerIndex1), register.get(registerIndex2));
        } else {
            int registerIndex1 = r.nextInt(register.size());
            mark = createInstructionP(selectedEnum, register.get(registerIndex1));
        }
        return mark;
    }

    @Override
    public InstructionMark createInstruction(InstructionOpcode instructionEnum, Register... registers) {
        RegularInstructionOpcodeEnum en = ((laboflieven.common.RegularInstructionOpcode)instructionEnum).getEnumer();
        if (registers.length == 1)
            return createInstructionP(en, registers[0]);
        if (registers.length == 2)
            return createInstructionP(en, registers[0], registers[1]);

        return null;
    }
}
