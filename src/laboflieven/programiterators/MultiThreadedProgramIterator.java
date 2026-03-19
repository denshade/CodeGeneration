package laboflieven.programiterators;

import laboflieven.ProgramResolution;
import laboflieven.common.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class MultiThreadedProgramIterator implements ProgramIterator
{
    private final int threads;
    private final Supplier<ProgramIterator> iteratorFactory;

    public MultiThreadedProgramIterator(int threads) {
        this(threads, RandomProgramIterator::new);
    }

    /**
     * @param iteratorFactory returns a fresh {@link ProgramIterator} instance per thread.
     *                         This avoids sharing mutable iterator state across threads.
     */
    public MultiThreadedProgramIterator(int threads, Supplier<ProgramIterator> iteratorFactory) {
        this.threads = threads;
        this.iteratorFactory = Objects.requireNonNull(iteratorFactory, "iteratorFactory");
    }

    /**
     * Uses {@code iteratorPrototype.getClass()} to create fresh instances per thread.
     * The iterator class must have a public no-arg constructor.
     */
    public MultiThreadedProgramIterator(int threads, ProgramIterator iteratorPrototype) {
        this(threads, () -> {
            Objects.requireNonNull(iteratorPrototype, "iteratorPrototype");
            try {
                return iteratorPrototype.getClass().getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                throw new IllegalArgumentException("ProgramIterator class must have a no-arg constructor: "
                        + iteratorPrototype.getClass().getName(), e);
            }
        });
    }

    public ProgramResolution iterate(Configuration config)
    {
        var pool = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            pool.execute(() -> iteratorFactory.get().iterate(config));
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
