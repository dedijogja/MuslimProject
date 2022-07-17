package info.didustudio.ceramahustadFawwazMatJan.fragment;


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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studiosatu.muslimproject.R;
import info.didustudio.ceramahustadFawwazMatJan.adapter.FavoritAdapter;
import info.didustudio.ceramahustadFawwazMatJan.model.KontenModel;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.Constant;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.DekorasiRecycleItem;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.ManajemenFavorit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FragmentFavorit extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private FavoritAdapter favoritAdapter;
    private List<KontenModel> kontenModels = new ArrayList<>();
    public FragmentFavorit() {

    }

    MyReceiver r;
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                FragmentFavorit.this.updateData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                new IntentFilter(Constant.kodeBroadcastDariList));
    }

    public void updateData() throws IOException {
        ArrayList<KontenModel> updateModel = new ArrayList<>();
        ManajemenFavorit manajemenFavorit = new ManajemenFavorit(context);
        for(int i = 0; i<manajemenFavorit.getDataFavorit().size(); i++){
            updateModel.add(Constant.models(context)[manajemenFavorit.getDataFavorit().get(i)]);
        }
        favoritAdapter.updateData(updateModel);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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

        ManajemenFavorit manajemenFavorit = new ManajemenFavorit(context);
        try {
            for(int i = 0; i<manajemenFavorit.getDataFavorit().size(); i++){
                try {
                    kontenModels.add(Constant.models(context)[manajemenFavorit.getDataFavorit().get(i)]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        favoritAdapter = new FavoritAdapter(context, kontenModels, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DekorasiRecycleItem(2, context));
        recyclerView.setAdapter(favoritAdapter);

        favoritAdapter.notifyDataSetChanged();

        return penampung;
    }

}