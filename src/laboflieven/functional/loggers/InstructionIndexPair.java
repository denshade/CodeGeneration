package laboflieven.functional.loggers;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.common.InstructionOpcode;
import laboflieven.statements.RegularInstructionOpcodeEnum;

import java.math.BigInteger;
import java.util.List;

public class InstructionIndexPair {
    BigInteger x, y;

    public static List getDefaultOpcodes(List<InstructionMark> instructions)
    {
        List opcodes;
        if (instructions.get(0) instanceof AccRegisterInstruction) {
            opcodes = List.of(AccInstructionOpcodeEnum.values());
        } else {
            opcodes = List.of(RegularInstructionOpcodeEnum.values());
        }
        return opcodes;
    }

    public InstructionIndexPair(List<InstructionMark> instructions, int nrRegisters, List<InstructionOpcode> opcodes)
    {
        x = new InstructionsBigIntegerIndex(instructions, opcodes).getSumRegister();
        y = new RegistersBigIntegerIndex(instructions, nrRegisters).getSumRegister();
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }
}
