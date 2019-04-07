package za.co.bubiit.ArtsAndI.datetime;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.widget.DatePicker;
import android.widget.TimePicker;
import za.co.bubiit.ArtsAndI.R;

public class CustomViewPager extends ViewPager {
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;
    private float mTouchSlop;
    private float x1;
    private float x2;
    private float y1;
    private float y2;

    public CustomViewPager(Context context) {
        super(context);
        init(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mTouchSlop = (float) ViewConfiguration.get(context).getScaledPagingTouchSlop();
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, 0));
            int h = child.getMeasuredHeight();
            if (h > height) {
                height = h;
            }
        }
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, 1073741824));
        this.mDatePicker = (DatePicker) findViewById(R.id.datePicker);
        this.mTimePicker = (TimePicker) findViewById(R.id.timePicker);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.x1 = event.getX();
                this.y1 = event.getY();
                break;
            case 2:
                this.x2 = event.getX();
                this.y2 = event.getY();
                if (isScrollingHorizontal(this.x1, this.y1, this.x2, this.y2)) {
                    return super.dispatchTouchEvent(event);
                }
                break;
        }
        switch (getCurrentItem()) {
            case 0:
                if (this.mDatePicker != null) {
                    this.mDatePicker.dispatchTouchEvent(event);
                    break;
                }
                break;
            case 1:
                if (this.mTimePicker != null) {
                    this.mTimePicker.dispatchTouchEvent(event);
                    break;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean isScrollingHorizontal(float x1, float y1, float x2, float y2) {
        float deltaX = x2 - x1;
        float deltaY = y2 - y1;
        if (Math.abs(deltaX) <= this.mTouchSlop || Math.abs(deltaX) <= Math.abs(deltaY)) {
            return false;
        }
        return true;
    }
}
