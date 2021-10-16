package laboflieven.accinstructions;

import laboflieven.common.ArrayOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AccInstructionOpcodeEnumBuilder
{

    private List<AccInstructionOpcodeEnum> enums = new ArrayList<>();
    public static AccInstructionOpcodeEnumBuilder make() {
        return new AccInstructionOpcodeEnumBuilder();
    }

    public AccInstructionOpcodeEnumBuilder with(AccInstructionOpcodeEnum... enumVals) {
        enums.addAll(Arrays.asList(enumVals));
        return this;
    }

    public AccInstructionOpcodeEnumBuilder anyExcept(Set<AccInstructionOpcodeEnum> eq)
    {
        ArrayOperations<AccInstructionOpcodeEnum> ops = new ArrayOperations<>();
        with(ops.anyExcept(eq, AccInstructionOpcodeEnum.values()).toArray(new AccInstructionOpcodeEnum[0]));
        return this;
    }

    public AccInstructionOpcodeEnumBuilder noGeo()
    {
        return anyExcept(Set.of(AccInstructionOpcodeEnum.LoadAccRightIntoRegister, AccInstructionOpcodeEnum.LoadIntoRightAcc,
                AccInstructionOpcodeEnum.JumpIfLteStart, AccInstructionOpcodeEnum.JumpIfGteStart,
                AccInstructionOpcodeEnum.Cos, AccInstructionOpcodeEnum.Sin));
    }

    public AccInstructionOpcodeEnum[] build()
    {
        return enums.toArray(new AccInstructionOpcodeEnum[0]);
    }
}
