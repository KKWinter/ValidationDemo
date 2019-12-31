package com.jumpraw.hook;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "hook";
    private Context context;
    private NotificationManager notificationManager;
    private ClipboardManager clipboardManager;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.getApplicationContext();

//        testStaticProxy();
//
//        testDynamicProxy();
//
//        testHookDemo();

        Button bt = findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: >>>>>>");
            }
        });

        testHookOnClickListener(bt);


        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                String CHANNEL_ONE_ID = "com.jumpraw.hook";
                Intent intent = new Intent(context, SecondActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                @SuppressLint("WrongConstant")
                Notification.Builder builder = new Notification.Builder(context)
                        .setContentTitle("this is title")
                        .setChannelId(CHANNEL_ONE_ID)
                        .setContentText("this is content")
                        .setContentIntent(pendingIntent)
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setVibrate(new long[]{0, 1000, 1000, 1000}) //通知栏消息震动
                        .setLights(Color.GREEN, 1000, 2000) //通知栏消息闪灯(亮一秒间隔两秒再亮)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setSmallIcon(R.mipmap.ic_launcher);
                notificationManager.notify(1, builder.build());  // 第一个参数1具体实现时 需要修改 用于显示不同消息。
            }
        });

        testHookNotification();


        clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipData clipData = ClipData.newPlainText("Label", "this is a phrase from kkwinter");
                clipboardManager.setPrimaryClip(clipData);

//                ClipData clipData = clipboardManager.getPrimaryClip();
//                for (int i = 0; i < clipData.getItemCount(); i++) {
//                    String result = clipData.getItemAt(i).getText().toString();
//                    Log.i(TAG, "onClick: >>>" + result);
//                }
            }
        });

//        testHookClipBoard();


        findViewById(R.id.bt4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("url", "http://www.baidu.com");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
//                context.startActivity(intent);
            }
        });

//        testHookStartActivity1();

