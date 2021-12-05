package edu.neu.coe.info6205.sort.huskySort;

import edu.neu.coe.info6205.sort.huskySortUtils.Coding;
import edu.neu.coe.info6205.sort.huskySortUtils.HuskyCoder;
import edu.neu.coe.info6205.sort.huskySortUtils.HuskyCoderFactory;
import edu.neu.coe.info6205.sort.elementary.InsertionSort;
import edu.neu.coe.info6205.util.Benchmark;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.LazyLogger;
import edu.neu.coe.info6205.util.Utilities;
import org.apache.log4j.BasicConfigurator;

import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Arrays.binarySearch;

/**
 * This class represents the purest form of Husky Sort based on IntroSort for pass 1 and the System sort for pass 2.
 * <p>
 * CONSIDER redefining all of the "to" parameters to be consistent with our other Sort utilities.
 *
 * @param <X> the type of the elements to be sorted.
 */
public class PureHuskySort<X extends Comparable<X>> {

    public static void main(final String[] args) {
        BasicConfigurator.configure();
        final int m = 100;
        words = Utilities.getWords("/shuffledHindi.txt", PureHuskySort.class);
        for (int i = 0; i<=12; i++) {
            logger.info("PureHuskySort.main: sorting " + N + " random alphabetic words " + m + " times");
            Supplier<String[]> supplier = () -> createArray(N);
            PureHuskySort.runBenchmark(supplier);
            N = N * 2;
        }
    }

    private static void runBenchmark(Supplier supplier){
        final PureHuskySort<String> pureHuskySort = new PureHuskySort<>(HuskyCoderFactory.unicodeCoder, false, false);
        final Benchmark<String[]> benchmark = new Benchmark_Timer<>("Husky sort", null, pureHuskySort::sort, null);
        logger.info(Utilities.formatDecimal3Places(benchmark.runFromSupplier(supplier, 100)) + " ms");
    }

    private static String[] createArray(int size) {
        if(size < words.length){
            return Arrays.stream(words).limit(size).collect(Collectors.toList()).toArray(new String[0]);
        }
        return words;
    }

    /**
     * The main sort method.
     *
     * @param xs the array to be sorted.
     */
    public void sort(final X[] xs) {
        // NOTE: we start with a random shuffle
        // This is necessary if we might be sorting a pre-sorted array. Otherwise, we usually don't need it.
        if (mayBeSorted) Collections.shuffle(Arrays.asList(xs));
        // NOTE: First pass where we code to longs and sort according to those.
        final Coding coding = huskyCoder.huskyEncode(xs);
        final long[] longs = coding.longs;
        introSort(xs, longs, 0, longs.length, 2 * floor_lg(xs.length));

        // NOTE: Second pass (if required) to fix any remaining inversions.
        if (coding.perfect)
            return;
        if (useInsertionSort)
            new InsertionSort<X>().mutatingSort(xs);
        else {
            final Collator collator = huskyCoder.getCollator();
            if (collator == null) Arrays.sort(xs);
            else Arrays.sort(xs, collator);
        }
    }

    /**
     * Primary constructor.
     *
     * @param huskyCoder       the Husky coder to be used for the encoding to longs.
     * @param mayBeSorted      if this is true, then we should perform a random shuffle to prevent an O(N*N) performance.
     *                         NOTE: that even though we are using IntroSort, the random shuffle precaution is necessary when
     * @param useInsertionSort if true, then insertion sort will be used to mop up remaining inversions instead of system sort.
     */
    public PureHuskySort(final HuskyCoder<X> huskyCoder, final boolean mayBeSorted, final boolean useInsertionSort) {
        this.huskyCoder = huskyCoder;
        this.mayBeSorted = mayBeSorted;
        this.useInsertionSort = useInsertionSort;
    }

    // CONSIDER invoke method in IntroSort
    private static int floor_lg(final int a) {
        return (int) (Math.floor(Math.log(a) / Math.log(2)));
    }

    private static final int sizeThreshold = 16;

    // TEST
    @SuppressWarnings({"UnnecessaryLocalVariable"})
    private void introSort(final X[] objects, final long[] longs, final int from, final int to, final int depthThreshold) {
        // CONSIDER merge with IntroHuskySort
        if (to - from <= sizeThreshold + 1) {
            insertionSort(objects, longs, from, to);
            return;
        }
        if (depthThreshold == 0) {
            heapSort(objects, longs, from, to);
            return;
        }

        final int lo = from;
        final int hi = to - 1;

        if (longs[hi] < longs[lo]) swap(objects, longs, lo, hi);

        int lt = lo + 1, gt = hi - 1;
        int i = lo + 1;
        while (i <= gt) {
            if (longs[i] < longs[lo]) swap(objects, longs, lt++, i++);
            else if (longs[hi] < longs[i]) swap(objects, longs, i, gt--);
            else i++;
        }
        swap(objects, longs, lo, --lt);
        swap(objects, longs, hi, ++gt);
        introSort(objects, longs, lo, lt, depthThreshold - 1);
        if (longs[lt] < longs[gt]) introSort(objects, longs, lt + 1, gt, depthThreshold - 1);
        introSort(objects, longs, gt + 1, hi + 1, depthThreshold - 1);
    }

