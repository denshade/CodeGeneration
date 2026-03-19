package laboflieven.programiterators;

import laboflieven.ProgramResolution;
import laboflieven.common.Configuration;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class MultiThreadedProgramIteratorTest {

    @Test
    void iterateStopsOtherWorkersWhenOneFindsSolution() {
        AtomicInteger created = new AtomicInteger(0);
        AtomicInteger interruptedWorkers = new AtomicInteger(0);

        MultiThreadedProgramIterator iterator = new MultiThreadedProgramIterator(4, () -> {
            int index = created.getAndIncrement();
            if (index == 0) {
                // One worker quickly finds a valid solution.
                return config -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return new ProgramResolution(Collections.emptyList(), 0.0);
                };
            }

            // Other workers run indefinitely unless cancelled/interrupted.
            return config -> {
                try {
                    while (true) {
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    interruptedWorkers.incrementAndGet();
                    Thread.currentThread().interrupt();
                    return new ProgramResolution(Collections.emptyList(), 1.0);
                }
            };
        });

        Configuration config = new Configuration();
        ProgramResolution resolution = assertTimeoutPreemptively(Duration.ofSeconds(2), () -> iterator.iterate(config));

        assertNotNull(resolution);
        assertEquals(0.0, resolution.weight, 0.0000001);
        assertTrue(interruptedWorkers.get() > 0, "Expected non-winning workers to be interrupted");
    }
}