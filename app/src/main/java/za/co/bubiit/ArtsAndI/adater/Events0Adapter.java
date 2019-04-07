package za.co.bubiit.ArtsAndI.adater;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.List;
import za.co.bubiit.ArtsAndI.R;
import za.co.bubiit.ArtsAndI.helper_util.AppController;

public class Events0Adapter extends Adapter<Events0Adapter.MyViewHolder> {
    private Context context;
    private List<String[]> eventsList;
    private int selectedPos;

    public class MyViewHolder extends ViewHolder {
        public TextView county;
        public TextView date;
        public TextView event;
        public LinearLayout layout;
        public NetworkImageView thumbNail;

        public MyViewHolder(View view) {
            super(view);
            this.layout = (LinearLayout) view.findViewById(R.id.rowImgLayOut);
            this.thumbNail = (NetworkImageView) view.findViewById(R.id.thumbnail);
            this.event = (TextView) view.findViewById(R.id.event);
            this.county = (TextView) view.findViewById(R.id.country);
            this.date = (TextView) view.findViewById(R.id.dateText);
            view.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view) {
                    Events0Adapter.this.notifyItemChanged(Events0Adapter.this.selectedPos);
                    Events0Adapter.this.selectedPos = MyViewHolder.this.getLayoutPosition();
                    Events0Adapter.this.notifyItemChanged(Events0Adapter.this.selectedPos);
                    Log.i((String)"onClick position: ", (String)("" + Events0Adapter.this.selectedPos));
                }
            });
        }
    }

    public Events0Adapter(List<String[]> eventsList, Context context, int selectedPos) {
        this.eventsList = eventsList;
        this.context = context;
        this.selectedPos = selectedPos;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.events_row, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        boolean isConnected;
        Log.i("onBindViewHo position: ", "" + position);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        String[] eventArray = (String[]) this.eventsList.get(position);
        NetworkInfo activeNetwork = ((ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (activeNetwork == null || !activeNetwork.isConnectedOrConnecting()) {
            isConnected = false;
        } else {
            isConnected = true;
        }
        if (isConnected) {
            holder.layout.setVisibility(View.VISIBLE);
            holder.thumbNail.setImageUrl(eventArray[1], imageLoader);
        } else {
            holder.layout.setVisibility(View.GONE);
        }
        holder.event.setText(eventArray[2]);
        holder.county.setText(eventArray[6]);
        String[] startdateArray = eventArray[3].split(" ");
        String[] enddateArray = eventArray[4].split(" ");
        if (!startdateArray[0].equals(enddateArray[0]) && startdateArray[1].equals(enddateArray[1]) && startdateArray[2].equals(enddateArray[2])) {
            holder.date.setText(startdateArray[0] + "-" + enddateArray[0] + " " + startdateArray[1] + " '" + startdateArray[2]);
        } else if (startdateArray[0].equals(enddateArray[0]) && startdateArray[1].equals(enddateArray[1]) && startdateArray[2].equals(enddateArray[2])) {
            holder.date.setText(startdateArray[0] + " " + startdateArray[1] + " '" + startdateArray[2]);
        } else {
            holder.date.setText(startdateArray[0] + " " + startdateArray[1] + " '" + startdateArray[2] + " - " + enddateArray[0] + " " + enddateArray[1] + " '" + enddateArray[2]);
        }
        /*if (this.selectedPos == position) {
            holder.itemView.setBackgroundResource(R.drawable.listbgpres);
        } else {
            holder.itemView.setBackgroundResource(R.drawable.listbgnorm);
        }*/
    }

    public int getItemCount() {
        return this.eventsList.size();
    }
}
