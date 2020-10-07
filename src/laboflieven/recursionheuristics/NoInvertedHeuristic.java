package laboflieven.recursionheuristics;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.statements.Instruction;
import laboflieven.statements.Invert;
import laboflieven.statements.Mul;
import laboflieven.statements.Sqrt;

import java.util.List;

public class NoInvertedHeuristic implements RecursionHeuristic
{

    @Override
    public boolean shouldRecurse(Program program, int nrInstructions) {
        String s = program.getInstructions().toString();
        if (s.startsWith("[PI")){
                return false;
        }
        if (s.startsWith("[Move")){
            return false;
        }
        for (int i = 1; i <= nrInstructions; i++) {
            if (s.contains("Invert R"+i+", Invert R"+i)) {
                return false;
            }
            if (s.contains("Mul R"+i+" -> R"+i+", Sqrt R"+i)) {
                return false;
            }
            if (s.contains("R"+i+ " -= R"+i)) {
                return false;
            }
            if (s.contains("Sqrt R"+i+", Mul R"+i+" -> R"+i)) {
                return false;
            }
            if (s.contains("JumpIfRegister1Zero R"+i+" -> R"+i+", JumpIfRegister1Zero R"+i+" -> R"+i))
            {
                return false;
            }
            if (s.contains("Move R"+i+ " -> R"+i))
            {
                return false;
            }
        }



        return true;
    }
}
