package laboflieven.instructions.accinstructions;

import laboflieven.InstructionMark;
import laboflieven.registers.Register;

public interface JumpInstruction extends InstructionMark
{
    Integer execute(Register left, Register right, Register jump, int ip);
}
