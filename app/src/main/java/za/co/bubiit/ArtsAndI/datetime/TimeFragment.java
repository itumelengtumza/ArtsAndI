package za.co.bubiit.ArtsAndI.datetime;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import za.co.bubiit.ArtsAndI.R;

public class TimeFragment extends Fragment {
    private TimeChangedListener mCallback;
    private TimePicker mTimePicker;

    /* renamed from: za.co.bubiit.ArtsAndI.datetime.TimeFragment$1 */
    class C02671 implements OnTimeChangedListener {
        C02671() {
        }

        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            TimeFragment.this.mCallback.onTimeChanged(hourOfDay, minute);
        }
    }

    /* renamed from: za.co.bubiit.ArtsAndI.datetime.TimeFragment$2 */
    class C02682 implements OnValueChangeListener {
        C02682() {
        }

        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            if (picker.getValue() == 1) {
                if (TimeFragment.this.mTimePicker.getCurrentHour().intValue() < 12) {
                    TimeFragment.this.mTimePicker.setCurrentHour(Integer.valueOf(TimeFragment.this.mTimePicker.getCurrentHour().intValue() + 12));
                }
            } else if (TimeFragment.this.mTimePicker.getCurrentHour().intValue() >= 12) {
                TimeFragment.this.mTimePicker.setCurrentHour(Integer.valueOf(TimeFragment.this.mTimePicker.getCurrentHour().intValue() - 12));
            }
            TimeFragment.this.mCallback.onTimeChanged(TimeFragment.this.mTimePicker.getCurrentHour().intValue(), TimeFragment.this.mTimePicker.getCurrentMinute().intValue());
        }
    }

    public interface TimeChangedListener {
        void onTimeChanged(int i, int i2);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.mCallback = (TimeChangedListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement TimeFragment.TimeChangedListener interface");
        }
    }

    public static final TimeFragment newInstance(int theme, int hour, int minute, boolean isClientSpecified24HourTime, boolean is24HourTime) {
        TimeFragment f = new TimeFragment();
        Bundle b = new Bundle();
        b.putInt("theme", theme);
        b.putInt("hour", hour);
        b.putInt("minute", minute);
        b.putBoolean("isClientSpecified24HourTime", isClientSpecified24HourTime);
        b.putBoolean("is24HourTime", is24HourTime);
        f.setArguments(b);
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int theme = getArguments().getInt("theme");
        int initialHour = getArguments().getInt("hour");
        int initialMinute = getArguments().getInt("minute");
        boolean isClientSpecified24HourTime = getArguments().getBoolean("isClientSpecified24HourTime");
        boolean is24HourTime = getArguments().getBoolean("is24HourTime");
        View v = inflater.cloneInContext(new ContextThemeWrapper(getActivity(), theme == 1 ? 16973931 : 16973934)).inflate(R.layout.fragment_time, container, false);
        this.mTimePicker = (TimePicker) v.findViewById(R.id.timePicker);
        this.mTimePicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        this.mTimePicker.setOnTimeChangedListener(new C02671());
        if (isClientSpecified24HourTime) {
            this.mTimePicker.setIs24HourView(Boolean.valueOf(is24HourTime));
        } else {
            this.mTimePicker.setIs24HourView(Boolean.valueOf(DateFormat.is24HourFormat(getTargetFragment().getActivity())));
        }
        this.mTimePicker.setCurrentHour(Integer.valueOf(initialHour));
        this.mTimePicker.setCurrentMinute(Integer.valueOf(initialMinute));
        if (VERSION.SDK_INT >= 14 && VERSION.SDK_INT <= 15) {
            fixTimePickerBug18982();
        }
        return v;
    }

    private void fixTimePickerBug18982() {
        View amPmView = ((ViewGroup) this.mTimePicker.getChildAt(0)).getChildAt(3);
        if (amPmView instanceof NumberPicker) {
            ((NumberPicker) amPmView).setOnValueChangedListener(new C02682());
        }
    }
}
