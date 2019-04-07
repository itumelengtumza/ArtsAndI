package za.co.bubiit.ArtsAndI.helper_util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;

public class RoundRectCornerImageView extends NetworkImageView {

    final private float radius = 12.0f;
    private Path path;
    private RectF rect;
    final private float[] radii = {radius, radius, 0, 0, 0 ,0, radius, radius};//topLeft[x,y],topRight[x,y],bottomRight[x,y],bottomLeft[x,y]

    public RoundRectCornerImageView(Context context) {
        super(context);
        init();
    }

    public RoundRectCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundRectCornerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        path = new Path();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, radii, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}