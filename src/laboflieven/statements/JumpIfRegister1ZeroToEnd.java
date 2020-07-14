package laboflieven.statements;

import laboflieven.common.InstructionOpcode;

/**
 * Created by Lieven on 1-5-2016.
 */
public class JumpIfRegister1ZeroToEnd extends SingleRegisterInstruction {

    public JumpIfRegister1ZeroToEnd(Register register1) {
        this.destination = register1;
    }

    /**
     * If register1 is 0 then jump to the end.
     * @return The address to change the instruction pointer to.
     */
    public Integer execute(int current) {
        if (destination.value == 0) {
            return 1000;
        }
        return null;
    }

    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.RegularInstructionOpcode(RegularInstructionOpcode.JmpIfZeroEnd);
    }

}
