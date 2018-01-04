package laboflieven.humanresource.instructions;


import laboflieven.humanresource.model.Guy;
import laboflieven.humanresource.model.HumanInstruction;
import laboflieven.humanresource.model.InvalidProgramException;

import java.util.Queue;

/**
 * Created by Lieven on 2-1-2018.
 */
public class Outbox extends HumanInstruction
{


    @Override
    public Integer execute(Queue<Integer> incomingQ, Queue<Integer> outgoingQ, Guy guy) throws InvalidProgramException {
        if (guy.valueHesHolding == null) throw new InvalidProgramException("Guy has nothing in his hands");
        outgoingQ.add(guy.valueHesHolding);
        guy.valueHesHolding = null;
        return null;
    }

    @Override
    public String toString() {
        return "Outbox";
    }
}
