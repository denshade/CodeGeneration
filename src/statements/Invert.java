package statements;

/**
 * Invert the signum.
 */

public class Invert extends SingleRegisterInstruction
{

    public Invert(Register destination) {
        this.destination = destination;
    }

    public void execute() {
        destination.value = destination.value * -1;
    }
}
