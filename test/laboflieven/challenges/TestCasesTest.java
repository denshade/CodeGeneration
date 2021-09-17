package laboflieven.challenges;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestCasesTest {

    @Test
    void loadFromCsvString() {
        assertEquals(1, TestCases.loadFromCsvString("data1,data2\n" +
                                            "2,4", 1).size());

    }
}