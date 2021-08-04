//
// Created by Shubham Patel on 04/08/21.
//
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_dev_shubhampatel_tmdb_APIKeyLibrary_getAPIKey(JNIEnv* env, jobject /* this */) {
    std::string api_key = "YOUR_AWESOME_API_KEY_GOES_HERE";
    return env->NewStringUTF(api_key.c_str());
}