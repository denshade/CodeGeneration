package laboflieven.humanresource.model;

import java.util.Queue;

/**
 * Created by Lieven on 2-1-2018.
 */
public abstract class HumanInstruction
{
    public HumanInstruction()
    {}
    public abstract Integer execute(Queue<Integer> incomingQ, Queue<Integer> outgoingQ, Guy guy) throws InvalidProgramException;

    public abstract String toString();
}
