package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.common.RegularInstructionOpcode;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.statements.*;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class ReverseProgramIterator
{
    public int maximumInstructions = 12;
    public long counter = 0;

    public List<List<InstructionMark>> positiveSolutions = new ArrayList<>();
    private ProgramFitnessExaminerInterface evaluator;
    private InstructionSet[] enums;
    private Register[] registers;
    private int numberOfRegisters;

    private List<InstructionMark> bestSolution;
    private double bestScore = Double.MAX_VALUE;
    private InstructionFactoryInterface instructionFactory = new InstructionFactory();


    public ReverseProgramIterator(ProgramFitnessExaminerInterface evaluator)
    {
        this.evaluator = evaluator;
        enums = InstructionSet.values();
    }

    public ReverseProgramIterator(ProgramFitnessExaminerInterface evaluator, InstructionSet[] enums)
    {
        this.evaluator = evaluator;
        this.enums = enums;
    }
    public void iterate(int numberOfRegisters, int maximumInstructions)
    {
        this.numberOfRegisters = numberOfRegisters;
        this.maximumInstructions = maximumInstructions;
        registers = Register.createRegisters(numberOfRegisters, "R").toArray(new Register[0]);
        Set<Register> availableRegisters = new HashSet<>();
        availableRegisters.add(registers[registers.length - 1]);// Add the result register.
        recurse(new ArrayList<>(), availableRegisters);
    }

    public void recurse(List<InstructionMark> instructions, Set<Register> availableRegisters)
    {
        if (instructions.size() >= maximumInstructions)
            return;

        /*Random r = new Random();
        if (r.nextInt(1000)==0){
            System.out.println(instructions);
        }*/
        int unusedRegisters = numberOfRegisters - (availableRegisters.size()); //not entirely correct, register1 can be part of available.
        int instructionsLeft = maximumInstructions - instructions.size();
        if (unusedRegisters > instructionsLeft)
        {
            return;
        }
        List<Register> registerList = Arrays.asList(registers);
        for (InstructionSet instruction : enums)
        {
            if (instruction.isDualRegister()) {
                for (Register register1 : registers) {

                    for (Register register2 : registers) {
                        if (instruction == InstructionSet.Move && register1.name.equals(register2.name)) {
                            continue;
                        }

                        InstructionMark actualInstruction = instructionFactory.createInstruction(new RegularInstructionOpcode(instruction), register1, register2);
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
                for (Register register1 : registers) {
                    InstructionMark actualInstruction = instructionFactory.createInstruction(new RegularInstructionOpcode(instruction), register1);
                    instructions.add(0, actualInstruction);
                    eval(instructions, Arrays.asList(registers));
                    /*
                     * Available registers remains the same. No new registers are used.
                     */
                    recurse(instructions, availableRegisters);
                    instructions.remove(0);
                }

            }
        }
    }

    private void eval(List<InstructionMark> instructions, List<Register> registers) {
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
            //throw new StoppedByUserException();
        }

    }

}
