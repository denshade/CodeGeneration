package laboflieven.statements;

/**
 * Invert the signum.
 */

public class Invert extends SingleRegisterInstruction
{

    public Invert(Register destination) {
        this.destination = destination;
    }

    public Integer execute() {
        destination.value = destination.value * -1;
        return null;
    }
}
