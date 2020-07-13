package laboflieven.statements;

public class Log extends SingleRegisterInstruction {


    public Log(Register destination) {
        this.destination = destination;
    }

    public Integer execute(int current) {
        destination.value = Math.log(destination.value);
        return null;
    }
    @Override
    public Object getInstructionOpcode() {
        return InstructionSet.Log;
    }

}