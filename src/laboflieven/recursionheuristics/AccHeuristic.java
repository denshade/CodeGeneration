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
        AccRegisterInstruction instruction = (AccRegisterInstruction) instructions.get(instructions.size() - 1);
        if (instructions.size() == 1 && !(instruction.equals(InstructionEnum.AccLeftPush) ||instruction.equals(InstructionEnum.AccRightPush)))
            return false;
        //Finish must be a push to a register.
        boolean isAccPushPull = !(instruction.equals(InstructionEnum.AccLeftPull) || instruction.equals(InstructionEnum.AccRightPull));
        if (instructions.size() == maximumInstructions - 1 && isAccPushPull)
            return false;
        //Don't use pull from right/left before a push.
        if (instruction.equals(InstructionEnum.AccLeftPull))
        {
            if (!hasAccLeftPush(instructions)) return false;
        }
        if (instruction.equals(InstructionEnum.AccRightPull))
        {
            if (!hasAccRightPush(instructions)) return false;
        }
        if (instructions.size() == maximumInstructions - 1 && isAccPushPull)
            return false;
        return true;
    }
    private boolean hasAccLeftPush(List<AccRegisterInstruction> instructions) {
        boolean used = false;
        for ( AccRegisterInstruction instructionI: instructions)
        {
            if (instructionI instanceof AccLeftPush) {
                used = true;
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
            }
        }
        return used;
    }

}
