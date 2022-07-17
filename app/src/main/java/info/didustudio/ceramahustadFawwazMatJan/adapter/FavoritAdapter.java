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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.studiosatu.muslimproject.R;
import info.didustudio.ceramahustadFawwazMatJan.activity.PemutarSuaraActivity;
import info.didustudio.ceramahustadFawwazMatJan.fragment.FragmentFavorit;
import info.didustudio.ceramahustadFawwazMatJan.model.KontenModel;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.BuatTempFile;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.Constant;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.ManajemenFavorit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FavoritAdapter extends RecyclerView.Adapter<FavoritAdapter.BindTampilan>{

    private Context context;
    private List<KontenModel> kontenModel;

    //dibutuhkan oleh android 23 keatas
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private String[] permissionsRequired = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private SharedPreferences permissionStatus;
    private FragmentFavorit fragmentFavorit;

    public FavoritAdapter(Context mContext, List<KontenModel> listKonten, FragmentFavorit fragmentFavorit) {
        this.context = mContext;
        this.kontenModel = listKonten;
        this.fragmentFavorit = fragmentFavorit;
        permissionStatus = mContext.getSharedPreferences("permissionStatus", Context.MODE_PRIVATE);
    }

    private void hapusData(int position) {
        kontenModel.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, kontenModel.size());
    }


    public void updateData(ArrayList<KontenModel> viewModels) {
        kontenModel.clear();
        kontenModel.addAll(viewModels);
        notifyDataSetChanged();
    }

    @Override
    public BindTampilan onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.designlist, parent, false);


        return new BindTampilan(itemView);
    }


    @Override
    public void onBindViewHolder(final BindTampilan holder, final int position) {

        holder.penampunglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PemutarSuaraActivity.class);
                intent.putExtra(Constant.kodeNamaFile, kontenModel.get(position).getAlamatmp3());
                context.startActivity(intent);
            }
        });
        holder.btnFavorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManajemenFavorit manajemenFavorit = new ManajemenFavorit(context);
                try {
                    for(int i = 0; i< Constant.models(context).length; i++){
                        if(Constant.models(context)[i].getAlamatmp3().equals(kontenModel.get(position).getAlamatmp3())) {
                            manajemenFavorit.hapusDariFavorit(i, Constant.kodeBroadCastDariFavorit);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                hapusData(position);

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
                            builder.setMessage("Kami membutuhkan ijin dari anda untuk menulis di SD card dan mengubah pengaturan.");
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
                            builder.setMessage("Kami membutuhkan ijin dari anda untuk menulis di SD card dan menulis pengaturan.");
                            builder.setPositiveButton("Ijinkan", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                                    intent.setData(uri);
                                    activity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                    Toast.makeText(activity, "Buka pengaturan untuk memberikan ijin menulis SD Card dan menulis pengaturan", Toast.LENGTH_LONG).show();
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
                        try {
                            for(int i = 0; i<Constant.models(context).length; i++){
                                if(Constant.models(context)[i].getAlamatmp3().equals(kontenModel.get(position).getAlamatmp3())) {
                                    BuatTempFile downloader = new BuatTempFile(activity, Constant.models(context)[i].getAlamatmp3());
                                    downloader.startDownload();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }else {
                    try {
                        for(int i = 0; i<Constant.models(context).length; i++){
                            if(Constant.models(context)[i].getAlamatmp3().equals(kontenModel.get(position).getAlamatmp3())) {
                                BuatTempFile downloader = new BuatTempFile(activity, Constant.models(context)[i].getAlamatmp3());
                                downloader.startDownload();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

    class BindTampilan extends RecyclerView.ViewHolder {

        private CardView penampunglist;
        private TextView nUstad;
        private TextView jCeramah;
        private ImageView btnFavorit, btnShare;

        private BindTampilan(View view) {
            super(view);
            penampunglist = (CardView) view.findViewById(R.id.penampunglist);
            nUstad = (TextView) view.findViewById(R.id.nUstad);
            jCeramah = (TextView) view.findViewById(R.id.jCeramah);
            btnFavorit = (ImageView) view.findViewById(R.id.btn_favorit);
            btnShare = (ImageView) view.findViewById(R.id.btn_share);
        }
    }
}
