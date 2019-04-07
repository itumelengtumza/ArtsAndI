package za.co.bubiit.ArtsAndI.datetime;

import java.util.Date;

public abstract class SlideDateTimeListener {
    public abstract void onDateTimeSet(Date date);

    public void onDateTimeCancel() {
    }
}
