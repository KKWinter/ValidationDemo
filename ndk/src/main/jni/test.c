#include<jni.h>
#include<stdio.h>
//导入我们创建的头文件
#include "test.h"
#include "jni_utils.h"
#include "libApo.h"

JNIEXPORT jstring JNICALL Java_com_phone_ndkdemo_JNITest_get
  (JNIEnv *env, jclass jclass){

      //返回一个字符串
      return (*env)->NewStringUTF(env,"This is my four NDK Application");
  }

