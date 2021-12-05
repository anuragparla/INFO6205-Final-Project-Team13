package edu.neu.coe.info6205.sort.linearithmic;
import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.GenericSort;
import edu.neu.coe.info6205.sort.Helper;
//import edu.neu.coe.info6205.sort.elementary.BenchmarkDriver;
import edu.neu.coe.info6205.sort.elementary.InsertionSort;
import edu.neu.coe.info6205.util.*;
import org.apache.log4j.BasicConfigurator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;


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
    public static String formatWhole(final int x) {
        return String.format("%,d", x);
    }

    static String[] getWords(final String resource, final Function<String, List<String>> stringListFunction) {
        Class<?> clazz = TimSortBenchmark.class;
        try {
            Object res = clazz.getResource(resource);
            final File file = new File(Objects.requireNonNull(clazz.getResource(resource)).toURI());
            final String[] result = getWordArray(file, stringListFunction, 2);
            System.out.println("getWords: testing with " + formatWhole(result.length) + " unique words: from " + file);
            return result;
        } catch (final URISyntaxException | NullPointerException e) {
            System.out.println("Cannot find resource: " + resource + "  relative to class: " + clazz);
            e.printStackTrace();
            return new String[0];
        }
    }


    private static List<String> getWordList(final FileReader fr, final Function<String, List<String>> stringListFunction, final int minLength) {
        final List<String> words = new ArrayList<>();
        for (final Object line : new BufferedReader(fr).lines().toArray())
            words.addAll(stringListFunction.apply((String) line));
        return words.stream().distinct().filter(s -> s.length() >= minLength).collect(Collectors.toList());
    }

    static String[] getWordArray(final File file, final Function<String, List<String>> stringListFunction, final int minLength) {
        try (final FileReader fr = new FileReader(file)) {
            return getWordList(fr, stringListFunction, minLength).toArray(new String[0]);
        } catch (final IOException e) {
            System.out.println("Cannot open file: " + file);
            return new String[0];
        }
    }

    static List<String> lineAsList(final String line) {
        final List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }
    private static String[] chineseArray() {
        String[] arr = {"刘持平,洪文胜,樊辉辉,苏会敏,高民政,刘持平,洪文胜,樊辉辉,苏会敏,高民政,刘持平,洪文胜,樊辉辉,苏会敏,高民政"};
        return arr;
    }

    final static LazyLogger logger = new LazyLogger(TimSortBenchmark.class);
    public static int N = 1000;
    private static Config config;
    public static void main(String[] args)
    {
        BasicConfigurator.configure();
        String description = "TimSort for the given array of ascii and unicode characters";
        TimSortBenchmark tsb = new TimSortBenchmark();
        //Helper<String> helper = new BaseHelper<>(description, N, config);
        //String[] arr = {"刘持平,洪文胜,樊辉辉,苏会敏,高民政"};
        Supplier<String[]> supplier = () -> getWords("/common-hindi-words.txt", TimSortBenchmark::lineAsList);
        tsb.runBenchmark(description,supplier);
    }
}