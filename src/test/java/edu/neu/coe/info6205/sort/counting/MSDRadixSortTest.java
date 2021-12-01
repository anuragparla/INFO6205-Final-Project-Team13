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
import edu.neu.coe.info6205.sort.counting.MSDRadixSort;
import edu.neu.coe.info6205.util.Config;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MSDRadixSortTest {

    @Test
    public void sort() {
        String[] input = "she sells seashells by the seashore the shells she sells are surely seashells".split(" ");
        String[] expected = "are by seashells seashells seashore sells sells she she shells surely the the".split(" ");

        System.out.println("Before: " + Arrays.toString(input));
        MSDRadixSort.sort(input);
        System.out.println("After: " + Arrays.toString(input));
        assertArrayEquals(expected, input);
    }

    @Test
    public void sort_2() {
        String[] input = "cafeteria caffeine café".split(" ");
        String[] expected = "café cafeteria caffeine".split(" ");

        System.out.println("Before: " + Arrays.toString(input));
        MSDRadixSort.sort(input);
        System.out.println("After: " + Arrays.toString(input));
        assertArrayEquals(expected, input);
    }

    @Test
    public void sort_chinese() {
        String[] input_chinese = "刘持平 洪文胜 樊辉辉 苏会敏 高民政".split(" ");
        String [] expected_chinese = "樊辉辉 高民政 洪文胜 刘持平 苏会敏".split(" ");

        MSDRadixSort.cmp = Collator.getInstance(Locale.CHINA);
        System.out.println("Before: " + Arrays.toString(input_chinese));
        MSDRadixSort.sort(input_chinese);
        System.out.println("After: " + Arrays.toString(input_chinese));
        assertArrayEquals(expected_chinese, input_chinese);
    }

    @Test
    public void sort_devanagri() {
        String[] input_devnagri = "खाली घर किताब करना किया कर खरगोश".split(" ");
        String [] expected_devnagri = "कर करना किताब किया खरगोश खाली घर".split(" ");

        MSDRadixSort.cmp = Collator.getInstance(new Locale("hi-IN"));
        System.out.println("Before: " + Arrays.toString(input_devnagri));
        MSDRadixSort.sort(input_devnagri);
        System.out.println("After: " + Arrays.toString(input_devnagri));
        assertArrayEquals(expected_devnagri, input_devnagri);
    }
}