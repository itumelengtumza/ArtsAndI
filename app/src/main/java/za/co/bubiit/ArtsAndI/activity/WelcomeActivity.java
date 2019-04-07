package za.co.bubiit.ArtsAndI.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import za.co.bubiit.ArtsAndI.R;
import za.co.bubiit.ArtsAndI.helper_util.DatabaseConnections;
import za.co.bubiit.ArtsAndI.helper_util.SQLiteHandler;
import za.co.bubiit.ArtsAndI.helper_util.ServerConnect;
import za.co.bubiit.ArtsAndI.helper_util.SessionManager;

import static java.lang.System.exit;

public class WelcomeActivity extends AppCompatActivity {
    private static final String EMAIL = "email";
    private static final String USER_DETAILS = "userDetails";
    private Button btnEnter;
    private Button btnLogout;
    private SQLiteHandler db;
    private DatabaseConnections dbConn;
    private SessionManager session;
    private TextView txtEmail;
    private TextView txtName;

    /* renamed from: za.co.bubiit.ArtsAndI.activity.WelcomeActivity$1 */
    class C02601 implements OnClickListener {
        C02601() {
        }

        public void onClick(View view) {
            WelcomeActivity.this.startActivity(new Intent(WelcomeActivity.this, TabsActivity.class));
            WelcomeActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.welcome);
        this.txtName = (TextView) findViewById(R.id.name);
        this.btnEnter = (Button) findViewById(R.id.enter);
        this.session = new SessionManager(getApplicationContext());
        if (!this.session.isLoggedIn()) {
            logoutUser();
        }
        this.db = new SQLiteHandler(getApplicationContext(), ServerConnect.user);
        this.dbConn = new DatabaseConnections(this);
        this.txtName.setText(getSharedPreferences(USER_DETAILS, 0).getString("email", "null"));
        this.btnEnter.setOnClickListener(new C02601());
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
            case android.R.id.home:
                finish();
                exit(0);
                return true;
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
        this.session.setLogin(false);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void onBackPressed() {
        finish();
    }
}
