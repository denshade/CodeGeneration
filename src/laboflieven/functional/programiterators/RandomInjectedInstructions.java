package laboflieven.functional.programiterators;

import laboflieven.InstructionMark;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.registers.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomInjectedInstructions
{
    public static List<InstructionMark> getRandomInjectedInstructions(InstructionFactoryInterface factoryInterface, List<InstructionMark> instructions, List<Register> registers)
    {
        InstructionMark randomInstruction = factoryInterface.generateRandomInstruction(registers);
        Random r = new Random();
        int pickedInstruction = r.nextInt(instructions.size());
        List<InstructionMark> instructionsClone = new ArrayList<>(instructions);
        instructionsClone.set(pickedInstruction, randomInstruction);
        return instructionsClone;
    }

}
