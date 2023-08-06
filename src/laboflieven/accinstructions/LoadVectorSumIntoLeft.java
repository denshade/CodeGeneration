package laboflieven.accinstructions;

import laboflieven.common.AccInstructionOpcode;
import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;
import laboflieven.statements.VectorRegister;

public class LoadVectorSumIntoLeft extends NoRegisterInstruction {
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new AccInstructionOpcode(AccInstructionOpcodeEnum.LoadVectorSumIntoLeft);
    }

    @Override
    public Integer execute(Register left, Register right, int ip) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Integer execute(Register left, Register right, VectorRegister vectorLeft, VectorRegister vectorRight, int ip) {
        left.value = vectorLeft.value.stream().reduce(0.0, Double::sum);
        return null;
    }

    @Override
    public String toString() {
        return "left = sum(leftV)";
    }
}

