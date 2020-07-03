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
        int size = instructions.size();
        if (size == 0) return true;
        AccRegisterInstruction lastInstruction = instructions.get(size - 1);
        if (size == 1 && !(lastInstruction instanceof AccLeftPush ||lastInstruction instanceof AccRightPush))
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
        if (size == maximumInstructions && (lastInstruction instanceof AccLeftPush || lastInstruction instanceof AccRightPush ))
            return false;
        if (size == maximumInstructions && !(lastInstruction instanceof AccLeftPull || lastInstruction instanceof AccRightPull ))
            return false;
        if (size - 2 >= 0)
        {
            AccRegisterInstruction prevInstruction = instructions.get(size - 2);
            //Two pull lefts overwrite each other.
            if (lastInstruction instanceof AccLeftPush && prevInstruction instanceof AccLeftPush) {
               return false;
            }
            //Two pull rights overwrite each other.
            if (lastInstruction instanceof AccRightPush && prevInstruction instanceof AccRightPush) {
                return false;
            }
            if (lastInstruction instanceof Add && prevInstruction instanceof Sub) {
                return false;
            }
            if (lastInstruction instanceof Sub && prevInstruction instanceof Add) {
                return false;
            }
            if (lastInstruction instanceof Div && prevInstruction instanceof Mul) {
                return false;
            }
            if (lastInstruction instanceof Mul && prevInstruction instanceof Div) {
                return false;
            }
            if (lastInstruction instanceof Swap && prevInstruction instanceof Swap) {
                return false;
            }
            //if pullLeft overwrite the result of operations.
            if (lastInstruction instanceof AccLeftPush && !(prevInstruction instanceof Swap || prevInstruction instanceof AccLeftPush)) {
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
