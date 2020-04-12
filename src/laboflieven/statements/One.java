package laboflieven.statements;

public class One extends SingleRegisterInstruction {


    public One(Register destination) {
        this.destination = destination;
    }

    public Integer execute() {
        destination.value = 1.0;
        return null;
    }
}
