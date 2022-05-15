package laboflieven.humanresource.instructions;

import laboflieven.humanresource.model.Guy;
import laboflieven.humanresource.model.HumanInstruction;
import laboflieven.humanresource.model.HumanRegister;
import laboflieven.humanresource.model.InvalidProgramException;

import java.util.Queue;

/**
 * Created by Lieven on 2-1-2018.
 */
public class CopyFrom extends HumanInstruction {
    private final HumanRegister register;

    public CopyFrom(HumanRegister register)
    {
        this.register = register;
    }

    @Override
    public Integer execute(Queue<Integer> incomingQ, Queue<Integer> outgoingQ, Guy guy) throws InvalidProgramException {
        if (register.value == null) throw new InvalidProgramException("Registered value is null " + register.name);
        guy.valueHesHolding = register.value;
        return null;
    }

    @Override
    public String toString() {
        return "CopyFrom " + register.name;
    }
}
