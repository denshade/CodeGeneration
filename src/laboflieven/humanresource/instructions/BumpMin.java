package laboflieven.humanresource.instructions;

import laboflieven.humanresource.model.Guy;
import laboflieven.humanresource.model.HumanInstruction;
import laboflieven.humanresource.model.HumanRegister;
import laboflieven.humanresource.model.InvalidProgramException;

import java.util.Queue;

public class BumpMin extends HumanInstruction
{
    private final HumanRegister register;

    public BumpMin(HumanRegister register)
    {
        this.register = register;
    }

    @Override
    public Integer execute(Queue<Integer> incomingQ, Queue<Integer> outgoingQ, Guy guy) throws InvalidProgramException {
        if (register.value == null) throw new InvalidProgramException("Register has no value yet" + register.name);
        register.value--;
        guy.valueHesHolding = register.value;
        return null;
    }

    @Override
    public String toString() {
        return "BumpMin " + register.name;
    }

}
