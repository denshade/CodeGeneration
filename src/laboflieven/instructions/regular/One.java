package laboflieven.instructions.regular;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

public class One extends SingleRegisterInstruction {


    public One(Register destination) {
        this.destination = destination;
    }

    public Integer execute(int current) {
        destination.value = 1.0;
        return null;
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.RegularInstructionOpcode(RegularInstructionOpcodeEnum.One);
    }

}
