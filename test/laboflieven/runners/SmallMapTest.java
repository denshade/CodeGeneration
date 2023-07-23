package laboflieven.runners;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SmallMapTest {

    @Test
    void testSize(){
        var map = new SmallMap(List.of("R1", "left", "right"));
        assertEquals(3, map.size());
    }
    @Test
    void testGetAndPut() {
        var map = new SmallMap(List.of("R1", "left", "right"));
        map.put("R1", 2.6);
        assertEquals(2.6, map.get("R1"));
    }
    @Test
    void keySet()
    {
        var map = new SmallMap(List.of("R1", "left", "right"));
        assertNotNull(map.keySet());
    }
}