package laboflieven.statements;

/**
 * Created by Lieven on 1-5-2016.
 */
public class JumpIfRegister1Zero
{

    private final Register register1;
    private Register jumpToAddress;

    public JumpIfRegister1Zero(Register register1, Register jumpToAddress) {
        this.register1 = register1;
        this.jumpToAddress = jumpToAddress;
    }

    /**
     * If register1 is 0 then jump to jump address.
     * @return The address to change the instruction pointer to.
     */
    public Integer execute() {
        if (register1.value == 0) {
            return (int)jumpToAddress.value;
        }
        return null;
    }

}
