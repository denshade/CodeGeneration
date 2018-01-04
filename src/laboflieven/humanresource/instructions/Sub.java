package laboflieven.humanresource.instructions;

import laboflieven.humanresource.model.Guy;
import laboflieven.humanresource.model.HumanInstruction;
import laboflieven.humanresource.model.HumanRegister;
import laboflieven.humanresource.model.InvalidProgramException;

import java.util.Queue;

/**
 * Created by Lieven on 3-1-2018.
 */
public class Sub extends HumanInstruction
{
    private final HumanRegister register;

    public Sub(HumanRegister register)
    {
        this.register = register;
    }
    @Override
    public Integer execute(Queue<Integer> incomingQ, Queue<Integer> outgoingQ, Guy guy) throws InvalidProgramException {
        if (!guy.hasValue()) throw new InvalidProgramException("Trying to add but guy has nothing");
        if (!register.hasValue()) throw new InvalidProgramException("Trying to add but guy has nothing");
        guy.valueHesHolding -= register.value;
        return null;
    }

    @Override
    public String toString() {
        return "Sub " + register.name;
    }
}
