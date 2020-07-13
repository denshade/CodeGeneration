package laboflieven.statements;

/**
 * Created by Lieven on 1-5-2016.
 */
public class JumpIfRegister1Zero extends DualRegisterInstruction {

    public JumpIfRegister1Zero(Register register1, Register jumpToAddress) {
        super(register1, jumpToAddress);
    }

    /**
     * If register1 is 0 then jump to jump address.
     * @return The address to change the instruction pointer to.
     */
    public Integer execute(int current) {
        if (source.value == 0) {
            return (int)destination.value;
        }
        return null;
    }

    @Override
    public Object getInstructionOpcode() {
        return RegularInstructionOpcode.JmpIfZero;
    }


}
