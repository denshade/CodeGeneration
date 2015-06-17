package laboflieven;

import laboflieven.statements.Instruction;
import laboflieven.statements.InstructionEnum;
import laboflieven.statements.InstructionFactory;
import laboflieven.statements.Register;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class BruteForceProgramIterator
{
    public static int maximumInstructions = 12;
    public long counter = 0;

    public List<List<Instruction>> positiveSolutions = new ArrayList<>();
    private ProgramEvaluator evaluator;


    public BruteForceProgramIterator(ProgramEvaluator evaluator)
    {
        this.evaluator = evaluator;
    }
    public void iterate()
    {
        Register[] registers = new Register[4];
        for (int i = 0; i <  registers.length; i++){
            registers[i] = new Register("r"+i);
        }
        recurse(new ArrayList<>(), registers);
    }

    public void recurse(List<Instruction> instructions, Register[] registers)
    {
        if (instructions.size() >= maximumInstructions)
            return;
        for (InstructionEnum instruction : InstructionEnum.values())
        {
            for (Register register1 : registers) {
                if (instruction.isDualRegister()) {
                    for (Register register2 : registers) {
                        if (instruction == InstructionEnum.Move && register1.name.equals(register2.name))
                        {
                            continue;
                        }
                        Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1, register2);
                        instructions.add(actualInstruction);
                        eval(instructions, Arrays.asList(registers));
                        recurse(instructions, registers);
                        instructions.remove(instructions.size() - 1);
                    }
                } else {
                    Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1);
                    instructions.add(actualInstruction);
                    eval(instructions, Arrays.asList(registers));
                    recurse(instructions, registers);
                    instructions.remove(instructions.size() - 1);
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
    public static void main(String[] args)
    {
        InOutParameters parameters = getInOutParameters1();
        InOutParameters parameters2 = getInOutParameters2();
        List<InOutParameters> collection = new ArrayList<>();
        collection.add(parameters);
        collection.add(parameters2);
        ProgramEvaluator evaluator = new ProgramEvaluator(collection);
        BruteForceProgramIterator iterator = new BruteForceProgramIterator(evaluator);
        maximumInstructions = 4;
        iterator.iterate();
        System.out.println(iterator.counter);
        System.out.println(iterator.positiveSolutions.size());
    }

    private static Map<String, Double> getMap(double a,double b,double c,double d)
    {
        Map<String, Double> results = new HashMap<String, Double>();
        results.put("r0", a);
        results.put("r1", b);
        results.put("r2", c);
        results.put("r3", d);
        return results;
    }

    private static InOutParameters getInOutParameters1() {
        return createParameter(2.0,-8.0,-24.0,0.0, 6.0);
    }

    private static InOutParameters getInOutParameters2() {
        return createParameter(1.0, 2.0, 1.0, 0.0, -1.0);
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

    public static void mainSimple(String[] args)
    {
        Map<String, Double> startParameters = new HashMap<>(4);
        startParameters.put("r0", 1.0);
        startParameters.put("r1", 2.0);
        startParameters.put("r2", 3.0);
        startParameters.put("r3", 4.0);
        Map<String, Double> endParameters = new HashMap<String, Double>(1);
        endParameters.put("r1", 3.0);
        InOutParameters parameters = new InOutParameters();
        parameters.input = startParameters;
        parameters.expectedOutput = endParameters;

        ProgramEvaluator evaluator = new ProgramEvaluator(Collections.singletonList(parameters));
        BruteForceProgramIterator iterator = new BruteForceProgramIterator(evaluator);
        maximumInstructions = 1;
        iterator.iterate();
        System.out.println(iterator.counter);
        System.out.println(iterator.positiveSolutions.size());
    }
}
