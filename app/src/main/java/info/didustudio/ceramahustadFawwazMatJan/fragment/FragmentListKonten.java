package info.didustudio.ceramahustadFawwazMatJan.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.clockbyte.admobadapter.expressads.AdmobExpressRecyclerAdapterWrapper;
import com.google.android.gms.ads.AdSize;
import com.startapp.android.publish.ads.banner.Banner;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.BicaraKeNative;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.Constant;
import info.didustudio.ceramahustadFawwazMatJan.model.KontenModel;
import info.didustudio.ceramahustadFawwazMatJan.adapter.ListAdapter;
import com.studiosatu.muslimproject.R;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.DekorasiRecycleItem;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.KelasInduk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentListKonten extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private List<KontenModel> kontenModels = new ArrayList<>();
    public FragmentListKonten() {
    }

    MyReceiver r;
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            FragmentListKonten.this.updateBintang();
        }
    }

    private void updateBintang(){
        listAdapter.updateBintang();
    }

    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(r);
    }

    @Override
    public void onResume() {
        super.onResume();
        r = new MyReceiver();
        LocalBroadcastManager.getInstance(context).registerReceiver(r,
                new IntentFilter(Constant.kodeBroadCastDariFavorit));
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = container.getContext();
        View penampung = inflater.inflate(R.layout.fragment_list_content, container, false);

        recyclerView = (RecyclerView) penampung.findViewById(R.id.listview);

        try {
            Collections.addAll(kontenModels, Constant.models(context));
        } catch (IOException e) {
            e.printStackTrace();
        }
        listAdapter = new ListAdapter(container.getContext(), kontenModels);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DekorasiRecycleItem(2, context));

        urusanIklan(penampung);
        listAdapter.notifyDataSetChanged();

        return penampung;
    }

    private void urusanIklan(View view){
        Activity activity = (Activity) context;
        final KelasInduk kelasInduk = (KelasInduk) activity.getApplication();
        String status = kelasInduk.getStatusIklan();
        if(status.equals(Constant.berhasilLoadIklan)) {
            DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            int width = (int) dpWidth;
            AdmobExpressRecyclerAdapterWrapper adapterWrapper = new AdmobExpressRecyclerAdapterWrapper(context,
                    new BicaraKeNative(context).getAdNativeSmall(),
                    new AdSize(width - 10, 132));
            adapterWrapper.setAdapter(listAdapter);
            adapterWrapper.setLimitOfAds(3);
            adapterWrapper.setNoOfDataBetweenAds(8);
            adapterWrapper.setFirstAdIndex(0);
            recyclerView.setAdapter(adapterWrapper);
        }else {
            recyclerView.setAdapter(listAdapter);
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.iklanBannerKecil);
            linearLayout.setVisibility(View.VISIBLE);
            Banner startAppBanner = new Banner(context);
            linearLayout.addView(startAppBanner);

        }
    }


}
