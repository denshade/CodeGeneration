package laboflieven.statements;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Sqrt extends SingleRegisterInstruction {


    public Sqrt(Register destination) {
        this.destination = destination;
    }

    public Integer execute() {
        destination.value = Math.sqrt(destination.value);
        return null;
    }
}
