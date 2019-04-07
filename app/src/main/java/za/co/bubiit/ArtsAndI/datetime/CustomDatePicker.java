package za.co.bubiit.ArtsAndI.datetime;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import java.lang.reflect.Field;
import za.co.bubiit.ArtsAndI.R;

public class CustomDatePicker extends DatePicker {
    private static final String TAG = "CustomDatePicker";

    public CustomDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        try {
            Class<?> idClass = Class.forName("com.android.internal.R$id");
            Field monthField = idClass.getField("month");
            Field dayField = idClass.getField("day");
            NumberPicker monthNumberPicker = (NumberPicker) findViewById(monthField.getInt(null));
            NumberPicker dayNumberPicker = (NumberPicker) findViewById(dayField.getInt(null));
            NumberPicker yearNumberPicker = (NumberPicker) findViewById(idClass.getField("year").getInt(null));
            Field selectionDividerField = Class.forName("android.widget.NumberPicker").getDeclaredField("mSelectionDivider");
            selectionDividerField.setAccessible(true);
            selectionDividerField.set(monthNumberPicker, getResources().getDrawable(R.drawable.customdatepicker_selectiondivider));
            selectionDividerField.set(dayNumberPicker, getResources().getDrawable(R.drawable.customdatepicker_selectiondivider));
            selectionDividerField.set(yearNumberPicker, getResources().getDrawable(R.drawable.customdatepicker_selectiondivider));
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "ClassNotFoundException in CustomDatePicker", e);
        } catch (NoSuchFieldException e2) {
            Log.e(TAG, "NoSuchFieldException in CustomDatePicker", e2);
        } catch (IllegalAccessException e3) {
            Log.e(TAG, "IllegalAccessException in CustomDatePicker", e3);
        } catch (IllegalArgumentException e4) {
            Log.e(TAG, "IllegalArgumentException in CustomDatePicker", e4);
        }
    }
}
