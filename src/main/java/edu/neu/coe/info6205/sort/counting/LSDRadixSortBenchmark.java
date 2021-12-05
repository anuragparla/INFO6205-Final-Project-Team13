package edu.neu.coe.info6205.sort.counting;
import edu.neu.coe.info6205.sort.linearithmic.TimSortBenchmark;
import edu.neu.coe.info6205.util.*;
import org.apache.log4j.BasicConfigurator;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LSDRadixSortBenchmark {
    public void runBenchmark(String description, Supplier<String[]> supplier) {
        try {
            final LSDUnicodeSort sort = new LSDUnicodeSort();
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

    final static LazyLogger logger = new LazyLogger(TimSortBenchmark.class);
    public static int N = 1000;
    private static String[] words;

    private static String[] createArray(int size) {
        if(size < words.length){
            return Arrays.stream(words).limit(size).collect(Collectors.toList()).toArray(new String[0]);
        }
        return words;
    }

    public static void main(String[] args)
    {
        BasicConfigurator.configure();
        String description = "LSDRadix Sort for the given array of unicode characters";
        words = Utilities.getWords("/shuffledHindi.txt", LSDRadixSortBenchmark.class);
        for (int i = 0; i<=12; i++) {
            LSDRadixSortBenchmark lsb = new LSDRadixSortBenchmark();
            Supplier<String[]> supplier = () -> createArray(N);
            lsb.runBenchmark(description,supplier);
            N = N * 2;
        }
    }
}
