#ifndef JNI_UTILS_H
#define JNI_UTILS_H

#include <stdbool.h>
#include <android/log.h>
#include <string.h>
#include <jni.h>
#include <stddef.h>
#include "str_utils.h"

#define LOG_TAG "logFromJR"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__) // 定义LOGD类型
#undef NULL
#define NULL 0

extern void jstring_to_string(JNIEnv *env,jstring jstr, char *buf, size_t size);
extern jstring class_static_field_string(JNIEnv* env,jclass class, const char *name);
extern jint class_static_field_int(JNIEnv* env,jclass class, const char *name);
extern void get_package_name(JNIEnv *env,jobject ctx,jclass _jclass_context,char* param, size_t size);
extern bool is_permission(JNIEnv *env,jobject ctx,jclass jclass_context,char* param);

#endif /* JNI_UTILS_H_ */
