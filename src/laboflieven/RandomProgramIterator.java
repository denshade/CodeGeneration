package laboflieven;

import laboflieven.statements.Instruction;
import laboflieven.statements.InstructionEnum;
import laboflieven.statements.InstructionFactory;
import laboflieven.statements.Register;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class RandomProgramIterator {
    public int maximumInstructions = 12;
    public long counter = 0;

    public List<List<Instruction>> positiveSolutions = new ArrayList<>();
    private ProgramEvaluator evaluator;
    private Register[] registers;
    private int numberOfRegisters;


    public RandomProgramIterator(ProgramEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    public void iterate(int numberOfRegisters, int maximumInstructions) {
        this.numberOfRegisters = numberOfRegisters;
        this.maximumInstructions = maximumInstructions;
        registers = new Register[numberOfRegisters];
        for (int i = 0; i < registers.length; i++) {
            registers[i] = new Register("r" + i);
        }
        Set<Register> availableRegisters = new HashSet<>();
        availableRegisters.add(registers[registers.length - 1]);// Add the result register.
        while (true) {
            recurse(new ArrayList<>());
        }
    }

    public void recurse(List<Instruction> instructions) {
        if (instructions.size() >= maximumInstructions)
            return;

        int instructionsLeft = maximumInstructions - instructions.size();
        if (instructionsLeft < 0) {
            return;
        }

        Random r = new Random();
        int location = r.nextInt(InstructionEnum.values().length);
        InstructionEnum instruction = InstructionEnum.values()[location];
        if (instruction.isDualRegister()) {
            Register register1 = registers[r.nextInt(registers.length)];
            Register register2 = registers[r.nextInt(registers.length)];

            if (isUselessOp(instruction, register1, register2)) {
                return;
            }

            Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1, register2);
            instructions.add(actualInstruction);
            eval(instructions, Arrays.asList(registers));
            recurse(instructions);
            instructions.remove(0);
        } else {

            Register register1 = registers[r.nextInt(registers.length)];
            Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1);
            instructions.add(0, actualInstruction);
            eval(instructions, Arrays.asList(registers));
            /**
             * Available registers remains the same. No new registers are used.
             */
            recurse(instructions);
            instructions.remove(0);

        }
    }

    private boolean isUselessOp(InstructionEnum instruction, Register register1, Register register2) {
        return instruction == InstructionEnum.Move && register1.name.equals(register2.name);
    }

    private void eval(List<Instruction> instructions, List<Register> registers) {
        counter++;
        if (counter % 100000 == 0) {
            System.out.println(counter + " " + instructions);
        }
        if (instructions.size() == maximumInstructions && evaluator.evaluate(instructions, registers)) {
            positiveSolutions.add(new ArrayList<>(instructions));
            System.out.println("Found a program! " + positiveSolutions);
            //throw new StoppedByUserException();
        }

    }

}
