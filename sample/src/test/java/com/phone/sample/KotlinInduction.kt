package com.phone.sample

import org.junit.Test

/**
 * Created by huangdong on 2019/1/23.
 * antony.huang@yeahmobi.com
 */
class KotlinInduction{

    @Test
    fun main(){

        //基本类型：数字、字符、布尔型、数组、字符串

        //数字 Double Float Long Int Short Byte

        val oneMillion = 1_000_000    //字面值中加下划线，更易读
        var twoMillion = 2*oneMillion
        println(twoMillion)


        val a: Int = 10000
        println(a == a)
//        println(a === a)

        val boxedA: Int? = a
        val anotherBoxedA: Int? = a
        println(boxedA == anotherBoxedA)
//        println(boxedA === anotherBoxedA)


    }




}