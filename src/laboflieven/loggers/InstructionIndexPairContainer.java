package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.common.InstructionOpcode;
import laboflieven.functional.loggers.InstructionIndexPair;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstructionIndexPairContainer
{
    private final List<InstructionOpcode> opcodes;
    private int maxX = 0;
    private int maxY = 0;
    private final Map<Point, Double> elements = new HashMap<>();

    public InstructionIndexPairContainer(List<InstructionOpcode> opcodes) {
        this.opcodes = opcodes;
    }


    public void addFitness(List<InstructionMark> instructions, int nrRegistersOld, double error) {
        InstructionIndexPair pair = new InstructionIndexPair(instructions, nrRegistersOld, opcodes);

        Point p = new Point();
        p.x  = pair.getX().intValue();
        p.y  = pair.getY().intValue();
        if (p.x > maxX) maxX = p.x;
        if (p.y > maxY) maxY = p.y;
        if (elements.containsKey(p)) {
            System.out.println("Duplicate key!!" + p);
        } else {
            elements.put(p, error);
        }
    }

    public Map<Point, Double> getElements() {
        return elements;
    }

    public int getMaxX()
    {
        return maxX;
    }
    public int getMaxY()
    {
        return maxY;
    }


}
