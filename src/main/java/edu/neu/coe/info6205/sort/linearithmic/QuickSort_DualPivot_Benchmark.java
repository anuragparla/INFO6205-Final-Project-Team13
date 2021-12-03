package edu.neu.coe.info6205.sort.linearithmic;

import edu.neu.coe.info6205.util.*;
import org.apache.log4j.BasicConfigurator;

import java.util.function.Supplier;

public class QuickSort_DualPivot_Benchmark {
    public void runBenchmark(String description, Supplier<String[]> supplier) {

        try {
            Benchmark<String[]> benchmark = new Benchmark_Timer<>(
                    description + " for Strings",
                    (String[] xs) -> new QuickSort_DualPivot<String>(xs.length,config).sort(xs,false)
            );
            //logger.info(Utilities.formatDecimal3Places(benchmark.runFromSupplier(supplier, 100)) + " ms");
            System.out.println(benchmark.runFromSupplier(supplier, 100) + " ms");
        }
        catch (Exception e){
            System.out.println("IO exception");
            e.printStackTrace(System.out);
        }

    }

    private static String[] devnagariArray = "खाली घर किताब करना किया कर खरगोश".split(" ");

    final static LazyLogger logger = new LazyLogger(QuickSort_DualPivot_Benchmark.class);
    private static Config config;
    public static void main(String[] args)
    {
        BasicConfigurator.configure();
        String description = "Dual Pivot quicksort for the given array of unicode characters";
        QuickSort_DualPivot_Benchmark dpqs = new QuickSort_DualPivot_Benchmark();
        Supplier<String[]> supplier = () -> devnagariArray;
        dpqs.runBenchmark(description,supplier);
    }

}
