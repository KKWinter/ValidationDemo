package com.jumpraw.webview;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    String patt = "[0-9]+";
    String str = "abc123def";

    @Test
    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);

        //正则表达式判断方法
        Pattern pattern = Pattern.compile(patt);
        Matcher m = pattern.matcher(str);
        System.out.println(">>" + m.matches());


        System.setProperty("key", "huang");

        System.out.println(System.getProperty("key"));

    }
}