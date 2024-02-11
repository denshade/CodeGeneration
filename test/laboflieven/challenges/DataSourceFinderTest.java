package laboflieven.challenges;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DataSourceFinderTest {

    @Test
    void runForConfig() throws IOException {
        var s = DataSourceFinder.runForConfig("NR_REGISTERS=3 PROGRAM_ITERATOR=brute MAX_NR_OF_INSTRUCTIONS=4 RECURSION_HEURISTIC=Acc".split(" "),
                "0,0,0\n1,1,2\n1,0,1");
        //run config parses and puts data in a map.
        assertNotNull(s.get("CONFIG"));
        assertNotNull(s.get("BESTSCORE"));
        assertNotNull(s.get("DEFAULTSCORE"));
        assertNotNull(s.get("RELATIVE_SCORE"));
        assertNotNull(s.get("TIMING"));

    }
}