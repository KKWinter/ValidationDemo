package com.kkwinter.thirdsdk;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.kkwinter.thirdsdk.advworkflow.b.f;
import com.kkwinter.thirdsdk.advworkflow.util.g;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String TAG = "advsdkTest";

    private static String pluginReqRequest = "ORp7Ou9vq/Zgg4WDNJDhJfF+pVsUUFmUFyRcNRm4iKO7NkA6nB7+RNP1VTbJEJ6kniaiXditXeDV\n" +
            "xG91IicdILMEKxpB+fppWoCmMwVBDkadAVho8a5qZBqMjVwz62BA9NT/eifXagZ3GB22rH/hmMvq\n" +
            "uMa37qvjmu6gVvtcPI3atoj9vGiJlcaCVJ6XcUHmKhhclCJYAahC1fHKmnMH00sI+0D+uCqhUFD5\n" +
            "aEvi/o0OC+R5+YyjAd0x5fD6mo3E1XizKztkQgmfuPbRek2QCKomkJbr7W/DraE1wZQE/UYC2SDt\n" +
            "yPoflJK4PeOwt+LQhdHQZwp1Qg3WowxvdQnKaKfgQOVpsEDBUBd+OxWHdjILZv5bTP6fcnhl4qpi\n" +
            "riYm3qlH4bG8h9EYI669+wfxtgJ+lVwEbXstNID1G1/fbUDATc2uI5h6IiUC4Ec8KdXxQU1Msv6r\n" +
            "ZACpvZhnLyjn4NYqWCoi3gy+iph5c7IUEGCXKJpdsp5PG3/MBzCQMwpkRL0ZYUgshvIVqfvhllJO\n" +
            "/WOxKnL5cb8L7w5lNUY9+sf6mUak8ecUEweNPRYmSAhKMo5eI0K3YL/1fIcJEipupbOkXrJaRvM2\n" +
            "v9HwprtLQO4ne7uZU5cT/RwK3xl7v6DD5WzBTObNtAN+TcIiP6+5jDSR8At9wUj79/m/xOAymBRu\n" +
            "FMaNxXzMzWWLXdL6GfL15k712JKspdGV+a5XyfhRXhMTWMuzBFYXyQ76P08fsUSAhWt4BGepicsn\n" +
            "jquywCNJH6LS0EO79CB6cgBH+HuBjBZ4JWTJYFtfhFAmdGMABgZpyn4N6ui0p+J4kx/lZM7Dr0n7\n" +
            "HxSPEYNHt1+Fa76ZZ1GCUMT2Mr0m8hnDtHeqEuVmunUY7vxJX7TJZULGOHKQvMrOfN6IoW6kH3jH\n" +
            "vnrdb4a5mmzOcEiPCoV884dY8T4R9aVPc3r+zBX1ipuh4OdrFWtJkxct6aB8Oqb24l+1c1/GOKMT\n" +
            "L1hQOozB5ArEiv2LTXtlyjy2bg8jcVyLPc5oZBL9giXjFjo3p+uSXRKBOpeRweHjJpy4VyMgHwiU\n" +
            "KUJr/d8By1vCeobLZBEDqGTtMUucRr+Y3iGtXo6sBuBU/ajVfEXfpH8t3FDOfae0sadPcEZ5wxPx\n" +
            "1rUl8h0FnCrKQ5VrhBSXMiFMJt7qjy8LajceHLZV0JMQ95GLqF6kA8xLi1BZGXXgtBHbe/7HRTzx\n" +
            "Cqt+2pTjW55Yg4/lFdMliW4Ak8o1PNgmaT9g+yEssEy0VlRKVvPfJeuROGMATa+oorpZ44o1HN9Z\n" +
            "uYWrVd6qpXMEErojXKDjJIX8TnDH18kx9IB2c8+kzJN8tkwjX7BSdIkK2S+34B9n7pcX7azRoeQW\n" +
            "6m/6p3mjMnSa/pwivrdcJnyb1O+iyKwkT7CLZgI9rHtcMnSR+6/fZ59DaQvzCVAPApN4k9YuJRzy\n" +
            "N9pYeF97lR1PR2+1W5T+yP6U3JL+JV3N+qkV9C6OXhFs2G71I+/VeRZkYDHA51UIrp4+MO/fXFtz\n" +
            "E1lVo28UvLRG6RxDYp7zMAyR69n0WSN6hgWv+wXsEp0BGQEKavsOxUykDAU+XS1qHek373mmHlYV\n" +
            "u7Ws1Igz/rkzjiYLwsFamlQ7hvivKedTOfp8lfW0JDjFn2ZJX0NUDR/a4LWzNs3mxceJaD2LhYF/\n" +
            "xNaeiHNI/pMyzLSZVVOtihC4gwHIfS/vwNRmKbd2QsBZ1zyqpis/86wjp1DNnyM+m2zODMqH+JUN\n" +
            "s/rF4zL9CDm/JsoLXvtDmDW06ztI6yVd1klMxm/2C2D1wXH5Rt/fWZijQhr0SiYnksiS5KMU3a48\n" +
            "Hwkfyq7v0ultHxcVr/bJwIWYcrxP2C5ojJAba4bJIE59YL7l8nez31k1hhjUT9cDvvYev/cgCMJz\n" +
            "bM4cA5aCvg==\n";

    private static String pluginReqResponse = "ORp7Ou9vq/Yrgr1loFqm8cP1FvQbZdl2FKBgRvjDllya8BkSIPlHTp8eUsvBWDZYie9XfdkTutCgsH7N4uWCWEgK1f9LQnfiYpaWSSeoyrdLFSrAzlxnwYuPtScnyWDbdKcTXe8CDLfcvPDt5RyqzqQnFWKLrmcxgSNuDqxkt7emzfDX9pZxOOTFkLoc/xkWVgVGkLkBoBbsNTcQA1onrGpb+egDcwLcX2nfQ+YyajuDaNtmxlAjil/8kkEsxWrwtbLsMO8k58DZyNc7ViUB33q3CAwCUn5sEtoNkvTCDWqmaXZyFDXbodQzAdVZ6USgyNFkoJQuHWaKB8ujnrFPrwyAiGqxiX+MmASvs+EgPYg8tTq6yHGOozVFnWVnFp4kmYDAVSxnC+0q23hkKTEBx4NNAiNZ/k6NxI9dulXORSnz8y6MYLcOUBe+frKjBoySTtv1/WbqurNhy/RAGM6eWydAhLqO/d+lQqkSYnWPfMhROFD59GHHl/No/oI+I+Laq7b/9JaJszhUh6jKF/R0tfPkLl/IpJRsEHn7N6e0Rk1z23ND/9LlboiMQMndeqbTsU+QfHFKWGHAQOOLQEhVqpv0JESpjtHjZyG592fgm1ywn/TYP2TCnQ9AoGsCf73QmidrjgACAiulACCokgEUCUeWSmP+7ejxyj0FA5ZX4IYKBoXzh/DOuNrh79DliJ8SQgNHA0Ht5e0OdIq0vH2cGejDn7rb0oR5bA8NPmNRPPCuSgiOjBzRsBme1U7IjWugrYP6xtYMX0hTGWFO+2IYikfy2AHSy4bypURREnhWDs0gdeqEIhOp/+BvFCQ6FoaH/AyW7kkDi6KGuXUwuqtsFWiuDd1UE7M7rW9CAn1TwH+FPEsWcXUusIxKZIaDa40oOhgFSwHoQRhJTp+KGU+NC8slAzC5XBdz6Gwdkk533EpSLUaOKMs/wp+STs9H9iV+CGlliyAJet1bnNjEdBp/wzyBRiysc0/ob5VQIBGOTR8gFd21XtI/byyHPZzwGvNvCRh4bfVfbcLxK5Ew7c7MvQOum4tKkhEWxHXkLtZNAPOmQK8VlALnq4uOAEEgRDSs";


    private static String pluginFeedbackRequest = "ORp7Ou9vq/Zgg4WDNJDhJfF+pVsUUFmUFyRcNRm4iKO7NkA6nB7+RNP1VTbJEJ6kniaiXditXeDV\n" +
            "xG91IicdILMEKxpB+fppWoCmMwVBDkadAVho8a5qZBqMjVwz62BA9NT/eifXagZ3GB22rH/hmMvq\n" +
            "uMa37qvjqcTnD3NnV83ccM+zvbD9utdpVpO9dkLVabijTeSNOR9Fr+l6qbzyztix79ZkomC5Ifk/\n" +
            "TDpzhI7Qd1NG5ZqScZ236lGOpbvL/jyhM5fUfdJPZP9ykus2M0dbYVKUyTWij7l9OOa+mcgsGf/M\n" +
            "4eyWbi6RDyM7qf7AlxIsm6eZdeIeQq6XiYcaDWlxDjbg2khpA5JLVbCctO3HCps8RbgXUQ==\n";



    private static String pluginFeedbackResponse = "ORp7Ou9vq/Yrgr1loFqm8cP1FvQbZdl2FKBgRvjDllya8BkSIPlHTh9RQzvOazRy3KY4FQhMCRLYo9808/GghZ7f9RVlIJcBv7gdqN1RlfvxpGD/XZj5f4vEdepUup14OluqczgD337/YJP5nmsnBLZp/HzHHKqnQ/z2MeWUC0Gf6v4Z/vHOtFpw73iUmrZuWTmeTLQeEAthaTqvt0UdgIlegCL17KXlyw7UeGZRewzuEV/n/ghAdOaTsmI9xKxRW2I+fBAo+f2r+jCc3/ZwtjfVdm7BESY86VgdherXgoYUGIoYm3T50oX5XoA3YelJM54TJFY9xTYGbqizDoMI9NqqlGz6ubjDITmzgc/4OOS5PCkqtHrO2bchSxs+oiIICevgpsWRkcvtL+4W2uzjK3qrMFQ/pR7nyJozJlBJGWxUdWah6kahsy3TWbBs7D0Rh327Btvbz2jhvAqfVVYGb8n9u8wobgSa67Pm7Dg+CyU=";



    private static String getTaskRequest = "ORp7Ou9vq/Zgg4WDNJDhJfF+pVsUUFmU59INJHwC6uyWjFwx+eXgU6IecEQ8FZ0B0USup0hummtl\n" +
            "+n9RWSMpmZkDBXS85siUz/TsMH+PoTzherVtKS9CIJ1QaDjqUNcNrUQ5VdLF9nSkkmXcqZp6xwxm\n" +
            "oYgURSLpzbCLYd9ZdOiySmRC2mXeUrkaUNfv04wA6XshCoaGXM33pQN9FUC3jUm9fvZKyRK5WHw7\n" +
            "um3GgwKbX7+pphegDBPMEDWNv3i+akoOJ0ZtZ6jh2uw0A74KTD+vKoKOoXW/uPObwEBOYzQx1xZr\n" +
            "kDBiVXPdDOoKykj9T2Yo8vn4qeSrPE8Alw85ihKQZa3mCeBajf9i/oJpqgu2F++6lZFW8dxj1oas\n" +
            "lRiV\n";

    private static String getTaskResponse = "rHmyhnzkeZW7Kx6sS1dgClMQuo4PHxkQ6bE7qbACCugPj1U0rkPlk5I/J18e4wi/f/vhwA3ythlcrYTfiVlefcaYNhOwalo4fnzHWaJ4ZeUnv1nZS3joBR39rA9wgWOlZGJJNA7CMshf/nWeQR+GH6YlIoIt/6ir46X4zsZ7z/jwMB/Ux3wdwYowvbJtZOVRw6zcxo+58A5SjYngSNLm6dnDXlEWGrsaEoi4fhb8BpzAqOsHtVqBL2rcNLjb1pSdSY968h48HcWAgIJW+ztGEHLQcvFlIWaVZBl7ckaRVs4icsBWtJ8MHwsPVViVk42IOL83sw7DfDHcO2EB/7vGHlhraiTdPjE0LlaZj6Afn1eEjVjN1OMKqvXniwgcNAZn2bdh1HCi9Mn3znokMsFu4rIBGey9b4Y9ArIl5/Glh+64Jhav4ZrDLy2a8VZzjpg+HBFaO1qOqB3tf+tIv0D+8SiLGYIX7oyPbolsXKdJFwnov1d0IqgwYunJOKe+z3sdyQ2fDf1I4yoYyLFZh4zmNmH2pmAgxofQXhUDPaQZj23evuqpH1678fZRUEyE4KHMH40kBo3q5dmS18i5+88hxX2zFm8E47GjpJzqr64cM/o+MQZDoJpOTyN5Dlk9PLq4my98r7SfIDRu1zNJAiwqqGi1QxBAtYVQHiosSZPfIFlZWG3hdmF2eqUo4bjU3AOYoHUqL8fRTgvn0XmchJVlL+ZTnc/8zsd2R6/QUSrr3C1cT4igK2SZ6Lvt0fr20ggCdxSRjAzx3rnkPcmm1Ziuwd6B7QvDm7cp8LFiAOaMOlT5HgAB/VicShwbhBfCg4mmGJK2x79pQ7tpOXwl7gLN0T/5b4nL16cOg89Nj1u1pof38LgFvUDGc4E7hO4cTXplVZhmovWxIPtzyOm24qsghvmipG/QxYzHr/Q61doY7zssO0DtemmphrF1Sc+zN44ssD2R1Mg6duzM33vRhYm6IrUtKK1+ZQir3n5D42j26bwSvRVOfpyOWF/5EkLJgTLKhKHIXj5nsVk30IEHMRCEx4aaQ2H46Pv8xRQVkt83WZ/DScjxd+8Ucx0d82+DeEYyXSyZrwoWPuMF/y7K6a7J4KBiHfdKP2jrp+PAVsASsLQsaB7yUix922QLRcW1A0LJ9LVlf+fQXkQRowZ/2kh0fEy847QHexcUDb1QJZNgGV6zwhSLwWkTMrSo7wJD5fxFMm2O2DRD7vLyy+0Mc3/+lxsWWygrOvvoioeT32enZKNhpD+9rZn1qyblS/MgbbsuWB0s5jeXtdQ911hLmmprusXbWp+wc++xanfsWuRhkXtuMkFqPTJaHNTyKE5m0MSHYhuzJcIgRDTzS93mxA3bnacUEyZ7z0vAAbYqYxJQNvEdf4i9LmIMnXFDBZDpO6xTAbTytGV8xSHvoAOZVi/zxpElE+N4HniSrhzZn500oOHzR9J/aroRe3tXDhQ4j0sLnzgrEJnKBiv1K86Q+dYOvNqT9AIuObcHK1i0kaqerD4iRy3JpDq/eBzUhBKh9DFfoZoxFNhy7fkyZeN+nfWkhjGqiwJPWO247wAyM2TzvF0cVL7YBvg29/kSFXg8O2pYrteE4DcuFfauBp+mNIDEa6pGFImhg48kHJxBHwAZF9K8Jha9f/uTzMBfFF9O13K9tsVMXpLx2zielhQ6TCDwOn3hA0FN+v6ObETnscace5XeBTga9JGTyvzjr2w+k8ns0PT4WEFyxPA=";

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();



        //request、response 解密
        String result1 = com.kkwinter.thirdsdk.advsdk.b.f.b(getTaskRequest, com.kkwinter.thirdsdk.advsdk.b.f.b);
        Log.i(TAG, "useAppContext: >>" + result1);

        String result2 = com.kkwinter.thirdsdk.advsdk.b.f.b(getTaskResponse, com.kkwinter.thirdsdk.advsdk.b.f.b);
        Log.i(TAG, "useAppContext: >>" + result2);


//        Log.i(TAG, "useAppContext: >>>" + f.a("FCLiJhYgJ/IEqP9u1lcApBW9KQ=="));
//
//        Log.i(TAG, "useAppContext: >>>" + f.a("Djs="));
//        Log.i(TAG, "useAppContext: >>>" + f.a("HCjgHBA3IcE="));
//        Log.i(TAG, "useAppContext: >>>" + f.a("ASfnEhE="));
//
//        Log.i(TAG, "useAppContext: >>>" + f.a("ADT4"));
//
//        Log.i(TAG, "useAppContext: >>>" + f.a("BSf3EgMmJeMSpPE="));
//
//        Log.i(TAG, "useAppContext: >>>" + f.a("ADT4RA=="));
//        Log.i(TAG, "useAppContext: >>>" + f.a("VTb1GgkgJ8g9qPlung=="));

    }



    public void test(Context context) {

        try {
            Class cls = Class.forName("com.kkwinter.thirdsdk.advsdk.b.n");

            Constructor con = cls.getConstructor(Context.class);
            Object object = con.newInstance(context);

            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);

                String name = field.getName();
                String result = String.valueOf(field.get(object));
                Log.i(TAG, "useAppContext: >name>>>" + name + ", >>result>: " + result);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }


}
