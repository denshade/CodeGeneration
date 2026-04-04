package laboflieven.instructions.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.instructions.regular.VectorRegister;
import laboflieven.registers.Register;

public class VectDiv extends NoRegisterInstruction {

    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.VectDiv);
    }

    @Override
    public Integer execute(Register left, Register right, int ip) {
        return null;
    }

    @Override
    public Integer execute(Register left, Register right, VectorRegister leftVect, VectorRegister rightVect, int ip) {
        VectorElementWiseBinary.applyInPlaceLeft(leftVect, rightVect, (a, b) -> a / b);
        return null;
    }

    @Override
    public String toString() {
        return "vectleft = vectleft / vectright";
    }
}
