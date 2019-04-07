package za.co.bubiit.ArtsAndI.adater;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import java.util.List;
import za.co.bubiit.ArtsAndI.R;
import za.co.bubiit.ArtsAndI.helper_util.AppController;

public class MediaAdapter extends Adapter<MediaAdapter.MyViewHolder> {
    private List<String[]> mediaList;

    public class MyViewHolder extends ViewHolder {
        public TextView mediaName;
        public TextView mediaType;
        public NetworkImageView thumbNail;

        public MyViewHolder(View view) {
            super(view);
            this.mediaName = (TextView) view.findViewById(R.id.mediaName);
            this.mediaType = (TextView) view.findViewById(R.id.mediaType);
            this.thumbNail = (NetworkImageView) view.findViewById(R.id.thumbnail);
        }
    }

    public MediaAdapter(List<String[]> mediaList) {
        this.mediaList = mediaList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.media_row, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        String[] mediaArray = (String[]) this.mediaList.get(position);
        imageLoader.get(mediaArray[1], ImageLoader.getImageListener(holder.thumbNail, R.drawable.no_image, R.drawable.no_image));
        holder.thumbNail.setImageUrl(mediaArray[1], imageLoader);
        holder.mediaName.setText(mediaArray[2]);
        holder.mediaType.setText(mediaArray[4]);
    }

    public int getItemCount() {
        return this.mediaList.size();
    }
}
