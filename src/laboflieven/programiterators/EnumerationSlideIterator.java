package laboflieven.programiterators;

import laboflieven.ProgramEnumerator;
import laboflieven.ProgramResolution;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.common.AccInstructionOpcode;
import laboflieven.common.BestFitRegister;
import laboflieven.common.InstructionOpcode;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.Register;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class EnumerationSlideIterator {
    private final ProgramFitnessExaminerInterface evaluator;
    private final List<InstructionOpcode> instructions;
    private final InstructionFactoryInterface instructionFactoryInterface;
    private BestFitRegister<ProgramResolution> bestFit = new BestFitRegister<>();

    public EnumerationSlideIterator(ProgramFitnessExaminerInterface evaluator, List<InstructionOpcode> instructions, InstructionFactoryInterface instructionFactoryInterface)
    {
        this.evaluator = evaluator;
        this.instructions = instructions;
        this.instructionFactoryInterface = instructionFactoryInterface;
    }

    public ProgramResolution iterate(int nrRegisters, int nrInstructions)
    {
        var registers = Register.createRegisters(nrRegisters, "R");
        var enumerator = new ProgramEnumerator(instructions, nrRegisters);
        BigInteger maxCounter = enumerator.getMaxCounter(nrInstructions);
        int maxVal = 100;
        BigInteger div10 = maxCounter.divide(BigInteger.valueOf(maxVal));
        BigInteger current = div10;
        for (int i = 0; i < maxVal - 1; i++)
        {
            current = current.add(div10);
            var instructions = enumerator.convertToInstructions(current, instructionFactoryInterface);
            double bestScore = evaluator.calculateFitness(instructions, registers);
            System.out.println(current + " " + bestScore);
            bestFit.register(bestScore,
                    new ProgramResolution(instructions, bestScore)
            );
        }
        return bestFit.getBest();
    }
}
