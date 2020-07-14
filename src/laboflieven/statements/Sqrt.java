package laboflieven.statements;

import laboflieven.common.InstructionOpcode;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Sqrt extends SingleRegisterInstruction {


    public Sqrt(Register destination) {
        this.destination = destination;
    }

    public Integer execute(int current) {
        destination.value = Math.sqrt(destination.value);
        return null;
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.RegularInstructionOpcode(RegularInstructionOpcodeEnum.Sqrt);
    }

}
