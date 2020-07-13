package laboflieven.statements;

public class Nand extends DualRegisterInstruction {

    public Nand(Register source, Register destination) {
        super(source, destination);
    }

    @Override
    public Integer execute(int current) {
        boolean sourceB = source.value < 0.0000001 ? false: true;
        boolean destinationB = destination.value < 0.0000001 ? false: true;
        boolean evaluation = !(sourceB && destinationB);
        destination.value = evaluation?1.0:0.0;
        return null;
    }

    @Override
    public Object getInstructionOpcode() {
        return InstructionSet.Nand;
    }

}
