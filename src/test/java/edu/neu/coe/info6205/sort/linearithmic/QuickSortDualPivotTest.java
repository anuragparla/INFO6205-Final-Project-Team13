/*
 * author hiral
 */

package edu.neu.coe.info6205.sort.linearithmic;

import edu.neu.coe.info6205.sort.*;
import edu.neu.coe.info6205.util.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;

import static org.junit.Assert.*;

@SuppressWarnings("ALL")
public class QuickSortDualPivotTest {

    @Test
    public void sort() {
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
    public void sort_2() {
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

        QuickSort_DualPivot.collatorObject = Collator.getInstance(new Locale("hi-IN"));

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

    final static LazyLogger logger = new LazyLogger(QuickSort_DualPivot.class);

    @BeforeClass
    public static void beforeClass() throws IOException {
        config = Config.load();
    }

    private static Config config;
}