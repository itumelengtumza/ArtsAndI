package za.co.bubiit.ArtsAndI.datetime;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import za.co.bubiit.ArtsAndI.R;
import za.co.bubiit.ArtsAndI.datetime.DateFragment.DateChangedListener;
import za.co.bubiit.ArtsAndI.datetime.TimeFragment.TimeChangedListener;

public class SlideDateTimeDialogFragment extends DialogFragment implements DateChangedListener, TimeChangedListener {
    public static final String TAG_SLIDE_DATE_TIME_DIALOG_FRAGMENT = "tagSlideDateTimeDialogFragment";
    private static SlideDateTimeListener mListener;
    private View mButtonHorizontalDivider;
    private View mButtonVerticalDivider;
    private Calendar mCalendar;
    private Button mCancelButton;
    private Context mContext;
    private int mDateFlags = 524306;
    private int mIndicatorColor;
    private Date mInitialDate;
    private boolean mIs24HourTime;
    private boolean mIsClientSpecified24HourTime;
    private Date mMaxDate;
    private Date mMinDate;
    private Button mOkButton;
    private SlidingTabLayout mSlidingTabLayout;
    private int mTheme;
    private CustomViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    /* renamed from: za.co.bubiit.ArtsAndI.datetime.SlideDateTimeDialogFragment$1 */
    class C02631 implements OnClickListener {
        C02631() {
        }

        public void onClick(View v) {
            if (SlideDateTimeDialogFragment.mListener == null) {
                throw new NullPointerException("Listener no longer exists for mOkButton");
            }
            SlideDateTimeDialogFragment.mListener.onDateTimeSet(new Date(SlideDateTimeDialogFragment.this.mCalendar.getTimeInMillis()));
            SlideDateTimeDialogFragment.this.dismiss();
        }
    }

    /* renamed from: za.co.bubiit.ArtsAndI.datetime.SlideDateTimeDialogFragment$2 */
    class C02642 implements OnClickListener {
        C02642() {
        }

        public void onClick(View v) {
            if (SlideDateTimeDialogFragment.mListener == null) {
                throw new NullPointerException("Listener no longer exists for mCancelButton");
            }
            SlideDateTimeDialogFragment.mListener.onDateTimeCancel();
            SlideDateTimeDialogFragment.this.dismiss();
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Fragment dateFragment = DateFragment.newInstance(SlideDateTimeDialogFragment.this.mTheme, SlideDateTimeDialogFragment.this.mCalendar.get(1), SlideDateTimeDialogFragment.this.mCalendar.get(2), SlideDateTimeDialogFragment.this.mCalendar.get(5), SlideDateTimeDialogFragment.this.mMinDate, SlideDateTimeDialogFragment.this.mMaxDate);
                    dateFragment.setTargetFragment(SlideDateTimeDialogFragment.this, 100);
                    return dateFragment;
                case 1:
                    Fragment timeFragment = TimeFragment.newInstance(SlideDateTimeDialogFragment.this.mTheme, SlideDateTimeDialogFragment.this.mCalendar.get(11), SlideDateTimeDialogFragment.this.mCalendar.get(12), SlideDateTimeDialogFragment.this.mIsClientSpecified24HourTime, SlideDateTimeDialogFragment.this.mIs24HourTime);
                    timeFragment.setTargetFragment(SlideDateTimeDialogFragment.this, 200);
                    return timeFragment;
                default:
                    return null;
            }
        }

