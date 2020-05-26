package laboflieven.common;

import laboflieven.accinstructions.AccProgramResolution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class PriorityQueueAlgos {

    public static PriorityQueue cutPopulation(int maxPopulation, final PriorityQueue solutions)
    {
        List l = (List)solutions.stream().limit(maxPopulation).collect(Collectors.toList());
        return new PriorityQueue(l);
    }

    public static Object getNthBestSolution(PriorityQueue solutions, int n) {
        Iterator it = solutions.iterator();
        Object val = null;
        for (int k = 0; k <= n; k++) {
            val = it.next();
        }
        return val;
    }
}