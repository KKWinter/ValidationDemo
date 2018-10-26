package com.phone.reflect;

import org.junit.Test;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void test() {



        try {

            throwException();
        } catch (IOException e) {
            System.out.print("======" + e.getMessage());
        } catch (Exception e) {
            System.out.print("<<<<<<" + e.getMessage());
        }


    }


    public void throwException() throws Exception {


        String str = null;

        str.isEmpty();


    }
}