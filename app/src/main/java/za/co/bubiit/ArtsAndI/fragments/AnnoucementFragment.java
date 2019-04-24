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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import za.co.bubiit.ArtsAndI.R;
import za.co.bubiit.ArtsAndI.adater.AnnoucementsAdapter;
import za.co.bubiit.ArtsAndI.helper_util.DatabaseConnections;
import za.co.bubiit.ArtsAndI.helper_util.MoreHolder;
import za.co.bubiit.ArtsAndI.helper_util.RecyclerTouchListener;
import za.co.bubiit.ArtsAndI.helper_util.RecyclerTouchListener.ClickListener;
import za.co.bubiit.ArtsAndI.helper_util.SQLiteHandler;
import za.co.bubiit.ArtsAndI.helper_util.ServerConnect;
import za.co.bubiit.ArtsAndI.helper_util.SessionManager;

public class AnnoucementFragment extends Fragment {
    private List<String[]> annoucementList = new ArrayList();
    private SQLiteHandler db;
    private DatabaseConnections dbConn;
    private AnnoucementsAdapter aAdapter;
    private boolean isConnected;
    private boolean isClicked;
    private Dialog dialog;
    private RecyclerView recyclerView;
    private Runnable run;
    private SessionManager sessionManager;
    private int selectedPosition = -1;

    /* renamed from: za.co.bubiit.ArtsAndI.fragments.AnnoucementFragment$1 */
    class C02691 implements Runnable {
        C02691() {
        }

        public void run() {
            AnnoucementFragment.this.annoucementList.clear();
            AnnoucementFragment.this.annoucementList.addAll(AnnoucementFragment.this.db.getFromSQLiteDB(""));
            AnnoucementFragment.this.aAdapter = new AnnoucementsAdapter(AnnoucementFragment.this.annoucementList, AnnoucementFragment.this.getContext(), AnnoucementFragment.this.selectedPosition);
            AnnoucementFragment.this.recyclerView.invalidate();
            AnnoucementFragment.this.recyclerView.refreshDrawableState();
            AnnoucementFragment.this.recyclerView.setAdapter(AnnoucementFragment.this.aAdapter);
            AnnoucementFragment.this.aAdapter.notifyDataSetChanged();
        }
    }

    class C02713 implements View.OnClickListener {
        C02713() {
        }

        public void onClick(View v) {
            AnnoucementFragment.this.dialog.dismiss();
        }
    }
    /* renamed from: za.co.bubiit.ArtsAndI.fragments.AnnoucementFragment$2 */
    class C03772 implements ClickListener {
        C03772() {
        }

        public void onClick(View view, int position) {
            AnnoucementFragment.this.selectedPosition = position;
            AnnoucementFragment.this.showAnnoucementsDetails();
        }
        public void onLongClick(View view, int position) {
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.db = new SQLiteHandler(getActivity(), ServerConnect.annoucement);
        this.sessionManager = new SessionManager(getActivity());
        NetworkInfo activeNetwork = ((ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        boolean z = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        this.isConnected = z;
        if (!this.isConnected) {
            Toast.makeText(getActivity(), "OFFLINE!", Toast.LENGTH_SHORT).show();
        }
        this.dbConn = new DatabaseConnections(getActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            this.selectedPosition = savedInstanceState.getInt("position", 0);
            this.isClicked = savedInstanceState.getBoolean("isClicked", false);
        }
        View view = inflater.inflate(R.layout.fragment_announcements, container, false);
        this.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());

        // check for internet and run

        /*this.dbConn.retrieveFromDB(ServerConnect.event, -1);
        this.dbConn.retrieveFromDB(ServerConnect.notice, -1);
        this.dbConn.retrieveFromDB(ServerConnect.annoucement, -1);
        this.dbConn.retrieveFromDB(ServerConnect.media, -1);*/
        this.run = new C02691();
        getActivity().runOnUiThread(this.run);
        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), this.recyclerView, new C03772()));
        if (this.isClicked) {
            showAnnoucementsDetails();
        }
        if (this.selectedPosition > -1) {
            this.recyclerView.smoothScrollToPosition(this.selectedPosition);
        }
        return view;
    }

    private void showAnnoucementsDetails() {
        new MoreHolder().setAnnoucement((String[]) this.annoucementList.get(this.selectedPosition));
        this.dialog = new Dialog(getContext(), R.style.full_screen_dialog);
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_annoucements_more, null);
        String[] annoucementArray = MoreHolder.getAnnoucement();
        TextView annoucementTittle = (TextView) view.findViewById(R.id.annoucementTittle);
        TextView postDate  = (TextView) view.findViewById(R.id.postDate);
        annoucementTittle.setText(annoucementArray[1]);
        String[] postDateArray = annoucementArray[3].split(" ");
        postDate.setText(postDateArray[0] + " " + postDateArray[1] + " '" + postDateArray[2]);
        this.dialog.setContentView(view);
        this.dialog.show();
        Toolbar toolbar = (Toolbar) this.dialog.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Annoucements");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new C02713());
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("position", this.selectedPosition);
        if (this.dialog != null) {
            this.isClicked = this.dialog.isShowing();
        }
        outState.putBoolean("isClicked", this.isClicked);
    }
    public void onDestroy() {
        super.onDestroy();
    }

    public void onPause() {
        super.onPause();
        this.isClicked = false;
    }


    public void onResume() {
        super.onResume();
        if (this.isClicked) {
            this.dialog.show();
        }
    }

    public void onStop() {
        super.onStop();
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
    }
}
