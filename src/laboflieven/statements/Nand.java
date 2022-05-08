package laboflieven.statements;

import laboflieven.common.InstructionOpcode;

public class Nand extends DualRegisterInstruction {

    public Nand(Register source, Register destination) {
        super(source, destination);
    }

    @Override
    public Integer execute(int current) {
        boolean sourceB = !(source.value < 0.0000001);
        boolean destinationB = !(destination.value < 0.0000001);
        boolean evaluation = !(sourceB && destinationB);
        destination.value = evaluation?1.0:0.0;
        return null;
    }

    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.RegularInstructionOpcode(RegularInstructionOpcodeEnum.Nand);
    }

}
