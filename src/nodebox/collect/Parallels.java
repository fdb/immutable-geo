package nodebox.collect;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Parallel utilities for transforming a list of homogeneous elements.
 */
public class Parallels {

    private final static int numThreads = Runtime.getRuntime().availableProcessors() + 2;
    private final static ExecutorService service = Executors.newFixedThreadPool(numThreads);

    public static <F, T> List<T> chunkedTransform(Iterable<F> fromList, int fromListSize, final Function<? super F, T> transform) {
        final Iterable<List<F>> chunks = Iterables.partition(fromList, fromListSize / numThreads);

        List<Iterable<T>> newChunks = transform(chunks, new Function<Iterable<F>, Iterable<T>>() {
            public Iterable<T> apply(Iterable<F> from) {
                ImmutableList.Builder<T> builder = ImmutableList.builder();
                for (F f: from) {
                    builder.add(transform.apply(f));
                }
                return builder.build();
            }
        });

        ImmutableList.Builder<T> builder = ImmutableList.builder();
        for(Iterable<T> newChunk:newChunks) {
            builder.addAll(newChunk);
        }
        return builder.build();
    }

    /**
     * Transform a list using the transformation function, wrapping each transformation in a callable.
     * @param fromList
     * @param transform
     * @param <F>
     * @param <T>
     * @return
     */
    public static <F, T> List<T> transform(Iterable<F> fromList, Function<? super F, T> transform) {
        ExecutorService executors = Executors.newFixedThreadPool(numThreads);
        try {
            Function<F, Callable<T>> toCallable = toCallable(transform);
            Function<Future<T>, T> fromFuture = fromFuture();
            Iterable<Callable<T>> callables = Iterables.transform(fromList, toCallable);
            List<Future<T>> futures = executors.invokeAll(ImmutableList.copyOf(callables));
            return Lists.transform(futures, fromFuture);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executors.shutdown();
        }
    }

    private static <F, T> Function<F, Callable<T>> toCallable(final Function<? super F, T> transform) {
        return new Function<F, Callable<T>>() {
            public Callable<T> apply(final F from) {
                return new Callable<T>() {
                    public T call() throws Exception {
                        return transform.apply(from);
                    }
                };
            }
        };
    }

    private static <T> Function<Future<T>, T> fromFuture() {
        return new Function<Future<T>, T>() {
            public T apply(Future<T> result) {
                try {
                    return result.get();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
