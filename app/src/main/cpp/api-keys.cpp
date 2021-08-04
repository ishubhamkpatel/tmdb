//
// Created by Shubham Patel on 04/08/21.
//
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_dev_shubhampatel_tmdb_utility_AppSecrets_getAuthToken(JNIEnv* env, jobject /* this */) {
    return (*env).NewStringUTF("Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhYjcyNGFhMzVmZWE5MDZmM2UxMmE0OGQ2NjE4MTk3MCIsInN1YiI6IjYxMDljYjg4ZGIxNTRmMDA1ZDNhMjNkMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.RwAK8LX_-bEIN1IbAwYnA6vmTlDCEsMXhy2J75dWsYY");
}

extern "C" JNIEXPORT jstring JNICALL
Java_dev_shubhampatel_tmdb_utility_AppSecrets_getApiKey(JNIEnv* env, jobject /* this */) {
return (*env).NewStringUTF("ab724aa35fea906f3e12a48d66181970");
}