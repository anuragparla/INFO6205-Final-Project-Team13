package edu.neu.info6205.sort.linearithmic;
import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.linearithmic.TimSort;
import edu.neu.coe.info6205.util.Config;
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
import static org.junit.Assert.assertEquals;

public class TimSortTest {
    Comparator cmp = null;

    @Test
    public void testChinese() {
        String[] actual = "刘持平 洪文胜 樊辉辉 苏会敏 高民政".split(" ");
        String[] expected = "樊辉辉 高民政 洪文胜 刘持平 苏会敏".split(" ");

        try {
            TimSort<String> ts = new TimSort<>();
            ts.cmp = Collator.getInstance(Locale.CHINA);
            ts.sort(actual, 0, actual.length);
            for (int i = 0; i < actual.length; i++) {
                System.out.println(actual[i]);
            }
            assertEquals(expected, actual);
        } catch (Exception e) {
            System.out.println("IO Exception occured");
        }
    }

    @Test
    public void testDevanagari() {
        String[] actualDevanagari = "खाली घर किताब करना किया कर खरगोश".split(" ");
        String[] expectedDevanagari = "कर करना किताब किया खरगोश खाली घर".split(" ");
        try {
            TimSort<String> ts = new TimSort<>();
            ts.cmp = Collator.getInstance(new Locale("hi-IN"));
            ts.sort(actualDevanagari, 0, actualDevanagari.length);
            for (int i = 0; i < actualDevanagari.length; i++) {
                System.out.println(actualDevanagari[i]);
            }
            assertEquals(expectedDevanagari, actualDevanagari);
        } catch (Exception e) {
            System.out.println("IO Exception occured");
        }
    }

    @Test
    public void testEnglish() {
        String[] actualEnglish = "Coffee Caffeteria Café".split(" ");
        String[] expectedEnglish = "Café Caffeteria Coffee".split(" ");
        try {
            TimSort<String> ts = new TimSort<>();
            ts.cmp = Collator.getInstance(Locale.US);
            ts.sort(actualEnglish, 0, actualEnglish.length);
            for (int i = 0; i < actualEnglish.length; i++) {
                System.out.println(actualEnglish[i]);
            }
            assertEquals(expectedEnglish, actualEnglish);
        } catch (Exception e) {
            System.out.println("IO Exception occured");
        }
    }

    @Test
    public void sort1() throws IOException {
        String[] words = getWords("/shuffledChinese.txt", TimSortTest::lineAsList);
        TimSort<String> ts = new TimSort<>();
        ts.cmp = Collator.getInstance(Locale.CHINA);
        ts.sort(words, 0, words.length);
        assertEquals("阿安", words[0]);
    }

    @Test
    public void testGetWords1() {
        String[] words = getWords("/shuffledChinese.txt", TimSortTest::lineAsList);
        assertEquals(999998, words.length);
    }

    @Test
    public void sort2() throws IOException {
        int n = 1000;
        final Helper<String> helper = new BaseHelper<>("test", n, 1L, Config.load(TimSortTest.class));
        helper.init(n);
        String[] words = getWords("/3000-common-words.txt", TimSortTest::lineAsList);
        final String[] xs = helper.random(String.class, r -> words[r.nextInt(words.length)]);
        assertEquals(n, xs.length);
        TimSort<String> ts = new TimSort<>();
        ts.sort(xs, 0, n);
        assertEquals("African-American", xs[0]);
        assertEquals("Palestinian", xs[16]);
    }

    @Test
    public void testGetWords2() {
        String[] words = getWords("/3000-common-words.txt", TimSortTest::lineAsList);
        assertEquals(2998, words.length);
    }

    /**
     * Create a string representing an integer, with commas to separate thousands.
     *
     * @param x the integer.
     * @return a String representing the number with commas.
     */
    public static String formatWhole(final int x) {
        return String.format("%,d", x);
    }

    /**
     * Method to open a resource relative to this class and from the corresponding File, get an array of Strings.
     *
     * @param resource           the URL of the resource containing the Strings required.
     * @param stringListFunction a function which takes a String and splits into a List of Strings.
     * @return an array of Strings.
     */
    static String[] getWords(final String resource, final Function<String, List<String>> stringListFunction) {
        Class<?> clazz = TimSortTest.class;
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
     *
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
}
