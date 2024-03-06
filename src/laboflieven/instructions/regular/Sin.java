package laboflieven.instructions.regular;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

/**
 * Created by Lieven on 23-4-2016.
 */
public class Sin extends SingleRegisterInstruction{

    public Sin(Register destination) {
        this.destination = destination;
    }

    @Override
    public Integer execute(int current) {
        destination.value = Math.sin(destination.value);
        return null;
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.RegularInstructionOpcode(RegularInstructionOpcodeEnum.Sin);
    }

}
