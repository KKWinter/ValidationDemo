LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_LDLIBS :=-llog
LOCAL_MODULE := ndkdemo

#LOCAL_SRC_FILES := test.c str_utils.c libApp.c jni_utils.c
LOCAL_SRC_FILES := test.c
#LOCAL_SRC_FILES := tools.c

include $(BUILD_SHARED_LIBRARY)
#include $(BUILD_EXECUTABLE)

