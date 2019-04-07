package za.co.bubiit.ArtsAndI.helper_util;

import android.os.Environment;

public class CheckForSDCard {
    public boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return true;
        }
        return false;
    }
}
