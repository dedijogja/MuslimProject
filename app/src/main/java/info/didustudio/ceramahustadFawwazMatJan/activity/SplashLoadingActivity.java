package info.didustudio.ceramahustadFawwazMatJan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.studiosatu.muslimproject.R;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.Constant;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.KelasInduk;

public class SplashLoadingActivity extends AppCompatActivity {
    private boolean statusIklan = true;
    int hitung = 0;
    int loadIklanBerapaKali = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_loading);

        final KelasInduk kelasInduk = (KelasInduk) getApplication();
        kelasInduk.initInterstitial();
        kelasInduk.loadIntersTitial();
        kelasInduk.getInterstitial().setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                hitung++;
                Log.d("iklan", "gagal "+String.valueOf(hitung));
                if(hitung<loadIklanBerapaKali){
                    if(statusIklan) {
                        kelasInduk.loadIntersTitial();
                    }
                }
                if(hitung == loadIklanBerapaKali){
                    if(statusIklan) {
                        statusIklan = false;
                        kelasInduk.setStatusIklan(Constant.gagalLoadIklan);
                        Intent intent = new Intent(SplashLoadingActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLoaded() {
                if(statusIklan) {
                    Log.d("iklan", "berhasil");
                    statusIklan = false;
                    kelasInduk.setStatusIklan(Constant.berhasilLoadIklan);
                    Intent intent = new Intent(SplashLoadingActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                }
                super.onAdLoaded();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(statusIklan) {
                    statusIklan = false;
                    kelasInduk.setStatusIklan(Constant.gagalLoadIklan);
                    Intent intent = new Intent(SplashLoadingActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 15000);


    }
}
