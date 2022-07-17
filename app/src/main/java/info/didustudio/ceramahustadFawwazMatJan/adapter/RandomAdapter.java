package info.didustudio.ceramahustadFawwazMatJan.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.studiosatu.muslimproject.R;
import info.didustudio.ceramahustadFawwazMatJan.activity.PemutarSuaraActivity;
import info.didustudio.ceramahustadFawwazMatJan.model.KontenModel;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.BicaraKeNative;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.Constant;
import info.didustudio.ceramahustadFawwazMatJan.utilitas.KelasInduk;

import java.util.List;

public class RandomAdapter extends RecyclerView.Adapter<RandomAdapter.BindTampilan>{

    private Context context;
    private List<KontenModel> kontenModel;

    private int posisiSedangDiputar = 0;
    public void beriTahuAdapterAdaYangBerubah(int posisi){
        Activity activity = (Activity) context;
        final KelasInduk kelasInduk = (KelasInduk) activity.getApplication();
        String status = kelasInduk.getStatusIklan();
        if (status.equals(Constant.berhasilLoadIklan)) {
            posisi++;
        }
        notifyItemChanged(posisi);
        notifyItemChanged(posisiSedangDiputar);
        posisiSedangDiputar = posisi;

    }

    public RandomAdapter(Context mContext, List<KontenModel> listGambar) {
        this.context = mContext;
        this.kontenModel = listGambar;
    }


    @Override
    public BindTampilan onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.random_designs, parent, false);

        return new RandomAdapter.BindTampilan(itemView);
    }

    @Override
    public void onBindViewHolder(final BindTampilan holder, final int position) {
        Activity activity = (Activity) context;
        final KelasInduk kelasInduk = (KelasInduk) activity.getApplication();
        String status = kelasInduk.getStatusIklan();
        if (status.equals(Constant.berhasilLoadIklan)) {
            if(position == 0){
                holder.penampunglist.setVisibility(View.GONE);
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
                int width = (int) dpWidth;

                final NativeExpressAdView adViewAtas = new NativeExpressAdView(context);
                adViewAtas.setAdSize(new AdSize(width - 10, 300));
                adViewAtas.setAdUnitId(new BicaraKeNative(context).getAdNativeMedium());
                AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
                adViewAtas.loadAd(adRequestBuilder.build());
                holder.rootRandom.addView(adViewAtas);

                adViewAtas.setAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int i) {
                        super.onAdFailedToLoad(i);
                        adViewAtas.setVisibility(View.GONE);

                    }
                });
            }else {
                final int posisi = position-1;
                holder.penampunglist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PemutarSuaraActivity pemutarSuaraActivity = (PemutarSuaraActivity) context;
                        pemutarSuaraActivity.putarMp3(posisi);
                    }
                });

                if (position == posisiSedangDiputar) {
                  //  holder.aturColor.setBackgroundColor(Color.parseColor("#bdc3c7"));
                    holder.equalizer.setImageResource(R.drawable.equalizer);
                } else {
                  //  holder.aturColor.setBackgroundColor(Color.parseColor("#F4F4F4"));
                    holder.equalizer.setImageDrawable(null);
                }

                holder.nUstad.setText(kontenModel.get(posisi).getnUstad());
                holder.jCeramah.setText(kontenModel.get(posisi).getjCeramah());
            }
        }else{
            holder.penampunglist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PemutarSuaraActivity pemutarSuaraActivity = (PemutarSuaraActivity) context;
                    pemutarSuaraActivity.putarMp3(position);
                }
            });

            if (position == posisiSedangDiputar) {
               // holder.aturColor.setBackgroundColor(Color.parseColor("#bdc3c7"));
                holder.equalizer.setImageResource(R.drawable.equalizer);
            } else {
              //  holder.aturColor.setBackgroundColor(Color.parseColor("#F4F4F4"));
                holder.equalizer.setImageDrawable(null);
            }
            holder.nUstad.setText(kontenModel.get(position).getnUstad());
            holder.jCeramah.setText(kontenModel.get(position).getjCeramah());
        }

    }

    @Override
    public int getItemCount() {
        Activity activity = (Activity) context;
        final KelasInduk kelasInduk = (KelasInduk) activity.getApplication();
        String status = kelasInduk.getStatusIklan();
        if (status.equals(Constant.berhasilLoadIklan)) {
            return kontenModel.size() + 1;
        }
        return kontenModel.size();
    }

    class BindTampilan extends RecyclerView.ViewHolder {
        private CardView penampunglist;
        private TextView nUstad;
        private TextView jCeramah;
        private ImageView equalizer;
        private RelativeLayout rootRandom;
        private LinearLayout aturColor;

        private BindTampilan(View view) {
            super(view);
            penampunglist = (CardView) view.findViewById(R.id.penampunglist);
            nUstad = (TextView) view.findViewById(R.id.nUstad);
            jCeramah = (TextView) view.findViewById(R.id.jCeramah);
            equalizer = (ImageView) view.findViewById(R.id.img_equalizer);
            rootRandom = (RelativeLayout) view.findViewById(R.id.rootRandom);
            aturColor  = (LinearLayout) view.findViewById(R.id.aturColor);
        }
    }
}
