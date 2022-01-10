package laboflieven.functional.programiterators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulatedAnnealingFollowNeighbourProbabilityTest {
    @Test
    public void check(){
        assertEquals(1.0, SimulatedAnnealingFollowNeighbourProbability.probabilityFollowNeighbour(12,12,120, 100));
        assertTrue(SimulatedAnnealingFollowNeighbourProbability.probabilityFollowNeighbour(12,8,120, 100) > .5);
        assertTrue(SimulatedAnnealingFollowNeighbourProbability.probabilityFollowNeighbour(8,100,120, 100) < 0.5);
        assertTrue(SimulatedAnnealingFollowNeighbourProbability.probabilityFollowNeighbour(8,100,2, 100) < 0.5);

    }

}