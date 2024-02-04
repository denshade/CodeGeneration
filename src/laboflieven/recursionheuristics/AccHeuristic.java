package laboflieven.recursionheuristics;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.accinstructions.*;

import java.util.List;

public class AccHeuristic implements RecursionHeuristic
{

    @Override
    public boolean shouldRecurse(Program program, int maximumInstructions) {
        List<AccRegisterInstruction> instructions = (List<AccRegisterInstruction>)(List<?>)program.getInstructions();
        int size = instructions.size();
        if (size == 0) return true;
        InstructionMark lastInstruction = instructions.get(size - 1);
        if (size == 1 && !(lastInstruction instanceof LoadIntoLeftAcc ||lastInstruction instanceof LoadIntoRightAcc))
            return false;
        //Finish must be a push to a register.
        //if (instructions.size() == maximumInstructions - 1 && isAccPushPull)
        //    return false;
        //Don't use pull from right/left before a push.
        if (size == maximumInstructions && (lastInstruction instanceof LoadIntoLeftAcc || lastInstruction instanceof LoadIntoRightAcc))
            return false;
        if (size == maximumInstructions && !(lastInstruction instanceof LoadAccLeftIntoRegister || lastInstruction instanceof LoadAccRightIntoRegister))
            return false;

        if (size - 2 >= 0)
        {
            InstructionMark prevInstruction = instructions.get(size - 2);
            //Two pull lefts overwrite each other.
            if (lastInstruction instanceof LoadIntoLeftAcc && prevInstruction instanceof LoadIntoLeftAcc) {
               return false;
            }
            //Two pull rights overwrite each other.
            if (lastInstruction instanceof LoadIntoRightAcc && prevInstruction instanceof LoadIntoRightAcc) {
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
            return !(lastInstruction instanceof LoadIntoLeftAcc) || (prevInstruction instanceof Swap || prevInstruction instanceof LoadIntoLeftAcc);
        }

        return true;
    }
    private boolean hasAccLeftPush(List<AccRegisterInstruction> instructions) {
        for ( AccRegisterInstruction instructionI: instructions)
        {
            if (instructionI instanceof LoadIntoLeftAcc) {
                return true;
            }
        }
        return false;
    }

    private boolean hasAccRightPush(List<AccRegisterInstruction> instructions) {
        for ( AccRegisterInstruction instructionI: instructions)
        {
            if (instructionI instanceof LoadIntoRightAcc) {
                return true;
            }
        }
        return false;
    }

}
