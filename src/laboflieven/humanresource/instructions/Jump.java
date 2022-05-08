package laboflieven.humanresource.instructions;

import laboflieven.humanresource.model.Guy;
import laboflieven.humanresource.model.HumanInstruction;

import java.util.Queue;

/**
 * Created by Lieven on 2-1-2018.
 */
public class Jump extends HumanInstruction
{
    private final Integer gotoInstruction;

    public Jump(Integer gotoInstruction)
    {
        this.gotoInstruction = gotoInstruction;
    }


    @Override
    public Integer execute(Queue<Integer> incomingQ, Queue<Integer> outgoingQ, Guy guy) {
        return gotoInstruction;
    }

    public Integer getGotoInstruction() {
        return gotoInstruction;
    }
    @Override
    public String toString() {
        return "Jump to " + gotoInstruction;
    }
}
