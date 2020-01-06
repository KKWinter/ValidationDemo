package com.jumpraw.handler;

import org.junit.Test;

import static com.jumpraw.handler.SdkParser.s;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);


//        String str0 = ".o.llk.com.jumpraw.handler.e38186fc6a4a7a3d.l";

//        String result1 = SdkParser.a.u(str0.getBytes());
//        String result2 = SdkParser.a.t(str0.getBytes());

//        System.out.println(result1);
//        System.out.println(result2);

        //
        String str1 = s(new byte[] { -78, -66, 7, -64, -32, -3, 65, -113, -48, 58, -61, -62, -112, 125, -73, -46, -103, 37, -74, -75, 9, -91 });

        String str2 = s(new byte[] { -112, -109, -15, -25 });

        String str3 = s(new byte[] { -117, -90, -89, -30, -43, -56 });


        String str4 = s(new byte[] { -31, -47, -115, -91 });


        String str5 = s(new byte[] { -111, 42, 77 });


        String str6 = s(new byte[] { -25, 36, 69 });


        String str7 = s(new byte[] { 85, -58, -85 });


        String str8 = s(new byte[] { -69, -104, -6 });

        String str9 = s(new byte[] { 2, 48, 86 });


        String str10 = s(new byte[] { -60, 48, 95 });

        String str11 = s(new byte[] { -11, -110, -10 });

        System.out.println(str1);
        System.out.println(str2);
        System.out.println(str3);
        System.out.println(str4);
        System.out.println(str5);
        System.out.println(str6);
        System.out.println(str7);
        System.out.println(str8);
        System.out.println(str9);
        System.out.println(str10);
        System.out.println(str11);
    }
}