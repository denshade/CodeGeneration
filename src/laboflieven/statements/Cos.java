package laboflieven.statements;

/**
 * Created by Lieven on 23-4-2016.
 */
public class Cos extends SingleRegisterInstruction{

    public Cos(Register destination) {
        this.destination = destination;
    }

    @Override
    public Integer execute(int current) {
        destination.value = Math.cos(destination.value);
        return null;
    }
    @Override
    public Object getInstructionOpcode() {
        return InstructionSet.Cos;
    }

}
