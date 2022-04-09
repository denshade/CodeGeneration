package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;
import laboflieven.statements.VectorRegister;

public class LeftVectShift extends NoRegisterInstruction {

    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.LeftVectShift);
    }

    @Override
    public Integer execute(Register left, Register right, int ip) {
        return null;
    }
    @Override
    public Integer execute(Register left, Register right, VectorRegister leftVect, VectorRegister rightVect, int ip) {
        if (leftVect.value.size() > 0)
            leftVect.value.remove(0);
        return null;
    }

    @Override
    public String toString() {
        return "vectleft = popfirst(vectleft)";
    }
}
