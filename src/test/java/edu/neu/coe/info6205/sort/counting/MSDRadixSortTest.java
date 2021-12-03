package edu.neu.coe.info6205.sort.counting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.Collator;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.linearithmic.TimSort;
import edu.neu.coe.info6205.util.Config;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MSDRadixSortTest {

    @Test
    public void sort() throws IOException {
        String[] input = "she sells seashells by the seashore the shells she sells are surely seashells".split(" ");
        String[] expected = "are by seashells seashells seashore sells sells she she shells surely the the".split(" ");

        System.out.println("Before: " + Arrays.toString(input));
        MSDRadixSort<String> msdSort = new MSDRadixSort<>();
        msdSort.sort(input);
        System.out.println("After: " + Arrays.toString(input));
        assertArrayEquals(expected, input);
    }

    @Test
    public void sort_2() throws IOException {
        String[] input = "cafeteria caffeine café".split(" ");
        String[] expected = "café cafeteria caffeine".split(" ");

        System.out.println("Before: " + Arrays.toString(input));
        MSDRadixSort<String> msdSort = new MSDRadixSort<>();
        msdSort.sort(input);
        System.out.println("After: " + Arrays.toString(input));
        assertArrayEquals(expected, input);
    }

    @Test
    public void sort_chinese() throws IOException {
        String[] input_chinese = "刘持平 洪文胜 樊辉辉 苏会敏 高民政".split(" ");
        String [] expected_chinese = "樊辉辉 高民政 洪文胜 刘持平 苏会敏".split(" ");

        MSDRadixSort.cmp = Collator.getInstance(Locale.CHINA);
        System.out.println("Before: " + Arrays.toString(input_chinese));
        MSDRadixSort<String> msdSort = new MSDRadixSort<>();
        msdSort.sort(input_chinese);
        System.out.println("After: " + Arrays.toString(input_chinese));
        assertArrayEquals(expected_chinese, input_chinese);
    }

    @Test
    public void sort_devanagri() throws IOException {
        String[] input_devnagri = "खाली घर किताब करना किया कर खरगोश".split(" ");
        String [] expected_devnagri = "कर करना किताब किया खरगोश खाली घर".split(" ");

        MSDRadixSort.cmp = Collator.getInstance(new Locale("hi-IN"));
        System.out.println("Before: " + Arrays.toString(input_devnagri));
        MSDRadixSort<String> msdSort = new MSDRadixSort<>();
        msdSort.sort(input_devnagri);
        System.out.println("After: " + Arrays.toString(input_devnagri));
        assertArrayEquals(expected_devnagri, input_devnagri);
    }

    @Test
    public void sort2() throws IOException {
        int n = 1000;
        final Helper<String> helper = new BaseHelper<>("test", n, 1L, Config.load(MSDRadixSortTest.class));
        helper.init(n);
        String[] words = getWords("/3000-common-words.txt", MSDRadixSortTest::lineAsList);
        final String[] xs = helper.random(String.class, r -> words[r.nextInt(words.length)]);
        assertEquals(n, xs.length);
        MSDRadixSort<String> msdSort = new MSDRadixSort<>();
        msdSort.sort(xs);
        assertEquals("African-American", xs[0]);
        assertEquals("Palestinian", xs[16]);
    }

    @Test
    public void testGetWords2() {
        String[] words = getWords("/3000-common-words.txt", MSDRadixSortTest::lineAsList);
        assertEquals(2998, words.length);
    }

    @Test
    public void testGetWords3() {
        String[] words = getWords("/common-hindi-words.txt", MSDRadixSortTest::lineAsList);
        assertEquals(1000, words.length);
    }

    @Test
    public void sort3() throws IOException {
        String[] words = getWords("/common-hindi-words.txt", MSDRadixSortTest::lineAsList);
        MSDRadixSort.cmp = Collator.getInstance(new Locale("hi-IN"));
        MSDRadixSort<String> msdSort = new MSDRadixSort<>();
        msdSort.sort(words);
        System.out.println("Here");
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
        Class<?> clazz = MSDRadixSortTest.class;
        try {
            final File file = new File(Objects.requireNonNull(clazz.getResource(resource)).toURI());
            final String[] result = getWordArray(file, stringListFunction, 2);
            System.out.println("getWords: testing with " + formatWhole(result.length) + " unique words: from " + file);
            return result;
        } catch (final URISyntaxException | NullPointerException e) {
            System.out.println("Cannot find resource: " + resource + "  relative to class: " + clazz);
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