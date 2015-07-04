package laboflieven;

import laboflieven.statements.Instruction;
import laboflieven.statements.InstructionEnum;
import laboflieven.statements.InstructionFactory;
import laboflieven.statements.Register;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class ReverseProgramGetter {
    public int maximumInstructions = 12;
    private int instructionCount;
    public long counter = 0;

    public List<List<Instruction>> positiveSolutions = new ArrayList<>();

    private Register[] registers;
    private int numberOfRegisters;


    public ReverseProgramGetter() {

    }

    public Program iterate(int numberOfRegisters, int maximumInstructions, int instructionCount) {
        this.numberOfRegisters = numberOfRegisters;
        this.maximumInstructions = maximumInstructions;
        this.instructionCount = instructionCount;
        registers = new Register[numberOfRegisters];
        for (int i = 0; i < registers.length; i++) {
            registers[i] = new Register("r" + i);
        }
        Set<Register> availableRegisters = new HashSet<>();
        availableRegisters.add(registers[registers.length - 1]);// Add the result register.
        return recurse(new ArrayList<>(), availableRegisters);
    }

    public Program recurse(List<Instruction> instructions, Set<Register> availableRegisters) {
        if (instructions.size() >= maximumInstructions)
            return null;

        /*Random r = new Random();
        if (r.nextInt(1000)==0){
            System.out.println(instructions);
        }*/
        int unusedRegisters = numberOfRegisters - (availableRegisters.size() + 1); //not entirely correct, register1 can be part of available.
        int instructionsLeft = maximumInstructions - instructions.size();
        if (unusedRegisters > instructionsLeft) {
            return null;
        }
        for (InstructionEnum instruction : InstructionEnum.values()) {
            if (instruction.isDualRegister()) {
                for (Register register1 : registers) {

                    for (Register register2 : availableRegisters) {
                        if (instruction == InstructionEnum.Move && register1.name.equals(register2.name)) {
                            continue;
                        }

                        Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1, register2);
                        instructions.add(0, actualInstruction);
                        Program program = eval(instructions, Arrays.asList(registers));
                        if (program != null)
                            return program;
                        Set<Register> newlyAvailableRegisters = new HashSet<>(availableRegisters);
                        newlyAvailableRegisters.add(register1);
                        /*
                        Why not just add the newly added register? And be done with create sets every iteration?
                        You can't properly cleanup. You'd have to go over all instructions and check the available registers.
                         */

                        program = recurse(instructions, newlyAvailableRegisters);
                        if (program != null)
                            return program;
                        instructions.remove(0);
                    }
                }
            } else {
                for (Register register1 : availableRegisters) {
                    Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1);
                    instructions.add(0, actualInstruction);
                    eval(instructions, Arrays.asList(registers));
                    /**
                     * Available registers remains the same. No new registers are used.
                     */
                    Program program = recurse(instructions, availableRegisters);
                    if (program != null)
                        return program;
                    instructions.remove(0);
                }

            }
        }
        return null;
    }

    private Program eval(List<Instruction> instructions, List<Register> registers) {
        counter++;
        if (counter == instructionCount) {
            return new Program(instructions, registers);
        }
        return null;
    }

    public static void main(String[] args)
    {
        ReverseProgramGetter getter = new ReverseProgramGetter();
        System.out.println(getter.iterate(2,2,2));
    }
}
