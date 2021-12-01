package edu.neu.edu.info6205.sort.linearithmic;
import edu.neu.coe.info6205.sort.linearithmic.TimSort;
import org.junit.Test;
import java.text.Collator;
import java.util.*;
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
            ts.sort(actual,0, actual.length);
            for(int i=0;i<actual.length;i++) {
                System.out.println(actual[i]);
            }
            assertEquals(expected,actual);
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
            ts.sort(actualDevanagari,0, actualDevanagari.length);
            for(int i=0;i<actualDevanagari.length;i++) {
                System.out.println(actualDevanagari[i]);
            }
            assertEquals(expectedDevanagari,actualDevanagari);
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
            ts.sort(actualEnglish,0, actualEnglish.length);
            for(int i=0;i<actualEnglish.length;i++) {
                System.out.println(actualEnglish[i]);
            }
            assertEquals(expectedEnglish,actualEnglish);
        } catch (Exception e) {
            System.out.println("IO Exception occured");
        }
    }
}
