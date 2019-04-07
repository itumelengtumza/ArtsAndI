package za.co.bubiit.ArtsAndI.adater;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.List;
import za.co.bubiit.ArtsAndI.R;
import za.co.bubiit.ArtsAndI.helper_util.AppController;

public class AnnoucementsAdapter extends Adapter<AnnoucementsAdapter.MyViewHolder> {
    private List<String[]> annoucementsList;
    private Context context;
    private int selectedPos;

    public class MyViewHolder extends ViewHolder {
        public TextView annoucementTittle;
        public TextView postDate;
        public NetworkImageView thumbNail;
        public LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            this.layout = (LinearLayout) view.findViewById(R.id.rowImgLayOut);
            this.thumbNail = (NetworkImageView) view.findViewById(R.id.thumbnail);
            this.annoucementTittle = (TextView) view.findViewById(R.id.annoucementTittle);
            this.postDate = (TextView) view.findViewById(R.id.postDate);
            view.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view) {
                    AnnoucementsAdapter.this.notifyItemChanged(AnnoucementsAdapter.this.selectedPos);
                    AnnoucementsAdapter.this.selectedPos = MyViewHolder.this.getLayoutPosition();
                    AnnoucementsAdapter.this.notifyItemChanged(AnnoucementsAdapter.this.selectedPos);
                }
            });
        }
    }

    public AnnoucementsAdapter(List<String[]> annoucementsList, Context context, int selectedPos) {
        this.annoucementsList = annoucementsList;
        this.context = context;
        this.selectedPos = selectedPos;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.annoucements_row, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        boolean isConnected;
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        String[] annoucementArray = this.annoucementsList.get(position);
        NetworkInfo activeNetwork = ((ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (activeNetwork == null || !activeNetwork.isConnectedOrConnecting()) {
            isConnected = false;
        } else {
            isConnected = true;
        }
        if (isConnected) {
            holder.layout.setVisibility(View.VISIBLE);
            holder.thumbNail.setImageUrl(annoucementArray[0], imageLoader);
        } else {
            holder.layout.setVisibility(View.GONE);
        }

        holder.annoucementTittle.setText(annoucementArray[1]);
        String[] postDateArray = annoucementArray[3].split(" ");
        holder.postDate.setText(postDateArray[0] + " " + postDateArray[1] + " '" + postDateArray[2]);
    }

    public int getItemCount() {
        return this.annoucementsList.size();
    }
}
