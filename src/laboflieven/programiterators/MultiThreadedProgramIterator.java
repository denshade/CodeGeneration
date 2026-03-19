package laboflieven.programiterators;

import laboflieven.ProgramResolution;
import laboflieven.common.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.Objects;
import java.util.function.Supplier;

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
        var completionService = new ExecutorCompletionService<ProgramResolution>(pool);
        List<Future<ProgramResolution>> futures = new ArrayList<>(threads);
        try {
            for (int i = 0; i < threads; i++) {
                futures.add(completionService.submit(() -> iteratorFactory.get().iterate(config)));
            }

            ProgramResolution best = null;
            for (int i = 0; i < threads; i++) {
                ProgramResolution result = completionService.take().get();
                if (result == null) {
                    continue;
                }
                if (best == null || result.weight < best.weight) {
                    best = result;
                }
                if (isSolutionFound(result, config)) {
                    return result;
                }
            }
            return best;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Failed while running multi-threaded iterators", e);
        } finally {
            for (Future<ProgramResolution> future : futures) {
                future.cancel(true);
            }
            pool.shutdownNow();
        }
    }

    private boolean isSolutionFound(ProgramResolution result, Configuration config) {
        return result.weight <= config.getErrorTolerance(0.0001);
    }
}
