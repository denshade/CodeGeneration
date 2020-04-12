package laboflieven.statements;

public class Zero extends SingleRegisterInstruction {


    public Zero(Register destination) {
        this.destination = destination;
    }

    public Integer execute() {
        destination.value = 0.0;
        return null;
    }
}
