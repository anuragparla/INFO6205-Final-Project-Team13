package edu.neu.coe.info6205.sort.linearithmic;

import edu.neu.coe.info6205.util.*;
import org.apache.log4j.BasicConfigurator;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class QuickSort_DualPivot_Benchmark {
    public void runBenchmark(String description, Supplier<String[]> supplier) {

        try {
            Benchmark<String[]> benchmark = new Benchmark_Timer<>(
                    description + " for Strings for size " + N,
                    (String[] xs) -> new QuickSort_DualPivot<String>(xs.length,config).sort(xs,false)
            );
            //logger.info(Utilities.formatDecimal3Places(benchmark.runFromSupplier(supplier, 100)) + " ms");
            System.out.println(benchmark.runFromSupplier(supplier, 1) + " ms");
        }
        catch (Exception e){
            System.out.println("IO exception");
            e.printStackTrace(System.out);
        }

    }

    private static String[] createArray(int size) {
        if(size < words.length){
            return Arrays.stream(words).limit(size).collect(Collectors.toList()).toArray(new String[0]);
        }
        return words;
    }

    private static String[] words;
    final static LazyLogger logger = new LazyLogger(QuickSort_DualPivot_Benchmark.class);
    private static Config config;
    public static int N = 1000;
    public static void main(String[] args)
    {
        BasicConfigurator.configure();
        String description = "Dual Pivot quicksort for the given array of unicode characters";
        words = Utilities.getWords("/shuffledHindi.txt", QuickSort_DualPivot_Benchmark.class);
        for (int i = 0; i<=12; i++) {
            QuickSort_DualPivot_Benchmark dpqs = new QuickSort_DualPivot_Benchmark();
            Supplier<String[]> supplier = () -> createArray(N);
            dpqs.runBenchmark(description,supplier);
            N = N * 2;
        }
    }

}
