package info.didustudio.ceramahustadFawwazMatJan.utilitas;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class KotakRelative extends RelativeLayout {

    public KotakRelative(Context context) {
        super(context);
    }

    public KotakRelative(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KotakRelative(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KotakRelative(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}
