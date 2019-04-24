package za.co.bubiit.ArtsAndI.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import za.co.bubiit.ArtsAndI.R;

public class Tourism0Adapter extends Adapter<Tourism0Adapter.MyViewHolder> {
    private List<String[]> tourismList;
    private int selectedPos;
    private Context context;

    public class MyViewHolder extends ViewHolder {
        public TextView deadlineDate;
        public TextView notice;
        public TextView organization;

        public MyViewHolder(View view) {
            super(view);
            this.organization = (TextView) view.findViewById(R.id.organization);
            this.notice = (TextView) view.findViewById(R.id.notice);
            this.deadlineDate = (TextView) view.findViewById(R.id.deadlineDate);
            view.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view) {
                    Tourism0Adapter.this.notifyItemChanged(Tourism0Adapter.this.selectedPos);
                    Tourism0Adapter.this.selectedPos = MyViewHolder.this.getLayoutPosition();
                    Tourism0Adapter.this.notifyItemChanged(Tourism0Adapter.this.selectedPos);
                }
            });
        }
    }

    public Tourism0Adapter(List<String[]> tourismList, Context context, int selectedPos) {
        this.tourismList = tourismList;
        this.context = context;
        this.selectedPos = selectedPos;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tourism_row, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        String[] tourismArray = (String[]) this.tourismList.get(position);
        holder.organization.setText(tourismArray[1]);
        holder.notice.setText(tourismArray[2]);
        holder.deadlineDate.setText(tourismArray[3]);
        //String[] deadlineArray = tourismArray[3].split(" ");
        //holder.deadlineDate.setText(deadlineArray[0] + " " + deadlineArray[1] + " '" + deadlineArray[2]);
    }

    public int getItemCount() {
        return this.tourismList.size();
    }
}
