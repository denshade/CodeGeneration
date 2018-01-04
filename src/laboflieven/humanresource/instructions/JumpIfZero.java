package laboflieven.humanresource.instructions;

import laboflieven.humanresource.model.Guy;
import laboflieven.humanresource.model.HumanInstruction;
import laboflieven.humanresource.model.InvalidProgramException;

import java.util.Queue;

/**
 * Created by Lieven on 2-1-2018.
 */
public class JumpIfZero extends HumanInstruction
{
    private Integer gotoInstruction;

    public JumpIfZero(Integer gotoInstruction)
    {
        this.gotoInstruction = gotoInstruction;
    }


    @Override
    public Integer execute(Queue<Integer> incomingQ, Queue<Integer> outgoingQ, Guy guy) throws InvalidProgramException {
        if (!guy.hasValue()) throw new InvalidProgramException("Guy has nothing in his hands");
        if (guy.valueHesHolding == 0)
        {
            return gotoInstruction;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Jump if zero to " + gotoInstruction;
    }
}
