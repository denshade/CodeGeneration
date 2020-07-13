package laboflieven.statements;

/**
 * Created by Lieven on 1-5-2016.
 */
public class JumpIfRegister2Steps extends SingleRegisterInstruction {

    public JumpIfRegister2Steps(Register register1) {
        this.destination = register1;
    }

    /**
     * If register1 is 0 then jump to the end.
     * @return The address to change the instruction pointer to.
     */
    public Integer execute(int current) {
        if (destination.value == 0) {
            return current + 2;
        }
        return null;
    }
    @Override
    public Object getInstructionOpcode() {
        return RegularInstructionOpcode.JmpIfRegister2Steps;
    }



}
