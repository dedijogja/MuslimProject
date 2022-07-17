package info.didustudio.ceramahustadFawwazMatJan.utilitas;

import android.content.Context;
import android.util.Log;

import info.didustudio.ceramahustadFawwazMatJan.pembaca.text.deskriptor.DeskripsiText;

public class BicaraKeNative {
    static {
        System.loadLibrary("native-lib");
    }

    public BicaraKeNative(Context context){
        if(!context.getPackageName().equals(new DeskripsiText(keyDesText(), packageName()).dapatkanTextAsli())){
            throw new RuntimeException(new DeskripsiText(keyDesText(), smesek()).dapatkanTextAsli());
        }
    }

    public String getKeyAssets() {
        return keyDesAssets();
    }

    public String getAdBanner() {
        Log.d("enskripsi getAdBanner", new DeskripsiText(keyDesText(), adBanner()).dapatkanTextAsli());
        return new DeskripsiText(keyDesText(), adBanner()).dapatkanTextAsli();
    }

    public String getAdInterstitial() {
        Log.d("enskripsi getAdInter", new DeskripsiText(keyDesText(), adInterstitial()).dapatkanTextAsli());
        return new DeskripsiText(keyDesText(), adInterstitial()).dapatkanTextAsli();
    }

    public String getAdNativeSmall() {
        Log.d("enskripsi nativeSmall", new DeskripsiText(keyDesText(), adNativeSmall()).dapatkanTextAsli());
        return new DeskripsiText(keyDesText(), adNativeSmall()).dapatkanTextAsli();
    }

    public String getAdNativeMedium() {
        Log.d("enskripsi nativeMedium", new DeskripsiText(keyDesText(), adNativeMedium()).dapatkanTextAsli());
        return new DeskripsiText(keyDesText(), adNativeMedium()).dapatkanTextAsli();
    }

    public String getStartAppId() {
        Log.d("enskripsi startAppId", new DeskripsiText(keyDesText(), startAppId()).dapatkanTextAsli());
        return new DeskripsiText(keyDesText(), startAppId()).dapatkanTextAsli();
    }

    public native String packageName();
    public native String keyDesText();
    public native String keyDesAssets();
    public native String adBanner();
    public native String adInterstitial();
    public native String adNativeSmall();
    public native String adNativeMedium();
    public native String startAppId();
    public native String smesek();
}
