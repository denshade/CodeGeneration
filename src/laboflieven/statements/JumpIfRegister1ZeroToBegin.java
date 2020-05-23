package laboflieven.statements;

/**
 * Created by Lieven on 1-5-2016.
 */
public class JumpIfRegister1ZeroToBegin extends SingleRegisterInstruction {

    public JumpIfRegister1ZeroToBegin(Register register1) {
        this.destination = register1;
    }

    /**
     * If register1 is 0 then jump to begin.
     * @return The address to change the instruction pointer to.
     */
    public Integer execute(int current) {
        if (destination.value == 0) {
            return 0;
        }
        return null;
    }

}
