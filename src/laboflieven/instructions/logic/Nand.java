package laboflieven.instructions.logic;

import laboflieven.common.InstructionOpcode;
import laboflieven.instructions.regular.RegularInstructionOpcodeEnum;
import laboflieven.registers.Register;
import laboflieven.registers.TemplateRegister;

public class Nand extends DualRegisterInstruction {

    public Nand(TemplateRegister<Boolean> source, TemplateRegister<Boolean> destination) {
        super(source, destination);
    }

    @Override
    public Integer execute(int current) {
        boolean sourceB = source.value ;
        boolean destinationB = destination.value;
        boolean evaluation = !(sourceB && destinationB);
        destination.value = evaluation;
        return null;
    }

    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.RegularInstructionOpcode(RegularInstructionOpcodeEnum.Nand);
    }

}
