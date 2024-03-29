package laboflieven.instructions.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;
import laboflieven.instructions.regular.VectorRegister;

public class LeftVectPushExponents extends NoRegisterInstruction {

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
        leftVect.value.insertElementAt(0.0,0);
        return null;
    }

    @Override
    public String toString() {
        return "vectleft = shvectleft";
    }
}
