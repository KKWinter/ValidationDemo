LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := ndkdemo
LOCAL_SRC_FILES := test.c libApo.c jni_utils.c strutils.c
include $(BUILD_SHARED_LIBRARY)

