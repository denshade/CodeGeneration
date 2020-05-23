package laboflieven.statements;

public class PI extends SingleRegisterInstruction {


    public PI(Register destination) {
        this.destination = destination;
    }

    public Integer execute(int current) {
        destination.value = Math.PI;
        return null;
    }
}
