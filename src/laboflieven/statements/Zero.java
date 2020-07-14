package laboflieven.statements;

import laboflieven.common.InstructionOpcode;

public class Zero extends SingleRegisterInstruction {


    public Zero(Register destination) {
        this.destination = destination;
    }

    public Integer execute(int current) {
        destination.value = 0.0;
        return null;
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.RegularInstructionOpcode(RegularInstructionOpcodeEnum.Zero);
    }

}
