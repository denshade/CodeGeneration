package laboflieven.instructions.regular;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Move extends DualRegisterInstruction
{

    public Move(Register source, Register destination) {
        super(source, destination);
    }

    public Integer execute(int current)
    {
        destination.value = source.value;
        return null;
    }

    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.RegularInstructionOpcode(RegularInstructionOpcodeEnum.Move);
    }

}
