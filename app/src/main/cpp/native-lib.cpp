#include <jni.h>
#include <string>

std::string keyDesText = "%4&hu&T0fL}o[vTH";
std::string keyDesAssets = "jO7ClKzvYm9ixQeG7e3+nA==";

std::string packageName = "O>hP!D}gUSIIj][2CUtF/AjOSTAJgAWygc sYlF,";
std::string adBanner       = "VFqSaPE]UBW)qw9Q)*9%7#2$@!#3^)*w60+5(_";
std::string adInterstitial = "VFqSaPE]UBW)qw9Q)*9%7#2$@!#3&%%-w4%7(%";
std::string adNativeSmall =  "VFqSaPE]UBW)qw9Q)*9%7#2$@!#3$$^7q7*5(#";
std::string adNativeMedium = "VFqSaPE]UBW)qw9Q)*9%7#2$@!#3(*!680*-(%";
std::string startAppId = "#Q5^-%Q9&";

std::string smesek = "tUyPi`IoNT{<u#tNCU[I[Om4";

extern "C"
JNIEXPORT jstring JNICALL
Java_info_didustudio_ceramahustadFawwazMatJan_utilitas_BicaraKeNative_keyDesText(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(keyDesText.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_info_didustudio_ceramahustadFawwazMatJan_utilitas_BicaraKeNative_keyDesAssets(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(keyDesAssets.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_info_didustudio_ceramahustadFawwazMatJan_utilitas_BicaraKeNative_packageName(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(packageName.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_info_didustudio_ceramahustadFawwazMatJan_utilitas_BicaraKeNative_adBanner(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(adBanner.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_info_didustudio_ceramahustadFawwazMatJan_utilitas_BicaraKeNative_adInterstitial(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(adInterstitial.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_info_didustudio_ceramahustadFawwazMatJan_utilitas_BicaraKeNative_adNativeSmall(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(adNativeSmall.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_info_didustudio_ceramahustadFawwazMatJan_utilitas_BicaraKeNative_adNativeMedium(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(adNativeMedium.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_info_didustudio_ceramahustadFawwazMatJan_utilitas_BicaraKeNative_startAppId(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(startAppId.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_info_didustudio_ceramahustadFawwazMatJan_utilitas_BicaraKeNative_smesek(
        JNIEnv *env,
        jobject ) {
    return env->NewStringUTF(smesek.c_str());
}