//        testHookStartActivity2();

        testHookStartActivity3();

        testHookSetIntent();

    }


    //静态代理

    //聚合代理模式
    interface ISubject {
        void doAction();
    }

    class RealSubject implements ISubject {
        @Override
        public void doAction() {
            //do something
        }
    }

    class ProxySubject implements ISubject {
        //含有真实对象的引用，从而可以操作真实对象
        private RealSubject realSubject;

        public ProxySubject(RealSubject realSubject) {
            this.realSubject = realSubject;
        }

        //提供和真实对象相同的接口，以便可以完全代替真实对象
        @Override
        public void doAction() {
            //操作真实对象的时候可以做附加操作
            //附加操作
            realSubject.doAction();
            //附加操作
        }
    }

    private void testStaticProxy() {
        ProxySubject proxySubject = new ProxySubject(new RealSubject());
        proxySubject.doAction();
    }


    //动态代理

    //聚合代理模式
    interface Moveable {
        int move(int num);
    }

    class Car implements Moveable {
        @Override
        public int move(int num) {
            Log.i(TAG, "move: 正在移动中。。。。.");
            return num;
        }
    }

    //代理对象关联的事务处理器
    class TestHandler implements InvocationHandler {

        //内部含有真实对象的引用，从而可以操作真实对象
        private Object mObject;

        public TestHandler(Object object) {
            mObject = object;
        }

        //获取代理对象，【实现了和真实对象相同的接口】
        public Object getProxy() {
            return Proxy.newProxyInstance(mObject.getClass().getClassLoader(), mObject.getClass().getInterfaces(), this);
        }

        /**
         * 代理对象实现了和真实对象相同的接口方法，以便可以完全代替真实对象 【在调用代理对象的方法时调用到这里】
         *
         * @param proxy  代理对象
         * @param method 代理对象中实现的和真实对象相同的方法
         * @param args   方法的参数列表
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            //在对真实对象做操作时可以附加其他操作 【偷梁换柱，哈哈！！】
            Log.i(TAG, "invoke: 移动之前");
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg instanceof Integer) {
                    args[i] = (Integer) arg + 2;
                }
            }

            int result = (int) method.invoke(mObject, args);
            Log.i(TAG, "invoke: 移动之后");

            return result;
        }
    }

    private void testDynamicProxy() {

        Car car = new Car();
        TestHandler testHandler = new TestHandler(car);
        Object object = testHandler.getProxy();     //代理对象
        int result = ((Moveable) object).move(1);

        Log.i(TAG, "testDynamicProxy: >>" + result);

    }


    //代理模式实践 —— hook
    private void testHookDemo() {
        TestFunction testFunction = new TestFunction();

        try {
            Class clsTest = testFunction.getClass();
            Field penField = clsTest.getDeclaredField("pen");
            penField.setAccessible(true);
            Object penObj = penField.get(testFunction);

            DemoHandler demoHandler = new DemoHandler(penObj);
            Object proxyPenObj = demoHandler.getProxy();

            penField.set(testFunction, proxyPenObj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        testFunction.start("http://www.baidu.com");
    }

    class DemoHandler implements InvocationHandler {

        private Object mObject;

        public DemoHandler(Object object) {
            mObject = object;
        }

        public Object getProxy() {
            return Proxy.newProxyInstance(mObject.getClass().getClassLoader(), mObject.getClass().getInterfaces(), this);
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg instanceof String) {
                    args[i] = "http://www.jianshu.com";
                }
            }

            method.invoke(mObject, args);

            return null;
        }
    }


    //代理模式实践 —— hook OnClickListener
    private void testHookOnClickListener(View view) {
        try {
            //OnClickListener是放在View的内部类ListenerInfo中
            //1. 获取OnClickListener实际对象
            //2. 获取OnCliicListener的代理对象
            //3. 用代理对象替换ListenerInfo中的实际对象

            Class viewCls = Class.forName("android.view.View");
            Method method = viewCls.getDeclaredMethod("getListenerInfo");
            method.setAccessible(true);
            Object infoObj = method.invoke(view);     //ListenerInfo对象

            Class infoCls = infoObj.getClass();
            Field field = infoCls.getDeclaredField("mOnClickListener");
            Object listenerObj = field.get(infoObj);  //OnClickListener实际对象

            ClickListenerHandler clickListenerHandler = new ClickListenerHandler(listenerObj);
            Object proxyObj = clickListenerHandler.getProxy();    //OnClickListener代理对象

            field.set(infoObj, proxyObj);

//            MyOnClickListener proxyOnClickListener = new MyOnClickListener((View.OnClickListener) listenerObj);  // 代理对象
//            field.set(infoObj, proxyOnClickListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ClickListenerHandler implements InvocationHandler {

        private Object mObject;

        public ClickListenerHandler(Object object) {
            mObject = object;
        }

        public Object getProxy() {
            return Proxy.newProxyInstance(mObject.getClass().getClassLoader(), mObject.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if ("onClick".equals(method.getName())) {
                Log.i(TAG, "点击: >>> from kkwinter invoke");
            }
            method.invoke(mObject, args);

            return null;
        }
    }

    class MyOnClickListener implements View.OnClickListener {

        private View.OnClickListener mObject;

        public MyOnClickListener(View.OnClickListener object) {
            mObject = object;
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, "点击: >>> from kkwinter invoke");
            mObject.onClick(v);
        }
    }


    //代理模式实践 —— hook notification
    private void testHookNotification() {

        try {
            Class notifyCls = NotificationManager.class;
            Method serviceMethod = notifyCls.getDeclaredMethod("getService");
            serviceMethod.setAccessible(true);
            Object serviceObj = serviceMethod.invoke(null);   //静态方法，获取真实对象

            NotificationHandler notificationHandler = new NotificationHandler(serviceObj);
            Object proxyObj = notificationHandler.getProxy();       //代理对象

            Field serviceField = notifyCls.getDeclaredField("sService");
            serviceField.setAccessible(true);
            serviceField.set(null, proxyObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class NotificationHandler implements InvocationHandler {

        private Object mObjcet;

        public NotificationHandler(Object object) {
            mObjcet = object;
        }

        public Object getProxy() {
            return Proxy.newProxyInstance(mObjcet.getClass().getClassLoader(), mObjcet.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            String name = method.getName();
            Log.i(TAG, "发通知: >>> from kkwinter invoke");

            method.invoke(mObjcet, args);

            return null;
        }
    }

    //代理模式实践 —— hook clipBoard
    private void testHookClipBoard() {

        try {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            Field mServiceFiled = ClipboardManager.class.getDeclaredField("mService");   //有报错，NoSuchField，很奇怪
            mServiceFiled.setAccessible(true);
            final Object mService = mServiceFiled.get(clipboardManager);    //实际对象

            ClipBoardHandler clipBoardHandler = new ClipBoardHandler(mService);
            Object proxyObj = clipBoardHandler.getProxy();             //代理对象

            mServiceFiled.set(clipboardManager, proxyObj);             //偷梁换柱

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ClipBoardHandler implements InvocationHandler {

        private Object object;

        public ClipBoardHandler(Object object) {
            this.object = object;
        }

        public Object getProxy() {
            return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Log.i(TAG, "粘贴板: >>> from kkwinter invoke");
            return method.invoke(object, args);
        }
    }


    //代理模式实践 —— hook startActivity
    //方法一: activity.startActivity()
    private void testHookStartActivity1() {
        //activity.startActivity()调用到这个方法，mInstrumentation.execStartActivity()；
        //而Instrumentation是类，不是接口，考虑用继承代理模式，可以用静态代理实现

        try {
            //先获取Activity中的mInstrumentation对象
            Field insField = Activity.class.getDeclaredField("mInstrumentation");
            insField.setAccessible(true);
            Object insObj = insField.get(this);   //mInstrumentation 真实对象

            //自己的实现的Instrumentation的子类对象，代理对象
            MyInstrumentation myInstrumentation = new MyInstrumentation(insObj);

            //偷梁换柱，用自己实现的代理对象替换真实对象
            insField.set(this, myInstrumentation);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //方法二：context.startActivity()
    private void testHookStartActivity2() {
        //context.startActivity() 是在 contextImpl.startActivity()中实现的
        //contextImpl.startActivity()中内部调用到mMainThread.getInstrumentation().execStartActivity()方法
        //所以是hook替换ActivityThread中的Instrumentation对象

        try {
            //获取ActivityThread对象的第一种方式
            Class impCls = Class.forName("android.app.ContextImpl");
            Method getImplMethod = impCls.getDeclaredMethod("getImpl", Context.class);
            getImplMethod.setAccessible(true);
            Object impObj = getImplMethod.invoke(null, context);

            Field threadField = impCls.getDeclaredField("mMainThread");
            threadField.setAccessible(true);
            Object threadObj = threadField.get(impObj);


            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");

            //获取ActivityThread对象的第二种方式
//            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
//            currentActivityThreadMethod.setAccessible(true);
//            Object threadObj = currentActivityThreadMethod.invoke(null);

            //再获取Instrumentation的对象
            Field insField = activityThreadClass.getDeclaredField("mInstrumentation");
            insField.setAccessible(true);
            Object insObj = insField.get(threadObj);        //真实对象

            //自己的实现的Instrumentation的子类对象，代理对象
            MyInstrumentation myInstrumentation = new MyInstrumentation(insObj);   //代理对象

            insField.set(threadObj, myInstrumentation);      //偷梁换柱

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    class MyInstrumentation extends Instrumentation {

        private Object mObject;

        //含有真实对象的引用，从而可以操作真实对象
        public MyInstrumentation(Object object) {
            mObject = object;
        }

        //提供和真实对象相同的方法，以便完全替代真实对象
        public ActivityResult execStartActivity(
                Context who, IBinder contextThread, IBinder token, Activity target,
                Intent intent, int requestCode, Bundle options) {

            Log.i(TAG, "execStartActivity: >>> from kkwinter");
            intent.putExtra("url", "lalalallalalalalal");

            try {
                //执行真实对象的操作
                //因为是隐藏方法，需要通过反射调用
                Method execMethod = mObject.getClass().getDeclaredMethod("execStartActivity",
                        Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class);

                execMethod.setAccessible(true);
                return (ActivityResult) execMethod.invoke(mObject, who, contextThread, token, target, intent, requestCode, options);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }


    }

    //方法三：
    private void testHookStartActivity3() {
        //上述两种方式最终都是调用到instrumentattion.execStartActivity()方法，只不过instrumentation所在的对象不同
        //可以在instrumentattion.execStartActivity()方法的内部查看是否有可以hook的点
        //最后发现调用到了ActivityManager.getService().startActivity，而ActivityManager.getService()得到的IActivityManager是个接口 —— 偷梁换柱
        //有一个知识点，Singleton是单例辅助类，可以对其进行操作获取和重置IActivityManager对象
        //api 26开始这块源码有变化，26之前的稍微有不同，不过最终还是替换IActivityManager

        try {
            Object singletonObj;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {    // api 26之后
                //获取ActivityManager中的静态成员变量IActivityManagerSingleton的对象
                Class ActivityManagerCls = Class.forName("android.app.ActivityManager");
                Field singletonField = ActivityManagerCls.getDeclaredField("IActivityManagerSingleton");
                singletonField.setAccessible(true);
                singletonObj = singletonField.get(null);
            } else {                                                // api 26之前
                Class ActivityManagerNativeClss = Class.forName("android.app.ActivityManagerNative");
                Field defaultFiled = ActivityManagerNativeClss.getDeclaredField("gDefault");
                defaultFiled.setAccessible(true);
                singletonObj = defaultFiled.get(null);
            }

            //在singleton中的mInstance成员变量指向的就是存储的单例对象IActivityManager
            Class singletonCls = Class.forName("android.util.Singleton");
            Field instanceField = singletonCls.getDeclaredField("mInstance");
            instanceField.setAccessible(true);
            Object iActivityManagerObj = instanceField.get(singletonObj);     //真实对象，IActivityManager，是个接口，可以动proxy动态代理方式

            IActivityManagerHandler iActivityManagerHandler = new IActivityManagerHandler(iActivityManagerObj);
            Object proxyObj = iActivityManagerHandler.getProxy();             //代理对象

            //再对singleton中的mInstance成员变量设置自己实现的代理对象
            instanceField.set(singletonObj, proxyObj);                        //偷梁换柱

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class IActivityManagerHandler implements InvocationHandler {

        private Object mObject;

        public IActivityManagerHandler(Object object) {
            mObject = object;
        }

        public Object getProxy() {
            return Proxy.newProxyInstance(mObject.getClass().getClassLoader(), mObject.getClass().getInterfaces(), this);
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            String methodName = method.getName();
            Log.i(TAG, "invoke: >>> from kkwinter invoke === " + methodName);

            if (method.getName().equals("startActivity")) {
                int index = 0;
                //  找到我们启动时的intent
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof Intent) {
                        index = i;
                        break;
                    }
                }

                //取出真实的Intent，做一些偷梁换柱的工作
                Intent originallyIntent = (Intent) args[index];

                //比如，替换intent中的url

                //比如，直接替换成别的intent
                //因为我们调用的Activity没有注册，所以这里我们先偷偷换成已注册，使用一个假的Intent
                Intent proxyIntent = new Intent();
                ComponentName componentName = new ComponentName("com.jumpraw.hook", "com.jumpraw.hook.ThirdActivity");
                proxyIntent.setComponent(componentName);
                //在这里把未注册的Intent先存起来 一会儿我们需要在Handle里取出来用
                proxyIntent.putExtra("ORIGIN", originallyIntent);
                args[index] = proxyIntent;
            }

            return method.invoke(mObject, args);
        }
    }


    //不在清单文件中注册的activity如何启动

    //TransactionExecutor 的execute方法

    private void testHookSetIntent() {

        try {
            Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = activityThreadClazz.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object sCurrentActivityThreadObj = sCurrentActivityThreadField.get(null);

            //hook handleMessage
            Field mHField = activityThreadClazz.getDeclaredField("mH");
            mHField.setAccessible(true);
            Handler mH = (Handler) mHField.get(sCurrentActivityThreadObj);   // mH对象，即Handler

            //handler的机制，有callback先回调callback
            Field callBackField = Handler.class.getDeclaredField("mCallback");
            callBackField.setAccessible(true);
            callBackField.set(mH, new ActivityThreadHandlerCallback(mH));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class ActivityThreadHandlerCallback implements Handler.Callback {

        private Handler handler;

        private ActivityThreadHandlerCallback(Handler handler) {
            this.handler = handler;
        }

        @Override
        public boolean handleMessage(Message msg) {
            Log.i("HookAmsUtil", "handleMessage");

            //替换之前的Intent
            if (msg.what == 100) {   //在handler中的LAUNCH_ACTIVITY中处理消息， android 9.0之前，android 9.0之后发生变化
                Log.i("HookAmsUtil", "lauchActivity");
                handleLauchActivity(msg);
            }

            handler.handleMessage(msg);
            return true;
        }

        private void handleLauchActivity(Message msg) {
            Object obj = msg.obj;//ActivityClientRecord
            try {
                Field intentField = obj.getClass().getDeclaredField("intent");
                intentField.setAccessible(true);
                Intent proxyInent = (Intent) intentField.get(obj);
                Intent realIntent = proxyInent.getParcelableExtra("ORIGIN");
                if (realIntent != null) {
                    proxyInent.setComponent(realIntent.getComponent());
                }
            } catch (Exception e) {
                Log.i("HookAmsUtil", "lauchActivity falied");
            }

        }
    }

}


