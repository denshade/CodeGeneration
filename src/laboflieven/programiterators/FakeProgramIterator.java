package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.ProgramResolution;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.recursionheuristics.RecursionHeuristic;
import laboflieven.registers.NumberNamingScheme;
import laboflieven.registers.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class FakeProgramIterator implements ProgramIterator
{
    public int maximumInstructions;
    public long counter = 0;
    public long secondsSinceEpochStart = System.currentTimeMillis() / 1000;
    private static final Logger LOGGER = Logger.getLogger(FakeProgramIterator.class.getName());
    public List<List<InstructionMark>> positiveSolutions = new ArrayList<>();
    private final ProgramFitnessExaminerInterface evaluator;
    private AccInstructionOpcodeEnum[] instructionEnums;
    private final List<InstructionMark> solution;
    private RecursionHeuristic heuristic = new AlwaysRecursionHeuristic();

    public FakeProgramIterator(ProgramFitnessExaminerInterface evaluator, List<InstructionMark> solution, RecursionHeuristic heuristic)
    {
        this.evaluator = evaluator;
        this.solution = solution;
        this.heuristic = heuristic;
    }


    public ProgramResolution iterate(final int nrOfRegisters, int maximumInstructions)
    {
        this.maximumInstructions = maximumInstructions;
        List<Register> registers = new NumberNamingScheme().createRegisters(nrOfRegisters);
        List<InstructionMark> solutionSoFar = new ArrayList<>();
        for (InstructionMark s : solution) {
            solutionSoFar.add(s);
            Program prog = new Program(solutionSoFar, registers);
            if (!heuristic.shouldRecurse(prog, maximumInstructions)) {
                LOGGER.warning("Not recursing on " + solutionSoFar);
            } else {
                eval(solutionSoFar, registers);
            }
        }
        return new ProgramResolution(positiveSolutions.get(0), evaluator.calculateFitness(positiveSolutions.get(0), registers));
    }


    public void eval(List<InstructionMark> instructions, List<Register> registers) {
        counter++;
        if (evaluator.isFit(instructions, registers)){
            System.out.println("Found a program: " + instructions);
            System.exit(0);
            positiveSolutions.add(new ArrayList<>(instructions));
        } else {
            LOGGER.warning(evaluator.calculateFitness(instructions, registers) + " diff" + instructions);
        }
    }


    @Override
    public ProgramResolution iterate(Configuration configuration) {
        return iterate(configuration.getNumberOfRegisters(2), configuration.getMaxNrInstructions(10));
    }
}
