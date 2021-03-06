package edu.neu.coe.info6205.sort.huskySort;

import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.counting.MSDRadixSortTest;
import edu.neu.coe.info6205.sort.huskySortUtils.Coding;
import edu.neu.coe.info6205.sort.huskySortUtils.HuskyCoder;
import edu.neu.coe.info6205.sort.huskySortUtils.HuskyCoderFactory;
import edu.neu.coe.info6205.util.Config;
import edu.neu.coe.info6205.util.PrivateMethodInvoker;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import static org.junit.Assert.*;

public class PureHuskySortTest {

    //private final BaseHelper<String> helper = new BaseHelper<>("dummy helper", Config.load(PureHuskySortTest.class));

    @Test
    public void testSortString1() throws IOException {
        final BaseHelper<String> helper = new BaseHelper<>("dummy helper", Config.load(PureHuskySortTest.class));
        String[] xs = {"Hello", "Goodbye", "Ciao", "Willkommen"};
        PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.unicodeCoder, false, false);
        sorter.sort(xs);
        assertTrue("sorted", helper.sorted(xs));
    }

    @Test
    public void testSortString2() {
        final BaseHelper<String> helper;
        try {
            helper = new BaseHelper<>("dummy helper", Config.load(PureHuskySortTest.class));
            PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, false);
            final int N = 1000;
            helper.init(N);
            final String[] xs = helper.random(String.class, r -> r.nextLong() + "");
            sorter.sort(xs);
            assertTrue("sorted", helper.sorted(xs));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSortString3() {
        final BaseHelper<String>helper;
        try {
            helper = new BaseHelper<>("dummy helper", Config.load(PureHuskySortTest.class));
            PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, false);
            final int N = 1000;
            helper.init(N);
            final String[] xs = helper.random(String.class, r -> {
                int x = r.nextInt(1000000000);
                final BigInteger b = BigInteger.valueOf(x).multiply(BigInteger.valueOf(1000000));
                return b.toString();
            });
            sorter.sort(xs);
            assertTrue("sorted", helper.sorted(xs));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSortString4() {
        final BaseHelper<String>helper;
        try {
            helper = new BaseHelper<>("dummy helper", Config.load(PureHuskySortTest.class));
            String[] xs = {"Hello", "Goodbye", "Ciao", "Willkommen"};
            PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, false);
            sorter.sort(xs);
            assertTrue("sorted", helper.sorted(xs));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSortString5() {
        final BaseHelper<String>helper;
        try {
            helper = new BaseHelper<>("dummy helper", Config.load(PureHuskySortTest.class));
            String[] xs = {"Hello", "Goodbye", "Ciao", "Welcome"};
            PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, false);
            sorter.sort(xs);
            assertTrue("sorted", helper.sorted(xs));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSortString5_new() {
        final BaseHelper<String>helper;
        try {
            helper = new BaseHelper<>("dummy helper", Config.load(PureHuskySortTest.class));
            String[] xs = {"????????????", "??????", "???????????????", "????????????","????????????","??????", "???????????????"};
            PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.unicodeCoder, false, false);
            sorter.sort(xs);
            System.out.println(Arrays.toString(xs));
            assertTrue("sorted", helper.sorted(xs));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSortString6() {
        final BaseHelper<String>helper;
        try {
            helper = new BaseHelper<>("dummy helper", Config.load(PureHuskySortTest.class));
            // order:       453922  252568   145313   673679   181452   31014   988329   659494    923995   890721   744769   293165   520163   199395   669978   765753
            String[] xs = {"?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????"};
            PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.chineseEncoder, false, false);
            sorter.sort(xs);
            System.out.println(Arrays.toString(xs));
            // order:           31014   145313   181452   199395   252568   293165   453922  520163   659494    669978   673679  744769   765753   890721   923995    988329
            String[] sorted = {"?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????", "?????????"};
            assertArrayEquals(sorted, xs);
    //        assertTrue("sorted", helper.sorted(xs));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFloorLg() {
        final BaseHelper<String>helper;
        try {
            helper = new BaseHelper<>("dummy helper", Config.load(PureHuskySortTest.class));
            PrivateMethodInvoker privateMethodInvoker = new PrivateMethodInvoker(PureHuskySort.class);
            assertEquals(Integer.valueOf(1), privateMethodInvoker.invokePrivate("floor_lg", 3));
            assertEquals(Integer.valueOf(2), privateMethodInvoker.invokePrivate("floor_lg", 5));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWithInsertionSort() {
        final BaseHelper<String>helper;
        try {
            helper = new BaseHelper<>("dummy helper", Config.load(PureHuskySortTest.class));
            PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, true);
            PrivateMethodInvoker privateMethodInvoker = new PrivateMethodInvoker(sorter);
            HuskyCoder<String> huskyCoder = (HuskyCoder<String>) privateMethodInvoker.invokePrivate("getHuskyCoder");
            final int N = 100;
            helper.init(N);
            final String[] xs = helper.random(String.class, r -> r.nextLong() + "");
            Coding coding = huskyCoder.huskyEncode(xs);
            sorter.insertionSort(xs, coding.longs, 0, N);
            assertEquals(0, helper.inversions(xs));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertionSort() {
        final BaseHelper<String>helper;
        try {
            helper = new BaseHelper<>("dummy helper", Config.load(PureHuskySortTest.class));
            PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.asciiCoder, false, false);
            PrivateMethodInvoker privateMethodInvoker = new PrivateMethodInvoker(sorter);
            HuskyCoder<String> huskyCoder = (HuskyCoder<String>) privateMethodInvoker.invokePrivate("getHuskyCoder");
            final int N = 100;
            helper.init(N);
            final String[] xs = helper.random(String.class, r -> r.nextLong() + "");
            Coding coding = huskyCoder.huskyEncode(xs);
            sorter.insertionSort(xs, coding.longs, 0, N);
            assertEquals(0, helper.inversions(xs));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
