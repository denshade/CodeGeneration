package laboflieven.statements;

public class Log extends SingleRegisterInstruction {


    public Log(Register destination) {
        this.destination = destination;
    }

    public Integer execute() {
        destination.value = Math.log(destination.value);
        return null;
    }
}