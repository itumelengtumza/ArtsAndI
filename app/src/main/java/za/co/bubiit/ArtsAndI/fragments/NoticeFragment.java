package za.co.bubiit.ArtsAndI.fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import za.co.bubiit.ArtsAndI.R;
import za.co.bubiit.ArtsAndI.adater.Notices0Adapter;
import za.co.bubiit.ArtsAndI.helper_util.DatabaseConnections;
import za.co.bubiit.ArtsAndI.helper_util.DividerItemDecoration;
import za.co.bubiit.ArtsAndI.helper_util.MoreHolder;
import za.co.bubiit.ArtsAndI.helper_util.RecyclerTouchListener;
import za.co.bubiit.ArtsAndI.helper_util.RecyclerTouchListener.ClickListener;
import za.co.bubiit.ArtsAndI.helper_util.SQLiteHandler;
import za.co.bubiit.ArtsAndI.helper_util.ServerConnect;
import za.co.bubiit.ArtsAndI.helper_util.SessionManager;

public class NoticeFragment extends Fragment {
    private static final String TAG = NoticeFragment.class.getSimpleName();
    private SQLiteHandler db;
    private DatabaseConnections dbConn;
    private boolean isConnected;
    private boolean isClicked;
    private Notices0Adapter nAdapter;
    private List<String[]> noticeList = new ArrayList();
    private Dialog dialog;
    private RecyclerView recyclerView;
    private Runnable run;
    private SessionManager sessionManager;
    private int selectedPosition = -1;

    /* renamed from: za.co.bubiit.ArtsAndI.fragments.NoticeFragment$1 */
    class C02721 implements Runnable {
        C02721() {
        }

        public void run() {
            NoticeFragment.this.noticeList.clear();
            NoticeFragment.this.noticeList.addAll(NoticeFragment.this.db.getFromSQLiteDB(""));
            NoticeFragment.this.nAdapter = new Notices0Adapter(NoticeFragment.this.noticeList, NoticeFragment.this.getContext(), NoticeFragment.this.selectedPosition);
            NoticeFragment.this.recyclerView.invalidate();
            NoticeFragment.this.recyclerView.refreshDrawableState();
            NoticeFragment.this.recyclerView.setAdapter(NoticeFragment.this.nAdapter);
            NoticeFragment.this.nAdapter.notifyDataSetChanged();
        }
    }

    /* renamed from: za.co.bubiit.ArtsAndI.fragments.NoticeFragment$2 */
    class C03792 implements ClickListener {
        C03792() {
        }

        public void onClick(View view, int position) {
            NoticeFragment.this.selectedPosition = position;
            NoticeFragment.this.showNoticeDetails();
        }

        public void onLongClick(View view, int position) {
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.db = new SQLiteHandler(getActivity(), ServerConnect.notice);
        this.sessionManager = new SessionManager(getActivity());
        Log.i(TAG, "INSIDE onCreate!!!");
        NetworkInfo activeNetwork = ((ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        boolean z = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        this.isConnected = z;
        if (!this.isConnected) {
            Toast.makeText(getActivity(), "OFFLINE!", Toast.LENGTH_SHORT).show();
        }
        this.dbConn = new DatabaseConnections(getActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "INSIDE onCreateView!!!");
        if (savedInstanceState != null) {
            this.selectedPosition = savedInstanceState.getInt("position", 0);
            this.isClicked = savedInstanceState.getBoolean("isClicked", false);
        }
        View view = inflater.inflate(R.layout.fragment_notices, container, false);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.dbConn.retrieveFromDB(ServerConnect.event, -1);
        this.dbConn.retrieveFromDB(ServerConnect.notice, -1);
        this.dbConn.retrieveFromDB(ServerConnect.annoucement, -1);
        this.dbConn.retrieveFromDB(ServerConnect.media, -1);
        this.run = new C02721();
        getActivity().runOnUiThread(this.run);
        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), this.recyclerView, new C03792()));
        if (this.isClicked) {
            showNoticeDetails();
        }
        if (this.selectedPosition > -1) {
            this.recyclerView.smoothScrollToPosition(this.selectedPosition);
        }
        return view;
    }

    public void showNoticeDetails() {
        new MoreHolder().setNotice(this.noticeList.get(this.selectedPosition));
        this.dialog = new Dialog(getContext(), R.style.full_screen_dialog);
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_notice_more, null);
        String[] noticeArray = MoreHolder.getNotice();
        TextView senderEmail = (TextView) view.findViewById(R.id.senderEmail);
        TextView organization = (TextView) view.findViewById(R.id.organization);
        TextView notice = (TextView) view.findViewById(R.id.notice);
        TextView deadlineDate = (TextView) view.findViewById(R.id.deadlineDate);
        TextView deadlineTime = (TextView) view.findViewById(R.id.deadlineTime);
        TextView division = (TextView) view.findViewById(R.id.division);
        TextView contact = (TextView) view.findViewById(R.id.contact);
        TextView dateSent = (TextView) view.findViewById(R.id.dateText);
        TextView timeSent = (TextView) view.findViewById(R.id.timeText);
        senderEmail.setText(noticeArray[0]);
        organization.setText(noticeArray[1]);
        notice.setText(noticeArray[2]);
        String[] deadlineArray = noticeArray[3].split(" ");
        deadlineDate.setText(deadlineArray[0] + " " + deadlineArray[1] + " '" + deadlineArray[2]);
        deadlineTime.setText(deadlineArray[3]);
        division.setText(noticeArray[4]);
        contact.setText(noticeArray[5]);
        String[] postdateArray = noticeArray[6].split(" ");
        dateSent.setText(postdateArray[0] + " " + postdateArray[1] + " '" + postdateArray[2]);
        timeSent.setText(postdateArray[3]);
        this.dialog.setContentView(view);
        this.dialog.show();
        Toolbar toolbar = (Toolbar) this.dialog.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Notices");
    }
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "INSIDE onSaveInstanceState!!!");
        outState.putInt("position", this.selectedPosition);
        if (this.dialog != null) {
            this.isClicked = this.dialog.isShowing();
        }
        outState.putBoolean("isClicked", this.isClicked);
    }

    public void onPause() {
        super.onPause();
        Log.i(TAG, "INSIDE onPause!!!");
        this.isClicked = false;
    }

    public void onStop() {
        super.onStop();
        Log.i(TAG, "INSIDE onStop!!!");
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
    }

    public void onResume() {
        super.onResume();
        Log.i(TAG, "INSIDE onResume!!!");
        if (this.isClicked) {
            this.dialog.show();
        }
    }

}
