package laboflieven;

import laboflieven.statements.Instruction;
import laboflieven.statements.InstructionEnum;
import laboflieven.statements.InstructionFactory;
import laboflieven.statements.Register;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class ReverseProgramIterator
{
    public int maximumInstructions = 12;
    public long counter = 0;

    public List<List<Instruction>> positiveSolutions = new ArrayList<>();
    private ProgramFitnessExaminer evaluator;
    private InstructionEnum[] enums;
    private Register[] registers;
    private int numberOfRegisters;

    private List<Instruction> bestSolution;
    private double bestScore = 1000;


    public ReverseProgramIterator(ProgramFitnessExaminer evaluator)
    {
        this.evaluator = evaluator;
        enums = InstructionEnum.values();
    }

    public ReverseProgramIterator(ProgramFitnessExaminer evaluator, InstructionEnum[] enums)
    {
        this.evaluator = evaluator;
        this.enums = enums;
    }
    public void iterate(int numberOfRegisters, int maximumInstructions)
    {
        this.numberOfRegisters = numberOfRegisters;
        this.maximumInstructions = maximumInstructions;
        registers = new Register[numberOfRegisters];
        for (int i = 0; i <  registers.length; i++){
            registers[i] = new Register("r"+i);
        }
        Set<Register> availableRegisters = new HashSet<>();
        availableRegisters.add(registers[registers.length - 1]);// Add the result register.
        recurse(new ArrayList<>(), availableRegisters);
    }

    public void recurse(List<Instruction> instructions, Set<Register> availableRegisters)
    {
        if (instructions.size() >= maximumInstructions)
            return;

        /*Random r = new Random();
        if (r.nextInt(1000)==0){
            System.out.println(instructions);
        }*/
        int unusedRegisters = numberOfRegisters - (availableRegisters.size() + 1); //not entirely correct, register1 can be part of available.
        int instructionsLeft = maximumInstructions - instructions.size();
        if (unusedRegisters > instructionsLeft)
        {
            return;
        }
        List<Register> registerList = Arrays.asList(registers);
        for (InstructionEnum instruction : enums)
        {
            if (instruction.isDualRegister()) {
                for (Register register1 : registers) {

                    for (Register register2 : availableRegisters) {
                        if (instruction == InstructionEnum.Move && register1.name.equals(register2.name)) {
                            continue;
                        }

                        Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1, register2);
                        instructions.add(0, actualInstruction);
                        eval(instructions, registerList);
                        Set<Register> newlyAvailableRegisters = new HashSet<>(availableRegisters);
                        newlyAvailableRegisters.add(register1);
                        /*
                        Why not just add the newly added register? And be done with create sets every iteration?
                        You can't properly cleanup. You'd have to go over all instructions and check the available registers.
                         */

                        recurse(instructions, newlyAvailableRegisters);
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
                    recurse(instructions, availableRegisters);
                    instructions.remove(0);
                }

            }
        }
    }

    private void eval(List<Instruction> instructions, List<Register> registers) {
        /*if (instructions.size() != maximumInstructions)
            return;*/
        double val =  evaluator.calculateFitness(instructions, registers);
        if (val < bestScore)
        {
            bestScore = val;
            bestSolution = instructions;
            System.out.println(counter + " " + instructions + " " + evaluator.calculateFitness(instructions, registers));
        }
        if (val < 0.0001){ //evaluator.isFit(instructions, registers
            positiveSolutions.add(new ArrayList<>(instructions));
            System.out.println("Found a program: " + instructions);
            throw new StoppedByUserException();
        }

    }

}
