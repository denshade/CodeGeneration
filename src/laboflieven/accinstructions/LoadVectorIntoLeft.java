package laboflieven.accinstructions;

import laboflieven.common.AccInstructionOpcode;
import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;
import laboflieven.statements.VectorRegister;
import org.apache.commons.math3.primes.Primes;

public class LoadVectorIntoLeft extends NoRegisterInstruction {
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new AccInstructionOpcode(AccInstructionOpcodeEnum.LoadVectorIntoLeft);
    }

    @Override
    public Integer execute(Register left, Register right, int ip) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Integer execute(Register left, Register right, VectorRegister vectorLeft, VectorRegister vectorRight, int ip) {
        left.value = LoadVectorIntoRight.convertVectorToDouble(vectorLeft.value);
        return null;
    }

    @Override
    public String toString() {
        return "left = combine(rightleft)";
    }
}
