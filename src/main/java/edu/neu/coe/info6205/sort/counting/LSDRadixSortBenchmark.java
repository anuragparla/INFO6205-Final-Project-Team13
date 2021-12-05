package edu.neu.coe.info6205.sort.counting;
import edu.neu.coe.info6205.sort.GenericSort;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.linearithmic.TimSort;
import edu.neu.coe.info6205.sort.linearithmic.TimSortBenchmark;
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
    public static String formatWhole(final int x) {
        return String.format("%,d", x);
    }

    static String[] getWords(final String resource, final Function<String, List<String>> stringListFunction) {
        Class<?> clazz = LSDRadixSortBenchmark.class;
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

    final static LazyLogger logger = new LazyLogger(TimSortBenchmark.class);
    public static int N = 1000;
    private static Config config;

    public static void main(String[] args)
    {
        BasicConfigurator.configure();
        String description = "LSDRadix Sort for the given array of ascii and unicode characters";
        LSDRadixSortBenchmark lsb = new LSDRadixSortBenchmark();
        Supplier<String[]> supplier = () -> getWords("/common-hindi-words.txt", LSDRadixSortBenchmark::lineAsList);
        lsb.runBenchmark(description,supplier);
    }
}
