package laboflieven.programiterators;

import laboflieven.ProgramResolution;
import laboflieven.common.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadedProgramIterator implements ProgramIterator
{
    private final int threads;

    public MultiThreadedProgramIterator(int threads) {
        this.threads = threads;
    }

    public ProgramResolution iterate(Configuration config)
    {
        var pool = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            var iterator = new RandomProgramIterator();
            pool.submit(() -> iterator.iterate(config));
        }
        try {
            Thread.sleep(3600000);
            //pool.awaitTermination(1, TimeUnit.HOURS); //this blocks all threads.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
