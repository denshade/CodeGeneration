package laboflieven.instructions.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.instructions.regular.VectorRegister;
import laboflieven.registers.Register;

import java.util.Vector;

/**
 * Rotates the left vector one step: the last element moves to index 0, every other element shifts
 * to the next index (equivalently, a right rotation by one).
 */
public class LeftVectRoundRobin extends NoRegisterInstruction {

    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.LeftVectRoundRobin);
    }

    @Override
    public Integer execute(Register left, Register right, int ip) {
        return null;
    }

    @Override
    public Integer execute(Register left, Register right, VectorRegister leftVect, VectorRegister rightVect, int ip) {
        Vector<Double> v = leftVect.value;
        int n = v.size();
        if (n > 1) {
            Double last = v.remove(n - 1);
            v.insertElementAt(last, 0);
        }
        return null;
    }

    @Override
    public String toString() {
        return "vectleft = roundrobin(vectleft)";
    }
}
