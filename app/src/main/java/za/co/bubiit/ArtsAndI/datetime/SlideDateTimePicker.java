package za.co.bubiit.ArtsAndI.datetime;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import java.util.Date;

public class SlideDateTimePicker {
    public static final int HOLO_DARK = 1;
    public static final int HOLO_LIGHT = 2;
    private FragmentManager mFragmentManager;
    private int mIndicatorColor;
    private Date mInitialDate;
    private boolean mIs24HourTime;
    private boolean mIsClientSpecified24HourTime;
    private SlideDateTimeListener mListener;
    private Date mMaxDate;
    private Date mMinDate;
    private int mTheme;

    public static class Builder {
        private FragmentManager fm;
        private int indicatorColor;
        private Date initialDate;
        private boolean is24HourTime;
        private boolean isClientSpecified24HourTime;
        private SlideDateTimeListener listener;
        private Date maxDate;
        private Date minDate;
        private int theme;

        public Builder(FragmentManager fm) {
            this.fm = fm;
        }

        public Builder setListener(SlideDateTimeListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setInitialDate(Date initialDate) {
            this.initialDate = initialDate;
            return this;
        }

        public Builder setMinDate(Date minDate) {
            this.minDate = minDate;
            return this;
        }

        public Builder setMaxDate(Date maxDate) {
            this.maxDate = maxDate;
            return this;
        }

        public Builder setIs24HourTime(boolean is24HourTime) {
            this.isClientSpecified24HourTime = true;
            this.is24HourTime = is24HourTime;
            return this;
        }

        public Builder setTheme(int theme) {
            this.theme = theme;
            return this;
        }

        public Builder setIndicatorColor(int indicatorColor) {
            this.indicatorColor = indicatorColor;
            return this;
        }

        public SlideDateTimePicker build() {
            SlideDateTimePicker picker = new SlideDateTimePicker(this.fm);
            picker.setListener(this.listener);
            picker.setInitialDate(this.initialDate);
            picker.setMinDate(this.minDate);
            picker.setMaxDate(this.maxDate);
            picker.setIsClientSpecified24HourTime(this.isClientSpecified24HourTime);
            picker.setIs24HourTime(this.is24HourTime);
            picker.setTheme(this.theme);
            picker.setIndicatorColor(this.indicatorColor);
            return picker;
        }
    }

    public SlideDateTimePicker(FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag(SlideDateTimeDialogFragment.TAG_SLIDE_DATE_TIME_DIALOG_FRAGMENT);
        if (prev != null) {
            ft.remove(prev);
            ft.commit();
        }
        this.mFragmentManager = fm;
    }

    public void setListener(SlideDateTimeListener listener) {
        this.mListener = listener;
    }

    public void setInitialDate(Date initialDate) {
        this.mInitialDate = initialDate;
    }

    public void setMinDate(Date minDate) {
        this.mMinDate = minDate;
    }

    public void setMaxDate(Date maxDate) {
        this.mMaxDate = maxDate;
    }

    private void setIsClientSpecified24HourTime(boolean isClientSpecified24HourTime) {
        this.mIsClientSpecified24HourTime = isClientSpecified24HourTime;
    }

    public void setIs24HourTime(boolean is24HourTime) {
        setIsClientSpecified24HourTime(true);
        this.mIs24HourTime = is24HourTime;
    }

    public void setTheme(int theme) {
        this.mTheme = theme;
    }

    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
    }

    public void show() {
        if (this.mListener == null) {
            throw new NullPointerException("Attempting to bind null listener to SlideDateTimePicker");
        }
        if (this.mInitialDate == null) {
            setInitialDate(new Date());
        }
        SlideDateTimeDialogFragment.newInstance(this.mListener, this.mInitialDate, this.mMinDate, this.mMaxDate, this.mIsClientSpecified24HourTime, this.mIs24HourTime, this.mTheme, this.mIndicatorColor).show(this.mFragmentManager, SlideDateTimeDialogFragment.TAG_SLIDE_DATE_TIME_DIALOG_FRAGMENT);
    }
}
