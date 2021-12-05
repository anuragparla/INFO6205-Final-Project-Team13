package edu.neu.coe.info6205.sort.counting;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class MSDUnicodeSort {

        public static String [] convertingToPinyin(String []a) throws BadHanyuPinyinOutputFormatCombination
        {
            String []t = new String[a.length];
            HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

            long length = a.length;
            for(int i = 0; i < length; i++)
            {
                String s = a[i];
                String temp = "";
                for(int j = 0 ; j < s.length(); j++)
                {
                    String []pinyinArray = PinyinHelper.toHanyuPinyinStringArray(s.charAt(j), format);
                    temp = temp.concat(pinyinArray[0]);
                }
                t[i] = temp;
            }
            return t;
        }

        public static void main(String args[]){
            String[] input = "刘持平 洪文胜 樊辉辉 苏会敏 高民政".split(" ");
            try {
                String[] output = MSDUnicodeSort.convertingToPinyin(input);
                for(String a : output)
                    System.out.println(a);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        }
    }
