package edu.neu.coe.info6205.sort.linearithmic;
import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.GenericSort;
import edu.neu.coe.info6205.sort.Helper;
//import edu.neu.coe.info6205.sort.elementary.BenchmarkDriver;
import edu.neu.coe.info6205.sort.elementary.InsertionSort;
import edu.neu.coe.info6205.util.*;
import org.apache.log4j.BasicConfigurator;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;


public class TimSortBenchmark {

    public void runBenchmark(String description, Supplier<String[]> supplier) {
        //helper.init(N);

        try {
            final GenericSort<String> sort = new TimSort<>();
            final Benchmark<String[]> benchmark = new Benchmark_Timer<>(
                    description + " for " + N + " Strings",
                    (xs) -> Arrays.copyOf(xs, xs.length),
                    sort::sort,
                    null
            );
            logger.info(Utilities.formatDecimal3Places(benchmark.runFromSupplier(supplier, 100)) + " ms");
        }
        catch (Exception e){
            System.out.println("IO exception");
            e.printStackTrace(System.out);
        }


    }

    private static String[] chineseArray() {
        String[] arr = {"刘持平,洪文胜,樊辉辉,苏会敏,高民政,刘持平,洪文胜,樊辉辉,苏会敏,高民政,刘持平,洪文胜,樊辉辉,苏会敏,高民政"};
        return arr;
    }

    final static LazyLogger logger = new LazyLogger(TimSortBenchmark.class);
    public static int N = 15;
    private static Config config;
    public static void main(String[] args)
    {
        BasicConfigurator.configure();
        String description = "TimSort for the given array of ascii and unicode characters";
        TimSortBenchmark tsb = new TimSortBenchmark();
        //Helper<String> helper = new BaseHelper<>(description, N, config);
        //String[] arr = {"刘持平,洪文胜,樊辉辉,苏会敏,高民政"};
        Supplier<String[]> supplier = () -> chineseArray();
        tsb.runBenchmark(description,supplier);
    }
}