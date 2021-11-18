package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;
import laboflieven.statements.VectorRegister;
import org.apache.commons.math3.primes.Primes;

public class LoadAccLeftIntoVector extends AccRegisterInstruction {
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return null;
    }

    @Override
    public Integer execute(Register left, Register right, int ip) {
        throw new RuntimeException("Not implemented");
    }
    @Override
    public Integer execute(Register left, Register right, VectorRegister vectorLeft, VectorRegister vectorRight, int ip) {
        vectorRight.value = LoadAccRightIntoVector.getVectorFromValue(right.value);
        return null;
    }

    @Override
    public String toString() {
        return  "leftVector = split(left)";
    }
}
