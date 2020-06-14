package laboflieven.recursionheuristics;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.accinstructions.InstructionEnum;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.statements.Register;

import java.util.List;

public class AccHeuristic implements RecursionHeuristic
{

    @Override
    public boolean shouldRecurse(List<InstructionMark> instructions, int nrInstructions) {
        if (instructions.size() == 0) return true;
        
        if (instructions.size() == 1 && !(instruction.equals(InstructionEnum.AccLeftPush) ||instruction.equals(InstructionEnum.AccRightPush)))
            return false;
        //Finish must be a push to a register.
        boolean isAccPushPull = !(instruction.equals(InstructionEnum.AccLeftPull) || instruction.equals(InstructionEnum.AccRightPull));
        if (instructions.size() == maximumInstructions - 1 && isAccPushPull)
            continue;
        //Don't use pull from right/left before a push.
        if (instruction.equals(InstructionEnum.AccLeftPull))
        {
            if (!hasAccLeftPush(instructions)) continue;
        }
        if (instruction.equals(InstructionEnum.AccRightPull))
        {
            if (!hasAccRightPush(instructions)) continue;
        }
        if (instructions.size() == maximumInstructions - 1 && isAccPushPull)
            continue;
        if (instruction.isSingleRegister()) {
            for (Register register1 : registers) {
                AccRegisterInstruction actualInstruction = InstructionFactory.createInstruction(instruction, register1);
                processInstruction(instructions, registers, actualInstruction);
            }
        } else {
            AccRegisterInstruction actualInstruction = InstructionFactory.createInstruction(instruction);
            processInstruction(instructions, registers, actualInstruction);
        }
    }
}
