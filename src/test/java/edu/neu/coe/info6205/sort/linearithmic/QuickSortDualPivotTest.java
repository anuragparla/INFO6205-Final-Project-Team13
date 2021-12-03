/*
 * author hiral
 */

package edu.neu.coe.info6205.sort.linearithmic;

import edu.neu.coe.info6205.sort.*;
import edu.neu.coe.info6205.util.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.Collator;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@SuppressWarnings("ALL")
public class QuickSortDualPivotTest {

    @Test
    public void sort_english() {
        String[] input = "she sells seashells by the seashore the shells she sells are surely seashells".split(" ");
        String[] expected = "are by seashells seashells seashore sells sells she she shells surely the the".split(" ");

        QuickSort_DualPivot.collatorObject = Collator.getInstance(Locale.ENGLISH);

        System.out.println("Before: " + Arrays.toString(input));
        GenericSort<String> s = new QuickSort_DualPivot<>(input.length, config);
        String[] ys = s.sort(input,false);

        System.out.println("After: " + Arrays.toString(input));
        System.out.println("Expected: " + Arrays.toString(expected));
        assertArrayEquals(expected, input);
    }

    @Test
    public void sort_english_2() {
        String[] input = "cafeteria caffeine cafe".split(" ");
        String[] expected = "cafe cafeteria caffeine".split(" ");

        QuickSort_DualPivot.collatorObject = Collator.getInstance(Locale.ENGLISH);

        System.out.println("Before: " + Arrays.toString(input));
        GenericSort<String> s = new QuickSort_DualPivot<>(input.length, config);
        String[] ys = s.sort(input, false);

        System.out.println("After: " + Arrays.toString(input));
        System.out.println("expected: " + Arrays.toString(expected));
        assertArrayEquals(expected, input);
    }

    @Test
    public void sort_devanagri() {
        String[] input_devnagri = "खाली घर किताब करना किया कर खरगोश".split(" ");
        String [] expected_devnagri = "कर करना किताब किया खरगोश खाली घर".split(" ");

        QuickSort_DualPivot.collatorObject = Collator.getInstance(new Locale("hi_IN"));

        System.out.println("Before: " + Arrays.toString(input_devnagri));
        GenericSort<String> s = new QuickSort_DualPivot<>(input_devnagri.length, config);
        String[] ys = s.sort(input_devnagri);
        System.out.println("After: " + Arrays.toString(ys));
        System.out.println("Expected" + Arrays.toString(expected_devnagri));
        assertArrayEquals(expected_devnagri, ys);
    }

    @Test
    public void sort_gujarati() {
        String[] input_gujarati = "હે યહોવા તું મારો દેવ છે".split(" ");
        String [] expected_guarati = "છે તું દેવ મારો યહોવા હે".split(" ");

        QuickSort_DualPivot.collatorObject = Collator.getInstance(new Locale("gu_IN"));

        System.out.println("Before: " + Arrays.toString(input_gujarati));
        GenericSort<String> s = new QuickSort_DualPivot<>(input_gujarati.length, config);
        String[] ys = s.sort(input_gujarati);
        System.out.println("After: " + Arrays.toString(ys));
        System.out.println("Expected" + Arrays.toString(expected_guarati));
        assertArrayEquals(expected_guarati, ys);
    }

    @Test
    public void sort() throws IOException {
        int n = 2998;
        final Helper<String> helper = new BaseHelper<>("test", n, 1L, Config.load(QuickSortDualPivotTest.class));
        helper.init(n);
        QuickSort_DualPivot.collatorObject = Collator.getInstance(Locale.ENGLISH);
        String[] words = getWords("/3000-common-words.txt", QuickSortDualPivotTest::lineAsList);
        final String[] xs = helper.random(String.class, r -> words[r.nextInt(words.length)]);
        assertEquals(n, xs.length);
        GenericSort<String> s = new QuickSort_DualPivot<>(xs.length, config);
        String[] ys = s.sort(xs,false);
        assertEquals("abandon", xs[0]);
        assertEquals("accompany", xs[16]);
    }

    @Test
    public void testGetWords() {
        String[] words = getWords("/3000-common-words.txt", QuickSortDualPivotTest::lineAsList);
        assertEquals(2998, words.length);
    }

    @Test
    public void sort2() throws IOException {
        int n = 2000;
        final Helper<String> helper = new BaseHelper<>("test", n, 1L, Config.load(QuickSortDualPivotTest.class));
        helper.init(n);
        QuickSort_DualPivot.collatorObject = Collator.getInstance(new Locale("hi_IN"));
        String[] words = getWords("/common-hindi-words.txt", QuickSortDualPivotTest::lineAsList);
        final String[] xs = helper.random(String.class, r -> words[r.nextInt(words.length)]);
        assertEquals(n, xs.length);
        GenericSort<String> s = new QuickSort_DualPivot<>(xs.length, config);
        String[] ys = s.sort(xs,false);
        assertEquals("अंत", xs[0]);
        assertEquals("अनुभ", xs[16]);
    }

    @Test
    public void testGetWords2() {
        String[] words = getWords("/common-hindi-words.txt", QuickSortDualPivotTest::lineAsList);
        assertEquals(2000, words.length);
    }

    /**
     * Create a string representing an integer, with commas to separate thousands.
     * @param x the integer.
     * @return a String representing the number with commas.
     */
    public static String formatWhole(final int x) {
        return String.format("%,d", x);
    }

    /**
     * Method to open a resource relative to this class and from the corresponding File, get an array of Strings.
     * @param resource           the URL of the resource containing the Strings required.
     * @param stringListFunction a function which takes a String and splits into a List of Strings.
     * @return an array of Strings.
     */
    static String[] getWords(final String resource, final Function<String, List<String>> stringListFunction) {
        Class<?> clazz = QuickSortDualPivotTest.class;
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

    /**
     * Method to read given file and return a String[] of its content.
     * @param file               the file to read.
     * @param stringListFunction a function which takes a String and splits into a List of Strings.
     * @param minLength          the minimum acceptable length for a word.
     * @return an array of Strings.
     */
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

    final static LazyLogger logger = new LazyLogger(QuickSort_DualPivot.class);

    @BeforeClass
    public static void beforeClass() throws IOException {
        config = Config.load();
    }

    private static Config config;
}