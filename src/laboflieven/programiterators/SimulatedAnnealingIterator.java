package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.ProgramResolution;
import laboflieven.common.ArrayListBestFitRegister;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.functional.programiterators.RandomInjectedInstructions;
import laboflieven.functional.programiterators.SimulatedAnnealingFollowNeighbourProbability;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulatedAnnealingIterator
{
    private double saturatedMax = 10000;
    private final InstructionFactoryInterface factoryInterface;
    private ArrayListBestFitRegister bestFit = new ArrayListBestFitRegister();
    private int temperatureRaiser = 100;

    public SimulatedAnnealingIterator(InstructionFactoryInterface factoryInterface, double saturatedMax, int tempRaiser)
    {
        this.factoryInterface = factoryInterface;
        this.saturatedMax = saturatedMax;
        this.temperatureRaiser = tempRaiser;
    }
    public ProgramResolution iterate(Configuration config)
    {
        return iterate(100000, config.getMaxNrInstructions(10), Register.createRegisters(config.getNumberOfRegisters(2), "R"), config.getFitnessExaminer());
    }

    public ProgramResolution iterate(int kMax, int nrInstructions, List<Register> registers, ProgramFitnessExaminerInterface evaluator)
    {
        Random r = new Random();
        List<InstructionMark> instructions = factoryInterface.generateRandomInstruction(registers, nrInstructions);
        for (int k = 0; k < kMax; k++)
        {
            double t = (kMax - k) * temperatureRaiser;
            List<InstructionMark> neighbour = RandomInjectedInstructions.getRandomInjectedInstructions(factoryInterface, instructions, registers);
            double randomInterval = r.nextDouble();
            double currentScore = evaluator.calculateFitness(instructions, registers);
            double neighbourScore = evaluator.calculateFitness(neighbour, registers);

            double a = SimulatedAnnealingFollowNeighbourProbability.probabilityFollowNeighbour(currentScore, neighbourScore, t, saturatedMax);
            if (a >= randomInterval) {
                instructions = neighbour;
            } else {
                System.out.println("not followed");
            }
            bestFit.register(evaluator.calculateFitness(instructions, registers), instructions);
        }
        return new ProgramResolution(bestFit.getBest(), evaluator.calculateFitness(bestFit.getBest(), registers));
    }

    private List<InstructionMark> getRandomNeighbour(List<InstructionMark> instructions, List<Register> registers) {
        InstructionMark randomInstruction = factoryInterface.generateRandomInstruction(registers);
        Random r = new Random();
        int pickedInstruction = r.nextInt(instructions.size());
        List<InstructionMark> instructionsClone = new ArrayList<>(instructions);
        instructionsClone.set(pickedInstruction, randomInstruction);
        return instructionsClone;

    }

}
