package za.co.bubiit.ArtsAndI.datetime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import java.util.Date;
import za.co.bubiit.ArtsAndI.R;

public class DateFragment extends Fragment {
    private DateChangedListener mCallback;
    private CustomDatePicker mDatePicker;

    /* renamed from: za.co.bubiit.ArtsAndI.datetime.DateFragment$1 */
    class C02621 implements OnDateChangedListener {
        C02621() {
        }

        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            DateFragment.this.mCallback.onDateChanged(year, monthOfYear, dayOfMonth);
        }
    }

    public interface DateChangedListener {
        void onDateChanged(int i, int i2, int i3);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.mCallback = (DateChangedListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement DateFragment.DateChangedListener interface");
        }
    }

    public static final DateFragment newInstance(int theme, int year, int month, int day, Date minDate, Date maxDate) {
        DateFragment f = new DateFragment();
        Bundle b = new Bundle();
        b.putInt("theme", theme);
        b.putInt("year", year);
        b.putInt("month", month);
        b.putInt("day", day);
        b.putSerializable("minDate", minDate);
        b.putSerializable("maxDate", maxDate);
        f.setArguments(b);
        return f;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int theme = getArguments().getInt("theme");
        int initialYear = getArguments().getInt("year");
        int initialMonth = getArguments().getInt("month");
        int initialDay = getArguments().getInt("day");
        Date minDate = (Date) getArguments().getSerializable("minDate");
        Date maxDate = (Date) getArguments().getSerializable("maxDate");
        View v = inflater.cloneInContext(new ContextThemeWrapper(getActivity(), theme == 1 ? 16973931 : 16973934)).inflate(R.layout.fragment_date, container, false);
        this.mDatePicker = (CustomDatePicker) v.findViewById(R.id.datePicker);
        this.mDatePicker.setDescendantFocusability(393216);
        this.mDatePicker.init(initialYear, initialMonth, initialDay, new C02621());
        if (minDate != null) {
            this.mDatePicker.setMinDate(minDate.getTime());
        }
        if (maxDate != null) {
            this.mDatePicker.setMaxDate(maxDate.getTime());
        }
        return v;
    }
}
