package laboflieven.statements;

/**
 * Created by Lieven on 23-4-2016.
 */
public class Cos extends SingleRegisterInstruction{

    public Cos(Register destination) {
        this.destination = destination;
    }

    @Override
    public void execute() {
        destination.value = Math.cos(destination.value);
    }
}
