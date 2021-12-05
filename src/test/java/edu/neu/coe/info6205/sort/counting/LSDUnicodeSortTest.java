package edu.neu.coe.info6205.sort.counting;

import edu.neu.coe.info6205.sort.linearithmic.TimSort;
import edu.neu.coe.info6205.sort.linearithmic.TimSortTest;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class LSDUnicodeSortTest {


    @Test
    public void sort1() throws IOException {
        String[] words = getWords("/common-hindi-words.txt", LSDUnicodeSortTest::lineAsList);
        LSDUnicodeSort lsdu = new LSDUnicodeSort();
        lsdu.sort(words);
        for(String s: words) {
            System.out.print(s + " ");
        }

    }

    public static String formatWhole(final int x) {
        return String.format("%,d", x);
    }

    static String[] getWords(final String resource, final Function<String, List<String>> stringListFunction) {
        Class<?> clazz = LSDUnicodeSortTest.class;
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

}
