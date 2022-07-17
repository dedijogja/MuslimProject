package info.didustudio.ceramahustadFawwazMatJan.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.startapp.android.publish.ads.nativead.NativeAdDetails;
import com.startapp.android.publish.ads.nativead.NativeAdPreferences;
import com.startapp.android.publish.ads.nativead.StartAppNativeAd;
import com.startapp.android.publish.ads.splash.SplashConfig;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;
import com.studiosatu.muslimproject.R;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.BicaraKeNative;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.Constant;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.KelasInduk;

import java.util.ArrayList;
import java.util.Calendar;

public class MenuActivity extends AppCompatActivity{

    private ImageView btnOpenApp, btnTasbih, btnAsmaulHusna, btnNabiDanRasul, btnRateMe, btnAppLain;
    TextView jamSaatIni, hariTanggalSaatini;
    private LinearLayout iklanMedium;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final KelasInduk kelasInduk = (KelasInduk) getApplication();
        String status = kelasInduk.getStatusIklan();
        if(status.equals(Constant.gagalLoadIklan)) {
            StartAppSDK.init(this, new BicaraKeNative(this).getStartAppId(), false);
            if (!isNetworkConnected()) {
                StartAppAd.disableSplash();
            }else{
                StartAppAd.showSplash(this, savedInstanceState,
                    new SplashConfig()
                        .setTheme(SplashConfig.Theme.USER_DEFINED)
                        .setCustomScreen(R.layout.activity_splash_loading)
                );
            }
        }
        setContentView(R.layout.activity_menu);

        urusanIklan();

        btnOpenApp = (ImageView) findViewById(R.id.btnBukaApp);
        btnTasbih = (ImageView) findViewById(R.id.btnTasbis);
        btnAsmaulHusna = (ImageView) findViewById(R.id.btnAsmaulHusna);
        btnNabiDanRasul = (ImageView) findViewById(R.id.btnNabiDanRasull);
        btnRateMe = (ImageView) findViewById(R.id.btnRateMe);
        btnAppLain = (ImageView) findViewById(R.id.btnAppLain);

        jamSaatIni = (TextView) findViewById(R.id.jamSaatIni);
        hariTanggalSaatini = (TextView) findViewById(R.id.hariTanggalSaatIni);

