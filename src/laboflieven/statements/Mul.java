package laboflieven.statements;

import laboflieven.common.InstructionOpcode;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Mul extends DualRegisterInstruction
{
    public Mul(Register source, Register destination) {
        super(source, destination);
    }

    public Integer execute(int current)
    {
        destination.value = source.value * destination.value;
        return null;
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.RegularInstructionOpcode(RegularInstructionOpcodeEnum.Mul);
    }

}
