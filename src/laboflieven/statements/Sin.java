package laboflieven.statements;

/**
 * Created by Lieven on 23-4-2016.
 */
public class Sin extends SingleRegisterInstruction{

    public Sin(Register destination) {
        this.destination = destination;
    }

    @Override
    public Integer execute() {
        destination.value = Math.sin(destination.value);
        return null;
    }
}
