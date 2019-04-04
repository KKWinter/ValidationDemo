package com.phone.reflect;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Constructor;
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

            Class cls = Class.forName("com.phone.reflect.Person");
            System.out.println(cls);


            Constructor<Person>[] con = cls.getConstructors();
            System.out.println(con.length);

            Constructor con1 = cls.getConstructor();
            Constructor con2 = cls.getConstructor(String.class, int.class);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}