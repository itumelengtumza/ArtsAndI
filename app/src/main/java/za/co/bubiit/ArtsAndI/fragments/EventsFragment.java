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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import za.co.bubiit.ArtsAndI.R;
import za.co.bubiit.ArtsAndI.adater.Events0Adapter;
import za.co.bubiit.ArtsAndI.helper_util.DatabaseConnections;
import za.co.bubiit.ArtsAndI.helper_util.MoreHolder;
import za.co.bubiit.ArtsAndI.helper_util.RecyclerTouchListener;
import za.co.bubiit.ArtsAndI.helper_util.RecyclerTouchListener.ClickListener;
import za.co.bubiit.ArtsAndI.helper_util.SQLiteHandler;
import za.co.bubiit.ArtsAndI.helper_util.ServerConnect;
import za.co.bubiit.ArtsAndI.helper_util.SessionManager;

public class EventsFragment extends Fragment {
    private static final String TAG = EventsFragment.class.getSimpleName();
    private SQLiteHandler db;
    private DatabaseConnections dbConn;
    private Dialog dialog;
    private Events0Adapter aAdapter;
    private List<String[]> eventList = new ArrayList();
    private boolean isClicked;
    private boolean isConnected;
    private RecyclerView recyclerView;
    private Runnable run;
    private int selectedPosition = -1;
    private SessionManager sessionManager;

    /* renamed from: za.co.bubiit.ArtsAndI.fragments.EventsFragment$1 */
    class C02701 implements Runnable {
        C02701() {
        }

        public void run() {
            EventsFragment.this.eventList.clear();
            EventsFragment.this.eventList.addAll(EventsFragment.this.db.getFromSQLiteDB(""));
            EventsFragment.this.aAdapter = new Events0Adapter(EventsFragment.this.eventList, EventsFragment.this.getContext(), EventsFragment.this.selectedPosition);
            EventsFragment.this.recyclerView.invalidate();
            EventsFragment.this.recyclerView.refreshDrawableState();
            EventsFragment.this.recyclerView.setAdapter(EventsFragment.this.aAdapter);
            EventsFragment.this.aAdapter.notifyDataSetChanged();
        }
    }

    /* renamed from: za.co.bubiit.ArtsAndI.fragments.EventsFragment$3 */
    class C02713 implements OnClickListener {
        C02713() {
        }

        public void onClick(View v) {
            EventsFragment.this.dialog.dismiss();
        }
    }

    /* renamed from: za.co.bubiit.ArtsAndI.fragments.EventsFragment$2 */
    class C03782 implements ClickListener {
        C03782() {
        }

        public void onClick(View view, int position) {
            EventsFragment.this.selectedPosition = position;
            EventsFragment.this.showEventDetails();
        }

        public void onLongClick(View view, int position) {
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.db = new SQLiteHandler(getActivity(), ServerConnect.event);
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
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.dbConn.retrieveFromDB(ServerConnect.event, -1);
        this.dbConn.retrieveFromDB(ServerConnect.notice, -1);
        this.dbConn.retrieveFromDB(ServerConnect.annoucement, -1);
        this.dbConn.retrieveFromDB(ServerConnect.media, -1);
        this.run = new C02701();
        getActivity().runOnUiThread(this.run);
        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), this.recyclerView, new C03782()));
        if (this.isClicked) {
            showEventDetails();
        }
        if (this.selectedPosition > -1) {
            this.recyclerView.smoothScrollToPosition(this.selectedPosition);
        }
        return view;
    }

    public void showEventDetails() {
        new MoreHolder().setEvent((String[]) this.eventList.get(this.selectedPosition));
        this.dialog = new Dialog(getContext(), R.style.full_screen_dialog);
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_event_more, null);
        String[] eventArray = MoreHolder.getEvent();
        TextView event = (TextView) view.findViewById(R.id.event);
        TextView date = (TextView) view.findViewById(R.id.dateText);
        TextView time = (TextView) view.findViewById(R.id.timeText);
        TextView city = (TextView) view.findViewById(R.id.city);
        TextView country = (TextView) view.findViewById(R.id.country);
        TextView venue = (TextView) view.findViewById(R.id.venue);
        TextView details = (TextView) view.findViewById(R.id.details);
        TextView contact = (TextView) view.findViewById(R.id.contact);
        TextView postDate = (TextView) view.findViewById(R.id.postDate);
        TextView postTime = (TextView) view.findViewById(R.id.postTime);
        TextView officialName = (TextView) view.findViewById(R.id.contactOfficial);
        /*NetworkImageView thumbNail = (NetworkImageView) view.findViewById(R.id.thumbnail);
        //((TextView) view.findViewById(R.id.senderEmail)).setText(eventArray[0]);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        if (this.isConnected) {
            thumbNail.setVisibility(View.VISIBLE);
            thumbNail.setImageUrl(eventArray[1], imageLoader);
        } else {
            thumbNail.setVisibility(View.GONE);
        }
        */
        event.setText(eventArray[2]);
        String[] startdateArray = eventArray[3].split(" ");
        String[] enddateArray = eventArray[4].split(" ");
        if (!startdateArray[0].equals(enddateArray[0]) && startdateArray[1].equals(enddateArray[1]) && startdateArray[2].equals(enddateArray[2])) {
            date.setText(startdateArray[0] + "-" + enddateArray[0] + " " + startdateArray[1] + " '" + startdateArray[2]);
        } else if (startdateArray[0].equals(enddateArray[0]) && startdateArray[1].equals(enddateArray[1]) && startdateArray[2].equals(enddateArray[2])) {
            date.setText(startdateArray[0] + " " + startdateArray[1] + " '" + startdateArray[2]);
        } else {
            date.setText(startdateArray[0] + " " + startdateArray[1] + " '" + startdateArray[2] + " - " + enddateArray[0] + " " + enddateArray[1] + " '" + enddateArray[2]);
        }
        time.setText(startdateArray[3] + " - " + enddateArray[3]);
        city.setText(eventArray[5]);
        country.setText(eventArray[6]);
        venue.setText(eventArray[7]);
        details.setText(eventArray[8]);
        contact.setText(eventArray[9]);
        String[] postdateArray = eventArray[10].split(" ");
        postDate.setText(postdateArray[0] + " " + postdateArray[1] + " '" + postdateArray[2]);
        postTime.setText(postdateArray[3]);
        officialName.setText(eventArray[11]);
        this.dialog.setContentView(view);
        this.dialog.show();
        Toolbar toolbar = (Toolbar) this.dialog.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Events");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new C02713());
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
