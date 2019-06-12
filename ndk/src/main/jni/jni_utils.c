#include "jni_utils.h"

void jstring_to_string(JNIEnv *env,jstring jstr, char *buf, size_t size) {

	if (jstr != NULL) {
		const char* id = (*env)->GetStringUTFChars(env, jstr, NULL);
		int len=strlen(id);
		char* a = "unknown";
		char* b = "Unknown";
		char* c = "<unknown ssid>";
		if (str_is_empty(id)==1 || strcmp(id, buf) == 0 ||strcmp(id, a)==0 || strcmp(id, b)==0 || strcmp(id, c)==0) {
			strncpy(buf, "\0", 1);
		}else{
			strncpy(buf, id, size > len ? size : len);
		}
		(*env)->ReleaseStringUTFChars(env, jstr, id); //释放
	}
}

jstring class_static_field_string(JNIEnv* env,jclass class, const char *name) {
	jfieldID field = (*env)->GetStaticFieldID(env, class, name, "Ljava/lang/String;");
	jstring str = (*env)->GetStaticObjectField(env, class, field);
	return str;
}

jint class_static_field_int(JNIEnv* env,jclass class, const char *name) {
	jfieldID field = (*env)->GetStaticFieldID(env, class, name, "I");
	jint value = (*env)->GetStaticIntField(env, class, field);
	return value;
}
/*
 * 获得当前应用的包名
 */
void get_package_name(JNIEnv *env,jobject ctx,jclass _jclass_context,char* param, size_t size) {
	jmethodID jmethod_package_name = (*env)->GetMethodID(env, _jclass_context,"getPackageName", "()Ljava/lang/String;");
	jstring jstring_application_package = (*env)->CallObjectMethod(env, ctx,jmethod_package_name);
	if (jstring_application_package != NULL) {
		jstring_to_string(env,jstring_application_package,param, size);
		(*env)->DeleteLocalRef(env, jstring_application_package);
	}
}

/**
 * 判断指定权限是否存在
 */
bool is_permission(JNIEnv *env,jobject ctx,jclass jclass_context,char* param){
	char package_name[40]={0};
	get_package_name(env, ctx,jclass_context,package_name, 40);
	jmethodID jmethod_package_manager = (*env)->GetMethodID(env, jclass_context, "getPackageManager", "()Landroid/content/pm/PackageManager;");
	jobject _jobject_package_manager = (*env)->CallObjectMethod(env, ctx, jmethod_package_manager);
	jclass _jclass_package_manager = (*env)->GetObjectClass(env, _jobject_package_manager);
	jmethodID _jmethod_check_permission = (*env)->GetMethodID(env, _jclass_package_manager, "checkPermission", "(Ljava/lang/String;Ljava/lang/String;)I");
	jstring jparam=(*env)->NewStringUTF(env, param);
	jstring jpackage_name=(*env)->NewStringUTF(env, package_name);
	jint jint_check_permission = (*env)->CallIntMethod(env, _jobject_package_manager, _jmethod_check_permission,jparam,jpackage_name);
	(*env)->DeleteLocalRef(env, _jobject_package_manager);
	(*env)->DeleteLocalRef(env, _jclass_package_manager);
	(*env)->DeleteLocalRef(env, jparam);
	(*env)->DeleteLocalRef(env, jpackage_name);
	if(jint_check_permission==-1)return false;
	return true;
}
