package edu.neu.coe.info6205.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Random;
import java.util.function.Function;

public class Utilities {
    /**
     * There is really no better way that I could find to do this with library/language methods.
     * Don't try to inline this if the generic type extends something like Comparable, or you will get a ClassCastException.
     *
     * @param ts  a collection of Ts.
     * @param <T> the underlying type of ts.
     * @return an array T[].
     */
    public static <T> T[] asArray(Collection<T> ts) {
        if (ts.isEmpty()) throw new RuntimeException("ts may not be empty");
        @SuppressWarnings("unchecked") T[] result = (T[]) Array.newInstance(ts.iterator().next().getClass(), 0);
        return ts.toArray(result);
    }

    /**
     * Create a string representing an double, with three decimal places.
     *
     * @param x the number to show.
     * @return a String representing the number rounded to three decimal places.
     */
    public static String formatDecimal3Places(double x) {
        double scaleFactor = 1000.0;
        return String.format("%.3f", round(x * scaleFactor) / scaleFactor);
    }

    /**
     * Create a string representing an integer, with commas to separate thousands.
     *
     * @param x the integer.
     * @return a String representing the number with commas.
     */
    public static String formatWhole(int x) {
        return String.format("%,d", x);
    }

    public static String asInt(double x) {
        final int i = round(x);
        return formatWhole(i);
    }

    public static int round(double x) {
        return (int) (Math.round(x));
    }

    public static <T> T[] fillRandomArray(Class<T> clazz, Random random, int n, Function<Random, T> f) {
        @SuppressWarnings("unchecked") T[] result = (T[]) Array.newInstance(clazz, n);
        for (int i = 0; i < n; i++) result[i] = f.apply(random);
        return result;
    }

    /**
     * Method to calculate log to tbe base 2 of n.
     *
     * @param n the number whose log we need.
     * @return lg n.
     */
    public static double lg(double n) {
        return Math.log(n) / Math.log(2);
    }

    /**
     * Method to open a resource relative to this class and from the corresponding File, get an array of Strings.
     *
     * @param resource           the URL of the resource containing the Strings required.
     * @return an array of Strings.
     */
    public static String[] getWords(final String resource, Class clazz) {
        try {
            final File file = new File(Objects.requireNonNull(clazz.getResource(resource)).toURI());
            final String[] result = getWordArray(file, 2);
            //System.out.println("getWords: testing with " + formatWhole(result.length) + " unique words: from " + file);
            return result;
        } catch (final URISyntaxException | NullPointerException e) {
            System.out.println("Cannot find resource: " + resource + "  relative to class: " + clazz);
            return new String[0];
        }
    }

    static List<String> getWordList(final FileReader fr, final int minLength) {
        final List<String> words = new ArrayList<>();
        for (final Object line : new BufferedReader(fr).lines().toArray())
            words.addAll(lineAsList((String) line));
        return words.stream().filter(s -> s.length() >= minLength).collect(Collectors.toList());
    }

    static List<String> lineAsList(final String line) {
        final List<String> words = new ArrayList<>();
        words.add(line);
        return words;
    }


    /**
     * Method to read given file and return a String[] of its content.
     *
     * @param file               the file to read.
     * @param minLength          the minimum acceptable length for a word.
     * @return an array of Strings.
     */
    static String[] getWordArray(final File file, final int minLength) {
        try (final FileReader fr = new FileReader(file)) {
            return getWordList(fr, minLength).toArray(new String[0]);
        } catch (final IOException e) {
            System.out.println("Cannot open file: " + file);
            return new String[0];
        }
    }

}
