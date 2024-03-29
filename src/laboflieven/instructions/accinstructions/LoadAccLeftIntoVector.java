package laboflieven.instructions.accinstructions;

import laboflieven.common.AccInstructionOpcode;
import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;
import laboflieven.instructions.regular.VectorRegister;

public class LoadAccLeftIntoVector extends NoRegisterInstruction {
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new AccInstructionOpcode(AccInstructionOpcodeEnum.LoadAccLeftIntoVector);
    }

    @Override
    public Integer execute(Register left, Register right, int ip) {
        throw new RuntimeException("Not implemented");
    }
    @Override
    public Integer execute(Register left, Register right, VectorRegister vectorLeft, VectorRegister vectorRight, int ip) {
        vectorLeft.value = LoadAccRightIntoVector.getVectorFromValue(left.value);
        return null;
    }

    @Override
    public String toString() {
        return  "leftVector = split(left)";
    }
}