        setKlikListener();
        urusanBagianAtas();
    }

    private void urusanIklan(){
        iklanMedium = (LinearLayout) findViewById(R.id.iklanBannerMedium);

        if(isNetworkConnected()) {
            final KelasInduk kelasInduk = (KelasInduk) getApplication();
            String status = kelasInduk.getStatusIklan();
            if (status.equals(Constant.berhasilLoadIklan)) {
                AdView bannerMedium = new AdView(this);
                bannerMedium.setAdSize(AdSize.MEDIUM_RECTANGLE);
                bannerMedium.setAdUnitId(new BicaraKeNative(this).getAdBanner());
                bannerMedium.loadAd(new AdRequest.Builder().build());
                iklanMedium.addView(bannerMedium);
            } else {
                LayoutInflater inflater = LayoutInflater.from(this);
                RelativeLayout layoutIklan = (RelativeLayout) inflater.inflate(R.layout.iklan_medium, null, false);

                final CardView penampung = (CardView) layoutIklan.findViewById(R.id.cardPenampungNativeMenu);
                penampung.setVisibility(View.GONE);
                final ImageView icon = (ImageView) layoutIklan.findViewById(R.id.iconNativeMenu);
                final TextView judulNative = (TextView)  layoutIklan.findViewById(R.id.textJudulNativeMenu);
                final RatingBar rating = (RatingBar) layoutIklan.findViewById(R.id.ratingNativeMenu);
                final TextView deskripsi = (TextView) layoutIklan.findViewById(R.id.textDeskripsiNativeMenu);
                final TextView jumlahInstal = (TextView) layoutIklan.findViewById(R.id.textJumlahInstalNativeMenu);
                final Button tombolInstal = (Button) layoutIklan.findViewById(R.id.tombolInstalNativeMenu);

                iklanMedium.addView(layoutIklan);
                final StartAppNativeAd startAppNativeAd = new StartAppNativeAd(this);
                final NativeAdDetails[] nativeAd = {null};

                AdEventListener nativeAdListener = new AdEventListener() {
                    @Override
                    public void onReceiveAd(Ad ad) {
                        ArrayList<NativeAdDetails> nativeAdsList = startAppNativeAd.getNativeAds();
                        if (nativeAdsList.size() > 0){
                            nativeAd[0] = nativeAdsList.get(0);
                        }
                        if (nativeAd[0] != null){
                            nativeAd[0].sendImpression(MenuActivity.this);
                            icon.setImageBitmap(nativeAd[0].getImageBitmap());
                            judulNative.setText(nativeAd[0].getTitle());
                            jumlahInstal.setText("Free !");
                            if(nativeAd[0].getInstalls() != null) {
                                jumlahInstal.setText(nativeAd[0].getInstalls() + " instal");
                            }
                            rating.setRating(nativeAd[0].getRating());
                            deskripsi.setText(nativeAd[0].getDescription());
                            tombolInstal.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    nativeAd[0].sendClick(context);
                                }
                            });
                            penampung.setVisibility(View.VISIBLE);
                        }
                    }
                    @Override
                    public void onFailedToReceiveAd(Ad ad) {

                    }
                };
                startAppNativeAd.loadAd(
                        new NativeAdPreferences()
                                .setAdsNumber(1)
                                .setAutoBitmapDownload(true)
                                .setImageSize(NativeAdPreferences
                                        .NativeAdBitmapSize.SIZE340X340),
                        nativeAdListener);
            }
        }else{
            ImageView imageView = new ImageView(this);
            imageView.setMaxWidth(300);
            imageView.setMaxHeight(250);
            imageView.setImageResource(R.drawable.didu);
            iklanMedium.addView(imageView);
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }



    private String konversiHarikeString(int hari){
        if(hari == 1){
            return "Minggu";
        }else if(hari == 2){
            return "Senin";
        }else if(hari == 3){
            return "Selasa";
        }else if(hari == 4){
            return "Rabu";
        }else if(hari == 5){
            return "Kamis";
        }else if(hari == 6){
            return "Jum'at";
        }else{
            return "Sabtu";
        }
    }

    private String konversiBulankeString(int bulan){
        if(bulan == 0){
            return "Januari";
        }else if(bulan == 1){
            return "Februari";
        }else if(bulan == 2){
            return "Maret";
        }else if(bulan == 3){
            return "April";
        }else if(bulan == 4){
            return "Mei";
        }else if(bulan == 5){
            return "Juni";
        }else if(bulan == 6) {
            return "Juli";
        }else if(bulan == 7) {
            return "Agustus";
        }else if(bulan == 8) {
            return "September";
        }else if(bulan == 9) {
            return "Oktober";
        }else if(bulan == 10) {
            return "November";
        }else{
            return "Desember";
        }
    }

    private void urusanBagianAtas(){
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            Calendar c = Calendar.getInstance();
                            String jam;
                            if(c.get(Calendar.HOUR_OF_DAY)>9) {
                                jam = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
                            }else{
                                jam = "0"+String.valueOf(c.get(Calendar.HOUR_OF_DAY));
                            }
                            String menit;
                            if(c.get(Calendar.MINUTE)>9) {
                                menit = String.valueOf(c.get(Calendar.MINUTE));
                            }else{
                                menit = "0"+String.valueOf(c.get(Calendar.MINUTE));
                            }
                            String detik;
                            if(c.get(Calendar.SECOND)>9) {
                                detik = String.valueOf(c.get(Calendar.SECOND));
                            }else{
                                detik = "0"+String.valueOf(c.get(Calendar.SECOND));
                            }

                            jamSaatIni.setText(jam + " : "+ menit + " : " + detik);

                            String hari = konversiHarikeString(c.get(Calendar.DAY_OF_WEEK));
                            String tanggal = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
                            String bulan = konversiBulankeString(c.get(Calendar.MONTH));
                            String tahun = String.valueOf(c.get(Calendar.YEAR));
                            hariTanggalSaatini.setText(hari + ", " + tanggal + " " + bulan + " " + tahun);
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException ignored) {
                }
            }
        };
        t.start();
    }

    private void setKlikListener(){
        btnOpenApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnTasbih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, TasbihActivity.class);
                startActivity(intent);
            }
        });

        btnAsmaulHusna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, WebviewActivity.class);
                intent.putExtra(Constant.kodeWebview, Constant.kodeWebviewAsmaulHusna);
                startActivity(intent);
            }
        });

        btnNabiDanRasul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, WebviewActivity.class);
                intent.putExtra(Constant.kodeWebview, Constant.kodeWebviewNabiRasul);
                startActivity(intent);
            }
        });

        btnRateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=" + getApplicationContext().getPackageName()));
                    startActivity(intent);
                }catch (Exception e){
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://play.google.com/store/apps/details?id="+ getApplicationContext().getPackageName()));
                    startActivity(i);
                }
            }
        });

        btnAppLain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://search?q=pub:"+ Constant.developerID));
                    startActivity(intent);
                }catch (Exception e){
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://play.google.com/store/apps/developer?id="+ Constant.developerID));
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(context)
                .setTitle("Konfirmasi Keluar")
                .setMessage("Anda menyukai aplikasi ini? Jangan lupa memberi kami bintang 5 di Playstore!")
                .setPositiveButton("Rate Me!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("market://details?id="+getApplicationContext().getPackageName()));
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Terimakasih", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                            i.setData(Uri.parse("https://play.google.com/store/apps/details?id="+ getApplicationContext().getPackageName()));
                            startActivity(i);
                        }
                    }
                })

                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .setNeutralButton("Tutup App", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
