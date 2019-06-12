#include<jni.h>
#include<stdio.h>
#include "test.h"
#include "str_utils.h"
#include <sys/system_properties.h>
#include <android/log.h>
#include "libApp.h"

#define LOG "jniDemo" // 这个是自定义的LOG的标识
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...)  __android_log_print(ANDROID_LOG_FATAL,LOG,__VA_ARGS__) // 定义LOGF类型




JNIEXPORT jstring JNICALL Java_com_phone_ndkdemo_JNITest_get
  (JNIEnv *env, jclass jclass){

     char* key = "ro.build.type";
     char* id = "ro.build.display.id";

     //获取系统类型(user/userdebug/eng), 非user类型不再继续
     char *value[PROP_VALUE_MAX] = {0};
     int ret = __system_property_get(id, value);
     if(ret > 0 && strcmp(value, "user") != 0) {    // >0获取成功, 同时value和user不相同时，不再继续

     }
     LOGD("return for build.type: %s", value);

//       StartJumpRaw(env, "");


      //返回一个字符串
      return (*env)->NewStringUTF(env,"This is my four NDK Application:");
  }

