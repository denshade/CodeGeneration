package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.ProgramResolution;
import laboflieven.common.ArrayListBestFitRegister;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
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

    SimulatedAnnealingIterator(InstructionFactoryInterface factoryInterface, double saturatedMax, int tempRaiser)
    {
        this.factoryInterface = factoryInterface;
        this.saturatedMax = saturatedMax;
        this.temperatureRaiser = tempRaiser;
    }
    public ProgramResolution iterate(int kMax, int nrInstructions, List<Register> registers, ProgramFitnessExaminerInterface evaluator)
    {
        Random r = new Random();
        List<InstructionMark> instructions = new ArrayList<>(nrInstructions);
        for (int i = 0; i < nrInstructions; i++) {
            instructions.add(factoryInterface.generateRandomInstruction(registers));
        }
        for (int k = 0; k < kMax; k++)
        {
            double t = (((double)k+1)/(double)kMax) * temperatureRaiser;
            List<InstructionMark> neighbour = getRandomNeighbour(instructions, registers);
            double randomInterval = r.nextDouble();
            double a = probabilityFollowNeighbour(evaluator.calculateFitness(instructions, registers), evaluator.calculateFitness(neighbour, registers), t);
            if (a >= randomInterval) {
                //System.out.println("followed ");
                instructions = neighbour;
            } else {
                System.out.println("not followed");
            }
            bestFit.register(evaluator.calculateFitness(instructions, registers), instructions);
        }
        return new ProgramResolution(bestFit.getBest(), evaluator.calculateFitness(bestFit.getBest(), registers));
    }

    public double probabilityFollowNeighbour(double currentScore, double neighbourScore, double t) {
        currentScore = Math.min(saturatedMax, currentScore);
        neighbourScore = Math.min(saturatedMax, neighbourScore);
        double currentHighScore = saturatedMax - currentScore; // higher scores are better.
        double neighbourHighScore = saturatedMax - neighbourScore;
        double exp = Math.exp(-1 * (currentHighScore - neighbourHighScore) / t);
        //System.out.println(exp + " current: " + currentScore + " " + neighbourScore);
        return exp;
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
