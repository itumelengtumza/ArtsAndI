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

public class Notices0Adapter extends Adapter<Notices0Adapter.MyViewHolder> {
    private List<String[]> noticesList;
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
                    Notices0Adapter.this.notifyItemChanged(Notices0Adapter.this.selectedPos);
                    Notices0Adapter.this.selectedPos = MyViewHolder.this.getLayoutPosition();
                    Notices0Adapter.this.notifyItemChanged(Notices0Adapter.this.selectedPos);
                }
            });
        }
    }

    public Notices0Adapter(List<String[]> noticesList, Context context, int selectedPos) {
        this.noticesList = noticesList;
        this.context = context;
        this.selectedPos = selectedPos;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notices_row, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        String[] noticeArray = (String[]) this.noticesList.get(position);
        holder.organization.setText(noticeArray[1]);
        holder.notice.setText(noticeArray[2]);
        String[] deadlineArray = noticeArray[3].split(" ");
        holder.deadlineDate.setText(deadlineArray[0] + " " + deadlineArray[1] + " '" + deadlineArray[2]);
    }

    public int getItemCount() {
        return this.noticesList.size();
    }
}
