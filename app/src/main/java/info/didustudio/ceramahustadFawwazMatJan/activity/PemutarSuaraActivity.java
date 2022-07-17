package info.didustudio.ceramahustadFawwazMatJan.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.startapp.android.publish.ads.banner.Banner;
import com.studiosatu.muslimproject.R;
import info.didustudio.ceramahustadFawwazMatJan.adapter.RandomAdapter;
import info.didustudio.ceramahustadFawwazMatJan.model.KontenModel;
import info.didustudio.ceramahustadFawwazMatJan.pembaca.assets.DeskripsiAsset;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.BicaraKeNative;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.Constant;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.DekorasiRecycleItem;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.KelasInduk;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PemutarSuaraActivity extends AppCompatActivity {

    private ImageView tombolKembali, tombolPrev, tombolPlayPause, tombolNext, tombolTigaTitik;
    private TextView namaUstad, judulCeramah, waktuSaatIni, panjangWaktuMusik;
    private String alamatMp3SaatIni;
    private Context context = this;

    //mediaplayer
    private MediaPlayer mediaPlayer =  new MediaPlayer();
    private SeekBar seekBar;
    private final Handler handler = new Handler();

    //area recycle
    RecyclerView recyclerView;
    private List<KontenModel> kontenModels = new ArrayList<>();
    int indexSaatIni = 0;
    RandomAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemutar_suara);

        alamatMp3SaatIni = getIntent().getStringExtra(Constant.kodeNamaFile);

        tombolKembali = (ImageView) findViewById(R.id.tombolKembali);
        tombolPrev = (ImageView) findViewById(R.id.tombolPrev);
        tombolPlayPause = (ImageView) findViewById(R.id.tombolPlayPause);
        tombolNext = (ImageView) findViewById(R.id.tombolNext);

        namaUstad = (TextView) findViewById(R.id.namaUstad);
        judulCeramah = (TextView) findViewById(R.id.judulCeramah);
        waktuSaatIni = (TextView) findViewById(R.id.waktuSaatIni);
        panjangWaktuMusik = (TextView) findViewById(R.id.pajangWaktuMusik);
        tombolTigaTitik = (ImageView) findViewById(R.id.tombolTigaTitik);
        tombolTigaTitik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tampilkanMenu(v);
            }
        });


        seekBar = (SeekBar) findViewById(R.id.seekbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycleRandom);

        List<Integer> dataIndex = null;
        try {
            dataIndex = dapatkanRandomData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i<dataIndex.size(); i++){
            try {
                kontenModels.add(Constant.models(this)[dataIndex.get(i)]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        listAdapter = new RandomAdapter(context, kontenModels);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(context) {
                    private static final float SPEED = 300f;
                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DekorasiRecycleItem(2, this));
        urusanIklan();
        listAdapter.notifyDataSetChanged();

        setKlikListener();

        putarMp3();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(1);
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                tombolPlayPause.setImageResource(R.drawable.play);
            }
        });


        seekBar.setPadding(0, 0, 0, 0);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnTouchListener(new View.OnTouchListener() {@Override public boolean onTouch(View v, MotionEvent event) {
            seekChange(v);
            return false; }
        });


    }

    private void tampilkanMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_app, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_home:
                        startActivity(new Intent(PemutarSuaraActivity.this, MenuActivity.class));
                        finish();
                        return true;
                    case R.id.menu_asmaul_husna:
                        Intent intent = new Intent(PemutarSuaraActivity.this, WebviewActivity.class);
                        intent.putExtra(Constant.kodeWebview, Constant.kodeWebviewAsmaulHusna);
                        startActivity(intent);
                        return true;
                    case R.id.menu_nabi:
                        Intent intents = new Intent(PemutarSuaraActivity.this, WebviewActivity.class);
                        intents.putExtra(Constant.kodeWebview, Constant.kodeWebviewNabiRasul);
                        startActivity(intents);
                        return true;
                    case R.id.menu_tasbih:
                        startActivity(new Intent(PemutarSuaraActivity.this, TasbihActivity.class));
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
        popup.show();
    }


    private void urusanIklan() {
        final KelasInduk kelasInduk = (KelasInduk) getApplication();
        String status = kelasInduk.getStatusIklan();
        recyclerView.setAdapter(listAdapter);
        if (!status.equals(Constant.berhasilLoadIklan)) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.iklanBannerKecilPlayer);
            linearLayout.setVisibility(View.VISIBLE);
            Banner startAppBanner = new Banner(context);
            linearLayout.addView(startAppBanner);
        }
    }


    private int dapatkanIndexMp3(String namaFile) throws IOException {
        for(int i = 0; i<Constant.models(this).length; i++){
            if(namaFile.equals(Constant.models(this)[i].getAlamatmp3())){
                return i;
            }
        }
        return 0;
    }

    private List<Integer> dapatkanRandomData() throws IOException {
        List<Integer> listTemp = new ArrayList<>();
        int maxItem = 5;
        if(Constant.models(this).length <= maxItem){
           maxItem = Constant.models(this).length;
        }

        int indexSaatIni = dapatkanIndexMp3(alamatMp3SaatIni);
        for(int i = indexSaatIni; i<indexSaatIni+maxItem; i++){
            int index;
            if(i>Constant.models(this).length-1){
                index = i - Constant.models(this).length;
            }else{
                index = i;
            }
            listTemp.add(index);
        }
        return listTemp;
    }

    @Override
    public void onBackPressed() {
        mediaPlayer.pause();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    private void persiapanDekrip(String namaFile){
        try {
            File tempMp3 = File.createTempFile("temp", "mp3", context.getCacheDir());
            tempMp3.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempMp3);
            fos.write(new DeskripsiAsset(context, new BicaraKeNative(this).getKeyAssets()).getByte(namaFile));
            fos.close();

            mediaPlayer.reset();

            FileInputStream fis = new FileInputStream(tempMp3);
            mediaPlayer.setDataSource(fis.getFD());

            mediaPlayer.prepare();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void seekChange(View v){
        if(mediaPlayer.isPlaying()){
            SeekBar sb = (SeekBar)v;
            mediaPlayer.seekTo(sb.getProgress());
            int waktuSekarang = mediaPlayer.getCurrentPosition()/1000;
            int sekaranghours = waktuSekarang / 3600;
            int sekarangminutes = (waktuSekarang % 3600) / 60;
            int sekarangseconds = waktuSekarang % 60;
            String sekarang = String.format("%02d:%02d:%02d", sekaranghours, sekarangminutes, sekarangseconds);
            waktuSaatIni.setText(sekarang);
        }
    }

    Runnable notification;
    private void startPlayProgressUpdater() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if (mediaPlayer.isPlaying()) {
            notification = new Runnable() {
                public void run() {
                    int totalWaktu = mediaPlayer.getDuration()/1000;
                    int totalhours = totalWaktu / 3600;
                    int totalminutes = (totalWaktu % 3600) / 60;
                    int totalseconds = totalWaktu % 60;
                    String panjang = String.format("%02d:%02d:%02d", totalhours, totalminutes, totalseconds);
                    int waktuSekarang = mediaPlayer.getCurrentPosition()/1000;
                    int sekaranghours = waktuSekarang / 3600;
                    int sekarangminutes = (waktuSekarang % 3600) / 60;
                    int sekarangseconds = waktuSekarang % 60;
                    String sekarang = String.format("%02d:%02d:%02d", sekaranghours, sekarangminutes, sekarangseconds);
                    panjangWaktuMusik.setText(panjang);
                    waktuSaatIni.setText(sekarang);
                    startPlayProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1000);
        }else{
            mediaPlayer.pause();
        }
    }



    private void setKlikListener(){
        tombolKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tombolPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    tombolPlayPause.setImageResource(R.drawable.play);
                    mediaPlayer.pause();
                }else{
                    tombolPlayPause.setImageResource(R.drawable.pause);
                    mediaPlayer.seekTo(seekBar.getProgress());
                    mediaPlayer.start();
                }
                startPlayProgressUpdater();
            }
        });

        tombolPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(indexSaatIni == 0){
                    indexSaatIni = kontenModels.size()-1;
                    putarMp3();
                }else{
                    indexSaatIni--;
                    putarMp3();
                }
            }
        });

        tombolNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(indexSaatIni == kontenModels.size()-1){
                    indexSaatIni = 0;
                    putarMp3();
                }else{
                    indexSaatIni++;
                    putarMp3();
                }
            }
        });
    }

    private void putarMp3(){
        tombolPlayPause.setImageResource(R.drawable.pause);

        persiapanDekrip(kontenModels.get(indexSaatIni).getAlamatmp3());
        mediaPlayer.start();
        startPlayProgressUpdater();

        judulCeramah.setText(kontenModels.get(indexSaatIni).getjCeramah());
        namaUstad.setText(kontenModels.get(indexSaatIni).getnUstad());

        listAdapter.beriTahuAdapterAdaYangBerubah(indexSaatIni);
        Activity activity = (Activity) context;
        final KelasInduk kelasInduk = (KelasInduk) activity.getApplication();
        String status = kelasInduk.getStatusIklan();
        if (status.equals(Constant.berhasilLoadIklan)) {
            recyclerView.smoothScrollToPosition(indexSaatIni+1);
        }else{
            recyclerView.smoothScrollToPosition(indexSaatIni);
        }
    }

    public void putarMp3(int posisi){
        tombolPlayPause.setImageResource(R.drawable.pause);
        indexSaatIni = posisi;

        persiapanDekrip(kontenModels.get(indexSaatIni).getAlamatmp3());
        mediaPlayer.start();
        startPlayProgressUpdater();

        judulCeramah.setText(kontenModels.get(indexSaatIni).getjCeramah());
        namaUstad.setText(kontenModels.get(indexSaatIni).getnUstad());

        listAdapter.beriTahuAdapterAdaYangBerubah(indexSaatIni);
    }

}
