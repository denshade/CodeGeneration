package laboflieven.statements;

import laboflieven.common.InstructionOpcode;

public class Log extends SingleRegisterInstruction {


    public Log(Register destination) {
        this.destination = destination;
    }

    public Integer execute(int current) {
        destination.value = Math.log(destination.value);
        return null;
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.RegularInstructionOpcode(RegularInstructionOpcodeEnum.Log);
    }

}