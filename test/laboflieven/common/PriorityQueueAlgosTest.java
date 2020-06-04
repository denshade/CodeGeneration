package laboflieven.common;

import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueAlgosTest {

    @Test
    void cutPopulation() {
        PriorityQueue<Integer> solutions = new PriorityQueue<Integer>();
        for (int i = 200000; i > -1; i--) {
            solutions.add(i);
        }
        solutions = PriorityQueueAlgos.cutPopulation(10000,solutions);
        for (int i = 0; i < 10; i++) {
            assertEquals(i, solutions.poll());
        }
    }
    @Test
    void getNth() {
        PriorityQueue<Integer> solutions = new PriorityQueue<Integer>();
        for (int i = 200000; i > -1; i--) {
            solutions.add(i);
        }
        Object obj = PriorityQueueAlgos.getNthBestSolution(solutions,3);
        assertEquals(obj, 3);
    }
}