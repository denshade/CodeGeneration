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
    public static int maximumInstructions = 12;
    public long counter = 0;

    public List<List<Instruction>> positiveSolutions = new ArrayList<>();
    private ProgramEvaluator evaluator;
    private Register[] registers;
    private int numberOfRegisters;


    public ReverseProgramIterator(ProgramEvaluator evaluator)
    {
        this.evaluator = evaluator;
    }
    public void iterate(int numberOfRegisters)
    {
        this.numberOfRegisters = numberOfRegisters;
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
        for (InstructionEnum instruction : InstructionEnum.values())
        {
            if (instruction.isDualRegister()) {
                for (Register register1 : registers) {

                    for (Register register2 : availableRegisters) {
                        if (instruction == InstructionEnum.Move && register1.name.equals(register2.name)) {
                            continue;
                        }

                        Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1, register2);
                        instructions.add(0, actualInstruction);
                        eval(instructions, Arrays.asList(registers));
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
        counter++;
        if (evaluator.evaluate(instructions, registers)){
            positiveSolutions.add(new ArrayList<>(instructions));
        }
        if (counter % 10000000 == 0)
        {
            System.out.println(counter + " " + positiveSolutions.size());
        }
    }
    public static void mainHard()
    {
        List<InOutParameters> collection = new ArrayList<>();
        collection.add(createParameter(2.0,-8.0,-24.0,0.0, 6.0));
        collection.add(createParameter(1.0, 2.0, 1.0, 0.0, -1.0));
        collection.add(createParameter(1.0, -1, -56, 0.0, 8));
        collection.add(createParameter(1.0, 2, -15, 0.0, 3));
        ProgramEvaluator evaluator = new ProgramEvaluator(collection);
        ReverseProgramIterator iterator = new ReverseProgramIterator(evaluator);
        iterator.iterate(4);
        System.out.println(iterator.counter);
        System.out.println(iterator.positiveSolutions.size());
    }

    private static Map<String, Double> getMap(double a,double b,double c,double d)
    {
        Map<String, Double> results = new HashMap<>();
        results.put("r0", a);
        results.put("r1", b);
        results.put("r2", c);
        results.put("r3", d);
        return results;
    }

    private static InOutParameters createParameter(double a, double b, double c, double d, double result)
    {
        Map<String, Double> startParameters  = getMap(a,b,c,d);
        Map<String, Double> endParameters = new HashMap<>(1);
        endParameters.put("r3", result);
        InOutParameters parameters = new InOutParameters();
        parameters.input = startParameters;
        parameters.expectedOutput = endParameters;
        return parameters;
    }

    private static InOutParameters createParameterSimple(double a, double b, double result)
    {
        Map<String, Double> startParameters = new HashMap<>();
        startParameters.put("r0", a);
        startParameters.put("r1", b);
        Map<String, Double> endParameters = new HashMap<>(1);
        endParameters.put("r1", result);
        InOutParameters parameters = new InOutParameters();
        parameters.input = startParameters;
        parameters.expectedOutput = endParameters;
        return parameters;
    }

    public static void main(String[] args)
    {
        boolean easy = false;
        maximumInstructions = 4;

        if (easy)mainEasy();
                else mainHard();
    }

    public static void mainEasy()
    {
        ProgramEvaluator evaluator = new ProgramEvaluator(Collections.singletonList(createParameterSimple(1,2,3)));
        ReverseProgramIterator iterator = new ReverseProgramIterator(evaluator);
        iterator.iterate(2);
        System.out.println(iterator.counter);
        System.out.println(iterator.positiveSolutions);
        System.out.println(iterator.positiveSolutions.size());
    }
}
