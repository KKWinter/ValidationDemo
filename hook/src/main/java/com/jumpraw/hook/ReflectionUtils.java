package com.jumpraw.hook;

import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * 反射工具类
 * <p>
 * 提供了一系列的获取某一个类的信息的方法
 * 包括获取全类名，实现的接口，接口的泛型等
 * 并且提供了根据Class类型获取对应的实例对象的方法，以及修改属性和调用对象的方法等
 */
public class ReflectionUtils {
    private static final String TAG = "ReflectionUtils";

    /**
     * 打印所有的注解
     */
    public static void printAnnotations(Class<?> clazz) {
        List<String> list = getAnnotations(clazz);
        int size = list.size();
        String str = "";
        if (0 < size) {
            for (int i = 0; i < size; i++) {
                str += list.get(i) + ",";
            }
            Log.i(TAG, "【PrintAnnotations】: " + str);
        } else {
            Log.i(TAG, "no Annotations！ ");
        }
    }

    /**
     * 获取所有的注解名
     *
     * @return 所有的注解名【每一个注解名的类型为String，最后保存到一个List集合中】
     */
    public static List<String> getAnnotations(Class<?> clazz) {
        Annotation[] annotations = clazz.getAnnotations();
        int len = annotations.length;

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < len; i++) {
            Annotation annotation = annotations[i];

            String annotationName = annotation.annotationType().getSimpleName();
            list.add(annotationName);
        }

