package za.co.bubiit.ArtsAndI.activity;

import android.content.Context;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import za.co.bubiit.ArtsAndI.R;
import za.co.bubiit.ArtsAndI.adater.MediaAdapter;
import za.co.bubiit.ArtsAndI.helper_util.DividerItemDecoration;
import za.co.bubiit.ArtsAndI.helper_util.MoreHolder;
import za.co.bubiit.ArtsAndI.helper_util.RecyclerTouchListener;
import za.co.bubiit.ArtsAndI.helper_util.RecyclerTouchListener.ClickListener;
import za.co.bubiit.ArtsAndI.helper_util.SQLiteHandler;
import za.co.bubiit.ArtsAndI.helper_util.ServerConnect;
import za.co.bubiit.ArtsAndI.helper_util.SessionManager;

public class MediaActivity extends AppCompatActivity {
    private SQLiteHandler db;
    private MediaAdapter aAdapter;
    private boolean isConnected;
    private List<String[]> mediaList = new ArrayList();
    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private Runnable run;
    private SessionManager sessionManager;

    /* renamed from: za.co.bubiit.ArtsAndI.activity.MediaActivity$1 */
    class C02451 implements Runnable {
        C02451() {
        }

        public void run() {
            MediaActivity.this.mediaList.clear();
            MediaActivity.this.mediaList.addAll(MediaActivity.this.db.getFromSQLiteDB(""));
            MediaActivity.this.aAdapter = new MediaAdapter(MediaActivity.this.mediaList);
            MediaActivity.this.recyclerView.invalidate();
            MediaActivity.this.recyclerView.refreshDrawableState();
            MediaActivity.this.recyclerView.setAdapter(MediaActivity.this.aAdapter);
            MediaActivity.this.aAdapter.notifyDataSetChanged();
        }
    }

    /* renamed from: za.co.bubiit.ArtsAndI.activity.MediaActivity$2 */
    class C03762 implements ClickListener {
        C03762() {
        }

        public void onClick(View view, int position) {
            new MoreHolder().setMedia((String[]) MediaActivity.this.mediaList.get(position));
            MediaActivity.this.startActivity(new Intent(MediaActivity.this, MediaMore.class));
        }

        public void onLongClick(View view, int position) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        boolean z;
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_media);
        this.db = new SQLiteHandler(this, ServerConnect.media);
        this.sessionManager = new SessionManager(this);
        NetworkInfo activeNetwork = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (activeNetwork == null || !activeNetwork.isConnectedOrConnecting()) {
            z = false;
        } else {
            z = true;
        }
        this.isConnected = z;
        if (!this.isConnected) {
            Toast.makeText(this, "OFFLINE!", Toast.LENGTH_SHORT).show();
        }
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.run = new C02451();
        runOnUiThread(this.run);
        this.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, this.recyclerView, new C03762()));
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                this.db = new SQLiteHandler(getApplicationContext(), ServerConnect.user);
                this.db.deleteSQLiteData();
                logoutUser();
                return true;
            case R.id.media:
                startActivity(new Intent(this, MediaActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logoutUser() {
        this.sessionManager.setLogin(false);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
