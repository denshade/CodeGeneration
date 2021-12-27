package laboflieven.common;

import laboflieven.accinstructions.AccInstructionOpcodeEnum;

public interface InstructionOpcode
{
    int getNrRegisters();
    String getName();
    Object getEnumeration();
}
