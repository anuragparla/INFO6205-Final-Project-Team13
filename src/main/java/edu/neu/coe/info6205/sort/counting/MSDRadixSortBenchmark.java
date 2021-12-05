package edu.neu.coe.info6205.sort.counting;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.GenericSort;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.util.*;
import org.apache.log4j.BasicConfigurator;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MSDRadixSortBenchmark  {

    private static Config config;
    public static int N = 1000;
    final static LazyLogger logger = new LazyLogger(MSDRadixSortBenchmark.class);
    private static String[] words;

    public static void main(String []args) {
        BasicConfigurator.configure();
        System.out.println("Benchmarking msd radix sort");
        words = Utilities.getWords("/shuffledHindi.txt", MSDRadixSortBenchmark.class);
        //Array with elements in random order
        for (int i = 0; i<=12; i++) {
            Helper<String> helper = new BaseHelper<>("MSD Radix Sort - Random", N, config);
            Supplier<String[]> supplier = () -> createArray(N);
            MSDRadixSortBenchmark.runBenchmark(helper, supplier, "Random");
            N = N * 2;
        }

    }

    private static String[] createArray(int size) {
        if(size < words.length){
            return Arrays.stream(words).limit(size).collect(Collectors.toList()).toArray(new String[0]);
        }
        return words;
    }

    private static void runBenchmark(Helper helper, Supplier supplier, String description){
        final GenericSort<String> sort = new MSDRadixSort<>(helper);
        final Benchmark<String[]> benchmark = new Benchmark_Timer<>(
                "MSD Radix sort " + description + " for array of size " + N,
                (xs) -> Arrays.copyOf(xs, xs.length),
                sort::sort,
                null
        );
        logger.info(Utilities.formatDecimal3Places(benchmark.runFromSupplier(supplier, 10)) + " ms");
    }

}
