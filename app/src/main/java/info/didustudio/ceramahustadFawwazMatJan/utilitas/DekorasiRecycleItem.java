package info.didustudio.ceramahustadFawwazMatJan.utilitas;


import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

public class DekorasiRecycleItem extends RecyclerView.ItemDecoration {
    private int jarak;
    private Context context;

    public DekorasiRecycleItem(int jarak, Context context) {
        this.jarak = jarak;
        this.context = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = dpToPx(jarak);

        if(parent.getChildAdapterPosition(view) == 0)
            outRect.top = 10;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
