package edu.neu.coe.info6205.sort.counting;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.GenericSort;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.elementary.InsertionSort;
import edu.neu.coe.info6205.sort.linearithmic.TimSort;
import edu.neu.coe.info6205.util.*;
import org.apache.log4j.BasicConfigurator;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

public class MSDRadixSortBenchmark  {

    private static Config config;
    public static int N;
    final static LazyLogger logger = new LazyLogger(MSDRadixSortBenchmark.class);

    public static void main(String []args) {
        BasicConfigurator.configure();
        System.out.println("Benchmarking insertion sort");
        resetArraySize();
        //Array with elements in random order
        //for (int i = 0; i<5; i++) {
            Helper<String> helper = new BaseHelper<>("Insertion Sort - Random", N, config);
            Supplier<String[]> supplier = () -> createArray(N);
            MSDRadixSortBenchmark.runBenchmark(helper, supplier, "Random");
            N = N * 2;
        //}
        resetArraySize();

//        //Benchmarking for ordered array
//        for (int i = 0; i<5; i++) {
//            Helper<String> helper = new BaseHelper<>("Insertion Sort - Random", N, config);
//            Supplier<String[]> supplier = () -> createArray(N);
//            MSDRadixSortBenchmark.runBenchmark(helper, supplier, "Ordered");
//            N = N * 2;
//        }
//        resetArraySize();
//
//        //Benchmarking for reverse-ordered array
//        for (int i = 0; i<5; i++) {
//            Helper<String> helper = new BaseHelper<>("Insertion Sort - Random", N, config);
//            Supplier<String[]> supplier = () -> createArray(N);;
//            MSDRadixSortBenchmark.runBenchmark(helper, supplier, "Reverse Ordered");
//            N = N * 2;
//        }
//        resetArraySize();
//
//        for (int i=0; i<5; i++) {
//            Helper<String> helper = new BaseHelper<>("Insertion Sort - Random", N, config);
//            Supplier<String[]> supplier = () -> createArray(N);;
//            MSDRadixSortBenchmark.runBenchmark(helper, supplier, "Partially Ordered");
//            N = N * 2;
//        }
    }

    private static String[] createArray(int size) {
        final String[] inputArr = {"刘持平,洪文胜,樊辉辉,苏会敏,高民政,刘持平,洪文胜,樊辉辉,苏会敏,高民政,刘持平,洪文胜,樊辉辉,苏会敏,高民政"};
        return inputArr;
    }

    public static void resetArraySize(){
        N = 4000;
    }

    private static void runBenchmark(Helper helper, Supplier supplier, String description){
        final GenericSort<String> sort = new MSDRadixSort<>(helper);
        final Benchmark<String[]> benchmark = new Benchmark_Timer<>(
                "Insert sort " + description + " for " + N + " Integers",
                (xs) -> Arrays.copyOf(xs, xs.length),
                sort::sort,
                null
        );
        logger.info(Utilities.formatDecimal3Places(benchmark.runFromSupplier(supplier, 100)) + " ms");
    }
}
