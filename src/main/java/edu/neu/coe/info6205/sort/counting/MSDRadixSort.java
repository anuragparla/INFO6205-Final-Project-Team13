package edu.neu.coe.info6205.sort.counting;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.sort.elementary.InsertionSortMSD;
import edu.neu.coe.info6205.util.Config;

import java.io.IOException;
import java.text.Collator;
import java.util.Collection;

public class MSDRadixSort<X extends Comparable<X>> extends SortWithHelper<X> {

    private static final int radix = 65536;
    private static final int cutoff = 15;
    private static String[] aux;       // auxiliary array for distribution
    public static Collator cmp;
    public static final String DESCRIPTION = "MSD Radix Sort";

    public MSDRadixSort(Helper<X> helper) { super(helper); }

    public MSDRadixSort() throws IOException {
        this(new BaseHelper<>(DESCRIPTION, Config.load(MSDRadixSort.class)));
    }
    /**
     * Sort an array of Strings using MSDStringSort.
     *
     * @param a the array to be sorted.
     */
    public X[] sort(X[] a) {
        int n = a.length;
        aux = new String[n];
        if (cmp != null) InsertionSortMSD.cmp = cmp;
        sort((String[]) a, 0, n, 0);
        return a;
    }

    @Override
    public void sort(X[] xs, int from, int to) {
        sort(xs);
    }

    /**
     * Sort from a[lo] to a[hi] (exclusive), ignoring the first d characters of each String.
     * This method is recursive.
     *
     * @param a the array to be sorted.
     * @param lo the low index.
     * @param hi the high index (one above the highest actually processed).
     * @param d the number of characters in each String to be skipped.
     */
    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi < lo + cutoff) InsertionSortMSD.sort( a, lo, hi, d);
        else {
            int[] count = new int[radix + 2];        // Compute frequency counts.
            for (int i = lo; i < hi; i++)
                count[charAt(a[i], d) + 2]++;
            for (int r = 0; r < radix + 1; r++)      // Transform counts to indices.
                count[r + 1] += count[r];
            for (int i = lo; i < hi; i++)     // Distribute.
                aux[count[charAt(a[i], d) + 1]++] = a[i];
            // Copy back.
            if (hi - lo >= 0) System.arraycopy(aux, 0, a, lo, hi - lo);
            // Recursively sort for each character value.
            for (int r = 0; r < radix; r++)
                sort(a, lo + count[r], lo + count[r+1] - 1, d+1);
        }
    }

    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        else return -1;
    }


}

