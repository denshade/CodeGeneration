package laboflieven.humanresource.instructions;

import laboflieven.humanresource.model.Guy;
import laboflieven.humanresource.model.HumanInstruction;
import laboflieven.humanresource.model.HumanRegister;
import laboflieven.humanresource.model.InvalidProgramException;

import java.util.Queue;

/**
 * Created by Lieven on 2-1-2018.
 */
public class CopyTo extends HumanInstruction {
    private HumanRegister register;

    public CopyTo(HumanRegister register)
    {
        this.register = register;
    }

    @Override
    public Integer execute(Queue<Integer> incomingQ, Queue<Integer> outgoingQ, Guy guy) throws InvalidProgramException {
        if (!guy.hasValue()) throw new InvalidProgramException("Guy has nothing left");
        register.value = guy.valueHesHolding;
        return null;
    }

    @Override
    public String toString() {
        return "CopyTo " + register.name;
    }
}
