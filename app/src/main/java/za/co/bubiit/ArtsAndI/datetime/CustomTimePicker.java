package za.co.bubiit.ArtsAndI.datetime;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import java.lang.reflect.Field;
import za.co.bubiit.ArtsAndI.R;

public class CustomTimePicker extends TimePicker {
    private static final String TAG = "CustomTimePicker";

    public CustomTimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            Class<?> idClass = Class.forName("com.android.internal.R$id");
            Field hourField = idClass.getField("hour");
            Field minuteField = idClass.getField("minute");
            NumberPicker hourNumberPicker = (NumberPicker) findViewById(hourField.getInt(null));
            NumberPicker minuteNumberPicker = (NumberPicker) findViewById(minuteField.getInt(null));
            NumberPicker amPmNumberPicker = (NumberPicker) findViewById(idClass.getField("amPm").getInt(null));
            Field selectionDividerField = Class.forName("android.widget.NumberPicker").getDeclaredField("mSelectionDivider");
            selectionDividerField.setAccessible(true);
            selectionDividerField.set(hourNumberPicker, getResources().getDrawable(R.drawable.customdatepicker_selectiondivider));
            selectionDividerField.set(minuteNumberPicker, getResources().getDrawable(R.drawable.customdatepicker_selectiondivider));
            selectionDividerField.set(amPmNumberPicker, getResources().getDrawable(R.drawable.customdatepicker_selectiondivider));
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "ClassNotFoundException in CustomTimePicker", e);
        } catch (NoSuchFieldException e2) {
            Log.e(TAG, "NoSuchFieldException in CustomTimePicker", e2);
        } catch (IllegalAccessException e3) {
            Log.e(TAG, "IllegalAccessException in CustomTimePicker", e3);
        } catch (IllegalArgumentException e4) {
            Log.e(TAG, "IllegalArgumentException in CustomTimePicker", e4);
        }
    }
}
