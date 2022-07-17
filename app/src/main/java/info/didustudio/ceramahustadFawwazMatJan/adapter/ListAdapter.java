package info.didustudio.ceramahustadFawwazMatJan.adapter;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clockbyte.admobadapter.ViewWrapper;
import com.google.android.gms.ads.AdListener;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.studiosatu.muslimproject.R;
import info.didustudio.ceramahustadFawwazMatJan.activity.PemutarSuaraActivity;
import info.didustudio.ceramahustadFawwazMatJan.model.KontenModel;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.BuatTempFile;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.Constant;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.KelasInduk;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.ManajemenFavorit;

import java.io.IOException;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<KontenModel> kontenModel;

    //android permission
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private String[] permissionsRequired = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private SharedPreferences permissionStatus;

    public ListAdapter(Context context, List<KontenModel> kontenModel) {
        this.context = context;
        this.kontenModel = kontenModel;
    }

    public void updateBintang() {
        notifyDataSetChanged();
    }


    @Override
    public ViewWrapper<RecycleWrapperTiapItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(new RecycleWrapperTiapItem(context));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holders, final int position) {
        final RecycleWrapperTiapItem holder = (RecycleWrapperTiapItem) holders.itemView;
        ManajemenFavorit manajemenFavorit = new ManajemenFavorit(context);
        if(manajemenFavorit.dataBelumAda(position)){
            holder.btnFavorit.setImageResource(R.drawable.staroff);
        }else {
            holder.btnFavorit.setImageResource(R.drawable.staron);
        }

        holder.penampunglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                final KelasInduk kelasInduk = (KelasInduk) activity.getApplication();
                String status = kelasInduk.getStatusIklan();

                if(status.equals(Constant.berhasilLoadIklan)) {
                    if (!kelasInduk.isBolehMenampilkanIklanHitung() || !kelasInduk.isBolehMenampilkanIklanWaktu()
                            || !kelasInduk.getInterstitial().isLoaded()) {
                        Intent intent = new Intent(context, PemutarSuaraActivity.class);
                        intent.putExtra(Constant.kodeNamaFile, kontenModel.get(position).getAlamatmp3());
                        context.startActivity(intent);
                    }
                    kelasInduk.getInterstitial().setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent intent = new Intent(context, PemutarSuaraActivity.class);
                            intent.putExtra(Constant.kodeNamaFile, kontenModel.get(position).getAlamatmp3());
                            context.startActivity(intent);

                            kelasInduk.loadIntersTitial();
                            super.onAdClosed();
                        }
                        @Override
                        public void onAdFailedToLoad(int i) {
                            if(kelasInduk.getHitungFailed() < 2 ) {
                                kelasInduk.loadIntersTitial();
                                kelasInduk.setHitungFailed();
                            }
                            super.onAdFailedToLoad(i);
                        }

                        @Override
                        public void onAdLoaded() {
                            kelasInduk.setHitungFailed(0);
                            super.onAdLoaded();
                        }
                    });
                    kelasInduk.tampilkanInterstitial();
                }else{
                    if(kelasInduk.getPenghitungStartApp() == 0) {
                        Log.d("iklan", "startApp inters tampil " + kelasInduk.getPenghitungStartApp());
                        kelasInduk.setPenghitungStartApp(1);
                        Intent intent = new Intent(context, PemutarSuaraActivity.class);
                        intent.putExtra(Constant.kodeNamaFile, kontenModel.get(position).getAlamatmp3());
                        context.startActivity(intent);
                        StartAppAd.showAd(context);
                    }else{
                        Log.d("iklan", "startApp tidak tampil " + kelasInduk.getPenghitungStartApp());
                        kelasInduk.setPenghitungStartApp(0);
                        Intent intent = new Intent(context, PemutarSuaraActivity.class);
                        intent.putExtra(Constant.kodeNamaFile, kontenModel.get(position).getAlamatmp3());
                        context.startActivity(intent);
                    }
                }
                
            }
        });
        holder.btnFavorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManajemenFavorit manajemenFavorit = new ManajemenFavorit(context);
                if(manajemenFavorit.dataBelumAda(position)){
                    manajemenFavorit.simpanKeFavorit(position, Constant.kodeBroadcastDariList);
                    holder.btnFavorit.setImageResource(R.drawable.staron);
                }else {
                    manajemenFavorit.hapusDariFavorit(position, Constant.kodeBroadcastDariList);
                    holder.btnFavorit.setImageResource(R.drawable.staroff);
                }
            }
        });
        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Activity activity = (Activity) context;
                if (Build.VERSION.SDK_INT >= 23) {
                    if(ActivityCompat.checkSelfPermission(activity, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED){
                        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionsRequired[0])){
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setTitle("Membutuhkan ijin");
                            builder.setMessage("Kami membutuhkan ijin dari anda untuk menulis di SD card");
                            builder.setPositiveButton("Ijinkan", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    ActivityCompat.requestPermissions(activity, permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                                }
                            });
                            builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        } else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setTitle("Membutuhkan ijin");
                            builder.setMessage("Kami membutuhkan ijin dari anda untuk menulis di SD card");
                            builder.setPositiveButton("Ijinkan", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                                    intent.setData(uri);
                                    activity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                    Toast.makeText(activity, "Buka pengaturan dan ijinkan aplikasi ini untuk menulis dan membaca di SD card anda", Toast.LENGTH_LONG).show();
                                }
                            });
                            builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        }  else {
                            ActivityCompat.requestPermissions(activity, permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
                        }

                        SharedPreferences.Editor editor = permissionStatus.edit();
                        editor.putBoolean(permissionsRequired[0],true);
                        editor.apply();
                    } else {
                        BuatTempFile downloader = null;
                        try {
                            downloader = new BuatTempFile(activity, Constant.models(context)[position].getAlamatmp3());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        downloader.startDownload();
                    }
                }else {
                    BuatTempFile downloader = null;
                    try {
                        downloader = new BuatTempFile(activity, Constant.models(context)[position].getAlamatmp3());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    downloader.startDownload();
                }
            }
        });
        holder.nUstad.setText(kontenModel.get(position).getnUstad());
        holder.jCeramah.setText(kontenModel.get(position).getjCeramah());

    }

    @Override
    public int getItemCount() {
        return kontenModel.size();
    }

    class RecycleWrapperTiapItem extends FrameLayout{

        private CardView penampunglist;
        private TextView nUstad;
        private TextView jCeramah;
        private ImageView btnFavorit, btnShare;

        public RecycleWrapperTiapItem(Context context) {
            super(context);
            inflate(context, R.layout.designlist, this);
            penampunglist = (CardView) findViewById(R.id.penampunglist);
            nUstad = (TextView) findViewById(R.id.nUstad);
            jCeramah = (TextView) findViewById(R.id.jCeramah);
            btnFavorit = (ImageView) findViewById(R.id.btn_favorit);
            btnShare = (ImageView) findViewById(R.id.btn_share);
        }
    }
}
