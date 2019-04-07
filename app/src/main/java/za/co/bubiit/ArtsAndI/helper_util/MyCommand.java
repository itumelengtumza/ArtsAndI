package za.co.bubiit.ArtsAndI.helper_util;

import android.content.Context;
import com.android.volley.Request;
import java.util.ArrayList;
import java.util.Iterator;

public class MyCommand<T> {
    private Context context;
    private ArrayList<Request<T>> requestList = new ArrayList();

    public MyCommand(Context context) {
        this.context = context;
    }

    public void add(Request<T> request) {
        this.requestList.add(request);
    }

    public void remove(Request<T> request) {
        this.requestList.remove(request);
    }

    public void execute() {
        Iterator it = this.requestList.iterator();
        while (it.hasNext()) {
            MySingleton.getInstance(this.context).addToRequestQueue((Request) it.next());
        }
    }
}