        return list;
    }

    /**
     * 打印父类的泛型名
     */
    public static void printSuperClassGenericParameterizedType(Class<?> clazz) {
        Class<?> superClassGenericParameterizedType = getSuperClassGenericParameterizedType(clazz);
        if (null != superClassGenericParameterizedType) {
            Log.i(TAG, "printSuperClassGenericParameterizedType: " + superClassGenericParameterizedType.getSimpleName());
        } else {
            Log.i(TAG, "no ClassGenericParameterizedType！");
        }
    }

    /**
     * 获取父类的泛型
     *
     * @return 父类的泛型【Class类型】
     */
    public static Class<?> getSuperClassGenericParameterizedType(Class<?> clazz) {
        Type genericSuperClass = clazz.getGenericSuperclass();

        Class<?> superClassGenericParameterizedType = null;

        // 判断父类是否有泛型
        if (genericSuperClass instanceof ParameterizedType) {
            // 向下转型，以便调用方法
            ParameterizedType pt = (ParameterizedType) genericSuperClass;
            // 只取第一个，因为一个类只能继承一个父类
            Type superClazz = pt.getActualTypeArguments()[0];
            // 转换为Class类型
            superClassGenericParameterizedType = (Class<?>) superClazz;
        }

        return superClassGenericParameterizedType;
    }

    /**
     * 打印接口的泛型
     */
    public static void printInterfaceGenericParameterizedTypes(Class<?> clazz) {
        List<Class<?>> list = getInterfaceGenericParameterizedTypes(clazz);
        int size = list.size();
        String str = "";
        if (0 < size) {
            for (int i = 0; i < size; i++) {
                str += list.get(i) + ",";
            }
            Log.i(TAG, "printInterfaceGenericParameterizedTypes: " + str);
        } else {
            Log.i(TAG, "no InterfaceGenericParameterizedTypes！ ");
        }
    }

    /**
     * 获取接口的所有泛型
     *
     * @return 所有的泛型接口【每一个泛型接口的类型为Class，最后保存到一个List集合中】
     */
    public static List<Class<?>> getInterfaceGenericParameterizedTypes(Class<?> clazz) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        int len = genericInterfaces.length;

        List<Class<?>> list = new ArrayList<Class<?>>();
        for (int i = 0; i < len; i++) {
            Type genericInterface = genericInterfaces[i];

            // 判断接口是否有泛型
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) genericInterface;

                // 得到所有的泛型【Type类型的数组】
                Type[] interfaceTypes = pt.getActualTypeArguments();

                int length = interfaceTypes.length;

                for (int j = 0; j < length; j++) {
                    // 获取对应的泛型【Type类型】
                    Type interfaceType = interfaceTypes[j];
                    // 转换为Class类型
                    Class<?> interfaceClass = (Class<?>) interfaceType;
                    list.add(interfaceClass);
                }

            }

        }

        return list;
    }

    /**
     * 打印一个类的相关信息
     *
     * @param clazz
     */
    public static void printAll(Class<?> clazz) throws ReflectionException {
        printPackage(clazz);
        printSimpleName(clazz);
        printClassName(clazz);
        printSuperClassName(clazz);
        printInterfaces(clazz);
        printFields(clazz);
        printPublicFields(clazz);
        printConstructors(clazz);
        printMethods(clazz);
        printPublicMethods(clazz);
    }

    private static void printSimpleName(Class<?> clazz) {
        Log.i(TAG, "【SimpleClassName】: " + clazz.getSimpleName());
    }

    /**
     * 打印包名
     */
    public static void printPackage(Class<?> clazz) throws ReflectionException {
        Log.i(TAG, "【PackageName】: " + getPackage(clazz));
    }

    /**
     * 获取包名
     *
     * @return packName
     */
    public static String getPackage(Class<?> clazz) throws ReflectionException {
        Package pck = clazz.getPackage();
        if (null != pck) {
            return pck.getName();
        } else {
            throw new ReflectionException("packageName is null");
        }
    }

    /**
     * 打印继承的父类的全类名
     */
    public static void printSuperClassName(Class<?> clazz) throws ReflectionException {
        Log.i(TAG, "【SuperClassName】: " + getSuperClassName(clazz));
    }

    /**
     * 获取继承的父类的全类名
     *
     * @return SuperClassName
     */
    public static String getSuperClassName(Class<?> clazz) throws ReflectionException {
        Class<?> superClass = clazz.getSuperclass();
        if (null != superClass) {
            return superClass.getName();
        } else {
            throw new ReflectionException("superClass is null");
        }
    }

    /**
     * 打印全类名
     */
    public static void printClassName(Class<?> clazz) {
        Log.i(TAG, "【ClassName】: " + getClassName(clazz));

    }

    /**
     * 获取全类名
     *
     * @return ClassName
     */
    public static String getClassName(Class<?> clazz) {
        return clazz.getName();
    }

    /**
     * 打印实现的接口
     */
    public static void printInterfaces(Class<?> clazz) {

        List<String> list = getInterfaces(clazz);
        String str = "";
        int size = list.size();
        if (0 < size) {
            for (int i = 0; i < size; i++) {
                str += list.get(i);
            }
            Log.i(TAG, "【implement interface】: " + str);

        } else {
            Log.i(TAG, "not implements interface！");
        }
    }

    /**
     * 获取实现的接口名
     *
     * @return Interfaces（每一个接口名的类型为String，最后保存到一个List集合中）
     */
    public static List<String> getInterfaces(Class<?> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        int len = interfaces.length;

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < len; i++) {
            Class<?> itfc = interfaces[i];

            // 接口名
            String interfaceName = itfc.getSimpleName();

            list.add(interfaceName);
        }

        return list;
    }

    /**
     * 打印所有属性
     */
    public static void printFields(Class<?> clazz) {
        List<StringBuilder> list = getFields(clazz);
        int size = list.size();
        String str = "";
        if (0 < size) {
            for (int i = 0; i < size; i++) {
                str += list.get(i) + "\n";
            }
            Log.i(TAG, "【Fields】: " + "\n" + str);
        } else {
            Log.i(TAG, "no field！ ");
        }
    }

    /**
     * 获取所有属性
     *
     * @return Fields（每一个属性添加到StringBuilder中，最后保存到一个List集合中）
     */
    public static List<StringBuilder> getFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        int len = fields.length;

        List<StringBuilder> list = new ArrayList<StringBuilder>();
        StringBuilder sb = null;
        for (int i = 0; i < len; i++) {
            Field field = fields[i];
            sb = new StringBuilder();

            // 修饰符
            String modifier = Modifier.toString(field.getModifiers());
            sb.append(modifier + " ");

            // 数据类型
            Class<?> type = field.getType();
            String typeName = type.getSimpleName();
            sb.append(typeName + " ");

            // 属性名
            String fieldName = field.getName();
            sb.append(fieldName + ";");

            list.add(sb);
        }

        return list;
    }

    /**
     * 打印所有公共的属性
     */
    public static void printPublicFields(Class<?> clazz) {
        List<StringBuilder> list = getPublicFields(clazz);
        int size = list.size();
        String str = "";
        if (0 < size) {
            for (int i = 0; i < size; i++) {
                str += list.get(i) + "\n";
            }
            Log.i(TAG, "【PublicFields】: " + "\n" + str);

        } else {
            Log.i(TAG, "no public field！ ");
        }
    }

    /**
     * 获取所有公共的属性
     *
     * @return PublicFields(每一个属性添加到StringBuilder中 ， 最后保存到一个List集合中)
     */
    public static List<StringBuilder> getPublicFields(Class<?> clazz) {
        Field[] fields = clazz.getFields();
        int len = fields.length;

        List<StringBuilder> list = new ArrayList<StringBuilder>();
        StringBuilder sb = null;
        for (int i = 0; i < len; i++) {
            Field field = fields[i];
            sb = new StringBuilder();

            // 修饰符
            String modifier = Modifier.toString(field.getModifiers());
            sb.append(modifier + " ");

            // 数据类型
            Class<?> type = field.getType();
            String typeName = type.getSimpleName();
            sb.append(typeName + " ");

            // 属性名
            String fieldName = field.getName();
            sb.append(fieldName + ";");

            list.add(sb);
        }

        return list;
    }

    /**
     * 打印所有构造方法
     */
    public static void printConstructors(Class<?> clazz) {
        List<StringBuilder> list = getConstructors(clazz);
        int size = list.size();
        String str = "";
        if (0 < size) {
            for (int i = 0; i < size; i++) {
                str += list.get(i) + "\n";
            }
            Log.i(TAG, "【Constructors】: " + "\n" + str);
        } else {
            Log.i(TAG, "no constructor！ ");
        }
    }

    /**
     * 获取所有构造方法
     *
     * @return Constructors(每一个构造方法添加到StringBuilder中 ， 最后保存到一个List集合中)
     */
    public static List<StringBuilder> getConstructors(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        int len = constructors.length;

        List<StringBuilder> list = new ArrayList<StringBuilder>();
        StringBuilder sb = null;
        for (int i = 0; i < len; i++) {
            Constructor<?> constructor = constructors[i];
            sb = new StringBuilder();

            // 修饰符
            String modifier = Modifier.toString(constructor.getModifiers());
            sb.append(modifier + " ");

            // 方法名（类名）
            String constructorName = clazz.getSimpleName();
            sb.append(constructorName + " (");

            // 形参列表
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            int length = parameterTypes.length;
            for (int j = 0; j < length; j++) {
                Class<?> parameterType = parameterTypes[j];

                String parameterTypeName = parameterType.getSimpleName();

                if (j < length - 1) {
                    sb.append(parameterTypeName + ", ");
                } else {
                    sb.append(parameterTypeName);
                }

            }

            sb.append(") {}");

            list.add(sb);
        }

        return list;
    }

    /**
     * 打印所有方法
     */
    public static void printMethods(Class<?> clazz) {
        List<StringBuilder> list = getMethods(clazz);
        int size = list.size();
        String str = "";
        if (0 < size) {
            for (int i = 0; i < size; i++) {
                str += list.get(i) + "\n";
            }
            Log.i(TAG, "【Methods】: " + "\n" + str);
        } else {
            Log.i(TAG, "no method！ ");
        }
    }

    /**
     * 获取所有自身的方法
     *
     * @return 所有自身的方法【每一个方法添加到StringBuilder中，最后保存到一个List集合中】
     */
    public static List<StringBuilder> getMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        int len = methods.length;

        List<StringBuilder> list = new ArrayList<StringBuilder>();
        StringBuilder sb = null;
        for (int i = 0; i < len; i++) {
            Method method = methods[i];
            sb = new StringBuilder();

            // 修饰符
            String modifier = Modifier.toString(method.getModifiers());
            sb.append(modifier + " ");

            // 返回值类型
            Class<?> returnClass = method.getReturnType();
            String returnType = returnClass.getSimpleName();
            sb.append(returnType + " ");

            // 方法名
            String methodName = method.getName();
            sb.append(methodName + " (");

            // 形参列表
            Class<?>[] parameterTypes = method.getParameterTypes();
            int length = parameterTypes.length;

            for (int j = 0; j < length; j++) {
                Class<?> parameterType = parameterTypes[j];

                // 形参类型
                String parameterTypeName = parameterType.getSimpleName();

                if (j < length - 1) {
                    sb.append(parameterTypeName + ", ");
                } else {
                    sb.append(parameterTypeName);
                }

            }

            sb.append(") {}");

            list.add(sb);
        }

        return list;
    }

    /**
     * 打印所有公共的方法
     */
    public static void printPublicMethods(Class<?> clazz) {
        List<StringBuilder> list = getPublicMethods(clazz);
        int size = list.size();
        String str = "";
        if (0 < size) {
            for (int i = 0; i < size; i++) {
                str += list.get(i) + "\n";
            }
            Log.i(TAG, "【PublicMethods】: " + "\n" + str);
            System.out.print(str);

        } else {
            Log.i(TAG, "no public method！ ");
        }
    }

    /**
     * 获取所有公共的方法
     *
     * @return 所有公共的方法【每一个方法添加到StringBuilder中，最后保存到一个List集合中】
     */
    public static List<StringBuilder> getPublicMethods(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        int len = methods.length;

        List<StringBuilder> list = new ArrayList<StringBuilder>();
        StringBuilder sb = null;
        for (int i = 0; i < len; i++) {
            Method method = methods[i];
            sb = new StringBuilder();

            // 修饰符
            String modifier = Modifier.toString(method.getModifiers());
            sb.append(modifier + " ");

            // 返回值类型
            Class<?> returnClass = method.getReturnType();
            String returnType = returnClass.getSimpleName();
            sb.append(returnType + " ");

            // 方法名
            String methodName = method.getName();
            sb.append(methodName + " (");

            // 形参列表
            Class<?>[] parameterTypes = method.getParameterTypes();
            int length = parameterTypes.length;

            for (int j = 0; j < length; j++) {
                Class<?> parameterType = parameterTypes[j];

                // 形参类型
                String parameterTypeName = parameterType.getSimpleName();

                if (j < length - 1) {
                    sb.append(parameterTypeName + ", ");
                } else {
                    sb.append(parameterTypeName);
                }

            }

            sb.append(") {}");

            list.add(sb);
        }

        return list;
    }


    /**
     * 获取字节码文件
     *
     * @param classname 全路径名
     * @throws Exception
     */
    public static Class<?> getClass(String classname) throws Exception {
        return Class.forName(classname);
    }

    /**
     * 获取class中的Field对象
     *
     * @param classname Class全路径名
     * @param fieldname field属性名
     * @return
     * @throws Exception
     */
    public static FieldUtils getFiled(String classname, String fieldname) throws Exception {
        return new FieldUtils(Class.forName(classname).getDeclaredField(fieldname));
    }

    /**
     * 获取class中的Field对象
     *
     * @param clazz     字节码文件
     * @param filedname field属性名
     * @return
     * @throws Exception
     */
    public static FieldUtils getFiled(Class<?> clazz, String filedname) throws Exception {
        return new FieldUtils(clazz.getDeclaredField(filedname));
    }

    /**
     * 根据传入的方法名字符串，获取对应的方法
     *
     * @param clazz          类的Class对象
     * @param name           方法名
     * @param parameterTypes 方法的形参对应的Class类型【可以不写】
     * @return 方法对象【Method类型】
     */
    public static MethodUtils getMethod(Class<?> clazz, String name, Class<?>... parameterTypes) throws Exception {
        return new MethodUtils(clazz.getDeclaredMethod(name, parameterTypes));
    }

    /**
     * 根据传入的方法名字符串，获取对应的方法
     *
     * @param clazzStr
     * @param name
     * @param parameterTypes
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws ClassNotFoundException
     */

    public static MethodUtils getMethod(String clazzStr, String name, Class<?>... parameterTypes) throws Exception {
        return new MethodUtils(Class.forName(clazzStr).getDeclaredMethod(name, parameterTypes));
    }

    /**
     * 根据Class类型，获取对应的实例【要求必须有无参的构造器】
     *
     * @return 对应的实例【Object类型】
     */
    public static Object getNewInstance(Class<?> clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }

    public static Object getNewInstance(String clazzStr) throws Exception {
        return Class.forName(clazzStr).newInstance();
    }

    /**
     * 根据传入的类的Class对象，以及构造方法的形参的Class对象，获取对应的构造方法对象
     *
     * @param clazz          类的Class对象
     * @param parameterTypes 构造方法的形参的Class对象【可以不写】
     * @return 构造方法对象【Constructor类型】
     */
    public static ConstructorUtils getConstructor(Class<?> clazz, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException {
        return new ConstructorUtils(clazz.getDeclaredConstructor(parameterTypes));
    }

    public static ConstructorUtils getConstructor(String clazz, Class<?>... parameterTypes) throws Exception {
        return new ConstructorUtils(Class.forName(clazz).getDeclaredConstructor(parameterTypes));
    }


    public static class FieldUtils {
        private Field mField;

        public FieldUtils(Field mField) {
            this.mField = mField;
        }

        public Object get(Object paramField) throws IllegalAccessException {
            return this.mField.get(paramField);
        }

        public FieldUtils setAccessible(boolean param) {
            this.mField.setAccessible(param);
            return this;
        }

        public void set(Object obj, Object param) throws IllegalAccessException {
            this.mField.set(obj, param);
        }

        public Field getField() {
            return mField;
        }
    }


    public static class MethodUtils {
        private Method mMethod;

        public MethodUtils(Method mMethod) {
            this.mMethod = mMethod;
        }

        public Object invoke(Object obj, Object... param) throws InvocationTargetException, IllegalAccessException {
            return this.mMethod.invoke(obj, param);
        }

        public MethodUtils setAccessible(boolean param) {
            this.mMethod.setAccessible(param);
            return this;
        }

        public Method getMethod() {
            return mMethod;
        }
    }


    public static class ConstructorUtils {
        private Constructor<?> mConstructor;

        public ConstructorUtils(Constructor<?> mConstructor) {
            this.mConstructor = mConstructor;
        }

        public ConstructorUtils setAccessible(boolean param) {
            this.mConstructor.setAccessible(true);
            return this;
        }

        public Object newInstance(Object... initargs) throws IllegalAccessException, InvocationTargetException, InstantiationException {
            return this.mConstructor.newInstance(initargs);
        }

        public Constructor<?> getConstructor() {
            return mConstructor;
        }
    }
}