    // TEST
    private static <T extends Comparable<T>> void heapSort(final T[] objects, final long[] longs, final int from, final int to) {
        // CONSIDER removing these size checks. They haven't really been tested.
        if (to - from <= sizeThreshold + 1) {
            insertionSort(objects, longs, from, to);
            return;
        }
        final int n = to - from;
        for (int i = n / 2; i >= 1; i = i - 1) {
            downHeap(objects, longs, i, n, from);
        }
        for (int i = n; i > 1; i = i - 1) {
            swap(objects, longs, from, from + i - 1);
            downHeap(objects, longs, 1, i - 1, from);
        }
    }

    // TEST
    private static <T extends Comparable<T>> void downHeap(final T[] objects, final long[] longs, int i, final int n, final int lo) {
        final long d = longs[lo + i - 1];
        final T od = objects[lo + i - 1];
        int child;
        while (i <= n / 2) {
            child = 2 * i;
            if (child < n && longs[lo + child - 1] < longs[lo + child]) child++;
            if (d >= longs[lo + child - 1]) break;
            longs[lo + i - 1] = longs[lo + child - 1];
            objects[lo + i - 1] = objects[lo + child - 1];
            i = child;
        }
        longs[lo + i - 1] = d;
        objects[lo + i - 1] = od;
    }

    static <T extends Comparable<T>> void insertionSort(final T[] objects, final long[] longs, final int from, final int to) {
        for (int i = from + 1; i < to; i++)
            if (OPTIMIZED)
                swapIntoSorted(objects, longs, i);
            else
                for (int j = i; j > from && longs[j] < longs[j - 1]; j--)
                    swap(objects, longs, j, j - 1);
    }

    /**
     * Regular swap of elements at indexes i and j, not necessarily adjacent.
     * However, for insertion sort, they will always be adjacent.
     *
     * @param xs    the X array.
     * @param longs the long array.
     * @param i     the index of one element to be swapped.
     * @param j     the index of the other element to be swapped.
     */
    private static <T extends Comparable<T>> void swap(final T[] xs, final long[] longs, final int i, final int j) {
        // Swap longs
        final long temp1 = longs[i];
        longs[i] = longs[j];
        longs[j] = temp1;
        // Swap xs
        final T temp2 = xs[i];
        xs[i] = xs[j];
        xs[j] = temp2;
    }

    /**
     * Swap method for insertion sort which takes advantage of the known fact that the elements of the array
     * at indices less than i are in order.
     *
     * @param xs    the X array.
     * @param longs the long array.
     * @param i     the index of the element to be moved.
     */
    private static <T extends Comparable<T>> void swapIntoSorted(final T[] xs, final long[] longs, final int i) {
        int j = binarySearch(longs, 0, i, longs[i]);
        if (j < 0) j = -j - 1;
        if (j < i) swapInto(xs, longs, j, i);
    }

    /**
     * Swap method which uses half-swaps.
     *
     * @param xs    the X array.
     * @param longs the long array.
     * @param i     the index of the element to be moved.
     * @param j     the index of the destination of that element.
     */
    static <T extends Comparable<T>> void swapInto(final T[] xs, final long[] longs, final int i, final int j) {
        if (j > i) {
            final T x = xs[j];
            System.arraycopy(xs, i, xs, i + 1, j - i);
            xs[i] = x;
            final long l = longs[j];
            System.arraycopy(longs, i, longs, i + 1, j - i);
            longs[i] = l;
        }
    }

    private HuskyCoder<X> getHuskyCoder() {
        return huskyCoder;
    }

    // NOTE that we keep this false because, for the size of arrays that we need to sort via insertion sort,
    // This optimization doesn't really help.
    // That might be because (a) arrays are short and (b) the binary search will likely take quite a bit longer than
    // necessary when the array is already close to being in order (since binary search starts in the middle).
    // It would be like looking up aardvark in the dictionary using strict binary search.
    private static final boolean OPTIMIZED = false;

    private final HuskyCoder<X> huskyCoder;
    private final boolean mayBeSorted;
    private final boolean useInsertionSort;
    private static String[] words;
    private static int N = 1000;

    private final static LazyLogger logger = new LazyLogger(PureHuskySort.class);
}
