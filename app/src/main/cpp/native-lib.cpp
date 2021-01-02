//
// Created by bpiba on 06/12/2020.
//
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_baguspribadi_cookowl_RecipeListFragment_helloWithJni(
        JNIEnv *env, jobject thiz, jstring words) {

    const char *nativeString;

    nativeString = (*env).GetStringUTFChars(words, NULL);

    int counter = 1, i;

    for (i = 0;nativeString[i] != '\0';i++)
    {
        if (nativeString[i] == ' ' && nativeString[i+1] != ' ')
            counter++;
    }

    char resChar [100];
    strcat(resChar, "Welcome ");
    strcat(resChar, nativeString);
    strcat(resChar, "!");
    std::string returnResult = resChar;

    return env->NewStringUTF(returnResult.c_str());
}