package laboflieven.statements;

import laboflieven.common.InstructionOpcode;

public class PI extends SingleRegisterInstruction {


    public PI(Register destination) {
        this.destination = destination;
    }

    public Integer execute(int current) {
        destination.value = Math.PI;
        return null;
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.RegularInstructionOpcode(RegularInstructionOpcodeEnum.PI);
    }

}
