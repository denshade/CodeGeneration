package laboflieven.statements;

/**
 * Created by Lieven on 1-5-2016.
 */
public class JumpIfRegister1NotZeroToEnd extends SingleRegisterInstruction {

    public JumpIfRegister1NotZeroToEnd(Register register1) {
        this.destination = register1;
    }

    /**
     * If register1 is 0 then jump to the end.
     * @return The address to change the instruction pointer to.
     */
    public Integer execute(int current) {
        if (destination.value != 0) {
            return 1000;
        }
        return null;
    }
    @Override
    public Object getInstructionOpcode() {
        return InstructionSet.JmpIfNotZeroEnd;
    }


}
