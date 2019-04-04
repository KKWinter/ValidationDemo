package com.phone.sample

import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    //定义顶层变量
    val g = 3.14
    var h = 30

    @Test
    fun main() {
        //定义局部变量

        //定义只读局部变量使用关键字val定义，只能为其赋值一次
        val a: Int = 1  //立即赋值
        val b = 2       //立即赋值可以省略类型，会自动推断出Int类型
        val c: Int      //没有初始值的话，不可以省略类型
        c = 3           //明确赋值

        println("a = $a, b = $b, c = $c")


        //可重新赋值的变量使用var关键字
        var e: Int = 5
        var f = 6
        e += 1
        println("e = $e, f=${f++}, f = ${++f}")


        //打印顶层变量
        println("m = $g, n = ${++h}")


        //使用字符串模板
        var i = 2
        val j1 = "i is ${i}"

        i = 5
        val j2 = "${j1.replace("is", "was")}, but now is $i"
        println(j2)
        println("abc".plus("def"))


        //调用函数
        println("sum of 4 and 5 is ${sum(4, 5)}")
        printSum(1, 2)
        printSum(1, 2, 3)


        println("the max of 1 and 2 is ${maxof(1, 2)}")
        println("the min of 1 and 2 is ${minof(1, 2)}")
        println(minof(1, 2, 3))

        printProduct("6", "7")
        printProduct("a", "7")


        checkString("huangdong", 26, "")


        val items = listOf<String>("one", "two", "three")

        //for循环
        for (item in items) {
            println(item)
        }

        for (index in items.indices) {    //items.indices 是角标的集合
            println("item at $index is ${items[index]}")
        }

        //while循环
        var index = 0                    //items.size是集合的大小
        while (index < items.size) {
            println("item at $index is ${items[index]}")
            index++
        }

        //when表达式(比较好玩)
        println(describe("hello"))
        println(describe(234234234234))
        println(describe(2))
        println(describe(1))


        //区间

        for (x in 1..5) {
            println(x)
        }

        if (1 in 1..4) {
            println("1 is in range")
        }

        if (-1 !in 1..4) {
            println("-1 is not in range")
        }

        for (i in 1..9 step 2) {
            print(i)
        }
        println()

        for (i in 9 downTo 1 step 3){
            print(i)
        }
        println()

        for (i in 1 until 10) {
            print(i)
        }
        println()


        //集合
        val friuts = listOf("banana", "avocado", "apple", "kiwifruit")

        for (friut in friuts) {
            println(friut)
        }

        if ("orange" in friuts) {
            println("orange is not in friuts")
        }


        when {
            "orange" in friuts -> println("juicy")
            "apple" in friuts -> println("app is fine too")
        }

        //lambda表达式
        friuts
                .filter { it.startsWith("a") }
                .sortedBy { it }
                .map { it.toUpperCase() }
                .forEach { println(it) }

    }





    //when表达式
    fun describe(obj: Any): String = when (obj) {
        "hello" -> "Greeting"
        1 -> "one"
        is Long -> "Long"
        !is Double -> "not Double"
        else -> "Unknown"
    }


    //类型检测和自动类型转化（变量类型检测成功之后，会自动转换成该类型）
    fun checkString(obj1: Any, obj2: Any, obj3: Any) {
        if (obj1 is String) {
            println("$obj1 is String, and the length is ${obj1.length}")
        }

        if (obj2 !is Int) {
            println("$obj2 is not Int")
        }

        if (obj3 is String && obj3.length > 0) {  //类型检测之后，&&右边就自动转换成了String类型
            println("$obj3 is String, and the length is not 0")
        }
    }


    //可为空值的类型定义以及null检测
    fun parseInt(str: String): Int? {  //可能为空值的话，声明的类型后要加？
        return str.toIntOrNull()
    }

    fun printProduct(arg1: String, arg2: String) {
        val x = parseInt(arg1)
        val y = parseInt(arg2)

        if (x == null || y == null) {
            println("$arg1 or $arg2 is not number")
        } else {
            println("$x * $y is ${x * y}")
        }
    }


    //使用条件表达式
    fun maxof(a: Int, b: Int): Int {
        if (a > b) {
            return a
        } else {
            return b
        }
    }

    fun minof(a: Int, b: Int) = if (a < b) b else a
    fun minof(a: Int, b: Int, c: Int) = if (a < b) if (a < c) a else c else if (b < c) b else c


    //定义函数
    fun sum(a: Int, b: Int): Int {
        return a + b
    }

    fun sum(a: Int, b: Int, c: Int): Int = a + b + c

    fun printSum(a: Int, b: Int): Unit {
        println("sum of $a and $b is ${a + b}")
    }

    fun printSum(a: Int, b: Int, c: Int) {
        println("sum of $a and $b and $c is ${a + b + c}")
    }


}
