package laboflieven.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestFitRegisterTest {

    @Test
    void register() {
        BestFitRegister<String> a = new BestFitRegister<>();
        a.register(2, "second");
        a.register(1, "first");
        assertEquals("first", a.getBest());
    }
}