package info.didustudio.ceramahustadFawwazMatJan.utilitas;


import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManajemenFavorit {
    private Context context;
    private int kodeUnik = 999;
    public ManajemenFavorit(Context context){
        this.context = context;
    }

    public void simpanKeFavorit(int index, String kode){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt("index_"+index, index).apply();

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(context);
        Intent i = new Intent(kode);
        lbm.sendBroadcast(i);

    }

    public void hapusDariFavorit(int index, String kode){
        PreferenceManager.getDefaultSharedPreferences(context).edit().remove("index_"+index).apply();

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(context);
        Intent i = new Intent(kode);
        lbm.sendBroadcast(i);

    }

    public boolean dataBelumAda(int index){
        int hasil = PreferenceManager.getDefaultSharedPreferences(context).getInt("index_"+index, kodeUnik);
        return hasil == kodeUnik;
    }

    public List<Integer> getDataFavorit() throws IOException {
        List<Integer> semuaDataFavorit = new ArrayList<>();
        for(int i = 0; i< Constant.models(context).length; i++) {
            int data = PreferenceManager.getDefaultSharedPreferences(context).getInt("index_"+i, kodeUnik);
            if(data != kodeUnik){
                semuaDataFavorit.add(data);
            }
        }
        return semuaDataFavorit;
    }
}