        public int getCount() {
            return 2;
        }
    }

    public static SlideDateTimeDialogFragment newInstance(SlideDateTimeListener listener, Date initialDate, Date minDate, Date maxDate, boolean isClientSpecified24HourTime, boolean is24HourTime, int theme, int indicatorColor) {
        mListener = listener;
        SlideDateTimeDialogFragment dialogFragment = new SlideDateTimeDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("initialDate", initialDate);
        bundle.putSerializable("minDate", minDate);
        bundle.putSerializable("maxDate", maxDate);
        bundle.putBoolean("isClientSpecified24HourTime", isClientSpecified24HourTime);
        bundle.putBoolean("is24HourTime", is24HourTime);
        bundle.putInt("theme", theme);
        bundle.putInt("indicatorColor", indicatorColor);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        unpackBundle();
        this.mCalendar = Calendar.getInstance();
        this.mCalendar.setTime(this.mInitialDate);
        switch (this.mTheme) {
            case 1:
                setStyle(1, 16973937);
                return;
            case 2:
                setStyle(1, 16973941);
                return;
            default:
                setStyle(1, 16973941);
                return;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.slide_date_time_picker, container);
        setupViews(view);
        customizeViews();
        initViewPager();
        initTabs();
        initButtons();
        return view;
    }

    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    private void unpackBundle() {
        Bundle args = getArguments();
        this.mInitialDate = (Date) args.getSerializable("initialDate");
        this.mMinDate = (Date) args.getSerializable("minDate");
        this.mMaxDate = (Date) args.getSerializable("maxDate");
        this.mIsClientSpecified24HourTime = args.getBoolean("isClientSpecified24HourTime");
        this.mIs24HourTime = args.getBoolean("is24HourTime");
        this.mTheme = args.getInt("theme");
        this.mIndicatorColor = args.getInt("indicatorColor");
    }

    private void setupViews(View v) {
        this.mViewPager = (CustomViewPager) v.findViewById(R.id.viewPager);
        this.mSlidingTabLayout = (SlidingTabLayout) v.findViewById(R.id.slidingTabLayout);
        this.mButtonHorizontalDivider = v.findViewById(R.id.buttonHorizontalDivider);
        this.mButtonVerticalDivider = v.findViewById(R.id.buttonVerticalDivider);
        this.mOkButton = (Button) v.findViewById(R.id.okButton);
        this.mCancelButton = (Button) v.findViewById(R.id.cancelButton);
    }

    private void customizeViews() {
        int lineColor;
        if (this.mTheme == 1) {
            lineColor = getResources().getColor(R.color.gray_holo_dark);
        } else {
            lineColor = getResources().getColor(R.color.gray_holo_light);
        }
        switch (this.mTheme) {
            case 1:
            case 2:
                this.mButtonHorizontalDivider.setBackgroundColor(lineColor);
                this.mButtonVerticalDivider.setBackgroundColor(lineColor);
                break;
            default:
                this.mButtonHorizontalDivider.setBackgroundColor(getResources().getColor(R.color.gray_holo_light));
                this.mButtonVerticalDivider.setBackgroundColor(getResources().getColor(R.color.gray_holo_light));
                break;
        }
        if (this.mIndicatorColor != 0) {
            this.mSlidingTabLayout.setSelectedIndicatorColors(this.mIndicatorColor);
        }
    }

    private void initViewPager() {
        this.mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        this.mViewPager.setAdapter(this.mViewPagerAdapter);
        this.mSlidingTabLayout.setCustomTabView(R.layout.slide_datetime_dialogfragment, R.id.tabText);
        this.mSlidingTabLayout.setViewPager(this.mViewPager);
    }

    private void initTabs() {
        updateDateTab();
        updateTimeTab();
    }

    private void initButtons() {
        this.mOkButton.setOnClickListener(new C02631());
        this.mCancelButton.setOnClickListener(new C02642());
    }

    public void onDateChanged(int year, int month, int day) {
        this.mCalendar.set(year, month, day);
        updateDateTab();
    }

    public void onTimeChanged(int hour, int minute) {
        this.mCalendar.set(11, hour);
        this.mCalendar.set(12, minute);
        updateTimeTab();
    }

    private void updateDateTab() {
        this.mSlidingTabLayout.setTabText(0, DateUtils.formatDateTime(this.mContext, this.mCalendar.getTimeInMillis(), this.mDateFlags));
    }

    @SuppressLint({"SimpleDateFormat"})
    private void updateTimeTab() {
        if (!this.mIsClientSpecified24HourTime) {
            this.mSlidingTabLayout.setTabText(1, DateFormat.getTimeFormat(this.mContext).format(Long.valueOf(this.mCalendar.getTimeInMillis())));
        } else if (this.mIs24HourTime) {
            this.mSlidingTabLayout.setTabText(1, new SimpleDateFormat("HH:mm").format(this.mCalendar.getTime()));
        } else {
            this.mSlidingTabLayout.setTabText(1, new SimpleDateFormat("h:mm aa").format(this.mCalendar.getTime()));
        }
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mListener == null) {
            throw new NullPointerException("Listener no longer exists in onCancel()");
        }
        mListener.onDateTimeCancel();
    }
}
