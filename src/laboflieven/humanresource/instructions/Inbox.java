package laboflieven.humanresource.instructions;


import laboflieven.humanresource.model.Guy;
import laboflieven.humanresource.model.HumanInstruction;

import java.util.Queue;

/**
 * Created by Lieven on 2-1-2018.
 */
public class Inbox extends HumanInstruction
{

    @Override
    public Integer execute(Queue<Integer> incomingQ, Queue<Integer> outgoingQ, Guy guy) {
        guy.valueHesHolding = incomingQ.poll();
        return null;
    }

    @Override
    public String toString() {
        return "Inbox";
    }
}
