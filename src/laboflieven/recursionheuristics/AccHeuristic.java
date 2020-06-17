package laboflieven.recursionheuristics;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.*;
import laboflieven.statements.Register;

import java.util.List;

public class AccHeuristic implements RecursionHeuristic
{

    @Override
    public boolean shouldRecurse(List<InstructionMark> instructionsMarks, int maximumInstructions) {
        List<AccRegisterInstruction> instructions = (List<AccRegisterInstruction>)(List<?>)instructionsMarks;
        if (instructions.size() == 0) return true;
        AccRegisterInstruction lastInstruction = (AccRegisterInstruction) instructions.get(instructions.size() - 1);
        if (instructions.size() == 1 && !(lastInstruction instanceof AccLeftPush ||lastInstruction instanceof AccRightPush))
            return false;
        //Finish must be a push to a register.
        //if (instructions.size() == maximumInstructions - 1 && isAccPushPull)
        //    return false;
        //Don't use pull from right/left before a push.
        if (lastInstruction instanceof AccLeftPull)
        {
            if (!hasAccLeftPush(instructions)) return false;
        }
        if (lastInstruction instanceof AccRightPull)
        {
            if (!hasAccRightPush(instructions)) return false;
        }
        if (instructions.size() == maximumInstructions && (lastInstruction instanceof AccLeftPush || lastInstruction instanceof AccRightPull ))
            return false;
        if (instructions.size() - 2 >= 0)
        {
            AccRegisterInstruction prevInstruction = instructions.get(instructions.size() - 2);
            if (lastInstruction instanceof AccLeftPull && prevInstruction instanceof AccLeftPull && lastInstruction.toString().equals(prevInstruction.toString())) {
               return false;
            }
            if (lastInstruction instanceof AccRightPull && prevInstruction instanceof AccRightPull && lastInstruction.toString().equals(prevInstruction.toString())) {
                return false;
            }
        }

        return true;
    }
    private boolean hasAccLeftPush(List<AccRegisterInstruction> instructions) {
        boolean used = false;
        for ( AccRegisterInstruction instructionI: instructions)
        {
            if (instructionI instanceof AccLeftPush) {
                used = true;
                break;
            }
        }
        return used;
    }

    private boolean hasAccRightPush(List<AccRegisterInstruction> instructions) {
        boolean used = false;
        for ( AccRegisterInstruction instructionI: instructions)
        {
            if (instructionI instanceof AccRightPush) {
                used = true;
                break;
            }
        }
        return used;
    }

}
